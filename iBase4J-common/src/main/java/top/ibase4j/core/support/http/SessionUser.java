package top.ibase4j.core.support.http;

import java.io.Serializable;





public class SessionUser
  implements Serializable
{
  private Long id;
  private String userName;
  private String userPhone;
  private Boolean rememberMe = Boolean.valueOf(false);
  
  public SessionUser(Long id, String userName, String userPhone, boolean rememberMe) {
    this.id = id;
    this.userName = userName;
    this.userPhone = userPhone;
    this.rememberMe = Boolean.valueOf(rememberMe);
  }

  
  public Long getId() { return this.id; }

  
  public SessionUser setId(Long id) {
    this.id = id;
    return this;
  }

  
  public String getUserName() { return this.userName; }

  
  public SessionUser setUserName(String userName) {
    this.userName = userName;
    return this;
  }

  
  public String getUserPhone() { return this.userPhone; }

  
  public SessionUser setUserPhone(String userPhone) {
    this.userPhone = userPhone;
    return this;
  }

  
  public Boolean getRememberMe() { return this.rememberMe; }

  
  public SessionUser setRememberMe(Boolean rememberMe) {
    this.rememberMe = rememberMe;
    return this;
  }
}
