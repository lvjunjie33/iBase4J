package top.ibase4j.core.interceptor.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import top.ibase4j.core.interceptor.EventInterceptor;
import top.ibase4j.core.base.provider.BaseProvider;
import top.ibase4j.core.base.provider.Parameter;
import top.ibase4j.model.SysEvent;







public class ProEventInterceptor extends EventInterceptor{
  @Autowired
  @Qualifier("sysProvider")
  protected BaseProvider sysProvider;
  
  protected void saveEvent(SysEvent record) { this.sysProvider.execute(new Parameter("sysEventService", "update", new Object[] { record })); }
}
