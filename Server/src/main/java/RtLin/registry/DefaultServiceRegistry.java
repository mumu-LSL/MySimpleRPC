package RtLin.registry;

import RtLin.enums.RpcErrorMessageEnum;
import RtLin.exception.RpcException;
import RtLin.rpcServer.RpcServerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lin
 * @Date 2022/5/12 - 20:45
 */
public class DefaultServiceRegistry implements ServiceRegistry{
    private static final Logger logger = LoggerFactory.getLogger(RpcServerImpl.class);
    private static final Map<String, Object> serviceMap = new ConcurrentHashMap<>();
    private static final Set<String> registeredService = ConcurrentHashMap.newKeySet();

    /*
    * 该方法的目的是建立起 服务的类所继承的接口的全类名——服务的实例对象 之间的映射关系
    * */
    @Override
    public <T> void register(T service) {
        //返回类的规范名称，指全类名，本例中指代RtLin.server.HelloServiceImpl
        String serviceName = service.getClass().getCanonicalName();
        if(registeredService.contains(serviceName)) return;
        registeredService.add(serviceName);
        //Class<？>表示类型不确定的类, getInterfaces()返回的是一个表示类所实现的接口的数组
        Class<?>[] interfaces = service.getClass().getInterfaces();
        if(interfaces.length == 0) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_NOT_IMPLEMENT_ANY_INTERFACE);
        }
        for(Class<?> i : interfaces) {
            System.out.println("test"+i.getCanonicalName());
            serviceMap.put(i.getCanonicalName(), service);
        }
        logger.info("向接口: {} 注册服务: {}", interfaces, serviceName);
    }

    /*
    * 根据全类名得到服务
    * */
    @Override
    public Object getService(String serviceName) {
        Object service = serviceMap.get(serviceName);
        if(service == null) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_FOUND);
        }
        return service;
    }
}
