package RtLin.requestHandler;

import RtLin.RPC_Response.RpcResponse;
import RtLin.registry.ServiceRegistry;
import RtLin.rpc_request.RpcRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketRequestThread implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(SocketRequestThread.class);

    private Socket socket;
    private RequestHandler requestHandler;
    private ServiceRegistry serviceRegistry;

    public SocketRequestThread(Socket socket, RequestHandler requestHandler, ServiceRegistry serviceRegistry) {
        this.socket = socket;
        this.requestHandler = requestHandler;
        this.serviceRegistry = serviceRegistry;
    }

    @Override
    public void run() {
        try (
                //获取socket的输入输出流
             ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {
            //读取请求
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            //获取接口名，也就是服务名,本例中为RtLin.HelloService
            String interfaceName = rpcRequest.getInterfaceName();
            logger.info("请求的服务名为: "+interfaceName);
            //获取服务实例对象，并执行其调用的方法（方法信息在rpcRequest对象中）
            Object service = serviceRegistry.getService(interfaceName);
            Object result = requestHandler.handle(rpcRequest, service);
            //将响应信息包装成一个响应包，并通过socket发送回去
            objectOutputStream.writeObject(RpcResponse.success(result));
            objectOutputStream.flush();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("调用或发送时有错误发生：", e);
        }
    }
}

