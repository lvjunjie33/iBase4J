package top.ibase4j.core.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import top.ibase4j.core.support.validate.RegexType;




































public final class DataUtil
{
  public static final String byte2hex(byte[] b) {
	return null;} // Byte code:
    //   0: new java/lang/StringBuilder
    //   3: dup
    //   4: aload_0
    //   5: arraylength
    //   6: iconst_2
    //   7: imul
    //   8: invokespecial <init> : (I)V
    //   11: astore_1
    //   12: ldc ''
    //   14: astore_2
    //   15: aload_0
    //   16: astore_3
    //   17: aload_3
    //   18: arraylength
    //   19: istore #4
    //   21: iconst_0
    //   22: istore #5
    //   24: iload #5
    //   26: iload #4
    //   28: if_icmpge -> 81
    //   31: aload_3
    //   32: iload #5
    //   34: baload
    //   35: istore #6
    //   37: iload #6
    //   39: sipush #255
    //   42: iand
    //   43: invokestatic toHexString : (I)Ljava/lang/String;
    //   46: astore_2
    //   47: aload_2
    //   48: invokevirtual length : ()I
    //   51: iconst_1
    //   52: if_icmpne -> 69
    //   55: aload_1
    //   56: ldc '0'
    //   58: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   61: aload_2
    //   62: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   65: pop
    //   66: goto -> 75
    //   69: aload_1
    //   70: aload_2
    //   71: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   74: pop
    //   75: iinc #5, 1
    //   78: goto -> 24
    //   81: aload_1
    //   82: invokevirtual toString : ()Ljava/lang/String;
    //   85: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #37	-> 0
    //   #38	-> 12
    //   #39	-> 15
    //   #41	-> 37
    //   #42	-> 47
    //   #43	-> 55
    //   #45	-> 69
    //   #39	-> 75
    //   #48	-> 81
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   37	38	6	element	B
    //   0	86	0	b	[B
    //   12	74	1	hs	Ljava/lang/StringBuilder;
    //   15	71	2	stmp	Ljava/lang/String; }
  
  public static final byte[] hex2byte(String hs) {
    byte[] b = hs.getBytes();
    if (b.length % 2 != 0) {
      throw new IllegalArgumentException("长度不是偶数");
    }
    byte[] b2 = new byte[b.length / 2];
    for (int n = 0; n < b.length; n += 2) {
      String item = new String(b, n, 2);
      
      b2[n / 2] = (byte)Integer.parseInt(item, 16);
    } 
    return b2;
  }













  
  public static final String getFullPathRelateClass(String relatedPath, Class<?> cls) {
    String path = null;
    if (relatedPath == null) {
      throw new NullPointerException();
    }
    String clsPath = getPathFromClass(cls);
    File clsFile = new File(clsPath);
    String tempPath = clsFile.getParent() + File.separator + relatedPath;
    File file = new File(tempPath);
    try {
      path = file.getCanonicalPath();
    } catch (IOException e) {
      e.printStackTrace();
    } 
    return path;
  }






  
  public static final String getPathFromClass(Class<?> cls) {
    String path = null;
    if (cls == null) {
      throw new NullPointerException();
    }
    URL url = getClassLocationURL(cls);
    if (url != null) {
      path = url.getPath();
      if ("jar".equalsIgnoreCase(url.getProtocol())) {
        try {
          path = (new URL(path)).getPath();
        } catch (MalformedURLException malformedURLException) {}
        
        int location = path.indexOf("!/");
        if (location != -1) {
          path = path.substring(0, location);
        }
      } 
      File file = new File(path);
      try {
        path = file.getCanonicalPath();
      } catch (IOException e) {
        e.printStackTrace();
      } 
    } 
    return path;
  }








  
  public static final boolean isEmpty(Object pObj) {
    if (pObj == null || "".equals(pObj)) {
      return true;
    }
    if (pObj instanceof String) {
      if (((String)pObj).trim().length() == 0) {
        return true;
      }
    } else if (pObj instanceof Collection) {
      if (((Collection)pObj).size() == 0) {
        return true;
      }
    } else if (pObj instanceof Map && (
      (Map)pObj).size() == 0) {
      return true;
    } 
    
    return false;
  }








  
  public static final boolean isNotEmpty(Object pObj) {
    if (pObj == null || "".equals(pObj)) {
      return false;
    }
    if (pObj instanceof String) {
      if (((String)pObj).trim().length() == 0) {
        return false;
      }
    } else if (pObj instanceof Collection) {
      if (((Collection)pObj).size() == 0) {
        return false;
      }
    } else if (pObj instanceof Map && (
      (Map)pObj).size() == 0) {
      return false;
    } 
    
    return true;
  }






  
  public static final String[] trim(String[] paramArray) {
    if (ArrayUtils.isEmpty(paramArray)) {
      return paramArray;
    }
    String[] resultArray = new String[paramArray.length];
    for (int i = 0; i < paramArray.length; i++) {
      String param = paramArray[i];
      resultArray[i] = StringUtils.trim(param);
    } 
    return resultArray;
  }






  
  private static URL getClassLocationURL(Class<?> cls) {
    if (cls == null) {
      throw new IllegalArgumentException("null input: cls");
    }
    URL result = null;
    String clsAsResource = cls.getName().replace('.', '/').concat(".class");
    ProtectionDomain pd = cls.getProtectionDomain();
    if (pd != null) {
      CodeSource cs = pd.getCodeSource();
      if (cs != null) {
        result = cs.getLocation();
      }
      if (result != null && 
        "file".equals(result.getProtocol())) {
        try {
          if (result.toExternalForm().endsWith(".jar") || result.toExternalForm().endsWith(".zip")) {
            result = new URL("jar:".concat(result.toExternalForm()).concat("!/").concat(clsAsResource));
          } else if ((new File(result.getFile())).isDirectory()) {
            result = new URL(result, clsAsResource);
          } 
        } catch (MalformedURLException malformedURLException) {}
      }
    } 

    
    if (result == null) {
      ClassLoader clsLoader = cls.getClassLoader();
      
      result = (clsLoader != null) ? clsLoader.getResource(clsAsResource) : ClassLoader.getSystemResource(clsAsResource);
    } 
    return result;
  }

  
  public static final <K> K ifNull(K k, K defaultValue) {
    if (k == null) {
      return defaultValue;
    }
    return k;
  }





  
  public static boolean isIp(String ip) {
    if (isEmpty(ip)) {
      return false;
    }
    return ip.matches(RegexType.IP.value());
  }





  
  public static boolean isEmail(String email) {
    if (isEmpty(email)) {
      return false;
    }
    return email.matches(RegexType.EMAIL.value());
  }





  
  public static boolean isNumber(String number) {
    if (isEmpty(number)) {
      return false;
    }
    return number.matches(RegexType.NUMBER.value());
  }






  
  public static boolean isDecimal(String decimal, int count) {
    if (isEmpty(decimal)) {
      return false;
    }
    String regex = "^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){" + count + "})?$";
    return decimal.matches(regex);
  }





  
  public static boolean isPhone(String phoneNumber) {
    if (isEmpty(phoneNumber)) {
      return false;
    }
    return phoneNumber.matches(RegexType.PHONE.value());
  }






  
  public static boolean isTelephone(String telephone) {
    if (isEmpty(telephone)) {
      return false;
    }
    return telephone.matches(RegexType.TELEPHONE.value());
  }





  
  public static boolean hasSpecialChar(String text) {
    if (isEmpty(text)) {
      return false;
    }
    if (text.replaceAll("[a-z]*[A-Z]*\\d*-*_*\\s*", "").length() == 0)
    {
      return true;
    }
    return false;
  }





  
  public static boolean isPassword(String value) {
    if (isEmpty(value)) {
      return false;
    }
    return value.matches(RegexType.PASSWORD.value());
  }





  
  public static boolean isChinese(String text) {
    if (isEmpty(text)) {
      return false;
    }
    Pattern p = Pattern.compile(RegexType.CHINESE.value());
    Matcher m = p.matcher(text);
    return m.find();
  }






  
  public static boolean isChinese2(String strName) {
	return false; }// Byte code:
    //   0: aload_0
    //   1: invokevirtual toCharArray : ()[C
    //   4: astore_1
    //   5: aload_1
    //   6: astore_2
    //   7: aload_2
    //   8: arraylength
    //   9: istore_3
    //   10: iconst_0
    //   11: istore #4
    //   13: iload #4
    //   15: iload_3
    //   16: if_icmpge -> 41
    //   19: aload_2
    //   20: iload #4
    //   22: caload
    //   23: istore #5
    //   25: iload #5
    //   27: invokestatic isChinese : (C)Z
    //   30: ifeq -> 35
    //   33: iconst_1
    //   34: ireturn
    //   35: iinc #4, 1
    //   38: goto -> 13
    //   41: iconst_0
    //   42: ireturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #377	-> 0
    //   #378	-> 5
    //   #379	-> 25
    //   #380	-> 33
    //   #378	-> 35
    //   #383	-> 41
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   25	10	5	c	C
    //   0	43	0	strName	Ljava/lang/String;
    //   5	38	1	ch	[C }





  
  private static boolean isChinese(char c) {
    Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
    if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION)
    {




      
      return true;
    }
    return false;
  }

  
  static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+(.\\d+)?$");
  
  public static final boolean isNumber(Object object) { return NUMBER_PATTERN.matcher(object.toString()).matches(); }







  
  public static final String format(Number obj, String pattern) {
    if (obj == null) {
      return null;
    }
    if (pattern == null || "".equals(pattern)) {
      pattern = "#";
    }
    DecimalFormat format = new DecimalFormat(pattern);
    return format.format(obj);
  }
}
