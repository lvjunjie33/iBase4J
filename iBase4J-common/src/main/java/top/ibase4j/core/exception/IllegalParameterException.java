package top.ibase4j.core.exception;

import top.ibase4j.core.support.http.HttpCode;










public class IllegalParameterException
  extends BaseException
{
  public IllegalParameterException() {}
  
  public IllegalParameterException(Throwable ex) { super(ex); }


  
  public IllegalParameterException(String message) { super(message); }


  
  public IllegalParameterException(String message, Throwable ex) { super(message, ex); }



  
  protected HttpCode getCode() { return HttpCode.BAD_REQUEST; }
}
