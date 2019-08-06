package top.ibase4j.model;

import java.io.Serializable;









public class Login
  implements Serializable
{
  private String account;
  private String password;
  private Boolean rememberMe = Boolean.valueOf(false);

  
  public Login() {}
  
  public Login(String account, String password, Boolean rememberMe) {
    this.account = account;
    this.password = password;
    this.rememberMe = rememberMe;
  }




  
  public String getAccount() { return this.account; }





  
  public void setAccount(String account) { this.account = account; }





  
  public String getPassword() { return this.password; }





  
  public void setPassword(String password) { this.password = password; }


  
  public Boolean getRememberMe() { return this.rememberMe; }


  
  public void setRememberMe(Boolean rememberMe) { this.rememberMe = rememberMe; }
}
