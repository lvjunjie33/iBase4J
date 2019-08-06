package top.ibase4j.core.util;

import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;






public final class PropertiesUtil
{
  private static Map<String, String> ctxPropertiesMap = new HashMap();

  
  public static Map<String, String> getProperties() { return ctxPropertiesMap; }







  
  public static String getString(String key) {
    try {
      return (String)ctxPropertiesMap.get(key);
    } catch (MissingResourceException e) {
      return null;
    } 
  }






  
  public static String getString(String key, String defaultValue) {
    try {
      String value = (String)ctxPropertiesMap.get(key);
      if (DataUtil.isEmpty(value)) {
        return defaultValue;
      }
      return value;
    } catch (MissingResourceException e) {
      return defaultValue;
    } 
  }






  
  public static Integer getInt(String key) {
    String value = (String)ctxPropertiesMap.get(key);
    if (DataUtil.isEmpty(value)) {
      return null;
    }
    return Integer.valueOf(Integer.parseInt(value));
  }







  
  public static int getInt(String key, int defaultValue) {
    String value = (String)ctxPropertiesMap.get(key);
    if (DataUtil.isEmpty(value)) {
      return defaultValue;
    }
    return Integer.parseInt(value);
  }






  
  public static long getLong(String keyName, long defaultValue) {
    String value = getString(keyName);
    if (DataUtil.isEmpty(value)) {
      return defaultValue;
    }
    try {
      return Long.parseLong(value.trim());
    } catch (Exception e) {
      return defaultValue;
    } 
  }
  
  public static boolean getBoolean(String key) {
    String value = (String)ctxPropertiesMap.get(key);
    if (DataUtil.isEmpty(value)) {
      return false;
    }
    return Boolean.parseBoolean(value.trim());
  }







  
  public static boolean getBoolean(String key, boolean defaultValue) {
    String value = (String)ctxPropertiesMap.get(key);
    if (DataUtil.isEmpty(value)) {
      return defaultValue;
    }
    return Boolean.parseBoolean(value.trim());
  }
}
