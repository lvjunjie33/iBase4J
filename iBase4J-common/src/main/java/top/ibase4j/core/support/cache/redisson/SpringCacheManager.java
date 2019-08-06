package top.ibase4j.core.support.cache.redisson;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.redisson.api.RMap;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonCache;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;




public class SpringCacheManager
  implements CacheManager, ResourceLoaderAware, InitializingBean
{
  private ResourceLoader resourceLoader;
  private boolean allowNullValues;
  private Codec codec;
  private RedissonClient redisson;
  private Map<String, CacheConfig> configMap = new LinkedHashMap();
  private Map<String, CacheConfig> patternMap = new LinkedHashMap();


  
  private String configLocation;


  
  private String regExpConfigLocation;



  
  public SpringCacheManager() {}


  
  public SpringCacheManager(RedissonClient redisson, Map<String, CacheConfig> config) { this(redisson, config, null, null); }















  
  public SpringCacheManager(RedissonClient redisson, Map<String, CacheConfig> config, Codec codec) { this(redisson, config, null, codec); }













  
  public SpringCacheManager(RedissonClient redisson, Map<String, CacheConfig> config, Map<String, CacheConfig> patternConfig) { this(redisson, config, patternConfig, null); }
















  
  public SpringCacheManager(RedissonClient redisson, Map<String, CacheConfig> config, Map<String, CacheConfig> patternConfig, Codec codec) {
    this.redisson = redisson;
    if (config != null) {
      this.configMap.putAll(config);
    }
    if (patternConfig != null) {
      this.patternMap.putAll(patternConfig);
    }
    this.codec = codec;
  }














  
  public SpringCacheManager(RedissonClient redisson, String configLocation) { this(redisson, configLocation, null, null); }
















  
  public SpringCacheManager(RedissonClient redisson, String configLocation, String regExpConfigLocation) { this(redisson, configLocation, regExpConfigLocation, null); }



















  
  public SpringCacheManager(RedissonClient redisson, String configLocation, Codec codec) { this(redisson, configLocation, null, codec); }




















  
  public SpringCacheManager(RedissonClient redisson, String configLocation, String regExpConfigLocation, Codec codec) {
    this.allowNullValues = true;
    this.redisson = redisson;
    this.configLocation = configLocation;
    this.regExpConfigLocation = regExpConfigLocation;
    this.codec = codec;
  }







  
  public void setConfigLocation(String configLocation) { this.configLocation = configLocation; }








  
  public void setRegExpConfigLocation(String regExpConfigLocation) { this.regExpConfigLocation = regExpConfigLocation; }







  
  public void setConfig(Map<String, ? extends CacheConfig> config) {
    this.configMap.clear();
    if (config == null) {
      return;
    }
    this.configMap.putAll(config);
  }






  
  public void setPatternConfig(Map<String, CacheConfig> config) {
    this.patternMap.clear();
    if (config == null) {
      return;
    }
    this.patternMap.putAll(config);
  }







  
  public void setRedisson(RedissonClient redisson) { this.redisson = redisson; }








  
  public void setCodec(Codec codec) { this.codec = codec; }


  
  public void setAllowNullValues(boolean allowNullValues) { this.allowNullValues = allowNullValues; }

  
  public Cache getCache(String name) {
    String cacheName;
    CacheConfig config = (CacheConfig)this.configMap.get(name);
    
    if (config == null) {
      Pattern pattern = testRegExp(name);
      if (pattern == null) {
        config = new CacheConfig();
        this.configMap.put(name, config);
        
        RMap<Object, Object> map = createMap(name);
        return new RedissonCache(map, this.allowNullValues);
      } 
      config = (CacheConfig)this.patternMap.get(name);
      cacheName = pattern.pattern();
    } else {
      
      cacheName = name;
    } 
    if (config.getMaxIdleTime() == 0L && config.getTTL() == 0L) {
      RMap<Object, Object> map = createMap(cacheName);
      return new RedissonCache(map, this.allowNullValues);
    } 
    RMapCache<Object, Object> map = createMapCache(cacheName);
    return new RedissonCache(map, config, this.allowNullValues);
  }
  
  private Pattern testRegExp(String name) {
    for (String regex : this.patternMap.keySet()) {
      Pattern pattern = Pattern.compile(regex);
      if (pattern.matcher(name).matches()) {
        return pattern;
      }
    } 
    return null;
  }
  
  private RMap<Object, Object> createMap(String name) {
    if (this.codec != null) {
      return this.redisson.getMap(name, this.codec);
    }
    return this.redisson.getMap(name);
  }
  
  private RMapCache<Object, Object> createMapCache(String name) {
    if (this.codec != null) {
      return this.redisson.getMapCache(name, this.codec);
    }
    return this.redisson.getMapCache(name);
  }

  
  public Collection<String> getCacheNames() {
    Set<String> names = Collections.emptySet();
    names.addAll(getConfigNames());
    names.addAll(getPatternNames());
    return Collections.unmodifiableSet(names);
  }

  
  public Collection<String> getConfigNames() { return Collections.unmodifiableSet(this.configMap.keySet()); }

  
  public Collection<String> getPatternNames() {
    Set<String> patterns = Collections.emptySet();
    for (String k : this.patternMap.keySet()) {
      patterns.add(k);
    }
    return Collections.unmodifiableSet(patterns);
  }


  
  public void setResourceLoader(ResourceLoader resourceLoader) { this.resourceLoader = resourceLoader; }


  
  public void afterPropertiesSet() {
    if (this.configLocation != null) {
      Resource resource = this.resourceLoader.getResource(this.configLocation);
      try {
        setConfig(CacheConfig.fromJSON(resource.getInputStream()));
      } catch (IOException e) {
        
        try {
          setConfig(CacheConfig.fromYAML(resource.getInputStream()));
        } catch (IOException e1) {
          throw new BeanDefinitionStoreException("Could not parse cache configuration at [" + this.configLocation + "]", e1);
        } 
      } 
    } 
    
    if (this.regExpConfigLocation != null) {
      Map<String, ? extends CacheConfig> confs; Resource resource = this.resourceLoader.getResource(this.regExpConfigLocation);
      
      try {
        confs = CacheConfig.fromJSON(resource.getInputStream());
      } catch (IOException e) {
        
        try {
          confs = CacheConfig.fromYAML(resource.getInputStream());
        } catch (IOException e1) {
          throw new BeanDefinitionStoreException("Could not parse cache configuration at [" + this.configLocation + "]", e1);
        } 
      } 
      
      for (Map.Entry<String, ? extends CacheConfig> conf : confs.entrySet())
        this.patternMap.put(conf.getKey(), conf.getValue()); 
    } 
  }
}
