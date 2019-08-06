package top.ibase4j.core.config;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import top.ibase4j.core.util.DataUtil;
import top.ibase4j.core.util.PropertiesUtil;
import top.ibase4j.core.util.SecurityUtil;










@Configuration
public class Configs
  implements EnvironmentPostProcessor, Ordered
{
  private Logger logger = LogManager.getLogger();


  
  public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
    MutablePropertySources propertySources = environment.getPropertySources();
    String[] profiles = environment.getActiveProfiles();
    Properties props = getConfig(profiles);
    propertySources.addLast(new PropertiesPropertySource("thirdEnv", props));
    for (PropertySource<?> propertySource : propertySources) {
      if (propertySource.getSource() instanceof Map) {
        Map map = (Map)propertySource.getSource();
        for (Object key : map.keySet()) {
          String keyStr = key.toString();
          Object value = map.get(key);
          if ("druid.password,druid.writer.password,druid.reader.password".contains(keyStr)) {
            String dkey = (String)map.get("druid.key");
            dkey = DataUtil.isEmpty(dkey) ? "90139119" : dkey;
            value = SecurityUtil.decryptDes(value.toString(), dkey.getBytes());
            map.put(key, value);
          } 
          PropertiesUtil.getProperties().put(keyStr, value.toString());
        } 
      } 
    } 
    System.out.println("* iBase4J Read configuration file finished.");
    this.logger.info("* iBase4J Read configuration file finished.");
  }


  
  public int getOrder() { return -2147483637; }











  
  private Properties getConfig(String[] profiles) {
	return null; // Byte code:
    //   0: new org/springframework/core/io/support/PathMatchingResourcePatternResolver
    //   3: dup
    //   4: invokespecial <init> : ()V
    //   7: astore_2
    //   8: invokestatic newArrayList : ()Ljava/util/ArrayList;
    //   11: astore_3
    //   12: aload_0
    //   13: aload_2
    //   14: aload_3
    //   15: ldc 'classpath*:config/*.properties'
    //   17: invokespecial addResources : (Lorg/springframework/core/io/support/PathMatchingResourcePatternResolver;Ljava/util/List;Ljava/lang/String;)V
    //   20: aload_1
    //   21: ifnull -> 116
    //   24: aload_1
    //   25: astore #4
    //   27: aload #4
    //   29: arraylength
    //   30: istore #5
    //   32: iconst_0
    //   33: istore #6
    //   35: iload #6
    //   37: iload #5
    //   39: if_icmpge -> 116
    //   42: aload #4
    //   44: iload #6
    //   46: aaload
    //   47: astore #7
    //   49: aload #7
    //   51: invokestatic isNotEmpty : (Ljava/lang/Object;)Z
    //   54: ifeq -> 79
    //   57: new java/lang/StringBuilder
    //   60: dup
    //   61: invokespecial <init> : ()V
    //   64: aload #7
    //   66: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   69: ldc '/'
    //   71: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   74: invokevirtual toString : ()Ljava/lang/String;
    //   77: astore #7
    //   79: aload_0
    //   80: aload_2
    //   81: aload_3
    //   82: new java/lang/StringBuilder
    //   85: dup
    //   86: invokespecial <init> : ()V
    //   89: ldc 'classpath*:config/'
    //   91: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   94: aload #7
    //   96: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   99: ldc '*.properties'
    //   101: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   104: invokevirtual toString : ()Ljava/lang/String;
    //   107: invokespecial addResources : (Lorg/springframework/core/io/support/PathMatchingResourcePatternResolver;Ljava/util/List;Ljava/lang/String;)V
    //   110: iinc #6, 1
    //   113: goto -> 35
    //   116: new org/springframework/beans/factory/config/PropertiesFactoryBean
    //   119: dup
    //   120: invokespecial <init> : ()V
    //   123: astore #4
    //   125: aload #4
    //   127: aload_3
    //   128: iconst_0
    //   129: anewarray org/springframework/core/io/Resource
    //   132: invokeinterface toArray : ([Ljava/lang/Object;)[Ljava/lang/Object;
    //   137: checkcast [Lorg/springframework/core/io/Resource;
    //   140: invokevirtual setLocations : ([Lorg/springframework/core/io/Resource;)V
    //   143: aload #4
    //   145: invokevirtual afterPropertiesSet : ()V
    //   148: aload #4
    //   150: invokevirtual getObject : ()Ljava/util/Properties;
    //   153: areturn
    //   154: astore #4
    //   156: new java/lang/RuntimeException
    //   159: dup
    //   160: aload #4
    //   162: invokespecial <init> : (Ljava/lang/Throwable;)V
    //   165: athrow
    // Line number table:
    //   Java source line number -> byte code offset
    //   #71	-> 0
    //   #72	-> 8
    //   #73	-> 12
    //   #74	-> 20
    //   #75	-> 24
    //   #76	-> 49
    //   #77	-> 57
    //   #79	-> 79
    //   #75	-> 110
    //   #83	-> 116
    //   #84	-> 125
    //   #85	-> 143
    //   #86	-> 148
    //   #87	-> 154
    //   #88	-> 156
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   49	61	7	p	Ljava/lang/String;
    //   125	29	4	config	Lorg/springframework/beans/factory/config/PropertiesFactoryBean;
    //   156	10	4	e	Ljava/io/IOException;
    //   0	166	0	this	Ltop/ibase4j/core/config/Configs;
    //   0	166	1	profiles	[Ljava/lang/String;
    //   8	158	2	resolver	Lorg/springframework/core/io/support/PathMatchingResourcePatternResolver;
    //   12	154	3	resouceList	Ljava/util/List;
    // Local variable type table:
    //   start	length	slot	name	signature
    //   12	154	3	resouceList	Ljava/util/List<Lorg/springframework/core/io/Resource;>;
    // Exception table:
    //   from	to	target	type
    //   116	153	154	java/io/IOException }





  }





  
  private void addResources(PathMatchingResourcePatternResolver resolver, List<Resource> resouceList, String path) { // Byte code:
    //   0: aload_1
    //   1: aload_3
    //   2: invokevirtual getResources : (Ljava/lang/String;)[Lorg/springframework/core/io/Resource;
    //   5: astore #4
    //   7: aload #4
    //   9: astore #5
    //   11: aload #5
    //   13: arraylength
    //   14: istore #6
    //   16: iconst_0
    //   17: istore #7
    //   19: iload #7
    //   21: iload #6
    //   23: if_icmpge -> 48
    //   26: aload #5
    //   28: iload #7
    //   30: aaload
    //   31: astore #8
    //   33: aload_2
    //   34: aload #8
    //   36: invokeinterface add : (Ljava/lang/Object;)Z
    //   41: pop
    //   42: iinc #7, 1
    //   45: goto -> 19
    //   48: goto -> 66
    //   51: astore #4
    //   53: aload_0
    //   54: getfield logger : Lorg/apache/logging/log4j/Logger;
    //   57: ldc ''
    //   59: aload #4
    //   61: invokeinterface error : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   66: return
    // Line number table:
    //   Java source line number -> byte code offset
    //   #95	-> 0
    //   #96	-> 7
    //   #97	-> 33
    //   #96	-> 42
    //   #101	-> 48
    //   #99	-> 51
    //   #100	-> 53
    //   #102	-> 66
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   33	9	8	resource	Lorg/springframework/core/io/Resource;
    //   7	41	4	resources	[Lorg/springframework/core/io/Resource;
    //   53	13	4	e	Ljava/lang/Exception;
    //   0	67	0	this	Ltop/ibase4j/core/config/Configs;
    //   0	67	1	resolver	Lorg/springframework/core/io/support/PathMatchingResourcePatternResolver;
    //   0	67	2	resouceList	Ljava/util/List;
    //   0	67	3	path	Ljava/lang/String;
    // Local variable type table:
    //   start	length	slot	name	signature
    //   0	67	2	resouceList	Ljava/util/List<Lorg/springframework/core/io/Resource;>;
    // Exception table:
    //   from	to	target	type
    //   0	48	51	java/lang/Exception }





  }




  
  public static void main(String[] args) {
    String encrypt = SecurityUtil.encryptDes("buzhidao", "90139119".getBytes());
    System.out.println(encrypt);
    System.out.println(SecurityUtil.decryptDes(encrypt, "90139119".getBytes()));
  }
}
