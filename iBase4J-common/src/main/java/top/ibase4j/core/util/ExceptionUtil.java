package top.ibase4j.core.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.http.HttpServletRequest;











public class ExceptionUtil
{
  public static RuntimeException unchecked(Exception e) {
    if (e instanceof RuntimeException) {
      return (RuntimeException)e;
    }
    return new RuntimeException(e);
  }




  
  public static String getStackTraceAsString(Throwable e) {
    if (e == null) {
      return "";
    }
    StringWriter stringWriter = new StringWriter();
    e.printStackTrace(new PrintWriter(stringWriter));
    return stringWriter.toString();
  }










  
  @SafeVarargs
  public static boolean isCausedBy(Exception ex, Class... causeExceptionClasses) {
	return false;} // Byte code:
    //   0: aload_0
    //   1: invokevirtual getCause : ()Ljava/lang/Throwable;
    //   4: astore_2
    //   5: aload_2
    //   6: ifnull -> 56
    //   9: aload_1
    //   10: astore_3
    //   11: aload_3
    //   12: arraylength
    //   13: istore #4
    //   15: iconst_0
    //   16: istore #5
    //   18: iload #5
    //   20: iload #4
    //   22: if_icmpge -> 48
    //   25: aload_3
    //   26: iload #5
    //   28: aaload
    //   29: astore #6
    //   31: aload #6
    //   33: aload_2
    //   34: invokevirtual isInstance : (Ljava/lang/Object;)Z
    //   37: ifeq -> 42
    //   40: iconst_1
    //   41: ireturn
    //   42: iinc #5, 1
    //   45: goto -> 18
    //   48: aload_2
    //   49: invokevirtual getCause : ()Ljava/lang/Throwable;
    //   52: astore_2
    //   53: goto -> 5
    //   56: iconst_0
    //   57: ireturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #44	-> 0
    //   #45	-> 5
    //   #46	-> 9
    //   #47	-> 31
    //   #48	-> 40
    //   #46	-> 42
    //   #51	-> 48
    //   #53	-> 56
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   31	11	6	causeClass	Ljava/lang/Class;
    //   0	58	0	ex	Ljava/lang/Exception;
    //   0	58	1	causeExceptionClasses	[Ljava/lang/Class;
    //   5	53	2	cause	Ljava/lang/Throwable;
    // Local variable type table:
    //   start	length	slot	name	signature
    //   31	11	6	causeClass	Ljava/lang/Class<+Ljava/lang/Exception;>;
    //   0	58	1	causeExceptionClasses	[Ljava/lang/Class<+Ljava/lang/Exception;>; }










  
  public static Throwable getThrowable(HttpServletRequest request) {
    Throwable ex = null;
    if (request.getAttribute("exception") != null) {
      ex = (Throwable)request.getAttribute("exception");
    } else if (request.getAttribute("javax.servlet.error.exception") != null) {
      ex = (Throwable)request.getAttribute("javax.servlet.error.exception");
    } 
    return ex;
  }
}
