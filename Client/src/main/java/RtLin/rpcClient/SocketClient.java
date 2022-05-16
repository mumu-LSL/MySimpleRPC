package RtLin.rpcClient;

import RtLin.rpc_request.RpcRequest;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Lin
 * @Date 2022/5/16 - 15:41
 */
public class SocketClient implements RpcClient{

    private String host;
    private int port;
    public SocketClient(String h,int p){
        this.host=h;
        this.port=p;
    }
    private static final Logger logger = LoggerFactory.getLogger(RpcClient.class);

    public Object sendRequest(RpcRequest rpcRequest) {
        try (Socket socket = new Socket(host, port)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(rpcRequest);
            objectOutputStream.flush();
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("调用时有错误发生：", e);
            return null;
        }
    }
}

