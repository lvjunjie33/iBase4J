package top.ibase4j.core.support;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import top.ibase4j.core.support.context.Resources;
import top.ibase4j.core.util.IDCardUtil;









public abstract class Assert
{
  private static String getMessage(String key, Object... args) { return Resources.getMessage(key, args); }


  
  public static void isTrue(boolean expression, String key) {
    if (!expression) {
      throw new IllegalArgumentException(getMessage(key, new Object[0]));
    }
  }

  
  public static void isNull(Object object, String key) {
    if (object != null) {
      throw new IllegalArgumentException(getMessage(key, new Object[0]));
    }
  }

  
  public static void notNull(Object object, String key, Object... args) {
    if (object == null) {
      throw new IllegalArgumentException(getMessage(key + "_IS_NULL", args));
    }
  }

  
  public static void hasLength(String text, String key) {
    if (StringUtils.isEmpty(text)) {
      throw new IllegalArgumentException(getMessage(key, new Object[0]));
    }
  }

  
  public static void hasText(String text, String key) {
    if (StringUtils.isBlank(text)) {
      throw new IllegalArgumentException(getMessage(key, new Object[0]));
    }
  }

  
  public static void doesNotContain(String textToSearch, String substring, String key) {
    if (StringUtils.isNotBlank(textToSearch) && StringUtils.isNotBlank(substring) && textToSearch
      .contains(substring)) {
      throw new IllegalArgumentException(getMessage(key, new Object[0]));
    }
  }

  
  public static void notEmpty(Object[] array, String key, Object... args) {
    if (ObjectUtils.isEmpty(array)) {
      throw new IllegalArgumentException(getMessage(key + "_IS_EMPTY", args));
    }
  }





  
  public static void noNullElements(Object[] array, String key) {} // Byte code:
    //   0: aload_0
    //   1: ifnull -> 51
    //   4: aload_0
    //   5: astore_2
    //   6: aload_2
    //   7: arraylength
    //   8: istore_3
    //   9: iconst_0
    //   10: istore #4
    //   12: iload #4
    //   14: iload_3
    //   15: if_icmpge -> 51
    //   18: aload_2
    //   19: iload #4
    //   21: aaload
    //   22: astore #5
    //   24: aload #5
    //   26: ifnonnull -> 45
    //   29: new java/lang/IllegalArgumentException
    //   32: dup
    //   33: aload_1
    //   34: iconst_0
    //   35: anewarray java/lang/Object
    //   38: invokestatic getMessage : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   41: invokespecial <init> : (Ljava/lang/String;)V
    //   44: athrow
    //   45: iinc #4, 1
    //   48: goto -> 12
    //   51: return
    // Line number table:
    //   Java source line number -> byte code offset
    //   #79	-> 0
    //   #80	-> 4
    //   #81	-> 24
    //   #82	-> 29
    //   #80	-> 45
    //   #86	-> 51
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   24	21	5	element	Ljava/lang/Object;
    //   0	52	0	array	[Ljava/lang/Object;
    //   0	52	1	key	Ljava/lang/String; }





  
  public static void notEmpty(Collection<?> collection, String key) {
    if (CollectionUtils.isEmpty(collection)) {
      throw new IllegalArgumentException(getMessage(key, new Object[0]));
    }
  }

  
  public static void notEmpty(Map<?, ?> map, String key) {
    if (CollectionUtils.isEmpty(map)) {
      throw new IllegalArgumentException(getMessage(key, new Object[0]));
    }
  }

  
  public static void isInstanceOf(Class<?> type, Object obj, String key) {
    notNull(type, key, new Object[0]);
    if (!type.isInstance(obj)) {
      throw new IllegalArgumentException(getMessage(key, new Object[0]));
    }
  }

  
  public static void isAssignable(Class<?> superType, Class<?> subType, String key) {
    notNull(superType, key, new Object[0]);
    if (subType == null || !superType.isAssignableFrom(subType)) {
      throw new IllegalArgumentException(getMessage(key, new Object[0]));
    }
  }

  
  public static void isBlank(String text, String key) {
    if (StringUtils.isNotBlank(text)) {
      throw new IllegalArgumentException(getMessage(key, new Object[0]));
    }
  }

  
  public static void isNotBlank(String text, String key) {
    if (StringUtils.isBlank(text)) {
      throw new IllegalArgumentException(getMessage(key + "_IS_NULL", new Object[0]));
    }
  }

  
  public static void min(Integer value, Integer min, String key) {
    notNull(value, key, new Object[0]);
    if (value.intValue() < min.intValue()) {
      throw new IllegalArgumentException(getMessage(key + "_MIN", new Object[] { min }));
    }
  }

  
  public static void max(Integer value, Integer max, String key) {
    notNull(value, key, new Object[0]);
    if (value.intValue() > max.intValue()) {
      throw new IllegalArgumentException(getMessage(key + "_MAX", new Object[] { max }));
    }
  }

  
  public static void range(Integer value, Integer min, Integer max, String key) {
    min(value, min, key);
    max(value, max, key);
  }

  
  public static void min(Float value, Float min, String key) {
    notNull(value, key, new Object[0]);
    if (value.floatValue() < min.floatValue()) {
      throw new IllegalArgumentException(getMessage(key + "_MIN", new Object[] { min }));
    }
  }

  
  public static void max(Float value, Float max, String key) {
    notNull(value, key, new Object[0]);
    if (value.floatValue() > max.floatValue()) {
      throw new IllegalArgumentException(getMessage(key + "_MAX", new Object[] { max }));
    }
  }

  
  public static void range(Float value, Float min, Float max, String key) {
    min(value, min, key);
    max(value, max, key);
  }

  
  public static void min(Double value, Double min, String key) {
    notNull(value, key, new Object[0]);
    if (value.doubleValue() < min.doubleValue()) {
      throw new IllegalArgumentException(getMessage(key + "_MIN", new Object[] { min }));
    }
  }

  
  public static void max(Double value, Double max, String key) {
    notNull(value, key, new Object[0]);
    if (value.doubleValue() > max.doubleValue()) {
      throw new IllegalArgumentException(getMessage(key + "_MAX", new Object[] { max }));
    }
  }

  
  public static void range(Double value, Double min, Double max, String key) {
    min(value, min, key);
    max(value, max, key);
  }

  
  public static void length(String text, Integer min, Integer max, String key) {
    notNull(text, key, new Object[0]);
    if (min != null && text.length() < min.intValue()) {
      throw new IllegalArgumentException(getMessage(key + "_LENGTH", new Object[] { min, max }));
    }
    if (max != null && text.length() > max.intValue()) {
      throw new IllegalArgumentException(getMessage(key + "_LENGTH", new Object[] { min, max }));
    }
  }

  
  public static void future(Date date, String key) {
    if (date != null && date.compareTo(new Date()) <= 0) {
      throw new IllegalArgumentException(getMessage(key + "_NOT_FUTURE", new Object[0]));
    }
  }

  
  public static void idCard(String text) throws Throwable {
    if (!IDCardUtil.isIdentity(text)) {
      throw new IllegalArgumentException(getMessage("IDCARD_ILLEGAL", new Object[0]));
    }
  }

  
  public static void email(String text) {
    String regex = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    pattern(text, regex, true, "EMAIL");
  }

  
  public static void mobile(String text) {
    String regex = "((^(13|15|17|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
    pattern(text, regex, true, "MOBILE");
  }

  
  public static void pattern(String text, String regex, boolean flag, String key) {
    boolean result = false;
    try {
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(text);
      result = matcher.matches();
    } catch (Exception e) {
      result = false;
    } 
    if (result != flag)
      throw new IllegalArgumentException(text + "->" + getMessage(key + "_ILLEGAL", new Object[0])); 
  }
}
