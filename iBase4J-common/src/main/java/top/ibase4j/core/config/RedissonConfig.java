package top.ibase4j.core.config;

import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.ibase4j.core.support.cache.RedissonHelper;
import top.ibase4j.core.support.redisson.Client;
import top.ibase4j.core.util.DataUtil;
import top.ibase4j.core.util.PropertiesUtil;







@Configuration
@ConditionalOnClass({RedissonClient.class})
public class RedissonConfig
{
  @Bean
  public RedissonClient redissonClient() {
    Client client = new Client();
    String nodes = PropertiesUtil.getString("redis.cluster.nodes");
    String master = PropertiesUtil.getString("redis.master");
    String sentinels = PropertiesUtil.getString("redis.sentinels");
    if (StringUtils.isNotBlank(nodes)) {
      client.setNodeAddresses(nodes);
    } else if (DataUtil.isNotEmpty(master) && DataUtil.isNotEmpty(sentinels)) {
      client.setMasterAddress(master);
      client.setSlaveAddresses(sentinels);
    } else {
      
      String address = "redis://" + PropertiesUtil.getString("redis.host") + ":" + PropertiesUtil.getString("redis.port");
      client.setAddress(address);
    } 
    client.setPassword(PropertiesUtil.getString("redis.password"));
    client.setTimeout(PropertiesUtil.getInt("redis.timeout"));
    return client.getRedissonClient();
  }
  
  @Bean
  public RedissonHelper redissonHelper(RedissonClient client) {
    RedissonHelper helper = new RedissonHelper();
    helper.setRedissonClient(client);
    return helper;
  }
}
