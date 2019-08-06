package top.ibase4j.core.exception;

import top.ibase4j.core.support.http.HttpCode;








public class FtpException
  extends BaseException
{
  public FtpException() {}
  
  public FtpException(String message) { super(message); }


  
  public FtpException(String message, Throwable throwable) { super(message, throwable); }



  
  protected HttpCode getCode() { return HttpCode.INTERNAL_SERVER_ERROR; }
}
