package RtLin.test;

import RtLin.HelloService;
import RtLin.registry.DefaultServiceRegistry;
import RtLin.registry.ServiceRegistry;
import RtLin.rpcServer.RpcServer;
import RtLin.rpcServer.RpcServerImpl;
import RtLin.serviceImpl.HelloServiceImpl;

/**
 * @author Lin
 * @Date 2022/5/12 - 15:41
 */
public class SocketServerTest {
    public static void main(String[] args) {
        //创建等待调用的服务
        HelloService helloService = new HelloServiceImpl();
        //创建服务注册中心
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        //注册服务
        serviceRegistry.register(helloService);
        //创建服务器
        RpcServer rpcServerImpl = new RpcServerImpl(serviceRegistry);
        //启动服务器，端口为9000
        rpcServerImpl.start(9999);
    }
}
