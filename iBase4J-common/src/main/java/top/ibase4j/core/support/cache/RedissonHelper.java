package top.ibase4j.core.support.cache;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.redisson.api.RBucket;
import org.redisson.api.RScript;
import org.redisson.api.RType;
import org.redisson.api.RedissonClient;
import top.ibase4j.core.support.redisson.Client;
import top.ibase4j.core.util.CacheUtil;
import top.ibase4j.core.util.InstanceUtil;
import top.ibase4j.core.util.PropertiesUtil;











public class RedissonHelper
  implements CacheManager
{
  private RedissonClient redissonClient;
  private final Integer EXPIRE = PropertiesUtil.getInt("redis.expiration");
  
  public void setClient(Client client) {
    this.redissonClient = client.getRedissonClient();
    CacheUtil.setLockManager(this);
  }
  
  public void setRedissonClient(RedissonClient redissonClient) {
    this.redissonClient = redissonClient;
    CacheUtil.setLockManager(this);
  }

  
  private RBucket<Object> getRedisBucket(String key) { return this.redissonClient.getBucket(key); }


  
  public final Object get(String key) {
    RBucket<Object> temp = getRedisBucket(key);
    return temp.get();
  }

  
  public Object get(String key, Integer expire) {
    RBucket<Object> temp = getRedisBucket(key);
    expire(temp, expire.intValue());
    return temp.get();
  }

  
  public Object getFire(String key) {
    RBucket<Object> temp = getRedisBucket(key);
    expire(temp, this.EXPIRE.intValue());
    return temp.get();
  }

  
  public final void set(String key, Serializable value) {
    RBucket<Object> temp = getRedisBucket(key);
    temp.set(value);
    expire(temp, this.EXPIRE.intValue());
  }

  
  public final void set(String key, Serializable value, int seconds) {
    RBucket<Object> temp = getRedisBucket(key);
    temp.set(value);
    expire(temp, seconds);
  }

  
  public final void multiSet(Map<String, Object> temps) { this.redissonClient.getBuckets().set(temps); }


  
  public final Boolean exists(String key) {
    RBucket<Object> temp = getRedisBucket(key);
    return Boolean.valueOf(temp.isExists());
  }


  
  public final void del(String key) { this.redissonClient.getKeys().delete(new String[] { key }); }



  
  public final void delAll(String pattern) { this.redissonClient.getKeys().deleteByPattern(pattern); }


  
  public final String type(String key) {
    RType type = this.redissonClient.getKeys().getType(key);
    if (type == null) {
      return null;
    }
    return type.getClass().getName();
  }






  
  private final void expire(RBucket<Object> bucket, int seconds) { bucket.expire(seconds, TimeUnit.SECONDS); }











  
  public final Boolean expireAt(String key, long unixTime) { return Boolean.valueOf(this.redissonClient.getBucket(key).expireAt(new Date(unixTime))); }


  
  public final Long ttl(String key) {
    RBucket<Object> rBucket = getRedisBucket(key);
    return Long.valueOf(rBucket.remainTimeToLive());
  }

  
  public final Object getSet(String key, Serializable value) {
    RBucket<Object> rBucket = getRedisBucket(key);
    return rBucket.getAndSet(value);
  }

  
  public Set<Object> getAll(String pattern) {
    Set<Object> set = InstanceUtil.newHashSet();
    Iterable<String> keys = this.redissonClient.getKeys().getKeysByPattern(pattern);
    for (String key : keys) {
      set.add(getRedisBucket(key).get());
    }
    return set;
  }

  
  public Set<Object> getAll(String pattern, Integer expire) {
    Set<Object> set = InstanceUtil.newHashSet();
    Iterable<String> keys = this.redissonClient.getKeys().getKeysByPattern(pattern);
    for (String key : keys) {
      RBucket<Object> bucket = getRedisBucket(key);
      expire(bucket, expire.intValue());
      set.add(bucket.get());
    } 
    return set;
  }

  
  public Boolean expire(String key, int seconds) {
    RBucket<Object> bucket = getRedisBucket(key);
    expire(bucket, seconds);
    return Boolean.valueOf(true);
  }


  
  public void hset(String key, Serializable field, Serializable value) { this.redissonClient.getMap(key).put(field, value); }



  
  public Object hget(String key, Serializable field) { return this.redissonClient.getMap(key).get(field); }



  
  public void hdel(String key, Serializable field) { this.redissonClient.getMap(key).remove(field); }


  
  public void sadd(String key, Serializable value) { this.redissonClient.getSet(key).add(value); }


  
  public Set<Object> sall(String key) { return this.redissonClient.getSet(key).readAll(); }


  
  public boolean sdel(String key, Serializable value) { return this.redissonClient.getSet(key).remove(value); }



  
  public boolean lock(String key, String requestId, long seconds) { return this.redissonClient.getBucket(key).trySet(requestId, seconds, TimeUnit.SECONDS); }


  
  public boolean unlock(String key, String requestId) {
    String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
    return ((Boolean)this.redissonClient.getScript().eval(RScript.Mode.READ_WRITE, script, RScript.ReturnType.BOOLEAN, 
        InstanceUtil.newArrayList(new Object[] { key } ), new Object[] { requestId })).booleanValue();
  }


  
  public boolean setnx(String key, Serializable value) { return this.redissonClient.getBucket(key).trySet(value); }



  
  public Long incr(String key) { return Long.valueOf(this.redissonClient.getAtomicLong(key).incrementAndGet()); }


  
  public Long incr(String key, int seconds) {
    Long incr = Long.valueOf(this.redissonClient.getAtomicLong(key).incrementAndGet());
    expire(key, seconds);
    return incr;
  }


  
  public void setrange(String key, long offset, String value) {}


  
  public String getrange(String key, long startOffset, long endOffset) { return null; }
}
