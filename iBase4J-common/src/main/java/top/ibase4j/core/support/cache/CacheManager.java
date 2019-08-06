package top.ibase4j.core.support.cache;

import java.io.Serializable;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;





public interface CacheManager
{
  public static final Logger logger = LogManager.getLogger();
  
  Object get(String paramString);
  
  Set<Object> getAll(String paramString);
  
  void set(String paramString, Serializable paramSerializable, int paramInt);
  
  void set(String paramString, Serializable paramSerializable);
  
  Boolean exists(String paramString);
  
  void del(String paramString);
  
  void delAll(String paramString);
  
  String type(String paramString);
  
  Boolean expire(String paramString, int paramInt);
  
  Boolean expireAt(String paramString, long paramLong);
  
  Long ttl(String paramString);
  
  Object getSet(String paramString, Serializable paramSerializable);
  
  boolean lock(String paramString1, String paramString2, long paramLong);
  
  boolean unlock(String paramString1, String paramString2);
  
  void hset(String paramString, Serializable paramSerializable1, Serializable paramSerializable2);
  
  Object hget(String paramString, Serializable paramSerializable);
  
  void hdel(String paramString, Serializable paramSerializable);
  
  boolean setnx(String paramString, Serializable paramSerializable);
  
  Long incr(String paramString);
  
  Long incr(String paramString, int paramInt);
  
  void setrange(String paramString1, long paramLong, String paramString2);
  
  String getrange(String paramString, long paramLong1, long paramLong2);
  
  Object get(String paramString, Integer paramInteger);
  
  Object getFire(String paramString);
  
  Set<Object> getAll(String paramString, Integer paramInteger);
}
