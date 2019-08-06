package top.ibase4j.core.support.generator;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.ibase4j.core.util.DataUtil;







public class Sequence
{
  private static final Sequence worker = new Sequence();

  
  public static Long next() { return Long.valueOf(worker.nextId()); }

  
  public static String uuid() {
    ThreadLocalRandom random = ThreadLocalRandom.current();
    return (new UUID(random.nextLong(), random.nextLong())).toString().replaceAll("-", "");
  }
  
  private Logger logger = LogManager.getLogger();
  
  private final long twepoch = 1288834974657L;
  private final long workerIdBits = 5L;
  private final long datacenterIdBits = 5L;
  private final long maxWorkerId = 31L;
  private final long maxDatacenterId = 31L;
  private final long sequenceBits = 12L;
  private final long workerIdShift = 12L;
  private final long datacenterIdShift = 17L;

  
  private final long timestampLeftShift = 22L;
  
  private final long sequenceMask = 4095L;
  
  private long workerId;
  
  private long datacenterId;
  private long sequence = 0L;
  
  private long lastTimestamp = -1L;
  
  public Sequence() {
    this.datacenterId = getDatacenterId(31L);
    this.workerId = getMaxWorkerId(this.datacenterId, 31L);
  }




  
  public Sequence(long workerId, long datacenterId) {
    if (workerId > 31L || workerId < 0L) {
      throw new RuntimeException(String.format("worker Id can't be greater than %d or less than 0", new Object[] { Long.valueOf(31L) }));
    }
    if (datacenterId > 31L || datacenterId < 0L) {
      throw new RuntimeException(
          String.format("datacenter Id can't be greater than %d or less than 0", new Object[] { Long.valueOf(31L) }));
    }
    this.workerId = workerId;
    this.datacenterId = datacenterId;
  }





  
  public long nextId() {
    long timestamp = timeGen();
    if (timestamp < this.lastTimestamp) {
      long offset = this.lastTimestamp - timestamp;
      if (offset <= 5L) {
        try {
          wait(offset);
          timestamp = timeGen();
          if (timestamp < this.lastTimestamp) {
            throw new RuntimeException(
                String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", new Object[] { Long.valueOf(offset) }));
          }
        } catch (Exception e) {
          throw new RuntimeException(e);
        } 
      } else {
        throw new RuntimeException(
            String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", new Object[] { Long.valueOf(offset) }));
      } 
    } 
    
    if (this.lastTimestamp == timestamp) {
      
      this.sequence = this.sequence + 1L & 0xFFFL;
      if (this.sequence == 0L)
      {
        timestamp = tilNextMillis(this.lastTimestamp);
      }
    } else {
      
      this.sequence = ThreadLocalRandom.current().nextLong(1L, 3L);
    } 
    
    this.lastTimestamp = timestamp;
    
    return timestamp - 1288834974657L << 22 | this.datacenterId << 17 | this.workerId << 12 | this.sequence;
  }




  
  protected long timeGen() { return SystemClock.now(); }






  
  private long getDatacenterId(long maxDatacenterId) {
    long id = 0L;
    try {
      InetAddress ip = InetAddress.getLocalHost();
      NetworkInterface network = NetworkInterface.getByInetAddress(ip);
      if (network == null) {
        id = 1L;
      } else {
        byte[] mac = network.getHardwareAddress();
        if (null != mac) {
          id = (0xFFL & mac[mac.length - 1] | 0xFF00L & mac[mac.length - 2] << 8) >> 6;
          id %= (maxDatacenterId + 1L);
        } 
      } 
    } catch (Exception e) {
      this.logger.warn(" getDatacenterId: " + e.getMessage());
    } 
    return id;
  }





  
  private long getMaxWorkerId(long datacenterId, long maxWorkerId) {
    StringBuilder mpid = new StringBuilder();
    mpid.append(datacenterId);
    String name = ManagementFactory.getRuntimeMXBean().getName();
    if (DataUtil.isNotEmpty(name))
    {
      mpid.append(name.split("@")[0]);
    }
    
    return (mpid.toString().hashCode() & 0xFFFF) % (maxWorkerId + 1L);
  }
  
  private long tilNextMillis(long lastTimestamp) {
    long timestamp = timeGen();
    while (timestamp <= lastTimestamp) {
      timestamp = timeGen();
    }
    return timestamp;
  }
}
