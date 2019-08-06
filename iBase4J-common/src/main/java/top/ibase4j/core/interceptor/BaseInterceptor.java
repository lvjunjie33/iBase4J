package top.ibase4j.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;






public class BaseInterceptor
  extends HandlerInterceptorAdapter
{
  protected static final Logger logger = LogManager.getLogger();
  
  private BaseInterceptor[] nextInterceptor;
  
  public void setNextInterceptor(BaseInterceptor... nextInterceptor) { this.nextInterceptor = nextInterceptor; }



  
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    if (this.nextInterceptor == null) {
      return true;
    }
    for (int i = 0; i < this.nextInterceptor.length; i++) {
      if (!this.nextInterceptor[i].preHandle(request, response, handler)) {
        return false;
      }
    } 
    return true;
  }
  
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception { // Byte code:
    //   0: aload_0
    //   1: getfield nextInterceptor : [Ltop/ibase4j/core/interceptor/BaseInterceptor;
    //   4: ifnull -> 51
    //   7: aload_0
    //   8: getfield nextInterceptor : [Ltop/ibase4j/core/interceptor/BaseInterceptor;
    //   11: astore #5
    //   13: aload #5
    //   15: arraylength
    //   16: istore #6
    //   18: iconst_0
    //   19: istore #7
    //   21: iload #7
    //   23: iload #6
    //   25: if_icmpge -> 51
    //   28: aload #5
    //   30: iload #7
    //   32: aaload
    //   33: astore #8
    //   35: aload #8
    //   37: aload_1
    //   38: aload_2
    //   39: aload_3
    //   40: aload #4
    //   42: invokevirtual postHandle : (Ljavax/servlet/http/HttpServletRequest;Ljavax/servlet/http/HttpServletResponse;Ljava/lang/Object;Lorg/springframework/web/servlet/ModelAndView;)V
    //   45: iinc #7, 1
    //   48: goto -> 21
    //   51: return
    // Line number table:
    //   Java source line number -> byte code offset
    //   #42	-> 0
    //   #43	-> 7
    //   #44	-> 35
    //   #43	-> 45
    //   #47	-> 51
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   35	10	8	element	Ltop/ibase4j/core/interceptor/BaseInterceptor;
    //   0	52	0	this	Ltop/ibase4j/core/interceptor/BaseInterceptor;
    //   0	52	1	request	Ljavax/servlet/http/HttpServletRequest;
    //   0	52	2	response	Ljavax/servlet/http/HttpServletResponse;
    //   0	52	3	handler	Ljava/lang/Object;
    //   0	52	4	modelAndView	Lorg/springframework/web/servlet/ModelAndView; }
  }
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception { // Byte code:
    //   0: aload_0
    //   1: getfield nextInterceptor : [Ltop/ibase4j/core/interceptor/BaseInterceptor;
    //   4: ifnull -> 51
    //   7: aload_0
    //   8: getfield nextInterceptor : [Ltop/ibase4j/core/interceptor/BaseInterceptor;
    //   11: astore #5
    //   13: aload #5
    //   15: arraylength
    //   16: istore #6
    //   18: iconst_0
    //   19: istore #7
    //   21: iload #7
    //   23: iload #6
    //   25: if_icmpge -> 51
    //   28: aload #5
    //   30: iload #7
    //   32: aaload
    //   33: astore #8
    //   35: aload #8
    //   37: aload_1
    //   38: aload_2
    //   39: aload_3
    //   40: aload #4
    //   42: invokevirtual afterCompletion : (Ljavax/servlet/http/HttpServletRequest;Ljavax/servlet/http/HttpServletResponse;Ljava/lang/Object;Ljava/lang/Exception;)V
    //   45: iinc #7, 1
    //   48: goto -> 21
    //   51: return
    // Line number table:
    //   Java source line number -> byte code offset
    //   #52	-> 0
    //   #53	-> 7
    //   #54	-> 35
    //   #53	-> 45
    //   #57	-> 51
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   35	10	8	element	Ltop/ibase4j/core/interceptor/BaseInterceptor;
    //   0	52	0	this	Ltop/ibase4j/core/interceptor/BaseInterceptor;
    //   0	52	1	request	Ljavax/servlet/http/HttpServletRequest;
    //   0	52	2	response	Ljavax/servlet/http/HttpServletResponse;
    //   0	52	3	handler	Ljava/lang/Object;
    //   0	52	4	ex	Ljava/lang/Exception; }
  }
  public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception { // Byte code:
    //   0: aload_0
    //   1: getfield nextInterceptor : [Ltop/ibase4j/core/interceptor/BaseInterceptor;
    //   4: ifnull -> 49
    //   7: aload_0
    //   8: getfield nextInterceptor : [Ltop/ibase4j/core/interceptor/BaseInterceptor;
    //   11: astore #4
    //   13: aload #4
    //   15: arraylength
    //   16: istore #5
    //   18: iconst_0
    //   19: istore #6
    //   21: iload #6
    //   23: iload #5
    //   25: if_icmpge -> 49
    //   28: aload #4
    //   30: iload #6
    //   32: aaload
    //   33: astore #7
    //   35: aload #7
    //   37: aload_1
    //   38: aload_2
    //   39: aload_3
    //   40: invokevirtual afterConcurrentHandlingStarted : (Ljavax/servlet/http/HttpServletRequest;Ljavax/servlet/http/HttpServletResponse;Ljava/lang/Object;)V
    //   43: iinc #6, 1
    //   46: goto -> 21
    //   49: return
    // Line number table:
    //   Java source line number -> byte code offset
    //   #62	-> 0
    //   #63	-> 7
    //   #64	-> 35
    //   #63	-> 43
    //   #67	-> 49
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   35	8	7	element	Ltop/ibase4j/core/interceptor/BaseInterceptor;
    //   0	50	0	this	Ltop/ibase4j/core/interceptor/BaseInterceptor;
    //   0	50	1	request	Ljavax/servlet/http/HttpServletRequest;
    //   0	50	2	response	Ljavax/servlet/http/HttpServletResponse;
    //   0	50	3	handler	Ljava/lang/Object; }
  }
}
