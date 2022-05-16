package RtLin.rpcServer;
import RtLin.codec.CommonDecoder;
import RtLin.codec.CommonEncoder;
import RtLin.rpcChannelHandler.NettyServerHandler;
import RtLin.serializer.KryoSerializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lin
 * @Date 2022/5/15 - 11:43
 */
//Netty 服务器的流程重点就在于本类和HandlerPipeLine上的那些处理器
public class NettyServer implements RpcServer {
    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);
    public void start(int port){
        EventLoopGroup  bossGroup = new NioEventLoopGroup();
        EventLoopGroup  workerGroup =new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    //在linux系统内核中维护了两个队列：syns queue和accept queue
                    //syns queue：保存一个SYN已经到达，但三次握手还没有完成的连接。
                    //accept queue：保存三次握手已完成，内核正等待进程执行accept的调用的连接。
                    //backlog 参数对应了listen函数的backlog参数，用来初始化服务端可连接的队列，backlog设置为上述两者的和
                    .option(ChannelOption.SO_BACKLOG,128)
                    .option(ChannelOption.SO_KEEPALIVE,true)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<SocketChannel>(){
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new CommonDecoder());
                            socketChannel.pipeline().addLast(new CommonEncoder(new KryoSerializer()));
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            ChannelFuture future = bootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        }catch (InterruptedException e) {
            logger.error("启动服务器时有错误发生: ", e);
        }
        finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
