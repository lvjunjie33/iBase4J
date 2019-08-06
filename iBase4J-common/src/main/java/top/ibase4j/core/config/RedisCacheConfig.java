package top.ibase4j.core.config;

import java.lang.reflect.Method;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({org.springframework.cache.annotation.CacheConfig.class})
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport{
//  String prefix = "iBase4J:M:";

  
//  @Bean
//  public KeyGenerator keyGenerator() {
//    return new KeyGenerator() {
//        public Object generate(Object o, Method method, Object... objects) { // Byte code:
          //   0: new java/lang/StringBuilder
          //   3: dup
          //   4: aload_0
          //   5: getfield this$0 : Ltop/ibase4j/core/config/RedisCacheConfig;
          //   8: getfield prefix : Ljava/lang/String;
          //   11: invokespecial <init> : (Ljava/lang/String;)V
          //   14: astore #4
          //   16: aload_1
          //   17: invokevirtual getClass : ()Ljava/lang/Class;
          //   20: ldc org/springframework/cache/annotation/CacheConfig
          //   22: invokevirtual getAnnotation : (Ljava/lang/Class;)Ljava/lang/annotation/Annotation;
          //   25: checkcast org/springframework/cache/annotation/CacheConfig
          //   28: astore #5
          //   30: aload_2
          //   31: ldc org/springframework/cache/annotation/Cacheable
          //   33: invokevirtual getAnnotation : (Ljava/lang/Class;)Ljava/lang/annotation/Annotation;
          //   36: checkcast org/springframework/cache/annotation/Cacheable
          //   39: astore #6
          //   41: aload_2
          //   42: ldc org/springframework/cache/annotation/CachePut
          //   44: invokevirtual getAnnotation : (Ljava/lang/Class;)Ljava/lang/annotation/Annotation;
          //   47: checkcast org/springframework/cache/annotation/CachePut
          //   50: astore #7
          //   52: aload_2
          //   53: ldc org/springframework/cache/annotation/CacheEvict
          //   55: invokevirtual getAnnotation : (Ljava/lang/Class;)Ljava/lang/annotation/Annotation;
          //   58: checkcast org/springframework/cache/annotation/CacheEvict
          //   61: astore #8
          //   63: aload #6
          //   65: ifnull -> 98
          //   68: aload #6
          //   70: invokeinterface value : ()[Ljava/lang/String;
          //   75: astore #9
          //   77: aload #9
          //   79: invokestatic isNotEmpty : ([Ljava/lang/Object;)Z
          //   82: ifeq -> 95
          //   85: aload #4
          //   87: aload #9
          //   89: iconst_0
          //   90: aaload
          //   91: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   94: pop
          //   95: goto -> 165
          //   98: aload #7
          //   100: ifnull -> 133
          //   103: aload #7
          //   105: invokeinterface value : ()[Ljava/lang/String;
          //   110: astore #9
          //   112: aload #9
          //   114: invokestatic isNotEmpty : ([Ljava/lang/Object;)Z
          //   117: ifeq -> 130
          //   120: aload #4
          //   122: aload #9
          //   124: iconst_0
          //   125: aaload
          //   126: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   129: pop
          //   130: goto -> 165
          //   133: aload #8
          //   135: ifnull -> 165
          //   138: aload #8
          //   140: invokeinterface value : ()[Ljava/lang/String;
          //   145: astore #9
          //   147: aload #9
          //   149: invokestatic isNotEmpty : ([Ljava/lang/Object;)Z
          //   152: ifeq -> 165
          //   155: aload #4
          //   157: aload #9
          //   159: iconst_0
          //   160: aaload
          //   161: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   164: pop
          //   165: aload #5
          //   167: ifnull -> 215
          //   170: aload #4
          //   172: invokevirtual toString : ()Ljava/lang/String;
          //   175: aload_0
          //   176: getfield this$0 : Ltop/ibase4j/core/config/RedisCacheConfig;
          //   179: getfield prefix : Ljava/lang/String;
          //   182: invokevirtual equals : (Ljava/lang/Object;)Z
          //   185: ifeq -> 215
          //   188: aload #5
          //   190: invokeinterface cacheNames : ()[Ljava/lang/String;
          //   195: astore #9
          //   197: aload #9
          //   199: invokestatic isNotEmpty : ([Ljava/lang/Object;)Z
          //   202: ifeq -> 215
          //   205: aload #4
          //   207: aload #9
          //   209: iconst_0
          //   210: aaload
          //   211: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   214: pop
          //   215: aload #4
          //   217: invokevirtual toString : ()Ljava/lang/String;
          //   220: aload_0
          //   221: getfield this$0 : Ltop/ibase4j/core/config/RedisCacheConfig;
          //   224: getfield prefix : Ljava/lang/String;
          //   227: invokevirtual equals : (Ljava/lang/Object;)Z
          //   230: ifeq -> 258
          //   233: aload #4
          //   235: aload_1
          //   236: invokevirtual getClass : ()Ljava/lang/Class;
          //   239: invokevirtual getName : ()Ljava/lang/String;
          //   242: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   245: ldc '.'
          //   247: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   250: aload_2
          //   251: invokevirtual getName : ()Ljava/lang/String;
          //   254: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   257: pop
          //   258: aload #4
          //   260: ldc ':'
          //   262: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   265: pop
          //   266: aload_3
          //   267: ifnull -> 312
          //   270: aload_3
          //   271: astore #9
          //   273: aload #9
          //   275: arraylength
          //   276: istore #10
          //   278: iconst_0
          //   279: istore #11
          //   281: iload #11
          //   283: iload #10
          //   285: if_icmpge -> 312
          //   288: aload #9
          //   290: iload #11
          //   292: aaload
          //   293: astore #12
          //   295: aload #4
          //   297: aload #12
          //   299: invokestatic toJSONString : (Ljava/lang/Object;)Ljava/lang/String;
          //   302: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   305: pop
          //   306: iinc #11, 1
          //   309: goto -> 281
          //   312: aload #4
          //   314: invokevirtual toString : ()Ljava/lang/String;
          //   317: areturn
          // Line number table:
          //   Java source line number -> byte code offset
          //   #43	-> 0
          //   #44	-> 16
          //   #45	-> 30
          //   #46	-> 41
          //   #47	-> 52
          //   #48	-> 63
          //   #49	-> 68
          //   #50	-> 77
          //   #51	-> 85
          //   #53	-> 95
          //   #54	-> 103
          //   #55	-> 112
          //   #56	-> 120
          //   #58	-> 130
          //   #59	-> 138
          //   #60	-> 147
          //   #61	-> 155
          //   #64	-> 165
          //   #65	-> 188
          //   #66	-> 197
          //   #67	-> 205
          //   #70	-> 215
          //   #71	-> 233
          //   #73	-> 258
          //   #74	-> 266
          //   #75	-> 270
          //   #76	-> 295
          //   #75	-> 306
          //   #79	-> 312
          // Local variable table:
          //   start	length	slot	name	descriptor
          //   77	18	9	cacheNames	[Ljava/lang/String;
          //   112	18	9	cacheNames	[Ljava/lang/String;
          //   147	18	9	cacheNames	[Ljava/lang/String;
          //   197	18	9	cacheNames	[Ljava/lang/String;
          //   295	11	12	object	Ljava/lang/Object;
          //   0	318	0	this	Ltop/ibase4j/core/config/RedisCacheConfig$1;
          //   0	318	1	o	Ljava/lang/Object;
          //   0	318	2	method	Ljava/lang/reflect/Method;
          //   0	318	3	objects	[Ljava/lang/Object;
          //   16	302	4	sb	Ljava/lang/StringBuilder;
          //   30	288	5	cacheConfig	Lorg/springframework/cache/annotation/CacheConfig;
          //   41	277	6	cacheable	Lorg/springframework/cache/annotation/Cacheable;
          //   52	266	7	cachePut	Lorg/springframework/cache/annotation/CachePut;
          //   63	255	8	cacheEvict	Lorg/springframework/cache/annotation/CacheEvict; }
//      };
//  }
}
