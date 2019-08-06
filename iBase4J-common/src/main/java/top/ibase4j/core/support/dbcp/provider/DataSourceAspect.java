package top.ibase4j.core.support.dbcp.provider;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import top.ibase4j.core.base.provider.Parameter;
import top.ibase4j.core.support.dbcp.HandleDataSource;









@Aspect
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DataSourceAspect
{
  static  {
    System.setProperty("druid.logType", "log4j2");
  }


  
  @Pointcut("this(top.ibase4j.core.base.provider.BaseProviderImpl)")
  public void aspect() {}


  
  @Before("aspect()")
  public void before(JoinPoint point) {
    Parameter parameter = (Parameter)point.getArgs()[0];
    String service = parameter.getService();
    String method = parameter.getMethod();
    HandleDataSource.setDataSource(service, method);
  }

  
  @After("aspect()")
  public void after(JoinPoint point) { HandleDataSource.clear(); }
}
