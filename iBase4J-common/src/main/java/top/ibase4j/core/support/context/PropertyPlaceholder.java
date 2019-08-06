package top.ibase4j.core.support.context;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import top.ibase4j.core.util.DataUtil;
import top.ibase4j.core.util.PropertiesUtil;
import top.ibase4j.core.util.SecurityUtil;











public class PropertyPlaceholder
  extends PropertyPlaceholderConfigurer
  implements ApplicationContextAware
{
  private List<String> decryptProperties;
  
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException { ApplicationContextHolder.applicationContext = applicationContext; }


  
  protected void loadProperties(Properties props) throws IOException {
    super.loadProperties(props);
    for (Map.Entry<Object, Object> entry : props.entrySet()) {
      String keyStr = entry.getKey().toString();
      String value = entry.getValue().toString();
      if (this.decryptProperties != null && this.decryptProperties.contains(keyStr)) {
        String dkey = props.getProperty("druid.key");
        dkey = DataUtil.isEmpty(dkey) ? "90139119" : dkey;
        value = SecurityUtil.decryptDes(value, dkey.getBytes());
        props.setProperty(keyStr, value);
      } 
      PropertiesUtil.getProperties().put(keyStr, value);
    } 
    this.logger.info("loadProperties ok.");
  }





  
  public void setDecryptProperties(List<String> decryptProperties) { this.decryptProperties = decryptProperties; }
}
