package top.ibase4j.core.support.http;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;






public class ReturnValueHandlerFactory
  implements InitializingBean
{
  @Autowired
  private RequestMappingHandlerAdapter adapter;
  
  public void afterPropertiesSet() {
    this.adapter.setReturnValueHandlers(
        decorateHandler(new ArrayList(this.adapter.getReturnValueHandlers())));
  }

  
  private List<HandlerMethodReturnValueHandler> decorateHandler(List<HandlerMethodReturnValueHandler> handlers) {
    for (HandlerMethodReturnValueHandler handler : handlers) {
      if (handler instanceof org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor) {
        handlers.set(handlers.indexOf(handler), new ReturnValueHandler(handler));
        break;
      } 
    } 
    return handlers;
  }
}
