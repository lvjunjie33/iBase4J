package top.ibase4j.core.config;

import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.Map;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import top.ibase4j.core.filter.SessionFilter;
import top.ibase4j.core.listener.SessionListener;
import top.ibase4j.core.support.cache.shiro.RedisCacheManager;
import top.ibase4j.core.support.context.OrderedProperties;
import top.ibase4j.core.support.shiro.Realm;
import top.ibase4j.core.support.shiro.RedisSessionDAO;
import top.ibase4j.core.util.InstanceUtil;
import top.ibase4j.core.util.PropertiesUtil;











@Configuration
@ConditionalOnBean({Realm.class})
@ConditionalOnClass({RememberMeManager.class})
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration.class})
public class ShiroConfig
{
  @Bean
  public SessionListener sessionListener() { return new SessionListener(); }

  
  @Bean
  public SessionDAO sessionDao(Realm realm) {
    RedisSessionDAO dao = new RedisSessionDAO();
    realm.setSessionDAO(dao);
    return dao;
  }

  
  @Bean
  public DefaultWebSecurityManager securityManager(AuthorizingRealm realm, SessionManager sessionManager, RememberMeManager rememberMeManager) {
    DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
    manager.setRealm(realm);
    manager.setCacheManager(new RedisCacheManager());
    manager.setSessionManager(sessionManager);
    manager.setRememberMeManager(rememberMeManager);
    return manager;
  }
  
  @Bean
  public SessionManager sessionManager(SessionDAO sessionDao, SessionListener sessionListener, Cookie cookie) {
    DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
    sessionManager.setSessionDAO(sessionDao);
//    sessionManager.getSessionListeners().add(sessionListener);
    sessionManager.setSessionIdCookie(cookie);
    sessionManager.setGlobalSessionTimeout(1000L * PropertiesUtil.getLong("session.timeout", 1800L));
    return sessionManager;
  }
  
  @Bean
  public Cookie cookie() {
    SimpleCookie cookie = new SimpleCookie("IBASE4JSESSIONID");
    cookie.setSecure(PropertiesUtil.getBoolean("session.cookie.secure", false));
    cookie.setHttpOnly(true);
    cookie.setPath("/");
    cookie.setMaxAge(-1);
    return cookie;
  }
  
  @Bean
  public RememberMeManager rememberMeManager() {
    CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
    String cipherKey = PropertiesUtil.getString("rememberMe.cookie.cipherKey", "HeUZ/LvgkO7nsa18ZyVxWQ==");
    rememberMeManager.setCipherKey(Base64.decode(cipherKey));
    rememberMeManager.getCookie().setMaxAge(PropertiesUtil.getInt("rememberMe.cookie.maxAge", 604800));
    return rememberMeManager;
  }






  
  @Bean
  public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) throws IOException {
    ShiroFilterFactoryBean factory = new ShiroFilterFactoryBean();
    factory.setSecurityManager(securityManager);
    factory.setLoginUrl("/unauthorized");
    factory.setUnauthorizedUrl("/forbidden");
    Map<String, String> filterMap = InstanceUtil.newLinkedHashMap();
    OrderedProperties properties = new OrderedProperties("classpath:config/shiro.properties");
    filterMap.putAll(Maps.fromProperties(properties));
    factory.setFilterChainDefinitionMap(filterMap);
    factory.getFilters().put("session", new SessionFilter());
    return factory;
  }

  
  @Bean
  public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() { return new LifecycleBeanPostProcessor(); }

  
  @Bean
  @DependsOn({"lifecycleBeanPostProcessor"})
  public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
    DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
    creator.setProxyTargetClass(true);
    return creator;
  }
  
  @Bean
  public AuthorizationAttributeSourceAdvisor authorizationAdvisor(SecurityManager securityManager) {
    AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
    advisor.setSecurityManager(securityManager);
    return advisor;
  }
}
