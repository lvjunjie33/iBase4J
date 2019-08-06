package top.ibase4j.core.base.provider;

import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import top.ibase4j.core.util.InstanceUtil;






public abstract class BaseProviderImpl
  implements ApplicationContextAware, BaseProvider
{
  protected Logger logger = LogManager.getLogger();
  
  private ApplicationContext applicationContext;

  
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException { this.applicationContext = applicationContext; }


  
  public Parameter execute(Parameter parameter) {
    String no = parameter.getNo();
    this.logger.info("{} request：{}", no, JSON.toJSONString(parameter));
    Object service = this.applicationContext.getBean(parameter.getService());
    try {
      String method = parameter.getMethod();
      Object[] param = parameter.getParam();
      Object result = InstanceUtil.invokeMethod(service, method, param);
      Parameter response = new Parameter(result);
      this.logger.info("{} response：{}", no, JSON.toJSONString(response));
      return response;
    } catch (Exception e) {
      this.logger.error(no + " " + "OH,MY GOD! SOME ERRORS OCCURED! AS FOLLOWS :", e);
      throw e;
    } 
  }

  
  public Object execute(String service, String method, Object... parameters) {
    this.logger.info("{}.{} request：{}", service, method, JSON.toJSONString(parameters));
    Object owner = this.applicationContext.getBean(service);
    try {
      Object result = InstanceUtil.invokeMethod(owner, method, parameters);
      Parameter response = new Parameter(result);
      this.logger.info("{}.{} response：{}", service, method, JSON.toJSONString(response));
      return response;
    } catch (Exception e) {
      this.logger.error(service + "." + method + " " + "OH,MY GOD! SOME ERRORS OCCURED! AS FOLLOWS :", e);
      throw e;
    } 
  }
}
