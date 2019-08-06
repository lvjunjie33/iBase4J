package top.ibase4j.core.support.context;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;





public class DubboContext
{
  private static ApplicationConfig dubboApplication;
  private static RegistryConfig dubboRegistry;
  
  public static <T> T getService(Class<T> cls) {
    if (dubboApplication == null || dubboRegistry == null) {
      synchronized (DubboContext.class) {
        if (dubboApplication == null) {
          dubboApplication = (ApplicationConfig)ApplicationContextHolder.getBean(ApplicationConfig.class);
        }
        if (dubboRegistry == null) {
          dubboRegistry = (RegistryConfig)ApplicationContextHolder.getBean(RegistryConfig.class);
        }
      } 
    }
	return null;
    
//    ReferenceConfig<?> reference = new ReferenceConfig<?>();
//    reference.setApplication(dubboApplication);
//    reference.setRegistry(dubboRegistry);
//    reference.setCheck(Boolean.valueOf(false));
//    reference.setInterface(cls);
//    return (T)reference.get();
  }
}
