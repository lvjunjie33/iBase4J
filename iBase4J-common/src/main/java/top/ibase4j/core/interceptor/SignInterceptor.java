package top.ibase4j.core.interceptor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import top.ibase4j.core.util.FileUtil;












public class SignInterceptor
  extends HandlerInterceptorAdapter
{
  private static final Logger logger = LogManager.getLogger();
  
  private List<String> whiteUrls;
  private int size = 0;

  
  public SignInterceptor() {
    String path = SignInterceptor.class.getResource("/").getFile();
    this.whiteUrls = FileUtil.readFile(path + "white/signWhite.txt");
    this.size = (null == this.whiteUrls) ? 0 : this.whiteUrls.size();
  }























  
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	return false; // Byte code:
  }
    //   0: aload_1
    //   1: invokeinterface getRequestURL : ()Ljava/lang/StringBuffer;
    //   6: invokevirtual toString : ()Ljava/lang/String;
    //   9: astore #4
    //   11: aload_1
    //   12: ldc 'Referer'
    //   14: invokeinterface getHeader : (Ljava/lang/String;)Ljava/lang/String;
    //   19: astore #5
    //   21: aload #5
    //   23: ifnull -> 48
    //   26: aload #5
    //   28: ldc '/swagger'
    //   30: invokevirtual contains : (Ljava/lang/CharSequence;)Z
    //   33: ifeq -> 48
    //   36: getstatic top/ibase4j/core/interceptor/SignInterceptor.logger : Lorg/apache/logging/log4j/Logger;
    //   39: ldc 'SignInterceptor skip'
    //   41: invokeinterface info : (Ljava/lang/String;)V
    //   46: iconst_1
    //   47: ireturn
    //   48: aload #4
    //   50: aload_0
    //   51: getfield size : I
    //   54: aload_0
    //   55: getfield whiteUrls : Ljava/util/List;
    //   58: invokestatic isWhiteRequest : (Ljava/lang/String;ILjava/util/List;)Z
    //   61: ifeq -> 76
    //   64: getstatic top/ibase4j/core/interceptor/SignInterceptor.logger : Lorg/apache/logging/log4j/Logger;
    //   67: ldc 'SignInterceptor skip'
    //   69: invokeinterface info : (Ljava/lang/String;)V
    //   74: iconst_1
    //   75: ireturn
    //   76: aload_1
    //   77: ldc 'sign'
    //   79: invokeinterface getHeader : (Ljava/lang/String;)Ljava/lang/String;
    //   84: astore #6
    //   86: aload #6
    //   88: invokestatic isEmpty : (Ljava/lang/Object;)Z
    //   91: ifeq -> 107
    //   94: aload_2
    //   95: getstatic top/ibase4j/core/support/http/HttpCode.NOT_ACCEPTABLE : Ltop/ibase4j/core/support/http/HttpCode;
    //   98: invokevirtual value : ()Ljava/lang/Integer;
    //   101: ldc '请求参数未签名'
    //   103: invokestatic write : (Ljavax/servlet/ServletResponse;Ljava/lang/Integer;Ljava/lang/String;)Z
    //   106: ireturn
    //   107: aload_1
    //   108: ldc 'timestamp'
    //   110: invokeinterface getHeader : (Ljava/lang/String;)Ljava/lang/String;
    //   115: astore #7
    //   117: aload #7
    //   119: invokestatic isEmpty : (Ljava/lang/Object;)Z
    //   122: ifeq -> 138
    //   125: aload_2
    //   126: getstatic top/ibase4j/core/support/http/HttpCode.NOT_ACCEPTABLE : Ltop/ibase4j/core/support/http/HttpCode;
    //   129: invokevirtual value : ()Ljava/lang/Integer;
    //   132: ldc '请求无效'
    //   134: invokestatic write : (Ljavax/servlet/ServletResponse;Ljava/lang/Integer;Ljava/lang/String;)Z
    //   137: ireturn
    //   138: getstatic top/ibase4j/core/interceptor/SignInterceptor.logger : Lorg/apache/logging/log4j/Logger;
    //   141: ldc 'timestamp={}'
    //   143: aload #7
    //   145: invokeinterface info : (Ljava/lang/String;Ljava/lang/Object;)V
    //   150: invokestatic currentTimeMillis : ()J
    //   153: aload #7
    //   155: invokestatic valueOf : (Ljava/lang/String;)Ljava/lang/Long;
    //   158: invokevirtual longValue : ()J
    //   161: lsub
    //   162: invokestatic abs : (J)J
    //   165: ldc2_w 300000
    //   168: lcmp
    //   169: ifle -> 185
    //   172: aload_2
    //   173: getstatic top/ibase4j/core/support/http/HttpCode.FORBIDDEN : Ltop/ibase4j/core/support/http/HttpCode;
    //   176: invokevirtual value : ()Ljava/lang/Integer;
    //   179: ldc '请求已过期'
    //   181: invokestatic write : (Ljavax/servlet/ServletResponse;Ljava/lang/Integer;Ljava/lang/String;)Z
    //   184: ireturn
    //   185: aload_1
    //   186: invokestatic getParameterMap : (Ljavax/servlet/http/HttpServletRequest;)Ljava/util/Map;
    //   189: astore #8
    //   191: aload #8
    //   193: invokeinterface keySet : ()Ljava/util/Set;
    //   198: iconst_0
    //   199: anewarray java/lang/String
    //   202: invokeinterface toArray : ([Ljava/lang/Object;)[Ljava/lang/Object;
    //   207: checkcast [Ljava/lang/String;
    //   210: astore #9
    //   212: aload #9
    //   214: invokestatic sort : ([Ljava/lang/Object;)V
    //   217: new java/lang/StringBuilder
    //   220: dup
    //   221: invokespecial <init> : ()V
    //   224: astore #10
    //   226: aload #10
    //   228: ldc 'timestamp='
    //   230: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   233: aload #7
    //   235: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   238: pop
    //   239: aload #9
    //   241: astore #11
    //   243: aload #11
    //   245: arraylength
    //   246: istore #12
    //   248: iconst_0
    //   249: istore #13
    //   251: iload #13
    //   253: iload #12
    //   255: if_icmpge -> 311
    //   258: aload #11
    //   260: iload #13
    //   262: aaload
    //   263: astore #14
    //   265: ldc 'dataFile'
    //   267: aload #14
    //   269: invokevirtual equals : (Ljava/lang/Object;)Z
    //   272: ifne -> 305
    //   275: aload #10
    //   277: ldc '&'
    //   279: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   282: aload #14
    //   284: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   287: ldc '='
    //   289: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   292: aload #8
    //   294: aload #14
    //   296: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   301: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   304: pop
    //   305: iinc #13, 1
    //   308: goto -> 251
    //   311: aload #10
    //   313: invokevirtual toString : ()Ljava/lang/String;
    //   316: ldc 'UTF-8'
    //   318: invokestatic encode : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   321: invokestatic md5Hex : (Ljava/lang/String;)Ljava/lang/String;
    //   324: astore #11
    //   326: aload #11
    //   328: invokevirtual toLowerCase : ()Ljava/lang/String;
    //   331: aload #6
    //   333: invokevirtual toLowerCase : ()Ljava/lang/String;
    //   336: invokevirtual equals : (Ljava/lang/Object;)Z
    //   339: ifne -> 369
    //   342: getstatic top/ibase4j/core/interceptor/SignInterceptor.logger : Lorg/apache/logging/log4j/Logger;
    //   345: ldc 'sign={} 错误， 正确sign= {}'
    //   347: aload #6
    //   349: aload #11
    //   351: invokeinterface warn : (Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V
    //   356: aload_2
    //   357: getstatic top/ibase4j/core/support/http/HttpCode.FORBIDDEN : Ltop/ibase4j/core/support/http/HttpCode;
    //   360: invokevirtual value : ()Ljava/lang/Integer;
    //   363: ldc '签名错误'
    //   365: invokestatic write : (Ljavax/servlet/ServletResponse;Ljava/lang/Integer;Ljava/lang/String;)Z
    //   368: ireturn
    //   369: getstatic top/ibase4j/core/interceptor/SignInterceptor.logger : Lorg/apache/logging/log4j/Logger;
    //   372: ldc 'SignInterceptor successful'
    //   374: invokeinterface info : (Ljava/lang/String;)V
    //   379: iconst_1
    //   380: ireturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #44	-> 0
    //   #45	-> 11
    //   #46	-> 21
    //   #47	-> 36
    //   #48	-> 46
    //   #50	-> 48
    //   #51	-> 64
    //   #52	-> 74
    //   #54	-> 76
    //   #55	-> 86
    //   #56	-> 94
    //   #58	-> 107
    //   #59	-> 117
    //   #60	-> 125
    //   #62	-> 138
    //   #63	-> 150
    //   #64	-> 172
    //   #67	-> 185
    //   #68	-> 191
    //   #69	-> 212
    //   #70	-> 217
    //   #71	-> 226
    //   #72	-> 239
    //   #73	-> 265
    //   #74	-> 275
    //   #72	-> 305
    //   #78	-> 311
    //   #79	-> 326
    //   #80	-> 342
    //   #81	-> 356
    //   #83	-> 369
    //   #84	-> 379
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   265	40	14	key	Ljava/lang/String;
    //   0	381	0	this	Ltop/ibase4j/core/interceptor/SignInterceptor;
    //   0	381	1	request	Ljavax/servlet/http/HttpServletRequest;
    //   0	381	2	response	Ljavax/servlet/http/HttpServletResponse;
    //   0	381	3	handler	Ljava/lang/Object;
    //   11	370	4	url	Ljava/lang/String;
    //   21	360	5	refer	Ljava/lang/String;
    //   86	295	6	sign	Ljava/lang/String;
    //   117	264	7	timestamp	Ljava/lang/String;
    //   191	190	8	params	Ljava/util/Map;
    //   212	169	9	keys	[Ljava/lang/String;
    //   226	155	10	sb	Ljava/lang/StringBuilder;
    //   326	55	11	encrypt	Ljava/lang/String;
    // Local variable type table:
    //   start	length	slot	name	signature
    //   191	190	8	params	Ljava/util/Map<Ljava/lang/String;Ljava/lang/Object;>; }




//  }

















  
  public static void main(String[] args) throws UnsupportedEncodingException {
    String encrypt = DigestUtils.md5Hex(URLEncoder.encode("timestamp=1551017726066", "UTF-8"));
    System.out.println(encrypt);
  }
}
