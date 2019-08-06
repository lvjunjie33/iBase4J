package top.ibase4j.core.support.redisson;

import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.MasterSlaveServersConfig;
import org.redisson.config.SingleServerConfig;



















public class Client
{
  private String address;
  private String password;
  private Set<String> nodeAddresses = new HashSet();


  
  private String masterAddress;

  
  private Integer timeout = Integer.valueOf(120000);



  
  private Set<String> slaveAddresses = new HashSet();
  
  public RedissonClient getRedissonClient() {
    Config config = new Config();
    if (StringUtils.isNotBlank(this.address)) {
      SingleServerConfig serverConfig = config.useSingleServer().setAddress(this.address);
      if (StringUtils.isNotBlank(this.password)) {
        serverConfig.setPassword(this.password);
      }
      serverConfig.setTimeout(this.timeout.intValue());
    } else if (!this.nodeAddresses.isEmpty()) {
      
      ClusterServersConfig serverConfig = config.useClusterServers().addNodeAddress((String[])this.nodeAddresses.toArray(new String[0]));
      if (StringUtils.isNotBlank(this.password)) {
        serverConfig.setPassword(this.password);
      }
      serverConfig.setTimeout(this.timeout.intValue());
    } else if (this.masterAddress != null && !this.slaveAddresses.isEmpty()) {
      
      MasterSlaveServersConfig serverConfig = config.useMasterSlaveServers().setMasterAddress(this.masterAddress).addSlaveAddress((String[])this.slaveAddresses.toArray(new String[0]));
      if (StringUtils.isNotBlank(this.password)) {
        serverConfig.setPassword(this.password);
      }
      serverConfig.setTimeout(this.timeout.intValue());
    } 
    return Redisson.create(config);
  }

  
  public void setAddress(String address) { this.address = address; }


  
  public void setPassword(String password) { this.password = password; }

  
  public void setNodeAddresses(String nodeAddresse) {
    if (nodeAddresse != null) {
      String[] nodeAddresses = nodeAddresse.split(",");
      for (int i = 0; i < nodeAddresses.length; i++) {
        if (StringUtils.isNotEmpty(nodeAddresses[i])) {
          this.nodeAddresses.add(nodeAddresses[i]);
        }
      } 
    } 
  }

  
  public void setMasterAddress(String masterAddress) { this.masterAddress = masterAddress; }

  
  public void setSlaveAddresses(String slaveAddresse) {
    if (slaveAddresse != null) {
      String[] slaveAddresses = slaveAddresse.split(",");
      for (int i = 0; i < slaveAddresses.length; i++) {
        if (StringUtils.isNotEmpty(slaveAddresses[i])) {
          this.slaveAddresses.add(slaveAddresses[i]);
        }
      } 
    } 
  }

  
  public void setTimeout(Integer timeout) { this.timeout = timeout; }
}
