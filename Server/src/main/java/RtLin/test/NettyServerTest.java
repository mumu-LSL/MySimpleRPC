package RtLin.test;

import RtLin.HelloService;
import RtLin.registry.DefaultServiceRegistry;
import RtLin.registry.ServiceRegistry;
import RtLin.rpcServer.NettyServer;
import RtLin.serviceImpl.HelloServiceImpl;

public class NettyServerTest {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry registry = new DefaultServiceRegistry();
        registry.register(helloService);
        NettyServer server = new NettyServer();
        server.start(9999);
    }

}
