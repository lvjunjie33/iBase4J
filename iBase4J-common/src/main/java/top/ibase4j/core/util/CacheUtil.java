package top.ibase4j.core.util;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.ibase4j.core.exception.BusinessException;
import top.ibase4j.core.support.cache.CacheManager;
import top.ibase4j.core.support.generator.Sequence;
import top.ibase4j.mapper.LockMapper;
import top.ibase4j.model.Lock;






public final class CacheUtil
{
  private static Logger logger = LogManager.getLogger();
  private static LockMapper lockMapper;
  private static CacheManager cacheManager;
  private static CacheManager lockManager;
  private static Map<String, Thread> safeThread = InstanceUtil.newHashMap();
  private static Map<String, ReentrantLock> thread = InstanceUtil.newConcurrentHashMap();
  private static ExecutorService executorService = ThreadUtil.threadPool(0, 2147483647, 60);

  
  public static void setLockMapper(LockMapper lockMapper) { CacheUtil.lockMapper = lockMapper; }


  
  public static void setCacheManager(CacheManager cacheManager) { CacheUtil.cacheManager = cacheManager; }


  
  public static void setLockManager(CacheManager cacheManager) { lockManager = cacheManager; }


  
  public static CacheManager getCache() { return cacheManager; }


  
  public static CacheManager getLockManager() { return lockManager; }



  
  public static boolean getLock(String key, String requestId) { return getLock(key, key, requestId); }


  
  public static boolean getLock(String key, String requestId, int seconds) { return getLock(key, key, requestId, seconds); }



  
  public static boolean getLock(String key, String name, String requestId) { return getLock(key, name, requestId, 60); }

  
  public static boolean getLock(final String key, String name, String requestId, final int seconds) {
    boolean success = tryLock(key, name, requestId, seconds);
    if (success) {
      Thread thread = new Thread()
        {
          public void run() {
            if (!isInterrupted()) {
              ThreadUtil.sleep(((seconds - 1) * 10));
            }
            while (lockManager.get(key) != null) {
              logger.info("守护{}", key);
              lockManager.expire(key, seconds);
              Lock param = new Lock();
              param.setKey(key);
              param.setCreateTime(new Date());
              lockMapper.updateById(param);
              if (!isInterrupted()) {
                ThreadUtil.sleep((seconds * 10));
              }
            } 
          }
        };
      thread.start();
      safeThread.put(key, thread);
    } 
    return success;
  }
  
  private static boolean tryLock(final String key, final String name, String requestId, final int seconds) {
    logger.debug("TOLOCK : " + key);
    try {
      boolean success = lockManager.lock(key, requestId, seconds);
      if (success) {
        executorService.execute(new Runnable()
            {
              public void run() {
                CacheUtil.getDBLock(key, name, seconds);
              }
            });
      }
      return success;
    } catch (Exception e) {
      logger.error("从redis获取锁信息失败", e);
      return getDBLock(key, name, seconds).booleanValue();
    } 
  }


  
  private static Boolean getDBLock(final String key, final String name, final int seconds) {
    if (!PropertiesUtil.getBoolean("dblock.open", false)) {
      return Boolean.valueOf(false);
    }
    try {
      if (thread.get(key) == null) {
        thread.put(key, new ReentrantLock());
      }
      ((ReentrantLock)thread.get(key)).lock();
      try {
        Map<String, Object> columnMap = InstanceUtil.newHashMap();
        columnMap.put("key_", key);
        List<Lock> lock = lockMapper.selectByMap(columnMap);
        if (lock.isEmpty()) {
          return (Boolean)executorService.submit(new Callable<Boolean>()
              {
                public Boolean call() throws Exception {
                  logger.debug("保存锁信息到数据库>" + key);
                  Lock param = new Lock();
                  param.setKey(key);
                  param.setName(name);
                  param.setExpireSecond(Integer.valueOf(seconds));
                  return Boolean.valueOf((lockMapper.insert(param) == 1));
                }
              }).get();
        }
        return Boolean.valueOf(false);
      } finally {
        if (thread.get(key) != null) {
          ((ReentrantLock)thread.get(key)).unlock();
        }
      } 
    } catch (Exception e) {
      logger.error("保存锁信息失败", e);
      ThreadUtil.sleep(50L);
      return getDBLock(key, name, seconds);
    } 
  }

  
  public static void unLock(String key, String requestId) {
    logger.debug("UNLOCK : " + key);
    try {
      lockManager.unlock(key, requestId);
    } catch (Exception e) {
      logger.error("从redis删除锁信息失败", e);
    } 
    if (PropertiesUtil.getBoolean("dblock.open", false)) {
      deleteLock(key, 1);
    }
    ((Thread)safeThread.get(key)).interrupt();
  }
  
  private static void deleteLock(final String key, int times) {
    boolean success = false;
    try {
      if (thread.containsKey(key)) {
        ((ReentrantLock)thread.get(key)).lock();
        try {
          logger.debug("从数据库删除锁信息>" + key);





          
          success = ((Boolean)executorService.submit(new Callable<Boolean>() { public Boolean call() throws Exception { Map<String, Object> columnMap = InstanceUtil.newHashMap("key_", key); return Boolean.valueOf((lockMapper.deleteByMap(columnMap) > 0)); } }).get()).booleanValue();
        } finally {
          ((ReentrantLock)thread.get(key)).unlock();
        } 
      } 
    } catch (Exception e) {
      logger.error("从数据库删除锁信息失败", e);
    } 
    if (!success) {
      if (times > PropertiesUtil.getInt("deleteLock.maxTimes", 20)) {
        return;
      }
      if (thread.containsKey(key)) {
        logger.warn(key + "从数据库删除锁信息失败,稍候再次尝试...");
      }
      ThreadUtil.sleep(100, 1000);
      deleteLock(key, times + 1);
    } else {
      thread.remove(key);
    } 
  }











  
  public static void refreshTimes(String key, int seconds, int frequency, String message) {
    String requestId = Sequence.next().toString();
    if (getLock(key + "-LOCK", "次数限制", requestId, 10)) {
      try {
        Integer times = Integer.valueOf(1);
        String timesStr = (String)lockManager.get(key);
        if (DataUtil.isNotEmpty(timesStr)) {
          times = Integer.valueOf(Integer.valueOf(timesStr).intValue() + 1);
          if (times.intValue() > frequency) {
            throw new BusinessException(message);
          }
        } 
        lockManager.set(key, times.toString(), seconds);
      } finally {
        unLock(key + "-LOCK", requestId);
      } 
    } else {
      refreshTimes(key, seconds, frequency, message);
    } 
  }
}
