package top.ibase4j.core.support.http;

import top.ibase4j.core.support.context.Resources;







public enum HttpCode{
  OK(Integer.valueOf(200)),
  
  MULTI_STATUS(Integer.valueOf(207)),
  
  BAD_REQUEST(Integer.valueOf(400)),
  
  UNAUTHORIZED(Integer.valueOf(401)),
  
  LOGIN_FAIL(Integer.valueOf(402)),
  
  FORBIDDEN(Integer.valueOf(403)),
  
  NOT_FOUND(Integer.valueOf(404)),
  
  METHOD_NOT_ALLOWED(Integer.valueOf(405)),
  
  NOT_ACCEPTABLE(Integer.valueOf(406)),
  
  REQUEST_TIMEOUT(Integer.valueOf(408)),
  
  CONFLICT(Integer.valueOf(409)),
  
  GONE(Integer.valueOf(410)),
  
  LENGTH_REQUIRED(Integer.valueOf(411)),
  
  PRECONDITION_FAILED(Integer.valueOf(412)),
  
  ENTITY_TOO_LARGE(Integer.valueOf(413)),
  
  UNSUPPORTED_MEDIA_TYPE(Integer.valueOf(415)),
  
  TOO_MANY_CONNECTIONS(Integer.valueOf(421)),
  
  LOCKED(Integer.valueOf(423)),
  
  UNAVAILABLE_LEGAL(Integer.valueOf(451)),
  
  INTERNAL_SERVER_ERROR(Integer.valueOf(500)),
  
  NOT_IMPLEMENTED(Integer.valueOf(501)),
  
  SERVICE_UNAVAILABLE(Integer.valueOf(503)),
  
  NOT_EXTENDED(Integer.valueOf(510));
  
  private final Integer value;

  
  HttpCode(Integer value) { this.value = value; }





  
  public Integer value() { return this.value; }


  
  public String msg() { return Resources.getMessage("HTTPCODE_" + this.value, new Object[0]); }



  
  public String toString() { return this.value.toString(); }
}
