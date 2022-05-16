package RtLin.rpc_request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Lin
 * @Date 2022/5/12 - 13:50
 */
//： @Data 注在类上，提供类的get、set、equals、hashCode、canEqual、toString方法
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RpcRequest implements Serializable {
    /*待调用的接口名词 */
    private String interfaceName;
    /*待调用的方法名*/
    private String methodName;
    /*调用的方法参数*/
    private Object[] parameters;
    /*调用的方法参数类型*/
    private Class<?>[] paramTypes;
}
