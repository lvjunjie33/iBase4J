package top.ibase4j.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;






public class GaodeMapUtil
{
  private static final Logger logger = LogManager.getLogger();

  
  public static Map<String, Object> getCoordinateByAddress(String address, String city) {
    String url = PropertiesUtil.getString("gaode.map.url");
    String key = PropertiesUtil.getString("gaode.map.key");
    Map<String, Object> map = InstanceUtil.newHashMap();
    
    try {
      url = MessageFormat.format(url, new Object[] { key, URLEncoder.encode(address, "UTF-8"), URLEncoder.encode(city, "UTF-8") });
      String result = HttpUtil.get(url);
      JSONObject jsonObject = JSON.parseObject(result);
      JSONArray pois = (JSONArray)jsonObject.get("pois");
      if (pois.size() > 0) {
        JSONObject jobj = (JSONObject)pois.get(0);
        String location = jobj.getString("location");
        String[] locs = location.split(",");
        map.put("xIndex", locs[0]);
        map.put("yIndex", locs[1]);
      } 
    } catch (UnsupportedEncodingException e) {
      logger.error(ExceptionUtil.getStackTraceAsString(e));
    } 
    return map;
  }
}
