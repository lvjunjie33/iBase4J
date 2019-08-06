package top.ibase4j.core.base.provider;

import java.io.Serializable;
import java.util.List;
import top.ibase4j.core.support.generator.Sequence;

public class Parameter
  implements Serializable
{
  private String service;
  private String method;
  private Object[] param;
  private Object result;
  
  public Parameter() {}
  
  public Parameter(String service, String method) {
    this.service = service;
    this.method = method;
  }
  
  public Parameter(String service, String method, Object... param) {
    this.service = service;
    this.method = method;
    this.param = param;
  }

  
  public Parameter(Object result) { this.result = result; }







  
  private final String no = "[" + Sequence.next() + "]";

  
  public String getService() { return this.service; }


  
  public void setService(String service) { this.service = service; }


  
  public String getMethod() { return this.method; }

  
  public Parameter setMethod(String method) {
    this.method = method;
    return this;
  }

  
  public Object[] getParam() { return this.param; }

  
  public Parameter setParam(Object... param) {
    this.param = param;
    return this;
  }

  
  public String getNo() { return this.no; }

  
  public Parameter setResult(Object result) {
    this.result = result;
    return this;
  }

  
  public Object getResult() { return this.result; }

  
  public List<?> getResultList() {
    if (this.result instanceof List) {
      return (List)this.result;
    }
    return null;
  }
  
  public Long getResultLong() {
    if (this.result instanceof Long) {
      return (Long)this.result;
    }
    return null;
  }
}
