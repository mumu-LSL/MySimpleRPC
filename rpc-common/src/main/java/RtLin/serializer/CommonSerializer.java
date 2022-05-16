package RtLin.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;

/**
 * @author Lin
 * @Date 2022/5/15 - 16:30
 */
public interface CommonSerializer {
    byte[] serialize(Object obj);
    Object deserialize(byte[] bytes,Class<?> clazz);
    int getCode();
    static CommonSerializer getByCode(int code){
        switch (code){
            case 0: return new KryoSerializer();
            case 1: return new JsonSerializer();
            default: return null;
        }
    }
}
