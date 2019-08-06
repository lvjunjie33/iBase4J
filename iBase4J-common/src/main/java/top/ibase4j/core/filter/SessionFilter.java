package top.ibase4j.core.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import top.ibase4j.core.support.http.SessionUser;
import top.ibase4j.core.util.WebUtil;







public class SessionFilter
  implements Filter
{
  private Logger logger = LogManager.getLogger();


  
  public void init(FilterConfig filterConfig) throws ServletException { this.logger.info("init SessionFilter."); }



  
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    SessionUser sessionUser = (SessionUser)SecurityUtils.getSubject().getPrincipal();
    if (sessionUser != null) {
      WebUtil.saveCurrentUser((HttpServletRequest)request, sessionUser);
    }
    chain.doFilter(request, response);
  }


  
  public void destroy() { this.logger.info("destroy SessionFilter."); }
}
