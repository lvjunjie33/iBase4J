package top.ibase4j.core.support.context;

import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import top.ibase4j.core.support.rpc.EnableDubboReference;
import top.ibase4j.core.support.rpc.EnableMotan;
import top.ibase4j.core.util.InstanceUtil;









@Component
public class ApplicationContextHolder
  implements ApplicationContextAware
{
  private static final Logger logger = LogManager.getLogger();
  static ApplicationContext applicationContext;
  private static final Map<String, Object> SERVICE_FACTORY = InstanceUtil.newHashMap();
  private static final EnableDubboReference ENABLE_DUBBO = new EnableDubboReference();
  private static final EnableMotan ENABLE_MOTAN = new EnableMotan();


  
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException { ApplicationContextHolder.applicationContext = applicationContext; }


  
  public static <T> T getBean(Class<T> t) { return (T)applicationContext.getBean(t); }


  
  public static <T> Map<String, T> getBeansOfType(Class<T> t) { return applicationContext.getBeansOfType(t); }


  
  public static Object getBean(String name) { return applicationContext.getBean(name); }


  
  public static <T> T getService(Class<T> cls) {
    String clsName = cls.getName();
    T v = (T)SERVICE_FACTORY.get(clsName);
    if (v == null) {
      synchronized (clsName) {
        v = (T)SERVICE_FACTORY.get(clsName);
        if (v == null) {
          logger.info("*****Autowire {}*****", cls);
          if (ENABLE_DUBBO.matches(null, null)) {
            v = (T)DubboContext.getService(cls);
          } else if (ENABLE_MOTAN.matches(null, null)) {
            v = (T)MotanContext.getService(cls);
          } else {
            v = (T)getBean(cls);
          } 
          logger.info("*****{} Autowired*****", cls);
          SERVICE_FACTORY.put(clsName, v);
        } 
      } 
    }
    return v;
  }
}
