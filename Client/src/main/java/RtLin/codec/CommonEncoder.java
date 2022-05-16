package RtLin.codec;

import RtLin.enums.PackageType;
import RtLin.rpc_request.RpcRequest;
import RtLin.serializer.CommonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author Lin
 * @Date 2022/5/15 - 16:24
 */
public class CommonEncoder extends MessageToByteEncoder {
    private static final int MAGIC_NUMBER=0xCAFEBABE;
    private final CommonSerializer serializer;
    public CommonEncoder(CommonSerializer Serializer) {
        this.serializer=Serializer;
    }
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf buf) throws Exception {
        //写魔数
        buf.writeInt(MAGIC_NUMBER);
        //写包的类型码
        if(msg instanceof RpcRequest){
            buf.writeInt(PackageType.REQUEST_PACK.getCode());
        }else {
            buf.writeInt(PackageType.RESPONSE_PACK.getCode());
        }
        //写序列化器的码值
        buf.writeInt(serializer.getCode());
        //序列化
        byte[] bytes=serializer.serialize(msg);
        //写数据包的长度
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);
    }
}
