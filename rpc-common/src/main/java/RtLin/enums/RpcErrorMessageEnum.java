package RtLin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Lin
 * @Date 2022/5/12 - 20:49
 */
@AllArgsConstructor
@Getter
@ToString
public enum RpcErrorMessageEnum {
    CLIENT_CONNECT_SERVER_FAILURE("客户端连接服务端失败"),
    SERVICE_INVOCATION_FAILURE("服务调用失败"),
    SERVICE_CAN_NOT_BE_FOUND("没有找到指定的服务"),
    SERVICE_NOT_IMPLEMENT_ANY_INTERFACE("注册的服务没有实现任何接口"),
    REQUEST_NOT_MATCH_RESPONSE("返回结果错误！请求和返回的相应不匹配"),
    UNKNOWN_PROTOCOL("不能识别的协议包"),
    UNKNOWN_PACKAGE_TYPE("不能识别的数据包"),
    UNKNOWN_SERIALIZER("不能识别的序列化器");
    private final String message;

}