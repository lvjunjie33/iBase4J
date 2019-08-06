package top.ibase4j.core.exception;

import top.ibase4j.core.support.http.HttpCode;






public class ValidateException
  extends BaseException
{
  public ValidateException() {}
  
  public ValidateException(Throwable ex) { super(ex); }


  
  public ValidateException(String message) { super(message); }


  
  public ValidateException(String message, Throwable ex) { super(message, ex); }



  
  protected HttpCode getCode() { return HttpCode.PRECONDITION_FAILED; }
}
