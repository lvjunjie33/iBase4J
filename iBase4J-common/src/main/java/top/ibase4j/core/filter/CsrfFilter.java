package top.ibase4j.core.filter;

import java.io.IOException;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.ibase4j.core.util.DateUtil;
import top.ibase4j.core.util.FileUtil;
import top.ibase4j.core.util.WebUtil;







public class CsrfFilter
  implements Filter
{
  private Logger logger = LogManager.getLogger();


  
  private List<String> whiteUrls;

  
  private int size = 0;

  
  public void init(FilterConfig filterConfig) {
    this.logger.info("init CsrfFilter..");
    
    String path = CsrfFilter.class.getResource("/").getFile();
    this.whiteUrls = FileUtil.readFile(path + "white/csrfWhite.txt");
    this.size = (null == this.whiteUrls) ? 0 : this.whiteUrls.size();
  }


  
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    try {
      HttpServletRequest req = (HttpServletRequest)request;
      
      String url = req.getRequestURL().toString();
      String referurl = req.getHeader("Referer");
      if (WebUtil.isWhiteRequest(referurl, this.size, this.whiteUrls)) {
        chain.doFilter(request, response);
      } else {
        
        this.logger.warn("跨站请求---->>>{} || {} || {} || {}", url, referurl, WebUtil.getHost(req), 
            DateUtil.getDateTime());
        WebUtil.write(response, Integer.valueOf(308), "错误的请求头信息");
        return;
      } 
    } catch (Exception e) {
      this.logger.error("doFilter", e);
    } 
  }


  
  public void destroy() { this.logger.info("destroy CsrfFilter."); }
}
