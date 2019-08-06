package top.ibase4j.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import top.ibase4j.core.base.BaseModel;












@TableName("sys_event")
public class SysEvent
  extends BaseModel
{
  @TableField("title_")
  private String title;
  private String requestUri;
  @TableField("parameters_")
  private String parameters;
  @TableField("method_")
  private String method;
  private String clientHost;
  private String userAgent;
  @TableField("status_")
  private Integer status;
  @TableField("user_name")
  private String userName;
  @TableField("user_phone")
  private String userPhone;
  
  public String getTitle() { return this.title; }






  
  public void setTitle(String title) { this.title = (title == null) ? null : title.trim(); }





  
  public String getRequestUri() { return this.requestUri; }






  
  public void setRequestUri(String requestUri) { this.requestUri = (requestUri == null) ? null : requestUri.trim(); }





  
  public String getParameters() { return this.parameters; }






  
  public void setParameters(String parameters) { this.parameters = (parameters == null) ? null : parameters.trim(); }





  
  public String getMethod() { return this.method; }






  
  public void setMethod(String method) { this.method = (method == null) ? null : method.trim(); }





  
  public String getClientHost() { return this.clientHost; }






  
  public void setClientHost(String clientHost) { this.clientHost = (clientHost == null) ? null : clientHost.trim(); }





  
  public String getUserAgent() { return this.userAgent; }






  
  public void setUserAgent(String userAgent) { this.userAgent = (userAgent == null) ? null : userAgent.trim(); }





  
  public Integer getStatus() { return this.status; }






  
  public void setStatus(Integer status) { this.status = status; }





  
  public String getUserName() { return this.userName; }






  
  public void setUserName(String userName) { this.userName = userName; }


  
  public String getUserPhone() { return this.userPhone; }


  
  public void setUserPhone(String userPhone) { this.userPhone = userPhone; }
}
