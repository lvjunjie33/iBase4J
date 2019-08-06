package top.ibase4j.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.util.WebUtils;
import top.ibase4j.core.support.http.SessionUser;
















public final class WebUtil
{
  public static ThreadLocal<HttpServletRequest> REQUEST = new NamedThreadLocal("ThreadLocalRequest");

  
  private static Logger logger = LogManager.getLogger();










  
  public static final String getCookieValue(HttpServletRequest request, String cookieName, String defaultValue) {
    Cookie cookie = WebUtils.getCookie(request, cookieName);
    if (cookie == null) {
      return defaultValue;
    }
    return cookie.getValue();
  }


  
  public static final SessionUser getCurrentUser(HttpServletRequest request) { return (SessionUser)request.getAttribute("CURRENT_USER"); }



  
  public static final void saveCurrentUser(HttpServletRequest request, SessionUser user) { request.setAttribute("CURRENT_USER", user); }





  
  public static final void setSession(HttpServletRequest request, String key, Object value) {
    HttpSession session = request.getSession();
    if (null != session) {
      session.setAttribute(key, value);
    }
  }



  
  public static final Object getSession(HttpServletRequest request, String key) {
    HttpSession session = request.getSession();
    if (null != session) {
      return session.getAttribute(key);
    }
    return null;
  }


  
  public static final void removeCurrentUser(HttpServletRequest request) { request.getSession().removeAttribute("CURRENT_USER"); }









  
  public static final String getApplicationResource(String key, HttpServletRequest request) {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("ApplicationResources", request.getLocale());
    return resourceBundle.getString(key);
  }







  
  public static final Map<String, Object> getParameterMap(HttpServletRequest request) { return WebUtils.getParametersStartingWith(request, null); }

  
  public static String getRequestBody(ServletRequest request) {
    String body = (String)request.getAttribute("iBase4J.requestBody");
    if (DataUtil.isEmpty(body)) {
      body = "";
      try {
        BufferedReader br = request.getReader(); String str;
        while ((str = br.readLine()) != null) {
          body = body + str;
        }
        logger.info("request body===>{}", body);
        request.setAttribute("iBase4J.requestBody", body);
      } catch (Exception e) {
        return null;
      } 
    } 
    return body;
  }







  
  public static Map<String, Object> getRequestParam(String param) {
	return null;} // Byte code:
    //   0: invokestatic newHashMap : ()Ljava/util/HashMap;
    //   3: astore_1
    //   4: aconst_null
    //   5: aload_0
    //   6: if_acmpeq -> 75
    //   9: aload_0
    //   10: ldc '&'
    //   12: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   15: astore_2
    //   16: aload_2
    //   17: astore_3
    //   18: aload_3
    //   19: arraylength
    //   20: istore #4
    //   22: iconst_0
    //   23: istore #5
    //   25: iload #5
    //   27: iload #4
    //   29: if_icmpge -> 75
    //   32: aload_3
    //   33: iload #5
    //   35: aaload
    //   36: astore #6
    //   38: aload #6
    //   40: ldc '='
    //   42: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   45: astore #7
    //   47: aload #7
    //   49: arraylength
    //   50: iconst_2
    //   51: if_icmpne -> 69
    //   54: aload_1
    //   55: aload #7
    //   57: iconst_0
    //   58: aaload
    //   59: aload #7
    //   61: iconst_1
    //   62: aaload
    //   63: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   68: pop
    //   69: iinc #5, 1
    //   72: goto -> 25
    //   75: aload_1
    //   76: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #143	-> 0
    //   #144	-> 4
    //   #145	-> 9
    //   #146	-> 16
    //   #147	-> 38
    //   #148	-> 47
    //   #149	-> 54
    //   #146	-> 69
    //   #153	-> 75
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   47	22	7	p	[Ljava/lang/String;
    //   38	31	6	param2	Ljava/lang/String;
    //   16	59	2	params	[Ljava/lang/String;
    //   0	77	0	param	Ljava/lang/String;
    //   4	73	1	paramMap	Ljava/util/Map;
    // Local variable type table:
    //   start	length	slot	name	signature
    //   4	73	1	paramMap	Ljava/util/Map<Ljava/lang/String;Ljava/lang/Object;>; }






  
  public static Map<String, Object> getParameter(HttpServletRequest request) {
    String body = getRequestBody(request);
    if (DataUtil.isNotEmpty(body)) {
      try {
        return (Map)JSON.parseObject(body, Map.class);
      } catch (Exception e) {
        try {
          return XmlUtil.parseXml2Map(body);
        } catch (Exception e1) {
          logger.error(ExceptionUtil.getStackTraceAsString(e));
          logger.error(ExceptionUtil.getStackTraceAsString(e));
          return getRequestParam(body);
        } 
      } 
    }
    return getParameterMap(request);
  }

  
  public static <T> T getParameter(HttpServletRequest request, Class<T> cls) { return (T)InstanceUtil.transMap2Bean(getParameter(request), cls); }

























  
  public static final String getHost(HttpServletRequest request) {
	return null; }// Byte code:
    //   0: aload_0
    //   1: ldc 'X-Forwarded-For'
    //   3: invokeinterface getHeader : (Ljava/lang/String;)Ljava/lang/String;
    //   8: astore_1
    //   9: aload_1
    //   10: invokestatic isEmpty : (Ljava/lang/Object;)Z
    //   13: ifne -> 25
    //   16: ldc 'unknown'
    //   18: aload_1
    //   19: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   22: ifeq -> 34
    //   25: aload_0
    //   26: ldc 'Proxy-Client-IP'
    //   28: invokeinterface getHeader : (Ljava/lang/String;)Ljava/lang/String;
    //   33: astore_1
    //   34: aload_1
    //   35: invokestatic isEmpty : (Ljava/lang/Object;)Z
    //   38: ifne -> 50
    //   41: ldc 'unknown'
    //   43: aload_1
    //   44: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   47: ifeq -> 59
    //   50: aload_0
    //   51: ldc 'WL-Proxy-Client-IP'
    //   53: invokeinterface getHeader : (Ljava/lang/String;)Ljava/lang/String;
    //   58: astore_1
    //   59: aload_1
    //   60: invokestatic isEmpty : (Ljava/lang/Object;)Z
    //   63: ifne -> 75
    //   66: ldc 'unknown'
    //   68: aload_1
    //   69: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   72: ifeq -> 84
    //   75: aload_0
    //   76: ldc 'HTTP_CLIENT_IP'
    //   78: invokeinterface getHeader : (Ljava/lang/String;)Ljava/lang/String;
    //   83: astore_1
    //   84: aload_1
    //   85: invokestatic isEmpty : (Ljava/lang/Object;)Z
    //   88: ifne -> 100
    //   91: ldc 'unknown'
    //   93: aload_1
    //   94: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   97: ifeq -> 109
    //   100: aload_0
    //   101: ldc 'HTTP_X_FORWARDED_FOR'
    //   103: invokeinterface getHeader : (Ljava/lang/String;)Ljava/lang/String;
    //   108: astore_1
    //   109: aload_1
    //   110: invokestatic isEmpty : (Ljava/lang/Object;)Z
    //   113: ifne -> 125
    //   116: ldc 'unknown'
    //   118: aload_1
    //   119: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   122: ifeq -> 134
    //   125: aload_0
    //   126: ldc 'X-Real-IP'
    //   128: invokeinterface getHeader : (Ljava/lang/String;)Ljava/lang/String;
    //   133: astore_1
    //   134: aload_1
    //   135: invokestatic isEmpty : (Ljava/lang/Object;)Z
    //   138: ifne -> 150
    //   141: ldc 'unknown'
    //   143: aload_1
    //   144: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   147: ifeq -> 157
    //   150: aload_0
    //   151: invokeinterface getRemoteAddr : ()Ljava/lang/String;
    //   156: astore_1
    //   157: aload_1
    //   158: ifnull -> 234
    //   161: aload_1
    //   162: ldc ','
    //   164: invokevirtual indexOf : (Ljava/lang/String;)I
    //   167: ifle -> 234
    //   170: getstatic top/ibase4j/core/util/WebUtil.logger : Lorg/apache/logging/log4j/Logger;
    //   173: aload_1
    //   174: invokeinterface info : (Ljava/lang/String;)V
    //   179: aload_1
    //   180: ldc ','
    //   182: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   185: astore_2
    //   186: aload_2
    //   187: astore_3
    //   188: aload_3
    //   189: arraylength
    //   190: istore #4
    //   192: iconst_0
    //   193: istore #5
    //   195: iload #5
    //   197: iload #4
    //   199: if_icmpge -> 234
    //   202: aload_3
    //   203: iload #5
    //   205: aaload
    //   206: astore #6
    //   208: aload #6
    //   210: astore #7
    //   212: ldc 'unknown'
    //   214: aload #7
    //   216: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   219: ifne -> 228
    //   222: aload #7
    //   224: astore_1
    //   225: goto -> 234
    //   228: iinc #5, 1
    //   231: goto -> 195
    //   234: ldc '127.0.0.1'
    //   236: aload_1
    //   237: invokevirtual equals : (Ljava/lang/Object;)Z
    //   240: ifne -> 252
    //   243: ldc '0:0:0:0:0:0:0:1'
    //   245: aload_1
    //   246: invokevirtual equals : (Ljava/lang/Object;)Z
    //   249: ifeq -> 282
    //   252: aconst_null
    //   253: astore_2
    //   254: invokestatic getLocalHost : ()Ljava/net/InetAddress;
    //   257: astore_2
    //   258: goto -> 273
    //   261: astore_3
    //   262: getstatic top/ibase4j/core/util/WebUtil.logger : Lorg/apache/logging/log4j/Logger;
    //   265: ldc 'getCurrentIP'
    //   267: aload_3
    //   268: invokeinterface error : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   273: aload_2
    //   274: ifnull -> 282
    //   277: aload_2
    //   278: invokevirtual getHostAddress : ()Ljava/lang/String;
    //   281: astore_1
    //   282: getstatic top/ibase4j/core/util/WebUtil.logger : Lorg/apache/logging/log4j/Logger;
    //   285: new java/lang/StringBuilder
    //   288: dup
    //   289: invokespecial <init> : ()V
    //   292: ldc 'getRemoteAddr ip: '
    //   294: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   297: aload_1
    //   298: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   301: invokevirtual toString : ()Ljava/lang/String;
    //   304: invokeinterface info : (Ljava/lang/String;)V
    //   309: aload_1
    //   310: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #181	-> 0
    //   #182	-> 9
    //   #183	-> 25
    //   #185	-> 34
    //   #186	-> 50
    //   #188	-> 59
    //   #189	-> 75
    //   #191	-> 84
    //   #192	-> 100
    //   #194	-> 109
    //   #195	-> 125
    //   #197	-> 134
    //   #198	-> 150
    //   #200	-> 157
    //   #201	-> 170
    //   #203	-> 179
    //   #204	-> 186
    //   #205	-> 208
    //   #206	-> 212
    //   #207	-> 222
    //   #208	-> 225
    //   #204	-> 228
    //   #212	-> 234
    //   #213	-> 252
    //   #215	-> 254
    //   #218	-> 258
    //   #216	-> 261
    //   #217	-> 262
    //   #219	-> 273
    //   #220	-> 277
    //   #223	-> 282
    //   #224	-> 309
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   212	16	7	strIp	Ljava/lang/String;
    //   208	20	6	ip2	Ljava/lang/String;
    //   186	48	2	ips	[Ljava/lang/String;
    //   262	11	3	e	Ljava/net/UnknownHostException;
    //   254	28	2	inet	Ljava/net/InetAddress;
    //   0	311	0	request	Ljavax/servlet/http/HttpServletRequest;
    //   9	302	1	ip	Ljava/lang/String;
    // Exception table:
    //   from	to	target	type
    //   254	258	261	java/net/UnknownHostException }
























  
  public static void setResponseFileName(HttpServletRequest request, HttpServletResponse response, String displayName) {
    String userAgent = request.getHeader("User-Agent");
    boolean isIE = false;
    if (userAgent != null && userAgent.toLowerCase().contains("msie")) {
      isIE = true;
    }
    try {
      String displayName2;
      if (isIE) {
        displayName2 = URLEncoder.encode(displayName, "UTF-8");
        displayName2 = displayName2.replaceAll("\\+", "%20");
        response.setHeader("Content-Disposition", "attachment;filename=" + displayName2);
      } else {
        displayName2 = new String(displayName.getBytes("UTF-8"), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=\"" + displayName2 + "\"");
      } 
      String extStr = displayName2.substring(displayName2.indexOf(".") + 1);
      if ("xls".equalsIgnoreCase(extStr)) {
        response.setContentType("application/vnd.ms-excel charset=UTF-8");
      } else {
        response.setContentType("application/octet-stream");
      } 
    } catch (UnsupportedEncodingException e) {
      logger.error("设置文件名发生错误", e);
    } 
  }

  
  public static boolean isWhiteRequest(String url, int size, List<String> whiteUrls) {
    if (url == null || "".equals(url) || size == 0) {
      return true;
    }
    url = url.toLowerCase();
    for (String urlTemp : whiteUrls) {
      if (url.indexOf(urlTemp.toLowerCase()) > -1) {
        return true;
      }
    } 
    
    return false;
  }

  
  public static boolean write(ServletResponse response, Integer code, String msg) throws IOException {
    response.setContentType("application/json;charset=UTF-8");
    Map<String, Object> modelMap = InstanceUtil.newLinkedHashMap();
    modelMap.put("code", code.toString());
    modelMap.put("msg", msg);
    modelMap.put("timestamp", Long.valueOf(System.currentTimeMillis()));
    logger.info("response===>" + JSON.toJSON(modelMap));
    response.getOutputStream().write(JSON.toJSONBytes(modelMap, new SerializerFeature[] { SerializerFeature.DisableCircularReferenceDetect }));
    return false;
  }
}
