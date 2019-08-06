package top.ibase4j.core.exception;

import top.ibase4j.core.support.http.HttpCode;







public class DataParseException
  extends BaseException
{
  public DataParseException() {}
  
  public DataParseException(Throwable ex) { super(ex); }


  
  public DataParseException(String message) { super(message); }


  
  public DataParseException(String message, Throwable ex) { super(message, ex); }



  
  protected HttpCode getCode() { return HttpCode.INTERNAL_SERVER_ERROR; }
}
