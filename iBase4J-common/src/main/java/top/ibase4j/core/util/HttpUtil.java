package top.ibase4j.core.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import javax.net.ssl.SSLContext;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;








public final class HttpUtil
{
  private static final Logger logger = LogManager.getLogger();

  
  private static final MediaType CONTENT_TYPE_FORM = MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8");

  
  private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36";


  
  public static void main(String[] args) { System.out.println(get("http://restapi.amap.com/v3/place/text?key=a7c65026724bee6e0c826ddef9155e69&keywords=%E6%98%8C%E5%B9%B3%E5%8C%BA&city=%E5%8C%97%E4%BA%AC%E5%B8%82%E5%8C%97%E4%BA%AC%E5%B8%82&children=1&offset=1&page=1&extensions=base")); }


  
  public static final String get(String url) {
    String result = "";
    HttpClient client = new HttpClient();
    GetMethod method = new GetMethod(url);
    method.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
    method.addRequestHeader("Content-Type", CONTENT_TYPE_FORM.toString());
    try {
      client.executeMethod(method);
      result = method.getResponseBodyAsString();
    } catch (Exception e) {
      logger.error(ExceptionUtil.getStackTraceAsString(e));
    } finally {
      method.releaseConnection();
    } 
    return result;
  }
  
  public static final String post(String url, NameValuePair... params) {
    String result = "";
    HttpClient client = new HttpClient();
    PostMethod method = new PostMethod(url);
    method.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
    method.addRequestHeader("Content-Type", CONTENT_TYPE_FORM.toString());
    try {
      method.addParameters(params);
      client.executeMethod(method);
      result = method.getResponseBodyAsString();
    } catch (Exception e) {
      logger.error(ExceptionUtil.getStackTraceAsString(e));
    } finally {
      method.releaseConnection();
    } 
    return result;
  }
  
  public static String post(String url, String params) {
    RequestBody body = RequestBody.create(CONTENT_TYPE_FORM, params);
    Request request = (new Request.Builder()).url(url).post(body).build();
    return exec(request);
  }
  
  private static String exec(Request request) {
    try {
      Response response = (new OkHttpClient()).newCall(request).execute();
      if (!response.isSuccessful()) {
        throw new RuntimeException("Unexpected code " + response);
      }
      return response.body().string();
    } catch (IOException e) {
      throw new RuntimeException(e);
    } 
  }
  
  public static String postSSL(String url, String data, String certPath, String certPass) {
    try {
      KeyStore clientStore = KeyStore.getInstance("PKCS12");
      
      FileInputStream instream = new FileInputStream(certPath);
      
      try {
        clientStore.load(instream, certPass.toCharArray());
      } finally {
        instream.close();
      } 
      SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(clientStore, certPass.toCharArray()).build();

      
      SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
      
      CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
      try {
        HttpPost httpost = new HttpPost(url);
        httpost.addHeader("Connection", "keep-alive");
        httpost.addHeader("Accept", "*/*");
        httpost.addHeader("Content-Type", CONTENT_TYPE_FORM.toString());
        httpost.addHeader("X-Requested-With", "XMLHttpRequest");
        httpost.addHeader("Cache-Control", "max-age=0");
        httpost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
        httpost.setEntity(new StringEntity(data, "UTF-8"));
        CloseableHttpResponse response = httpclient.execute(httpost);


      
      }
      finally {



        
        httpclient.close();
      } 
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
	return certPass; 
  }
}
