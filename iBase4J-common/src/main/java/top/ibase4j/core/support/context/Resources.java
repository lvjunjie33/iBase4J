package top.ibase4j.core.support.context;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import org.springframework.context.i18n.LocaleContextHolder;








public final class Resources
{
  public static final ResourceBundle THIRDPARTY = ResourceBundle.getBundle("config/thirdParty");
  
  private static final Map<String, ResourceBundle> MESSAGES = new HashMap();

  
  public static String getMessage(String key, Object... params) {
    Locale locale = LocaleContextHolder.getLocale();
    ResourceBundle message = (ResourceBundle)MESSAGES.get(locale.getLanguage());
    if (message == null) {
      synchronized (MESSAGES) {
        message = (ResourceBundle)MESSAGES.get(locale.getLanguage());
        if (message == null) {
          message = ResourceBundle.getBundle("i18n/messages", locale);
          MESSAGES.put(locale.getLanguage(), message);
        } 
      } 
    }
    if (params != null && params.length > 0) {
      return String.format(message.getString(key), params);
    }
    return message.getString(key);
  }


  
  public static void flushMessage() { MESSAGES.clear(); }
}
