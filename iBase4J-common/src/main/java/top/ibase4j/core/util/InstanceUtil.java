package top.ibase4j.core.util;

import com.alibaba.fastjson.JSON;
import com.esotericsoftware.reflectasm.MethodAccess;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.ibase4j.core.exception.InstanceException;















public final class InstanceUtil
{
  protected static Logger logger = LogManager.getLogger();



  
  private static final Map<String, MethodAccess> methodMap = newHashMap();
  private static final Map<String, Field> fieldMap = newHashMap();




















  
  public static final <T> T to(Object orig, Class<T> clazz) {
	return null;} // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aload_1
    //   3: invokevirtual newInstance : ()Ljava/lang/Object;
    //   6: astore_2
    //   7: aload_0
    //   8: invokevirtual getClass : ()Ljava/lang/Class;
    //   11: astore_3
    //   12: aload_3
    //   13: invokestatic getBeanInfo : (Ljava/lang/Class;)Ljava/beans/BeanInfo;
    //   16: astore #4
    //   18: aload #4
    //   20: invokeinterface getPropertyDescriptors : ()[Ljava/beans/PropertyDescriptor;
    //   25: astore #5
    //   27: invokestatic newHashMap : ()Ljava/util/HashMap;
    //   30: astore #6
    //   32: aload #5
    //   34: astore #7
    //   36: aload #7
    //   38: arraylength
    //   39: istore #8
    //   41: iconst_0
    //   42: istore #9
    //   44: iload #9
    //   46: iload #8
    //   48: if_icmpge -> 79
    //   51: aload #7
    //   53: iload #9
    //   55: aaload
    //   56: astore #10
    //   58: aload #6
    //   60: aload #10
    //   62: invokevirtual getName : ()Ljava/lang/String;
    //   65: aload #10
    //   67: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   72: pop
    //   73: iinc #9, 1
    //   76: goto -> 44
    //   79: aload_1
    //   80: invokestatic getBeanInfo : (Ljava/lang/Class;)Ljava/beans/BeanInfo;
    //   83: astore #7
    //   85: aload #7
    //   87: invokeinterface getPropertyDescriptors : ()[Ljava/beans/PropertyDescriptor;
    //   92: astore #8
    //   94: aload #8
    //   96: astore #9
    //   98: aload #9
    //   100: arraylength
    //   101: istore #10
    //   103: iconst_0
    //   104: istore #11
    //   106: iload #11
    //   108: iload #10
    //   110: if_icmpge -> 290
    //   113: aload #9
    //   115: iload #11
    //   117: aaload
    //   118: astore #12
    //   120: aload #12
    //   122: invokevirtual getName : ()Ljava/lang/String;
    //   125: astore #13
    //   127: ldc 'class'
    //   129: aload #13
    //   131: invokevirtual equals : (Ljava/lang/Object;)Z
    //   134: ifne -> 284
    //   137: aload #6
    //   139: aload #13
    //   141: invokeinterface containsKey : (Ljava/lang/Object;)Z
    //   146: ifeq -> 284
    //   149: aload #6
    //   151: aload #13
    //   153: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   158: checkcast java/beans/PropertyDescriptor
    //   161: invokevirtual getReadMethod : ()Ljava/lang/reflect/Method;
    //   164: astore #14
    //   166: aload #14
    //   168: aload_0
    //   169: iconst_0
    //   170: anewarray java/lang/Object
    //   173: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   176: aload #12
    //   178: invokevirtual getPropertyType : ()Ljava/lang/Class;
    //   181: aconst_null
    //   182: invokestatic convert : (Ljava/lang/Object;Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Object;
    //   185: astore #15
    //   187: new java/lang/StringBuilder
    //   190: dup
    //   191: invokespecial <init> : ()V
    //   194: aload_1
    //   195: invokevirtual getName : ()Ljava/lang/String;
    //   198: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   201: ldc '.'
    //   203: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   206: aload #13
    //   208: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   211: invokevirtual toString : ()Ljava/lang/String;
    //   214: astore #16
    //   216: getstatic top/ibase4j/core/util/InstanceUtil.fieldMap : Ljava/util/Map;
    //   219: aload #16
    //   221: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   226: checkcast java/lang/reflect/Field
    //   229: astore #17
    //   231: aload #17
    //   233: ifnonnull -> 257
    //   236: aload_1
    //   237: aload #13
    //   239: invokevirtual getDeclaredField : (Ljava/lang/String;)Ljava/lang/reflect/Field;
    //   242: astore #17
    //   244: getstatic top/ibase4j/core/util/InstanceUtil.fieldMap : Ljava/util/Map;
    //   247: aload #16
    //   249: aload #17
    //   251: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   256: pop
    //   257: aload #17
    //   259: iconst_1
    //   260: invokevirtual setAccessible : (Z)V
    //   263: aload #17
    //   265: aload_2
    //   266: aload #15
    //   268: invokevirtual set : (Ljava/lang/Object;Ljava/lang/Object;)V
    //   271: goto -> 284
    //   274: astore #16
    //   276: aload_2
    //   277: aload #13
    //   279: aload #15
    //   281: invokestatic setProperty : (Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)V
    //   284: iinc #11, 1
    //   287: goto -> 106
    //   290: goto -> 321
    //   293: astore_3
    //   294: getstatic top/ibase4j/core/util/InstanceUtil.logger : Lorg/apache/logging/log4j/Logger;
    //   297: new java/lang/StringBuilder
    //   300: dup
    //   301: invokespecial <init> : ()V
    //   304: ldc 'to Error '
    //   306: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   309: aload_3
    //   310: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   313: invokevirtual toString : ()Ljava/lang/String;
    //   316: invokeinterface error : (Ljava/lang/String;)V
    //   321: aload_2
    //   322: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #59	-> 0
    //   #61	-> 2
    //   #62	-> 7
    //   #63	-> 12
    //   #64	-> 18
    //   #65	-> 27
    //   #66	-> 32
    //   #67	-> 58
    //   #66	-> 73
    //   #70	-> 79
    //   #71	-> 85
    //   #72	-> 94
    //   #73	-> 120
    //   #75	-> 127
    //   #76	-> 149
    //   #77	-> 166
    //   #79	-> 187
    //   #80	-> 216
    //   #81	-> 231
    //   #82	-> 236
    //   #83	-> 244
    //   #85	-> 257
    //   #86	-> 263
    //   #89	-> 271
    //   #87	-> 274
    //   #88	-> 276
    //   #72	-> 284
    //   #94	-> 290
    //   #92	-> 293
    //   #93	-> 294
    //   #95	-> 321
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   58	15	10	property	Ljava/beans/PropertyDescriptor;
    //   216	55	16	fieldName	Ljava/lang/String;
    //   231	40	17	field	Ljava/lang/reflect/Field;
    //   276	8	16	e	Ljava/lang/Exception;
    //   166	118	14	getter	Ljava/lang/reflect/Method;
    //   187	97	15	value	Ljava/lang/Object;
    //   127	157	13	key	Ljava/lang/String;
    //   120	164	12	property	Ljava/beans/PropertyDescriptor;
    //   12	278	3	cls	Ljava/lang/Class;
    //   18	272	4	orgInfo	Ljava/beans/BeanInfo;
    //   27	263	5	orgPty	[Ljava/beans/PropertyDescriptor;
    //   32	258	6	propertyMap	Ljava/util/Map;
    //   85	205	7	beanInfo	Ljava/beans/BeanInfo;
    //   94	196	8	propertyDescriptors	[Ljava/beans/PropertyDescriptor;
    //   294	27	3	e	Ljava/lang/Exception;
    //   0	323	0	orig	Ljava/lang/Object;
    //   0	323	1	clazz	Ljava/lang/Class;
    //   2	321	2	bean	Ljava/lang/Object;
    // Local variable type table:
    //   start	length	slot	name	signature
    //   12	278	3	cls	Ljava/lang/Class<*>;
    //   32	258	6	propertyMap	Ljava/util/Map<Ljava/lang/String;Ljava/beans/PropertyDescriptor;>;
    //   0	323	1	clazz	Ljava/lang/Class<TT;>;
    //   2	321	2	bean	TT;
    // Exception table:
    //   from	to	target	type
    //   2	290	293	java/lang/Exception
    //   187	271	274	java/lang/Exception }




















  
  public static final <T> T parse(String json, Class<T> clazz) {
    try {
      Map map = (Map)JSON.parseObject(json, Map.class);
      return (T)transMap2Bean(map, clazz);
    } catch (Exception e) {
      logger.error("parse", e);
      
      return null;
    } 
  }






























  
  public static <T> T transMap2Bean(Map<String, Object> map, Class<T> clazz) {
	return null;} // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aload_1
    //   3: invokevirtual newInstance : ()Ljava/lang/Object;
    //   6: astore_2
    //   7: aload_1
    //   8: invokestatic getBeanInfo : (Ljava/lang/Class;)Ljava/beans/BeanInfo;
    //   11: astore_3
    //   12: aload_3
    //   13: invokeinterface getPropertyDescriptors : ()[Ljava/beans/PropertyDescriptor;
    //   18: astore #4
    //   20: aload #4
    //   22: astore #5
    //   24: aload #5
    //   26: arraylength
    //   27: istore #6
    //   29: iconst_0
    //   30: istore #7
    //   32: iload #7
    //   34: iload #6
    //   36: if_icmpge -> 203
    //   39: aload #5
    //   41: iload #7
    //   43: aaload
    //   44: astore #8
    //   46: aload #8
    //   48: invokevirtual getName : ()Ljava/lang/String;
    //   51: astore #9
    //   53: aload_0
    //   54: aload #9
    //   56: invokeinterface containsKey : (Ljava/lang/Object;)Z
    //   61: ifeq -> 180
    //   64: aload_0
    //   65: aload #9
    //   67: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   72: aload #8
    //   74: invokevirtual getPropertyType : ()Ljava/lang/Class;
    //   77: aconst_null
    //   78: invokestatic convert : (Ljava/lang/Object;Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Object;
    //   81: astore #10
    //   83: new java/lang/StringBuilder
    //   86: dup
    //   87: invokespecial <init> : ()V
    //   90: aload_1
    //   91: invokevirtual getName : ()Ljava/lang/String;
    //   94: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   97: ldc '.'
    //   99: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   102: aload #9
    //   104: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   107: invokevirtual toString : ()Ljava/lang/String;
    //   110: astore #11
    //   112: getstatic top/ibase4j/core/util/InstanceUtil.fieldMap : Ljava/util/Map;
    //   115: aload #11
    //   117: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   122: checkcast java/lang/reflect/Field
    //   125: astore #12
    //   127: aload #12
    //   129: ifnonnull -> 153
    //   132: aload_1
    //   133: aload #9
    //   135: invokevirtual getDeclaredField : (Ljava/lang/String;)Ljava/lang/reflect/Field;
    //   138: astore #12
    //   140: getstatic top/ibase4j/core/util/InstanceUtil.fieldMap : Ljava/util/Map;
    //   143: aload #11
    //   145: aload #12
    //   147: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   152: pop
    //   153: aload #12
    //   155: iconst_1
    //   156: invokevirtual setAccessible : (Z)V
    //   159: aload #12
    //   161: aload_2
    //   162: aload #10
    //   164: invokevirtual set : (Ljava/lang/Object;Ljava/lang/Object;)V
    //   167: goto -> 180
    //   170: astore #11
    //   172: aload_2
    //   173: aload #9
    //   175: aload #10
    //   177: invokestatic setProperty : (Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)V
    //   180: goto -> 197
    //   183: astore #9
    //   185: getstatic top/ibase4j/core/util/InstanceUtil.logger : Lorg/apache/logging/log4j/Logger;
    //   188: ldc 'transMap2Bean setter Error '
    //   190: aload #9
    //   192: invokeinterface error : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   197: iinc #7, 1
    //   200: goto -> 32
    //   203: goto -> 218
    //   206: astore_3
    //   207: getstatic top/ibase4j/core/util/InstanceUtil.logger : Lorg/apache/logging/log4j/Logger;
    //   210: ldc 'transMap2Bean Error '
    //   212: aload_3
    //   213: invokeinterface error : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   218: aload_2
    //   219: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #111	-> 0
    //   #113	-> 2
    //   #114	-> 7
    //   #115	-> 12
    //   #116	-> 20
    //   #118	-> 46
    //   #119	-> 53
    //   #120	-> 64
    //   #122	-> 83
    //   #123	-> 112
    //   #124	-> 127
    //   #125	-> 132
    //   #126	-> 140
    //   #128	-> 153
    //   #129	-> 159
    //   #132	-> 167
    //   #130	-> 170
    //   #131	-> 172
    //   #136	-> 180
    //   #134	-> 183
    //   #135	-> 185
    //   #116	-> 197
    //   #140	-> 203
    //   #138	-> 206
    //   #139	-> 207
    //   #141	-> 218
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   112	55	11	fieldName	Ljava/lang/String;
    //   127	40	12	field	Ljava/lang/reflect/Field;
    //   172	8	11	e	Ljava/lang/Exception;
    //   83	97	10	value	Ljava/lang/Object;
    //   53	127	9	key	Ljava/lang/String;
    //   185	12	9	e	Ljava/lang/Exception;
    //   46	151	8	property	Ljava/beans/PropertyDescriptor;
    //   12	191	3	beanInfo	Ljava/beans/BeanInfo;
    //   20	183	4	propertyDescriptors	[Ljava/beans/PropertyDescriptor;
    //   207	11	3	e	Ljava/lang/Exception;
    //   0	220	0	map	Ljava/util/Map;
    //   0	220	1	clazz	Ljava/lang/Class;
    //   2	218	2	bean	Ljava/lang/Object;
    // Local variable type table:
    //   start	length	slot	name	signature
    //   0	220	0	map	Ljava/util/Map<Ljava/lang/String;Ljava/lang/Object;>;
    //   0	220	1	clazz	Ljava/lang/Class<TT;>;
    //   2	218	2	bean	TT;
    // Exception table:
    //   from	to	target	type
    //   2	203	206	java/lang/Exception
    //   46	180	183	java/lang/Exception
    //   83	167	170	java/lang/Exception }






























  
  public static Map<String, Object> transBean2Map(Object obj) {
	return null;} // Byte code:
    //   0: invokestatic newHashMap : ()Ljava/util/HashMap;
    //   3: astore_1
    //   4: aload_0
    //   5: ifnonnull -> 10
    //   8: aload_1
    //   9: areturn
    //   10: aload_0
    //   11: invokevirtual getClass : ()Ljava/lang/Class;
    //   14: invokestatic getBeanInfo : (Ljava/lang/Class;)Ljava/beans/BeanInfo;
    //   17: astore_2
    //   18: aload_2
    //   19: invokeinterface getPropertyDescriptors : ()[Ljava/beans/PropertyDescriptor;
    //   24: astore_3
    //   25: aload_3
    //   26: astore #4
    //   28: aload #4
    //   30: arraylength
    //   31: istore #5
    //   33: iconst_0
    //   34: istore #6
    //   36: iload #6
    //   38: iload #5
    //   40: if_icmpge -> 103
    //   43: aload #4
    //   45: iload #6
    //   47: aaload
    //   48: astore #7
    //   50: aload #7
    //   52: invokevirtual getName : ()Ljava/lang/String;
    //   55: astore #8
    //   57: ldc 'class'
    //   59: aload #8
    //   61: invokevirtual equals : (Ljava/lang/Object;)Z
    //   64: ifne -> 97
    //   67: aload #7
    //   69: invokevirtual getReadMethod : ()Ljava/lang/reflect/Method;
    //   72: astore #9
    //   74: aload #9
    //   76: aload_0
    //   77: iconst_0
    //   78: anewarray java/lang/Object
    //   81: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   84: astore #10
    //   86: aload_1
    //   87: aload #8
    //   89: aload #10
    //   91: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   96: pop
    //   97: iinc #6, 1
    //   100: goto -> 36
    //   103: goto -> 134
    //   106: astore_2
    //   107: getstatic top/ibase4j/core/util/InstanceUtil.logger : Lorg/apache/logging/log4j/Logger;
    //   110: new java/lang/StringBuilder
    //   113: dup
    //   114: invokespecial <init> : ()V
    //   117: ldc 'transBean2Map Error '
    //   119: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   122: aload_2
    //   123: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   126: invokevirtual toString : ()Ljava/lang/String;
    //   129: invokeinterface error : (Ljava/lang/String;)V
    //   134: aload_1
    //   135: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #146	-> 0
    //   #147	-> 4
    //   #148	-> 8
    //   #151	-> 10
    //   #152	-> 18
    //   #153	-> 25
    //   #154	-> 50
    //   #156	-> 57
    //   #158	-> 67
    //   #159	-> 74
    //   #160	-> 86
    //   #153	-> 97
    //   #165	-> 103
    //   #163	-> 106
    //   #164	-> 107
    //   #166	-> 134
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   74	23	9	getter	Ljava/lang/reflect/Method;
    //   86	11	10	value	Ljava/lang/Object;
    //   57	40	8	key	Ljava/lang/String;
    //   50	47	7	property	Ljava/beans/PropertyDescriptor;
    //   18	85	2	beanInfo	Ljava/beans/BeanInfo;
    //   25	78	3	propertyDescriptors	[Ljava/beans/PropertyDescriptor;
    //   107	27	2	e	Ljava/lang/Exception;
    //   0	136	0	obj	Ljava/lang/Object;
    //   4	132	1	map	Ljava/util/Map;
    // Local variable type table:
    //   start	length	slot	name	signature
    //   4	132	1	map	Ljava/util/Map<Ljava/lang/String;Ljava/lang/Object;>;
    // Exception table:
    //   from	to	target	type
    //   10	103	106	java/lang/Exception }





























  
  public static <T> T getDiff(T oldBean, T newBean) {
	return newBean;} // Byte code:
    //   0: aload_0
    //   1: ifnonnull -> 10
    //   4: aload_1
    //   5: ifnull -> 10
    //   8: aload_1
    //   9: areturn
    //   10: aload_1
    //   11: ifnonnull -> 16
    //   14: aconst_null
    //   15: areturn
    //   16: aload_0
    //   17: invokevirtual getClass : ()Ljava/lang/Class;
    //   20: astore_2
    //   21: aload_2
    //   22: invokevirtual newInstance : ()Ljava/lang/Object;
    //   25: astore_3
    //   26: aload_2
    //   27: invokestatic getBeanInfo : (Ljava/lang/Class;)Ljava/beans/BeanInfo;
    //   30: astore #4
    //   32: aload #4
    //   34: invokeinterface getPropertyDescriptors : ()[Ljava/beans/PropertyDescriptor;
    //   39: astore #5
    //   41: aload #5
    //   43: astore #6
    //   45: aload #6
    //   47: arraylength
    //   48: istore #7
    //   50: iconst_0
    //   51: istore #8
    //   53: iload #8
    //   55: iload #7
    //   57: if_icmpge -> 246
    //   60: aload #6
    //   62: iload #8
    //   64: aaload
    //   65: astore #9
    //   67: aload #9
    //   69: invokevirtual getName : ()Ljava/lang/String;
    //   72: astore #10
    //   74: ldc 'class'
    //   76: aload #10
    //   78: invokevirtual equals : (Ljava/lang/Object;)Z
    //   81: ifne -> 240
    //   84: aload #9
    //   86: invokevirtual getReadMethod : ()Ljava/lang/reflect/Method;
    //   89: astore #11
    //   91: aload #11
    //   93: aload_0
    //   94: iconst_0
    //   95: anewarray java/lang/Object
    //   98: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   101: astore #12
    //   103: aload #11
    //   105: aload_1
    //   106: iconst_0
    //   107: anewarray java/lang/Object
    //   110: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   113: astore #13
    //   115: aload #13
    //   117: ifnull -> 240
    //   120: aload #13
    //   122: aload #12
    //   124: invokevirtual equals : (Ljava/lang/Object;)Z
    //   127: ifne -> 240
    //   130: aload #13
    //   132: aload #9
    //   134: invokevirtual getPropertyType : ()Ljava/lang/Class;
    //   137: aconst_null
    //   138: invokestatic convert : (Ljava/lang/Object;Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Object;
    //   141: astore #14
    //   143: new java/lang/StringBuilder
    //   146: dup
    //   147: invokespecial <init> : ()V
    //   150: aload_2
    //   151: invokevirtual getName : ()Ljava/lang/String;
    //   154: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   157: ldc '.'
    //   159: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   162: aload #10
    //   164: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   167: invokevirtual toString : ()Ljava/lang/String;
    //   170: astore #15
    //   172: getstatic top/ibase4j/core/util/InstanceUtil.fieldMap : Ljava/util/Map;
    //   175: aload #15
    //   177: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   182: checkcast java/lang/reflect/Field
    //   185: astore #16
    //   187: aload #16
    //   189: ifnonnull -> 213
    //   192: aload_2
    //   193: aload #10
    //   195: invokevirtual getDeclaredField : (Ljava/lang/String;)Ljava/lang/reflect/Field;
    //   198: astore #16
    //   200: getstatic top/ibase4j/core/util/InstanceUtil.fieldMap : Ljava/util/Map;
    //   203: aload #15
    //   205: aload #16
    //   207: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   212: pop
    //   213: aload #16
    //   215: iconst_1
    //   216: invokevirtual setAccessible : (Z)V
    //   219: aload #16
    //   221: aload_3
    //   222: aload #14
    //   224: invokevirtual set : (Ljava/lang/Object;Ljava/lang/Object;)V
    //   227: goto -> 240
    //   230: astore #15
    //   232: aload_3
    //   233: aload #10
    //   235: aload #14
    //   237: invokestatic setProperty : (Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)V
    //   240: iinc #8, 1
    //   243: goto -> 53
    //   246: aload_3
    //   247: areturn
    //   248: astore_3
    //   249: new top/ibase4j/core/exception/DataParseException
    //   252: dup
    //   253: aload_3
    //   254: invokespecial <init> : (Ljava/lang/Throwable;)V
    //   257: athrow
    // Line number table:
    //   Java source line number -> byte code offset
    //   #175	-> 0
    //   #176	-> 8
    //   #177	-> 10
    //   #178	-> 14
    //   #180	-> 16
    //   #183	-> 21
    //   #184	-> 26
    //   #185	-> 32
    //   #186	-> 41
    //   #187	-> 67
    //   #189	-> 74
    //   #191	-> 84
    //   #193	-> 91
    //   #194	-> 103
    //   #196	-> 115
    //   #197	-> 130
    //   #199	-> 143
    //   #200	-> 172
    //   #201	-> 187
    //   #202	-> 192
    //   #203	-> 200
    //   #205	-> 213
    //   #206	-> 219
    //   #209	-> 227
    //   #207	-> 230
    //   #208	-> 232
    //   #186	-> 240
    //   #213	-> 246
    //   #214	-> 248
    //   #215	-> 249
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   172	55	15	fieldName	Ljava/lang/String;
    //   187	40	16	field	Ljava/lang/reflect/Field;
    //   232	8	15	e	Ljava/lang/Exception;
    //   143	97	14	value	Ljava/lang/Object;
    //   91	149	11	getter	Ljava/lang/reflect/Method;
    //   103	137	12	oldValue	Ljava/lang/Object;
    //   115	125	13	newValue	Ljava/lang/Object;
    //   74	166	10	key	Ljava/lang/String;
    //   67	173	9	property	Ljava/beans/PropertyDescriptor;
    //   26	222	3	object	Ljava/lang/Object;
    //   32	216	4	beanInfo	Ljava/beans/BeanInfo;
    //   41	207	5	propertyDescriptors	[Ljava/beans/PropertyDescriptor;
    //   249	9	3	e	Ljava/lang/Exception;
    //   21	237	2	cls1	Ljava/lang/Class;
    //   0	258	0	oldBean	Ljava/lang/Object;
    //   0	258	1	newBean	Ljava/lang/Object;
    // Local variable type table:
    //   start	length	slot	name	signature
    //   26	222	3	object	TT;
    //   21	237	2	cls1	Ljava/lang/Class<*>;
    //   0	258	0	oldBean	TT;
    //   0	258	1	newBean	TT;
    // Exception table:
    //   from	to	target	type
    //   21	247	248	java/lang/Exception
    //   143	227	230	java/lang/Exception }





























  
  public static final Class<?> getClass(String clazz) {
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    try {
      if (loader != null) {
        return Class.forName(clazz, true, loader);
      }



      
      return Class.forName(clazz);
    } catch (ClassNotFoundException e) {
      throw new InstanceException(e);
    } 
  }









  
  public static final <E> List<E> getInstanceList(Class<E> cls, List<?> list) {
    List<E> resultList = newArrayList();
    E object = null;
    for (Object name : list) {
      Map<?, ?> map = (Map)name;
      object = (E)newInstance(cls, new Object[] { map });
      resultList.add(object);
    } 
    return resultList;
  }


















  
  public static final <E> List<E> getInstanceList(Class<E> cls, ResultSet rs) {
	return null;} // Byte code:
    //   0: invokestatic newArrayList : ()Ljava/util/ArrayList;
    //   3: astore_2
    //   4: aload_0
    //   5: invokevirtual newInstance : ()Ljava/lang/Object;
    //   8: astore_3
    //   9: aload_0
    //   10: invokevirtual getDeclaredFields : ()[Ljava/lang/reflect/Field;
    //   13: astore #4
    //   15: aload_1
    //   16: invokeinterface next : ()Z
    //   21: ifeq -> 93
    //   24: aload_0
    //   25: invokevirtual newInstance : ()Ljava/lang/Object;
    //   28: astore_3
    //   29: aload #4
    //   31: astore #5
    //   33: aload #5
    //   35: arraylength
    //   36: istore #6
    //   38: iconst_0
    //   39: istore #7
    //   41: iload #7
    //   43: iload #6
    //   45: if_icmpge -> 82
    //   48: aload #5
    //   50: iload #7
    //   52: aaload
    //   53: astore #8
    //   55: aload #8
    //   57: invokevirtual getName : ()Ljava/lang/String;
    //   60: astore #9
    //   62: aload_3
    //   63: aload #9
    //   65: aload_1
    //   66: aload #9
    //   68: invokeinterface getObject : (Ljava/lang/String;)Ljava/lang/Object;
    //   73: invokestatic setProperty : (Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)V
    //   76: iinc #7, 1
    //   79: goto -> 41
    //   82: aload_2
    //   83: aload_3
    //   84: invokeinterface add : (Ljava/lang/Object;)Z
    //   89: pop
    //   90: goto -> 15
    //   93: goto -> 106
    //   96: astore_3
    //   97: new top/ibase4j/core/exception/InstanceException
    //   100: dup
    //   101: aload_3
    //   102: invokespecial <init> : (Ljava/lang/Throwable;)V
    //   105: athrow
    //   106: aload_2
    //   107: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #280	-> 0
    //   #282	-> 4
    //   #283	-> 9
    //   #284	-> 15
    //   #285	-> 24
    //   #286	-> 29
    //   #287	-> 55
    //   #288	-> 62
    //   #286	-> 76
    //   #290	-> 82
    //   #294	-> 93
    //   #292	-> 96
    //   #293	-> 97
    //   #295	-> 106
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   62	14	9	fieldName	Ljava/lang/String;
    //   55	21	8	field	Ljava/lang/reflect/Field;
    //   9	84	3	object	Ljava/lang/Object;
    //   15	78	4	fields	[Ljava/lang/reflect/Field;
    //   97	9	3	e	Ljava/lang/Exception;
    //   0	108	0	cls	Ljava/lang/Class;
    //   0	108	1	rs	Ljava/sql/ResultSet;
    //   4	104	2	resultList	Ljava/util/List;
    // Local variable type table:
    //   start	length	slot	name	signature
    //   9	84	3	object	TE;
    //   0	108	0	cls	Ljava/lang/Class<TE;>;
    //   4	104	2	resultList	Ljava/util/List<TE;>;
    // Exception table:
    //   from	to	target	type
    //   4	93	96	java/lang/Exception }

















  
  public static final <E> E newInstance(Class<E> cls, Map<String, ?> map) {
    E object = null;
    try {
      object = (E)cls.newInstance();
      BeanUtils.populate(object, map);
    } catch (Exception e) {
      throw new InstanceException(e);
    } 
    return object;
  }











  
  public static final Object newInstance(String clazz) {
    try {
      return getClass(clazz).newInstance();
    } catch (Exception e) {
      throw new InstanceException(e);
    } 
  }
  
  public static final <K> K newInstance(Class<K> cls, Object... args) {
    try {
      Class[] argsClass = null;
      if (args != null) {
        argsClass = new Class[args.length];
        for (int i = 0, j = args.length; i < j; i++) {
          argsClass[i] = args[i].getClass();
        }
      } 
      Constructor<K> cons = cls.getConstructor(argsClass);
      return (K)cons.newInstance(args);
    } catch (Exception e) {
      throw new InstanceException(e);
    } 
  }
  
  public static Map<String, Class<?>> clazzMap = new HashMap();









  
  public static final Object newInstance(String className, Object... args) {
    try {
      Class<?> newoneClass = (Class)clazzMap.get(className);
      if (newoneClass == null) {
        newoneClass = Class.forName(className);
        clazzMap.put(className, newoneClass);
      } 
      return newInstance(newoneClass, args);
    } catch (Exception e) {
      throw new InstanceException(e);
    } 
  }











  
  public static final Object invokeMethod(Object owner, String methodName, Object... args) {
    Class<?> ownerClass = owner.getClass();
    String key = null;
    if (args != null) {
      Class[] argsClass = new Class[args.length];
      for (int i = 0, j = args.length; i < j; i++) {
        if (args[i] != null) {
          argsClass[i] = args[i].getClass();
        }
      } 
      key = ownerClass + "_" + methodName + "_" + StringUtils.join(argsClass, ",");
    } else {
      key = ownerClass + "_" + methodName;
    } 
    MethodAccess methodAccess = (MethodAccess)methodMap.get(key);
    if (methodAccess == null) {
      methodAccess = MethodAccess.get(ownerClass);
      methodMap.put(key, methodAccess);
    } 
    return methodAccess.invoke(owner, methodName, args);
  }




  
  public static final <E> ArrayList<E> newArrayList() { return new ArrayList(); }





  
  public static final <E> ArrayList<E> newArrayList(E... e) {
    ArrayList<E> list = newArrayList();
    Collections.addAll(list, e);
    return list;
  }




  
  public static final <k, v> HashMap<k, v> newHashMap() { return new HashMap(); }





  
  public static final <E> HashSet<E> newHashSet() { return new HashSet(); }





  
  public static final <k, v> Hashtable<k, v> newHashtable() { return new Hashtable(); }





  
  public static final <k, v> LinkedHashMap<k, v> newLinkedHashMap() { return new LinkedHashMap(); }





  
  public static final <E> LinkedHashSet<E> newLinkedHashSet() { return new LinkedHashSet(); }





  
  public static final <E> LinkedList<E> newLinkedList() { return new LinkedList(); }





  
  public static final <k, v> TreeMap<k, v> newTreeMap() { return new TreeMap(); }





  
  public static final <E> TreeSet<E> newTreeSet() { return new TreeSet(); }





  
  public static final <E> Vector<E> newVector() { return new Vector(); }





  
  public static final <k, v> WeakHashMap<k, v> newWeakHashMap() { return new WeakHashMap(); }




  
  public static final <k, v> HashMap<k, v> newHashMap(k key, v value) {
    HashMap<k, v> map = newHashMap();
    map.put(key, value);
    return map;
  }



  
  public static final <k, v> HashMap<k, v> newHashMap(k[] key, v[] value) {
    HashMap<k, v> map = newHashMap();
    for (int i = 0; i < key.length; i++) {
      map.put(key[i], value[i]);
    }
    return map;
  }



  
  public static final <k, v> LinkedHashMap<k, v> newLinkedHashMap(k key, v value) {
    LinkedHashMap<k, v> map = newLinkedHashMap();
    map.put(key, value);
    return map;
  }




  
  public static final <k, v> ConcurrentHashMap<k, v> newConcurrentHashMap() { return new ConcurrentHashMap(); }





  
  public static final <e> ConcurrentLinkedDeque<e> newConcurrentLinkedDeque() { return new ConcurrentLinkedDeque(); }





  
  public static final <e> ConcurrentLinkedQueue<e> newConcurrentLinkedQueue() { return new ConcurrentLinkedQueue(); }





  
  public static final <e> ConcurrentSkipListSet<e> newConcurrentSkipListSet() { return new ConcurrentSkipListSet(); }





  
  public static <E> Set<E> newHashSet(E[] e) {
    Set<E> set = newHashSet();
    Collections.addAll(set, e);
    return set;
  }
}
