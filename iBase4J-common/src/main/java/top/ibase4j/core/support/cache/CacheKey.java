package top.ibase4j.core.support.cache;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.redis.core.RedisHash;
import top.ibase4j.core.Constants;
import top.ibase4j.core.util.DataUtil;
import top.ibase4j.core.util.MathUtil;
import top.ibase4j.core.util.PropertiesUtil;






public class CacheKey
{
  private String value;
  private int timeToLive;
  
  public CacheKey(String value, int timeToLive) {
    this.value = value;
    this.timeToLive = timeToLive;
  }

  
  public String getValue() { return this.value; }


  
  public void setValue(String value) { this.value = value; }


  
  public int getTimeToLive() { return this.timeToLive; }


  
  public void setTimeToLive(int timeToLive) { this.timeToLive = timeToLive; }

  
  public static CacheKey getInstance(Class<?> cls) {
    CacheKey cackKey = (CacheKey)Constants.CACHEKEYMAP.get(cls);
    if (DataUtil.isEmpty(cackKey)) {
      String cacheName;
      RedisHash redisHash = null;
      Long timeToLive = Long.valueOf(MathUtil.getRandom(1.0D, 1.5D)
          .multiply(new BigDecimal(PropertiesUtil.getString("redis.expiration", "600"))).longValue());
      ParameterizedType parameterizedType = (ParameterizedType)cls.getGenericSuperclass();
      if (parameterizedType != null) {
        Type[] actualTypes = parameterizedType.getActualTypeArguments();
        if (actualTypes != null && actualTypes.length > 0)
        {
          redisHash = (RedisHash)actualTypes[0].getClass().getAnnotation(RedisHash.class);
        }
      } 
      if (redisHash != null) {
        cacheName = redisHash.value();
        timeToLive = Long.valueOf(redisHash.timeToLive());
      } else {
        
        CacheConfig cacheConfig = (CacheConfig)cls.getAnnotation(CacheConfig.class);
        if (cacheConfig != null && ArrayUtils.isNotEmpty(cacheConfig.cacheNames())) {
          cacheName = cacheConfig.cacheNames()[0];
        } else {
          return null;
        } 
      } 
      cackKey = new CacheKey("iBase4J:" + cacheName, timeToLive.intValue());
      Constants.CACHEKEYMAP.put(cls, cackKey);
    } 
    return cackKey;
  }
}
