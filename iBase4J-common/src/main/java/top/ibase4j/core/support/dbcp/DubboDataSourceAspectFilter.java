package top.ibase4j.core.support.dbcp;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;





@Activate(group = {"provider"})
public class DubboDataSourceAspectFilter
  implements Filter
{
  private static final Logger logger = LogManager.getLogger();
  
  public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
    String service = invoker.getInterface().getSimpleName();
    String method = invocation.getMethodName();
    HandleDataSource.setDataSource(service, method);
    Result result = invoker.invoke(invocation);
    logger.info(service + "." + method + "=>end.");
    return result;
  }
}
