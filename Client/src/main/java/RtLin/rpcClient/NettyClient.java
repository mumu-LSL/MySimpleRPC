package RtLin.rpcClient;

import RtLin.RPC_Response.RpcResponse;
import RtLin.codec.CommonEncoder;
import RtLin.codec.CommonDecoder;
import RtLin.handler.NettyClientHandler;
import RtLin.rpc_request.RpcRequest;
import RtLin.serializer.JsonSerializer;
import RtLin.serializer.KryoSerializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lin
 * @Date 2022/5/13 - 22:18
 */
public class NettyClient implements RpcClient{
    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);
    private String host;
    private int port;
    private static final Bootstrap bootstrap;

    public NettyClient(String host,int port){
        this.host=host;
        this.port=port;
    }
    static {
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new CommonDecoder())
                                .addLast(new CommonEncoder(new KryoSerializer()))
                                .addLast(new NettyClientHandler());
                    }
                });
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        try {
            ChannelFuture future = bootstrap.connect(host,port).sync();
            logger.info("客户端连接到服务器{}:{}",host,port);
            Channel channel = future.channel();
            if(channel!=null){
                channel.writeAndFlush(rpcRequest).addListener(future1 -> {
                    if(future1.isSuccess()){
                        logger.info(String.format("客户端发送消息: %s", rpcRequest.toString()));
                    }else{
                        logger.error("发送消息时有错误发生：",future1.cause());
                    }
                });
                channel.closeFuture().sync();
                AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse");
                RpcResponse rpcResponse=channel.attr(key).get();
                return rpcResponse;
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }
}
