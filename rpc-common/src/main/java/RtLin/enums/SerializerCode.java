package RtLin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Lin
 * @Date 2022/5/15 - 17:28
 */
@Getter
@AllArgsConstructor
public enum SerializerCode {
    KRYO(0),JSON(1),HESSIAN(2), PROTOBUF(3);
    private final int code;
}
