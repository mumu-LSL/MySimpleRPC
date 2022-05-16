package RtLin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Lin
 * @Date 2022/5/15 - 17:14
 */
/*
* 使用enum定义的枚举类默认继承了java.lang.Enum，而不是继承Object类。枚举类可以实现一个或多个接口。
* 枚举类的所有实例都必须放在第一行展示，不需使用new 关键字，不需显式调用构造器。自动添加public static final修饰
* 使用enum定义、非抽象的枚举类默认使用final修饰，不可以被继承。
* 枚举类的构造器只能是私有的。
* */

@AllArgsConstructor //枚举类第一行实例的声明是调用了有参构造器的
@Getter
public enum  PackageType {

    REQUEST_PACK(0), RESPONSE_PACK(1);
    private final int code;
}
