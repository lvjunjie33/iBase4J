package top.ibase4j.core.support.cache.shiro;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;










public class RedisCacheManager
  implements CacheManager
{
  private final Logger logger = LogManager.getLogger();

  
  private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap();



  
  private String keyPrefix = "S:iBase4J:SHIRO-CACHE:";






  
  public String getKeyPrefix() { return this.keyPrefix; }







  
  public void setKeyPrefix(String keyPrefix) { this.keyPrefix = keyPrefix; }





  
  public <K, V> Cache<K, V> getCache(String name) throws CacheException {
    this.logger.debug("获取名称为: " + name + " 的RedisCache实例");
    
    Cache c = (Cache)this.caches.get(name);
    
    if (c == null) {
      
      RedisCache cache = new RedisCache(this.keyPrefix);
      
      this.caches.put(name, cache);
    } 
    return c;
  }
}
