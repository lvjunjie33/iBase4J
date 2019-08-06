package top.ibase4j.core.support.login;

import top.ibase4j.core.base.BaseModel;









public class ThirdPartyUser
  extends BaseModel
{
  private String account;
  private String userName;
  private String avatarUrl;
  private String gender;
  private String token;
  private String openid;
  private String provider;
  private Integer userId;
  
  public Integer getUserId() { return this.userId; }


  
  public void setUserId(Integer userId) { this.userId = userId; }


  
  public String getUserName() { return this.userName; }


  
  public void setUserName(String userName) { this.userName = userName; }


  
  public String getProvider() { return this.provider; }


  
  public void setProvider(String provider) { this.provider = provider; }


  
  public String getOpenid() { return this.openid; }


  
  public void setOpenid(String openid) { this.openid = openid; }


  
  public String getToken() { return this.token; }


  
  public void setToken(String token) { this.token = token; }


  
  public String getAccount() { return this.account; }


  
  public void setAccount(String account) { this.account = account; }


  
  public String getAvatarUrl() { return this.avatarUrl; }


  
  public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }


  
  public String getGender() { return this.gender; }


  
  public void setGender(String gender) { this.gender = gender; }
}
