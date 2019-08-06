package top.ibase4j.core.filter;

import java.io.IOException;
import java.util.Locale;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.i18n.LocaleContextHolder;






public class LocaleFilter
  implements Filter
{
  private Logger logger = LogManager.getLogger();


  
  public void init(FilterConfig filterConfig) throws ServletException { this.logger.info("init LocaleFilter."); }




  
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    Locale locale = request.getLocale();
    if (locale == null) {
      String language = request.getParameter("locale");
      if (StringUtils.isNotBlank(language)) {
        locale = new Locale(language);
      } else {
        locale = Locale.SIMPLIFIED_CHINESE;
      } 
    } 
    LocaleContextHolder.setLocale(locale);
    chain.doFilter(request, response);
  }


  
  public void destroy() { this.logger.info("destroy LocaleFilter."); }
}
