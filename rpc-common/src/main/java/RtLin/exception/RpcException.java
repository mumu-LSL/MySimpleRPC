package RtLin.exception;

import RtLin.enums.RpcErrorMessageEnum;

/**
 * RPC调用异常
 *
 * @author ziyang
 */
public class RpcException extends RuntimeException {

    public RpcException(RpcErrorMessageEnum error, String detail) {
        super(error.getMessage() + ": " + detail);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(RpcErrorMessageEnum error) {
        super(error.getMessage());
    }

}
