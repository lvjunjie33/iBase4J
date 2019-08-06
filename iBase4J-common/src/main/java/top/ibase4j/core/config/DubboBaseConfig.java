package top.ibase4j.core.config;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.springframework.context.annotation.Bean;
import top.ibase4j.core.util.PropertiesUtil;





public class DubboBaseConfig
{
  @Bean
  public ApplicationConfig application() {
    ApplicationConfig applicationConfig = new ApplicationConfig();
    applicationConfig.setQosEnable(Boolean.valueOf(true));
    applicationConfig.setQosAcceptForeignIp(Boolean.valueOf(false));
    applicationConfig.setQosPort(Integer.valueOf(PropertiesUtil.getInt("rpc.protocol.port", 22222) + 10));
    applicationConfig.setName(PropertiesUtil.getString("rpc.registry.name"));
    applicationConfig.setLogger("slf4j");
    return applicationConfig;
  }
  
  @Bean
  public RegistryConfig registry() {
    RegistryConfig registryConfig = new RegistryConfig();
    registryConfig.setRegister(Boolean.valueOf(PropertiesUtil.getBoolean("rpc.register", true)));
    registryConfig.setAddress(PropertiesUtil.getString("rpc.address"));
    registryConfig.setProtocol(PropertiesUtil.getString("rpc.protocol"));
    registryConfig.setFile(PropertiesUtil.getString("rpc.cache.dir") + "/dubbo-" + 
        PropertiesUtil.getString("rpc.registry.name") + ".cache");
    return registryConfig;
  }
}
