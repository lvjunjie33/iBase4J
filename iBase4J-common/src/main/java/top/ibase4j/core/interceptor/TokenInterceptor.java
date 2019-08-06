package top.ibase4j.core.interceptor;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import top.ibase4j.core.support.http.HttpCode;
import top.ibase4j.core.support.http.SessionUser;
import top.ibase4j.core.util.CacheUtil;
import top.ibase4j.core.util.DataUtil;
import top.ibase4j.core.util.FileUtil;
import top.ibase4j.core.util.PropertiesUtil;
import top.ibase4j.core.util.SecurityUtil;
import top.ibase4j.core.util.WebUtil;








public class TokenInterceptor
  extends BaseInterceptor
{
  private SignInterceptor signInterceptor;
  private List<String> whiteUrls;
  private int size = 0;
  
  public TokenInterceptor() {
    this.signInterceptor = new SignInterceptor();
    
    String path = TokenInterceptor.class.getResource("/").getFile();
    this.whiteUrls = FileUtil.readFile(path + "white/tokenWhite.txt");
    this.size = (null == this.whiteUrls) ? 0 : this.whiteUrls.size();
  }


  
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    SessionUser session = null;
    
    String token = request.getHeader("token");
    if (DataUtil.isNotEmpty(token)) {
      String cacheKey = "S:iBase4J:TOKEN_KEY:" + SecurityUtil.encryptMd5(token);
      session = (SessionUser)CacheUtil.getCache().get(cacheKey);
      if (DataUtil.isNotEmpty(session)) {
        request.setAttribute("CURRENT_USER", session);
        CacheUtil.getCache().expire(cacheKey, PropertiesUtil.getInt("APP-TOKEN-EXPIRE", 604800));
      } 
    } 
    
    String url = request.getRequestURL().toString();
    String refer = request.getHeader("Referer");
    if ((refer != null && refer.contains("/swagger")) || WebUtil.isWhiteRequest(url, this.size, this.whiteUrls)) {
      logger.info("TokenInterceptor skip");
      if (this.signInterceptor.preHandle(request, response, handler)) {
        return super.preHandle(request, response, handler);
      }
      return false;
    } 
    if (DataUtil.isEmpty(token)) {
      return WebUtil.write(response, HttpCode.UNAUTHORIZED.value(), "请登录");
    }
    logger.debug("Token {}", token);
    
    if (DataUtil.isEmpty(session)) {
      return WebUtil.write(response, HttpCode.UNAUTHORIZED.value(), "会话已过期");
    }
    if (this.signInterceptor.preHandle(request, response, handler)) {
      logger.info("TokenInterceptor successful");
      return super.preHandle(request, response, handler);
    } 
    return false;
  }
}
