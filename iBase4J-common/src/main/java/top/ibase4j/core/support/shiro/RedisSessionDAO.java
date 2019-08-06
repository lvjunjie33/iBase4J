package top.ibase4j.core.support.shiro;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import top.ibase4j.core.util.InstanceUtil;
import top.ibase4j.core.util.SerializeUtil;








public class RedisSessionDAO
  extends AbstractSessionDAO
{
  private static final int EXPIRE_TIME = 300;
  @Autowired
  private RedisTemplate<Serializable, Serializable> redisTemplate;
  
  public void delete(Serializable sessionId) {
//    if (sessionId != null) {
//      byte[] sessionKey = buildRedisSessionKey(sessionId);
//      factory = this.redisTemplate.getConnectionFactory();
//      conn = null;
//      try {
//        conn = RedisConnectionUtils.getConnection(factory);
//        conn.del(new byte[][] { sessionKey });
//      } finally {
//        RedisConnectionUtils.releaseConnection(conn, factory);
//      } 
//    } 
  }

  
  protected Serializable doCreate(Session session) {
    Serializable sessionId = generateSessionId(session);
    assignSessionId(session, sessionId);
    saveSession(session);
    return sessionId;
  }

  
  protected Session doReadSession(Serializable sessionId) {
	return null;
//    byte[] sessionKey = buildRedisSessionKey(sessionId);
//    factory = this.redisTemplate.getConnectionFactory();
//    conn = null;
//    try {
//      conn = RedisConnectionUtils.getConnection(factory);
//      byte[] value = conn.get(sessionKey);
//      if (value == null) {
//        return null;
//      }
//      Session session = (Session)SerializeUtil.deserialize(value, org.apache.shiro.session.mgt.SimpleSession.class);
//      return session;
//    } finally {
//      RedisConnectionUtils.releaseConnection(conn, factory);
//    } 
  }


  
  public void update(Session session) throws UnknownSessionException { saveSession(session); }


  
  public void delete(Session session) throws UnknownSessionException {
//    if (session != null) {
//      Serializable id = session.getId();
//      if (id != null) {
//        factory = this.redisTemplate.getConnectionFactory();
//        conn = null;
//        try {
//          conn = RedisConnectionUtils.getConnection(factory);
//          conn.del(new byte[][] { buildRedisSessionKey(id) });
//        } finally {
//          RedisConnectionUtils.releaseConnection(conn, factory);
//        } 
//      } 
//    } 
  }

  
  public Collection<Session> getActiveSessions() {
	return null;
//    List<Session> list = InstanceUtil.newArrayList();
//    factory = this.redisTemplate.getConnectionFactory();
//    conn = null;
//    try {
//      conn = RedisConnectionUtils.getConnection(factory);
//      Set<byte[]> set = conn.keys("S:iBase4J:SHIRO-SESSION:*".getBytes());
//      for (byte[] key : set) {
//        list.add(SerializeUtil.deserialize(conn.get(key), org.apache.shiro.session.mgt.SimpleSession.class));
//      }
//    } finally {
//      RedisConnectionUtils.releaseConnection(conn, factory);
//    } 
//    return list;
  }
  
  private void saveSession(Session session) throws UnknownSessionException {
//    if (session == null || session.getId() == null) {
//      throw new UnknownSessionException("session is empty");
//    }
//    byte[] sessionKey = buildRedisSessionKey(session.getId());
//    long sessionTimeOut = session.getTimeout() + 300L;
//    byte[] value = SerializeUtil.serialize(session);
//    factory = this.redisTemplate.getConnectionFactory();
//    conn = null;
//    try {
//      conn = RedisConnectionUtils.getConnection(factory);
//      conn.setEx(sessionKey, sessionTimeOut, value);
//    } finally {
//      RedisConnectionUtils.releaseConnection(conn, factory);
//    } 
  }

  
  private byte[] buildRedisSessionKey(Serializable sessionId) { return ("S:iBase4J:SHIRO-SESSION:" + sessionId).getBytes(); }
}
