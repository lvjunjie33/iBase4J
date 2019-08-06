package top.ibase4j.core.interceptor;

import com.alibaba.fastjson.JSON;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import top.ibase4j.core.support.http.HttpCode;
import top.ibase4j.core.util.CacheUtil;
import top.ibase4j.core.util.FileUtil;
import top.ibase4j.core.util.WebUtil;











public class MaliciousRequestInterceptor
  extends BaseInterceptor
{
  private boolean allRequest = false;
  private boolean containsParamter = true;
  private int minRequestIntervalTime = 100;
  
  private int maxMaliciousTimes = 0;

  
  private List<String> whiteUrls;
  
  private int size = 0;

  
  public MaliciousRequestInterceptor() {
    String path = MaliciousRequestInterceptor.class.getResource("/").getFile();
    this.whiteUrls = FileUtil.readFile(path + "white/mrqWhite.txt");
    this.size = (null == this.whiteUrls) ? 0 : this.whiteUrls.size();
  }


  
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String url = request.getServletPath();
    if (url.endsWith("/unauthorized") || url.endsWith("/forbidden") || 
      WebUtil.isWhiteRequest(url, this.size, this.whiteUrls)) {
      return super.preHandle(request, response, handler);
    }
    if (this.containsParamter) {
      url = url + JSON.toJSONString(WebUtil.getParameterMap(request));
    }
    Object userId = WebUtil.getCurrentUser(request);
    String user = (userId != null) ? userId.toString() : (WebUtil.getHost(request) + request.getHeader("USER-AGENT"));
    String preRequest = (String)CacheUtil.getCache().getFire("iBase4J:PREREQUEST" + user);
    Long preRequestTime = (Long)CacheUtil.getCache().getFire("iBase4J:PREREQUEST_TIME" + user);
    int seconds = this.minRequestIntervalTime;
    
    if (preRequestTime != null && preRequest != null) {
      boolean timeout = (System.currentTimeMillis() - preRequestTime.longValue() < this.minRequestIntervalTime);
      if ((url.equals(preRequest) || this.allRequest) && timeout) {
        
        Integer maliciousRequestTimes = (Integer)CacheUtil.getCache().getFire("iBase4J:MALICIOUS_REQUEST_TIMES" + user);
        if (maliciousRequestTimes == null) {
          maliciousRequestTimes = Integer.valueOf(1);
        } else {
          Integer integer1, integer2 = maliciousRequestTimes = (integer1 = maliciousRequestTimes).valueOf(maliciousRequestTimes.intValue() + 1); 
        
        } 
        CacheUtil.getCache().set("iBase4J:MALICIOUS_REQUEST_TIMES" + user, maliciousRequestTimes, seconds);
        if (maliciousRequestTimes.intValue() > this.maxMaliciousTimes) {
          CacheUtil.getCache().set("iBase4J:MALICIOUS_REQUEST_TIMES" + user, Integer.valueOf(0), seconds);
          logger.warn("To intercept a malicious request : {}", url);
          return WebUtil.write(response, HttpCode.MULTI_STATUS.value(), HttpCode.MULTI_STATUS
              .msg());
        } 
      } else {
        CacheUtil.getCache().set("iBase4J:MALICIOUS_REQUEST_TIMES" + user, Integer.valueOf(0), seconds);
      } 
    } 
    CacheUtil.getCache().set("iBase4J:PREREQUEST" + user, url, seconds);
    CacheUtil.getCache().set("iBase4J:PREREQUEST_TIME" + user, Long.valueOf(System.currentTimeMillis()), seconds);
    return super.preHandle(request, response, handler);
  }
  
  public MaliciousRequestInterceptor setAllRequest(boolean allRequest) {
    this.allRequest = allRequest;
    return this;
  }
  
  public MaliciousRequestInterceptor setContainsParamter(boolean containsParamter) {
    this.containsParamter = containsParamter;
    return this;
  }
  
  public MaliciousRequestInterceptor setMinRequestIntervalTime(int minRequestIntervalTime) {
    this.minRequestIntervalTime = minRequestIntervalTime;
    return this;
  }
  
  public MaliciousRequestInterceptor setMaxMaliciousTimes(int maxMaliciousTimes) {
    this.maxMaliciousTimes = maxMaliciousTimes;
    return this;
  }
}
