package top.ibase4j.core.interceptor;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiOperation;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.method.HandlerMethod;
import top.ibase4j.core.support.context.ApplicationContextHolder;
import top.ibase4j.core.support.http.SessionUser;
import top.ibase4j.core.util.DateUtil;
import top.ibase4j.core.util.ExceptionUtil;
import top.ibase4j.core.util.ThreadUtil;
import top.ibase4j.core.util.WebUtil;
import top.ibase4j.model.SysEvent;
import top.ibase4j.service.SysEventService;









public class EventInterceptor
  extends BaseInterceptor
{
  private final ThreadLocal<Long> STARTTIME_THREADLOCAL = new NamedThreadLocal("ThreadLocalStartTime");
  protected ExecutorService executorService = ThreadUtil.threadPool(0, 2147483647, 60);
  
  private SysEventService sysEventService;

  
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String path = request.getServletPath();
    
    this.STARTTIME_THREADLOCAL.set(Long.valueOf(System.currentTimeMillis()));
    Map<String, Object> params = WebUtil.getParameterMap(request);
    logger.info("request {} parameters===>{}", path, JSON.toJSONString(params));
    WebUtil.REQUEST.set(request);
    return super.preHandle(request, response, handler);
  }


  
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, final Exception ex) throws Exception {
    Long startTime = (Long)this.STARTTIME_THREADLOCAL.get();
    Long endTime = Long.valueOf(System.currentTimeMillis());
    String path = request.getServletPath();
    
    if (handler instanceof HandlerMethod) {
      try {
        String userAgent = request.getHeader("USER-AGENT");
        String clientIp = WebUtil.getHost(request);
        SessionUser session = WebUtil.getCurrentUser(request);
        if (!path.contains("/error") && !path.contains("/read/") && !path.contains("/get") && 
          !path.contains("/query") && !path.contains("/detail") && 
          !path.contains("/unauthorized") && !path.contains("/forbidden")) {
          final SysEvent record = new SysEvent();
          record.setMethod(request.getMethod());
          record.setRequestUri(path);
          record.setClientHost(clientIp);
          record.setUserAgent(userAgent);
          if (path.contains("/upload")) {
            record.setParameters("");
          } else {
            record.setParameters(JSON.toJSONString(WebUtil.getParameter(request)));
          } 
          record.setStatus(Integer.valueOf(response.getStatus()));
          if (session != null) {
            record.setUserName(session.getUserName());
            record.setUserPhone(session.getUserPhone());
            record.setCreateBy(session.getId());
            record.setUpdateBy(session.getId());
          } 
          final String msg = (String)request.getAttribute("msg");
          try {
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            ApiOperation apiOperation = (ApiOperation)handlerMethod.getMethod().getAnnotation(ApiOperation.class);
            if (apiOperation != null) {
              record.setTitle(apiOperation.value());
            }
          } catch (Exception e) {
            logger.error("", e);
          } 
          this.executorService.execute(new Runnable()
              {
                public void run() {
                  try {
                    if (StringUtils.isNotBlank(msg)) {
                      record.setRemark(msg);
                    } else {
                      record.setRemark(ExceptionUtil.getStackTraceAsString(ex));
                    } 
                    EventInterceptor.this.saveEvent(record);
                  } catch (Exception e) {
                    BaseInterceptor.logger.error("Save event log cause error :", e);
                  } 
                }
              });
        } else if (path.contains("/unauthorized")) {
          logger.warn("The user [{}] no login", clientIp + "@" + userAgent);
        } else if (path.contains("/forbidden")) {
          logger.warn("The user [{}] no promission", 
              JSON.toJSONString(session) + "@" + clientIp + "@" + userAgent);
        } else {
          logger.info(JSON.toJSONString(session) + "@" + path + "@" + clientIp + userAgent);
        } 
      } catch (Throwable e) {
        logger.error("", e);
      } 
    }
    
    String message = "Starttime: {}; Endtime: {}; Used time: {}s; URI: {}; ";
    logger.debug(message, DateUtil.format(startTime, "HH:mm:ss.SSS"), DateUtil.format(endTime, "HH:mm:ss.SSS"), 
        String.valueOf((endTime.longValue() - startTime.longValue()) / 1000.0D), path);
    super.afterCompletion(request, response, handler, ex);
  }
  
  protected void saveEvent(SysEvent record) {
    if (this.sysEventService == null) {
      this.sysEventService = (SysEventService)ApplicationContextHolder.getService(SysEventService.class);
    }
    this.sysEventService.update(record);
  }
}
