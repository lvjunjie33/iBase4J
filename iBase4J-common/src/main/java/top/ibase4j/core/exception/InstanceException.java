package top.ibase4j.core.exception;

import top.ibase4j.core.support.http.HttpCode;











public class InstanceException
  extends BaseException
{
  public InstanceException() {}
  
  public InstanceException(Throwable t) { super(t); }



  
  protected HttpCode getCode() { return HttpCode.INTERNAL_SERVER_ERROR; }
}
