package top.ibase4j.core.support.context;

import com.weibo.api.motan.config.ProtocolConfig;
import com.weibo.api.motan.config.RefererConfig;
import com.weibo.api.motan.config.RegistryConfig;





public class MotanContext
{
  private static ProtocolConfig motanProtocol;
  private static RegistryConfig motanRegistry;
  
  public static <T> T getService(Class<T> cls) {
    if (motanProtocol == null || motanRegistry == null) {
      synchronized (MotanContext.class) {
        if (motanProtocol == null) {
          motanProtocol = (ProtocolConfig)ApplicationContextHolder.getBean(ProtocolConfig.class);
        }
        if (motanRegistry == null) {
          motanRegistry = (RegistryConfig)ApplicationContextHolder.getBean(RegistryConfig.class);
        }
      } 
    }
    
    RefererConfig reference = new RefererConfig();
    reference.setProtocol(motanProtocol);
    reference.setRegistry(motanRegistry);
    reference.setInterface(cls);
    reference.setCheck("false");
    return (T)reference.getRef();
  }
}
