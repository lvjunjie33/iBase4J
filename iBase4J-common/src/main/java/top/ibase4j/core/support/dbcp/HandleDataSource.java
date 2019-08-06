package top.ibase4j.core.support.dbcp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.ibase4j.core.util.DataUtil;
import top.ibase4j.core.util.PropertiesUtil;







public class HandleDataSource
{
  private static final Logger logger = LogManager.getLogger();
  
  static Map<String, List<String>> METHODTYPE = new HashMap();
  
  private static final ThreadLocal<String> holder = new ThreadLocal();

  
  public static void putDataSource(String datasource) { holder.set(datasource); }


  
  public static String getDataSource() { return (String)holder.get(); }


  
  public static void clear() { holder.remove(); }

  
  public static void setDataSource(String service, String method) {
    logger.info(service + "." + method + "=>start...");
    if (DataUtil.isNotEmpty(PropertiesUtil.getString("druid.reader.url")))
      try {
        for (Map.Entry<String, List<String>> entry : METHODTYPE.entrySet()) {
          for (String type : entry.getValue()) {
            if (method.startsWith(type)) {
              logger.info(service + "." + method + "=>" + (String)entry.getKey());
              putDataSource((String)entry.getKey());
              // Byte code: goto -> 216
            } 
          } 
        } 
      } catch (Exception e) {
        logger.error("", e);
        putDataSource("write");
      }  
  }
}
