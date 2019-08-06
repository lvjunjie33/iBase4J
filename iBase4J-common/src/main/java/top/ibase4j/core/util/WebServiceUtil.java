package top.ibase4j.core.util;

import java.net.URL;
import org.codehaus.xfire.client.Client;









public final class WebServiceUtil
{
  public static final Object invoke(String url, String method, Object... params) {
    try {
      Client client = new Client(new URL(url + "?wsdl"));
      return client.invoke(method, params);
    } catch (Exception e) {
      throw new RuntimeException(e);
    } 
  }
}
