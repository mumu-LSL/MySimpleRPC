package RtLin.registry;

/**
 * @author Lin
 * @Date 2022/5/12 - 20:44
 */
public interface ServiceRegistry {
    <T> void register(T service);
    Object getService(String serviceName);
}
