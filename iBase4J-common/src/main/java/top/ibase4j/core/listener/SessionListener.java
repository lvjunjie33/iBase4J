package top.ibase4j.core.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;







public class SessionListener{
  private Logger logger = LogManager.getLogger();

  
  @Autowired
  RedisTemplate redisTemplate;


  
  public void onStart(Session session) {
    session.setAttribute("webTheme", "default");
    this.logger.info("创建了一个Session连接:[" + session.getId() + "]");
    this.redisTemplate.opsForSet().add("S:iBase4J:ALLUSER_NUMBER", new Object[] { session.getId() });
  }



  
  public void onStop(Session session) {
    if (getAllUserNumber().intValue() > 0) {
      this.logger.info("销毁了一个Session连接:[" + session.getId() + "]");
    }
    session.removeAttribute("CURRENT_USER");
    this.redisTemplate.opsForSet().remove("S:iBase4J:ALLUSER_NUMBER", new Object[] { session.getId() });
  }




  
  public void onExpiration(Session session) { onStop(session); }



  
  public Integer getAllUserNumber() { return Integer.valueOf(this.redisTemplate.opsForSet().size("S:iBase4J:ALLUSER_NUMBER").intValue()); }
}
