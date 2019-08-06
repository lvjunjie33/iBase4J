package top.ibase4j.core.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;














public class ApplicationReadyListener
  implements ApplicationListener
{
  protected final Logger logger = LogManager.getLogger();

  
  public void onApplicationEvent(ApplicationEvent event) {
    if (event instanceof org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent) {
      this.logger.info("==========初始化环境变量==============");
    } else if (event instanceof org.springframework.boot.context.event.ApplicationPreparedEvent) {
      this.logger.info("==========初始化完成==============");
    } else if (event instanceof org.springframework.context.event.ContextRefreshedEvent) {
      this.logger.info("==========应用刷新==============");
    } else if (event instanceof ApplicationReadyEvent) {
      this.logger.info("=================================");
      
      String server = ((ApplicationReadyEvent)event).getSpringApplication().getAllSources().iterator().next().toString();
      this.logger.info("系统[{}]启动完成!!!", server.substring(server.lastIndexOf(".") + 1));
      this.logger.info("=================================");
    } else if (event instanceof org.springframework.context.event.ContextStartedEvent) {
      this.logger.info("==========应用启动==============");
    } else if (event instanceof org.springframework.context.event.ContextStoppedEvent) {
      this.logger.info("==========应用停止==============");
    } else if (event instanceof org.springframework.context.event.ContextClosedEvent) {
      this.logger.info("==========应用关闭==============");
    } 
  }
}
