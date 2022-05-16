package RtLin.rpcClient;

import RtLin.rpc_request.RpcRequest;

/**
 * @author Lin
 * @Date 2022/5/13 - 22:11
 */
public interface RpcClient {
    Object sendRequest(RpcRequest rpcRequest);
}