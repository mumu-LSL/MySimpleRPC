package RtLin.serviceImpl;
import RtLin.HelloObject;
import RtLin.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author Lin
 * @Date 2022/5/12 - 13:39
 */
public class HelloServiceImpl implements HelloService {
    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    /*客户端要调用的函数 */
    @Override
    public String hello(HelloObject object) {
        logger.info("接收到消息 {}",object.getMessage());
        return "ID:"+object.getID();
    }
}