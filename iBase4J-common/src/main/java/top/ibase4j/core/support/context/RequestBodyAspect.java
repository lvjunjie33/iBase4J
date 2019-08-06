package top.ibase4j.core.support.context;

import java.lang.reflect.Method;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import top.ibase4j.core.util.InstanceUtil;













@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class RequestBodyAspect
{
  private final Logger logger = LogManager.getLogger();
  private static Map<Class<?>, Method[]> methodMap = InstanceUtil.newHashMap();










  
  @Pointcut("execution(* *..*.web..*Controller.*(..))")
  public void requestBody() {}










  
  @Before("requestBody()")
  public void before(JoinPoint pjp) { 
  // Byte code:
  }
    //   0: aload_1
    //   1: invokeinterface getSignature : ()Lorg/aspectj/lang/Signature;
    //   6: invokeinterface getName : ()Ljava/lang/String;
    //   11: astore_2
    //   12: aload_1
    //   13: invokeinterface getTarget : ()Ljava/lang/Object;
    //   18: invokevirtual getClass : ()Ljava/lang/Class;
    //   21: astore_3
    //   22: aload_0
    //   23: aload_3
    //   24: invokespecial getMethods : (Ljava/lang/Class;)[Ljava/lang/reflect/Method;
    //   27: astore #4
    //   29: aload #4
    //   31: astore #5
    //   33: aload #5
    //   35: arraylength
    //   36: istore #6
    //   38: iconst_0
    //   39: istore #7
    //   41: iload #7
    //   43: iload #6
    //   45: if_icmpge -> 213
    //   48: aload #5
    //   50: iload #7
    //   52: aaload
    //   53: astore #8
    //   55: aload #8
    //   57: invokevirtual getName : ()Ljava/lang/String;
    //   60: aload_2
    //   61: invokevirtual equals : (Ljava/lang/Object;)Z
    //   64: ifeq -> 207
    //   67: aload #8
    //   69: invokevirtual getParameters : ()[Ljava/lang/reflect/Parameter;
    //   72: astore #9
    //   74: iconst_0
    //   75: istore #10
    //   77: iload #10
    //   79: aload #9
    //   81: arraylength
    //   82: if_icmpge -> 207
    //   85: aload #9
    //   87: iload #10
    //   89: aaload
    //   90: astore #11
    //   92: aload_1
    //   93: invokeinterface getArgs : ()[Ljava/lang/Object;
    //   98: iload #10
    //   100: aaload
    //   101: astore #12
    //   103: aload #11
    //   105: ldc org/springframework/web/bind/annotation/RequestBody
    //   107: invokevirtual getAnnotation : (Ljava/lang/Class;)Ljava/lang/annotation/Annotation;
    //   110: checkcast org/springframework/web/bind/annotation/RequestBody
    //   113: astore #13
    //   115: aload #13
    //   117: ifnull -> 201
    //   120: aload_0
    //   121: getfield logger : Lorg/apache/logging/log4j/Logger;
    //   124: new java/lang/StringBuilder
    //   127: dup
    //   128: invokespecial <init> : ()V
    //   131: ldc 'Save RequestBody=>'
    //   133: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   136: aload_3
    //   137: invokevirtual getName : ()Ljava/lang/String;
    //   140: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   143: ldc '.'
    //   145: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   148: aload_2
    //   149: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   152: invokevirtual toString : ()Ljava/lang/String;
    //   155: invokeinterface info : (Ljava/lang/String;)V
    //   160: aload #12
    //   162: invokestatic toJSONString : (Ljava/lang/Object;)Ljava/lang/String;
    //   165: astore #14
    //   167: aload_0
    //   168: getfield logger : Lorg/apache/logging/log4j/Logger;
    //   171: ldc 'request body===>{}'
    //   173: aload #14
    //   175: invokeinterface info : (Ljava/lang/String;Ljava/lang/Object;)V
    //   180: getstatic top/ibase4j/core/util/WebUtil.REQUEST : Ljava/lang/ThreadLocal;
    //   183: invokevirtual get : ()Ljava/lang/Object;
    //   186: checkcast javax/servlet/http/HttpServletRequest
    //   189: ldc 'iBase4J.requestBody'
    //   191: aload #14
    //   193: invokeinterface setAttribute : (Ljava/lang/String;Ljava/lang/Object;)V
    //   198: goto -> 213
    //   201: iinc #10, 1
    //   204: goto -> 77
    //   207: iinc #7, 1
    //   210: goto -> 41
    //   213: goto -> 230
    //   216: astore_2
    //   217: aload_0
    //   218: getfield logger : Lorg/apache/logging/log4j/Logger;
    //   221: aload_2
    //   222: invokestatic getStackTraceAsString : (Ljava/lang/Throwable;)Ljava/lang/String;
    //   225: invokeinterface error : (Ljava/lang/String;)V
    //   230: return
    // Line number table:
    //   Java source line number -> byte code offset
    //   #46	-> 0
    //   #47	-> 12
    //   #48	-> 22
    //   #49	-> 29
    //   #50	-> 55
    //   #51	-> 67
    //   #52	-> 74
    //   #53	-> 85
    //   #54	-> 92
    //   #55	-> 103
    //   #56	-> 115
    //   #57	-> 120
    //   #58	-> 160
    //   #59	-> 167
    //   #60	-> 180
    //   #61	-> 198
    //   #52	-> 201
    //   #49	-> 207
    //   #68	-> 213
    //   #66	-> 216
    //   #67	-> 217
    //   #69	-> 230
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   167	34	14	body	Ljava/lang/String;
    //   92	109	11	parameter	Ljava/lang/reflect/Parameter;
    //   103	98	12	value	Ljava/lang/Object;
    //   115	86	13	rb	Lorg/springframework/web/bind/annotation/RequestBody;
    //   77	130	10	i	I
    //   74	133	9	ps	[Ljava/lang/reflect/Parameter;
    //   55	152	8	method	Ljava/lang/reflect/Method;
    //   12	201	2	methodName	Ljava/lang/String;
    //   22	191	3	cls	Ljava/lang/Class;
    //   29	184	4	methods	[Ljava/lang/reflect/Method;
    //   217	13	2	e	Ljava/lang/Exception;
    //   0	231	0	this	Ltop/ibase4j/core/support/context/RequestBodyAspect;
    //   0	231	1	pjp	Lorg/aspectj/lang/JoinPoint;
    // Local variable type table:
    //   start	length	slot	name	signature
    //   22	191	3	cls	Ljava/lang/Class<*>;
    // Exception table:
    //   from	to	target	type
    //   0	213	216	java/lang/Exception }










  
  private Method[] getMethods(Class<?> cls) {
    if (methodMap.containsKey(cls)) {
      return (Method[])methodMap.get(cls);
    }
    Method[] methods = cls.getDeclaredMethods();
    methodMap.put(cls, methods);
    return methods;
  }
}
