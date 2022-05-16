package RtLin.RPC_Response;
import RtLin.enums.ResponseCode;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Lin
 * @Date 2022/5/12 - 14:02
 */
/*
* 本质上是一个包装类，将外界输入的响应数据，与状态码，状态补充信息包装成一个响应包，以便发送和接受
* */
@Data
public class RpcResponse<T> implements Serializable {
    /*状态响应码*/
    private Integer StatusCode;
    /*响应状态补充信息*/
    private String message;
    /*响应数据*/
    private T Data;
    //<T> 表示这是一个泛型方法
    public static <T> RpcResponse<T> success(T data) {
        //填充成功的响应码，并设置信息
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatusCode(ResponseCode.SUCCESS.getCode());
        response.setData(data);
        return response;
    }
    public static <T> RpcResponse<T> fail(ResponseCode code) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatusCode(code.getCode());
        response.setMessage(code.getMessage());
        return response;
    }

}
