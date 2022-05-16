package RtLin.codec;
import RtLin.RPC_Response.RpcResponse;
import RtLin.enums.PackageType;
import RtLin.enums.RpcErrorMessageEnum;
import RtLin.exception.RpcException;
import RtLin.rpc_request.RpcRequest;
import RtLin.serializer.CommonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * @author Lin
 * @Date 2022/5/15 - 16:24
 */
public class CommonDecoder extends ReplayingDecoder {
    private static final Logger logger = LoggerFactory.getLogger(CommonDecoder.class);
    private static final int MAGIC_NUMBER=0xCAFEBABE;
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        int magic= buf.readInt();
        if(magic!=MAGIC_NUMBER){
            logger.info("无法识别的协议包: {}",magic);
            throw new RpcException(RpcErrorMessageEnum.UNKNOWN_PROTOCOL);
        }
        int packageCode = buf.readInt();
        Class<?> packageClass;
        if(packageCode== PackageType.REQUEST_PACK.getCode()){
            packageClass= RpcRequest.class;
        }else if(packageCode==PackageType.RESPONSE_PACK.getCode()){
            packageClass= RpcResponse.class;
        }
        else {
            logger.error("不识别的数据包:{}", packageCode);
            throw new RpcException(RpcErrorMessageEnum.UNKNOWN_PACKAGE_TYPE);
        }
        int serializerCode=buf.readInt();
        CommonSerializer serializer = CommonSerializer.getByCode(serializerCode);
        if(serializer==null){
            logger.error("不识别的序列化器",serializerCode);
            throw new RpcException(RpcErrorMessageEnum.UNKNOWN_SERIALIZER);
        }
        int length=buf.readInt();
        byte[] bytes = new byte[length];
        buf.readBytes(bytes);
        Object object = serializer.deserialize(bytes,packageClass);
        out.add(object);//
    }
}
