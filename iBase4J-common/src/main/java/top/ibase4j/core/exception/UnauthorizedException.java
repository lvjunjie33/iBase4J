package top.ibase4j.core.exception;

import top.ibase4j.core.support.http.HttpCode;






public class UnauthorizedException
  extends BaseException
{
  public UnauthorizedException() {}
  
  public UnauthorizedException(String message) { super(message); }


  
  public UnauthorizedException(String message, Exception e) { super(message, e); }



  
  protected HttpCode getCode() { return HttpCode.UNAUTHORIZED; }
}
