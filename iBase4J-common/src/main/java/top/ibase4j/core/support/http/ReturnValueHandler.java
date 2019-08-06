package top.ibase4j.core.support.http;

import java.util.List;
import java.util.Map;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import top.ibase4j.core.support.Pagination;
import top.ibase4j.core.util.DataUtil;
import top.ibase4j.core.util.InstanceUtil;







public class ReturnValueHandler
  implements HandlerMethodReturnValueHandler
{
  private HandlerMethodReturnValueHandler handler;
  
  public ReturnValueHandler(HandlerMethodReturnValueHandler handler) { this.handler = handler; }



  
  public boolean supportsReturnType(MethodParameter returnType) { return this.handler.supportsReturnType(returnType); }



  
  public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
    if (returnValue != null) {
      if (returnValue instanceof org.springframework.http.ResponseEntity) {
        this.handler.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
      } else {
        Map<String, Object> modelMap = InstanceUtil.newHashMap();
        if (returnValue instanceof Pagination) {
          Pagination<?> page = (Pagination)returnValue;
          modelMap.put("rows", page.getRecords());
          modelMap.put("current", Long.valueOf(page.getCurrent()));
          modelMap.put("size", Long.valueOf(page.getSize()));
          modelMap.put("pages", Long.valueOf(page.getPages()));
          modelMap.put("total", page.getTotal());
        } else if (returnValue instanceof List) {
          modelMap.put("rows", returnValue);
          modelMap.put("total", Integer.valueOf(((List)returnValue).size()));
        } else if (DataUtil.isNotEmpty(returnValue)) {
          modelMap.put("data", returnValue);
        } 
        modelMap.put("code", Integer.valueOf(200));
        modelMap.put("msg", "");
        modelMap.put("timestamp", Long.valueOf(System.currentTimeMillis()));
        this.handler.handleReturnValue(modelMap, returnType, mavContainer, webRequest);
      } 
    } else {
      this.handler.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
    } 
  }
}
