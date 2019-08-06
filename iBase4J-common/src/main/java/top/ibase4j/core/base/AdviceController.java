package top.ibase4j.core.base;

import com.alibaba.fastjson.JSON;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import top.ibase4j.core.exception.BaseException;
import top.ibase4j.core.exception.IllegalParameterException;
import top.ibase4j.core.support.DateFormat;
import top.ibase4j.core.support.context.StringEscapeEditor;
import top.ibase4j.core.support.http.HttpCode;
import top.ibase4j.core.util.PropertiesUtil;










@ControllerAdvice
public class AdviceController
{
  private Logger logger = LogManager.getLogger();


  
  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(new DateFormat("yyyy-MM-dd HH:mm:ss"), true));


    
    binder.registerCustomEditor(String.class, new StringEscapeEditor(
          PropertiesUtil.getBoolean("spring.mvc.htmlEscape", false), 
          PropertiesUtil.getBoolean("spring.mvc.javaScriptEscape", false)));
  }


  
  @ExceptionHandler({Throwable.class})
  public ResponseEntity<ModelMap> exceptionHandler(HttpServletRequest request, HttpServletResponse response, Throwable ex) {
    this.logger.error("OH,MY GOD! SOME ERRORS OCCURED! AS FOLLOWS :", ex);
    ModelMap modelMap = new ModelMap();
    if (ex instanceof BaseException) {
      ((BaseException)ex).handler(modelMap);
    } else if (ex instanceof IllegalArgumentException) {
      (new IllegalParameterException(ex.getMessage())).handler(modelMap);
    } else if ("org.apache.shiro.authz.UnauthorizedException".equals(ex.getClass().getName())) {
      modelMap.put("code", HttpCode.FORBIDDEN.value().toString());
      modelMap.put("msg", HttpCode.FORBIDDEN.msg());
    } else {
      modelMap.put("code", HttpCode.INTERNAL_SERVER_ERROR.value().toString());
      String msg = (String)StringUtils.defaultIfBlank(ex.getMessage(), HttpCode.INTERNAL_SERVER_ERROR.msg());
      this.logger.debug(msg);
      modelMap.put("msg", (msg.length() > 100) ? "系统繁忙，请稍候再试." : msg);
    } 
    modelMap.put("timestamp", Long.valueOf(System.currentTimeMillis()));
    this.logger.info("response===>" + JSON.toJSON(modelMap));
    return ResponseEntity.ok(modelMap);
  }
}
