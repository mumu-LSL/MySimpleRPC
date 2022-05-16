package RtLin.test;

import RtLin.HelloObject;
import RtLin.HelloService;
import RtLin.proxy.RpcClientProxy;
import RtLin.rpcClient.NettyClient;
import RtLin.rpcClient.RpcClient;

public class NettyTestClient {

    public static void main(String[] args) {
        RpcClient client = new NettyClient("127.0.0.1", 9999);
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
    }

}
