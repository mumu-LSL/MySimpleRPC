package RtLin.rpcChannelHandler;

import RtLin.RPC_Response.RpcResponse;
import RtLin.registry.DefaultServiceRegistry;
import RtLin.registry.ServiceRegistry;
import RtLin.requestHandler.RequestHandler;
import RtLin.rpc_request.RpcRequest;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lin
 * @Date 2022/5/15 - 16:29
 */
@Data
@NoArgsConstructor
public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {
    private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
    private static RequestHandler requestHandler;
    private static ServiceRegistry serviceRegistry;
    static {
        requestHandler = new RequestHandler();
        serviceRegistry = new DefaultServiceRegistry();
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest req) throws Exception {
        try {
            logger.info("服务器收到请求: {}",req);
            //获取服务名
            String interfaceName = req.getInterfaceName();
            //获取服务实例对象
            Object service = serviceRegistry.getService(interfaceName);
            //调用服务，返回结果
            Object result = requestHandler.handle(req,service);
            //写回结果
            ChannelFuture future = ctx.writeAndFlush(RpcResponse.success(result));
            //
            future.addListener(ChannelFutureListener.CLOSE);
        }finally {
            //
            ReferenceCountUtil.release(req);
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("处理过程调用时有错误发生:");
        cause.printStackTrace();
        ctx.close();
    }
}
