package RtLin.proxy;

import RtLin.RPC_Response.RpcResponse;
import RtLin.rpcClient.NettyClient;
import RtLin.rpcClient.RpcClient;
import RtLin.rpcClient.SocketClient;
import RtLin.rpc_request.RpcRequest;
import io.netty.util.concurrent.CompleteFuture;
import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.CompletableFuture;


@Data
@Builder

public class RpcClientProxy implements InvocationHandler {
    private static final Logger logger = LoggerFactory.getLogger(RpcClientProxy.class);
    private final RpcClient client;

    public RpcClientProxy(RpcClient cl){
        this.client=cl;
    }
    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        logger.info("调用方法: {}#{}", method.getDeclaringClass().getName(), method.getName());
        RpcRequest rpcRequest = new RpcRequest(method.getDeclaringClass().getName(),
                method.getName(), args, method.getParameterTypes());
        RpcResponse rpcResponse = null;
        if (client instanceof NettyClient) {
            try {
                rpcResponse =(RpcResponse)client.sendRequest(rpcRequest);
            } catch (Exception e) {
                logger.error("方法调用请求发送失败", e);
                return null;
            }
        }
        if (client instanceof SocketClient) {
            try {
                rpcResponse = (RpcResponse) client.sendRequest(rpcRequest);
            }catch (Exception e){
                logger.error("请求失败！",e);
                return null;
            }
        }
   //     RpcMessageChecker.check(rpcRequest, rpcResponse);
        return rpcResponse.getData();
    }
}
