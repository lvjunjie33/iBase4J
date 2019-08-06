package top.ibase4j.core.config;

import org.apache.dubbo.config.ConsumerConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.ProviderConfig;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import top.ibase4j.core.util.PropertiesUtil;







public class RpcConfig
{
  @Configuration
  @Conditional({top.ibase4j.core.support.rpc.EnableDubboService.class})
  @DubboComponentScan({"${rpc.package}"})
  static class DubboServiceConfig
    extends DubboBaseConfig
  {
    @Bean
    public ProviderConfig provider() {
      ProviderConfig providerConfig = new ProviderConfig();
      providerConfig.setFilter("dataSourceAspect,default");
      providerConfig.setTimeout(Integer.valueOf(PropertiesUtil.getInt("rpc.connect.timeout", 20000)));
      providerConfig.setVersion(PropertiesUtil.getString("rpc.service.version"));
      return providerConfig;
    }
    
    @Bean
    public ProtocolConfig protocol() {
      ProtocolConfig protocolConfig = new ProtocolConfig();
      protocolConfig.setHost(PropertiesUtil.getString("rpc.protocol.post"));
      protocolConfig.setPort(Integer.valueOf(PropertiesUtil.getInt("rpc.protocol.port", 20880)));
      protocolConfig.setThreadpool("cached");
      protocolConfig.setThreads(Integer.valueOf(PropertiesUtil.getInt("rpc.protocol.maxThread", 100)));
      protocolConfig.setPayload(Integer.valueOf(PropertiesUtil.getInt("rpc.protocol.maxContentLength", 1048576)));
      return protocolConfig;
    }
  }
  
  @Configuration
  @Conditional({top.ibase4j.core.support.rpc.EnableDubboReference.class})
  static class DubboConsumerConfig extends DubboBaseConfig {
    @Bean
    public ConsumerConfig consumer() {
      ConsumerConfig consumerConfig = new ConsumerConfig();
      consumerConfig.setLoadbalance("leastactive");
      consumerConfig.setTimeout(Integer.valueOf(PropertiesUtil.getInt("rpc.request.timeout", 20000)));
      consumerConfig.setRetries(Integer.valueOf(PropertiesUtil.getInt("rpc.consumer.retries", 0)));
      consumerConfig.setCheck(Boolean.valueOf(PropertiesUtil.getBoolean("rpc.consumer.check", false)));
      consumerConfig.setVersion(PropertiesUtil.getString("rpc.service.version"));
      return consumerConfig;
    }
  }
  
  @Configuration
  @Conditional({top.ibase4j.core.support.rpc.EnableMotan.class})
  @ImportResource({"classpath*:spring/motan.xml"})
  static class MotanConfig {}
}
