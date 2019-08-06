package top.ibase4j.core.support.cache.shiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;
import top.ibase4j.core.util.CacheUtil;









public class RedisCache<K, V>
  extends Object
  implements Cache<K, V>
{
  private final Logger logger = LogManager.getLogger();



  
  private String keyPrefix = "S:iBase4J:SHIRO-CACHE:";






  
  public String getKeyPrefix() { return this.keyPrefix; }







  
  public void setKeyPrefix(String keyPrefix) { this.keyPrefix = keyPrefix; }








  
  public RedisCache(String prefix) { this.keyPrefix = prefix; }


  
  public V get(K key) throws CacheException {
    this.logger.debug("根据key从Redis中获取对象 key [" + key + "]");
    
    return (V)CacheUtil.getCache().getFire(getKey(key));
  }


  
  public V put(K key, V value) throws CacheException {
    this.logger.debug("根据key从存储 key [" + key + "]");
    CacheUtil.getCache().set(getKey(key), (Serializable)value);
    return value;
  }

  
  public V remove(K key) throws CacheException {
    this.logger.debug("从redis中删除 key [" + key + "]");
    V previous = (V)get(key);
    CacheUtil.getCache().del(getKey(key));
    return previous;
  }

  
  public void clear() throws CacheException {
    this.logger.debug("从redis中删除所有元素");
    CacheUtil.getCache().delAll(this.keyPrefix + "*");
  }


  
  public int size() { return CacheUtil.getCache().getAll(this.keyPrefix + "*").size(); }



  
  public Set<K> keys() {
    Set<Object> keys = CacheUtil.getCache().getAll(this.keyPrefix + "*");
    if (CollectionUtils.isEmpty(keys)) {
      return Collections.emptySet();
    }
    Set<K> newKeys = new HashSet<K>();
    for (Object key : keys) {
      newKeys.add((K) key);
    }
    return newKeys;
  }


  
  public Collection<V> values() {
    Set<Object> keys = CacheUtil.getCache().getAll(this.keyPrefix + "*");
    if (!CollectionUtils.isEmpty(keys)) {
      List<V> values = new ArrayList<V>(keys.size());
      for (Object key : keys) {
        
        V value = (V)get((K) key);
        if (value != null) {
          values.add(value);
        }
      } 
      return Collections.unmodifiableList(values);
    } 
    return Collections.emptyList();
  }


  
  private String getKey(K key) { return this.keyPrefix + key; }
}
