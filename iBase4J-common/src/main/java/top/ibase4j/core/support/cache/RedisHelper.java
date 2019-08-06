package top.ibase4j.core.support.cache;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;
import top.ibase4j.core.support.context.ApplicationContextHolder;
import top.ibase4j.core.util.CacheUtil;
import top.ibase4j.core.util.InstanceUtil;
import top.ibase4j.core.util.PropertiesUtil;







public final class RedisHelper
  implements CacheManager
{
  private RedisTemplate<Serializable, Serializable> redisTemplate;
  private final Integer EXPIRE = PropertiesUtil.getInt("redis.expiration");
  
  public RedisHelper(RedisTemplate<Serializable, Serializable> redisTemplate) {
    this.redisTemplate = redisTemplate;
    CacheUtil.setCacheManager(this);
  }

  
  public RedisTemplate<Serializable, Serializable> getRedisTemplate() {
    if (this.redisTemplate == null) {
      logger.warn("redisTemplate is null");
      this
        .redisTemplate = (RedisTemplate)ApplicationContextHolder.getBean("lockRedisTemplate");
    } 
    return this.redisTemplate;
  }


  
  public final Object get(String key) { return getRedisTemplate().boundValueOps(key).get(); }


  
  public final Object get(String key, Integer expire) {
    expire(key, expire.intValue());
    return getRedisTemplate().boundValueOps(key).get();
  }

  
  public final Object getFire(String key) {
    expire(key, this.EXPIRE.intValue());
    return getRedisTemplate().boundValueOps(key).get();
  }

  
  public final Set<Object> getAll(String pattern) {
    Set<Object> values = InstanceUtil.newHashSet();
    Set<Serializable> keys = getRedisTemplate().keys(pattern);
    for (Serializable key : keys) {
      values.add(getRedisTemplate().opsForValue().get(key));
    }
    return values;
  }

  
  public final Set<Object> getAll(String pattern, Integer expire) {
    Set<Object> values = InstanceUtil.newHashSet();
    Set<Serializable> keys = getRedisTemplate().keys(pattern);
    for (Serializable key : keys) {
      expire(key.toString(), expire.intValue());
      values.add(getRedisTemplate().opsForValue().get(key));
    } 
    return values;
  }

  
  public final void set(String key, Serializable value, int seconds) {
    getRedisTemplate().boundValueOps(key).set(value);
    expire(key, seconds);
  }

  
  public final void set(String key, Serializable value) {
    getRedisTemplate().boundValueOps(key).set(value);
    expire(key, this.EXPIRE.intValue());
  }


  
  public final Boolean exists(String key) { return getRedisTemplate().hasKey(key); }



  
  public final void del(String key) { getRedisTemplate().delete(key); }



  
  public final void delAll(String pattern) { getRedisTemplate().delete(getRedisTemplate().keys(pattern)); }



  
  public final String type(String key) { return getRedisTemplate().type(key).getClass().getName(); }







  
  public final Boolean expire(String key, int seconds) { return getRedisTemplate().expire(key, seconds, TimeUnit.SECONDS); }









  
  public final Boolean expireAt(String key, long unixTime) { return getRedisTemplate().expireAt(key, new Date(unixTime)); }



  
  public final Long ttl(String key) { return getRedisTemplate().getExpire(key, TimeUnit.SECONDS); }



  
  public final void setrange(String key, long offset, String value) { getRedisTemplate().boundValueOps(key).set(value, offset); }



  
  public final String getrange(String key, long startOffset, long endOffset) { return getRedisTemplate().boundValueOps(key).get(startOffset, endOffset); }



  
  public final Object getSet(String key, Serializable value) { return getRedisTemplate().boundValueOps(key).getAndSet(value); }



  
  public boolean setnx(String key, Serializable value) { return getRedisTemplate().boundValueOps(key).setIfAbsent(value).booleanValue(); }


  
  private byte[] rawKey(Object key) {
    Assert.notNull(key, "non null key required");
    RedisSerializer keySerializer = getRedisTemplate().getKeySerializer();
    if (keySerializer == null && key instanceof byte[]) {
      return (byte[])key;
    }
    return keySerializer.serialize(key);
  }

  
  private byte[] rawValue(Object value) {
    RedisSerializer valueSerializer = getRedisTemplate().getValueSerializer();
    if (valueSerializer == null && value instanceof byte[]) {
      return (byte[])value;
    }
    return valueSerializer.serialize(value);
  }


  
  public boolean lock(final String key, final String requestId, final long seconds) { return ((Boolean)getRedisTemplate().execute(new RedisCallback<Boolean>()
        {
          public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
            return connection.set(RedisHelper.this.rawKey(key), RedisHelper.this.rawValue(requestId), Expiration.seconds(seconds), 
                RedisStringCommands.SetOption.ifAbsent());
          }
        })).booleanValue(); }


  
  public boolean unlock(String key, String requestId) {
    String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
    return ((Boolean)getRedisTemplate().execute(new DefaultRedisScript(script, Boolean.class), 
        InstanceUtil.newArrayList(new Serializable[] { key } ), new Object[] { requestId })).booleanValue();
  }


  
  public void hset(String key, Serializable field, Serializable value) { getRedisTemplate().boundHashOps(key).put(field, value); }



  
  public Object hget(String key, Serializable field) { return getRedisTemplate().boundHashOps(key).get(field); }



  
  public void hdel(String key, Serializable field) { getRedisTemplate().boundHashOps(key).delete(new Object[] { field }); }



  
  public Long incr(String key) { return getRedisTemplate().boundValueOps(key).increment(1L); }


  
  public Long incr(String key, int seconds) {
    Long incr = getRedisTemplate().boundValueOps(key).increment(1L);
    expire(key, seconds);
    return incr;
  }
}
