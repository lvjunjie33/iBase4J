package top.ibase4j.core.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;




@Deprecated
public class ServerListener
  implements ServletContextListener
{
  protected final Logger logger = LogManager.getLogger();
  
  public void contextInitialized(ServletContextEvent contextEvent) {
    this.logger.info("=================================");
    this.logger.info("系统[{}]启动完成!!!", contextEvent.getServletContext().getServletContextName());
    this.logger.info("=================================");
  }
  
  public void contextDestroyed(ServletContextEvent contextEvent) {}
}
