package top.ibase4j.core.support.validate;





public enum RegexType
{
  NONE(null),
  
  SPECIALCHAR(""),
  
  NONECHINESE(""),
  
  IDCARD(""),
  
  DATE("[1-9]{4}([-./_]?)(0?[1-9]|1[0-2])([-./_]?)((0?[1-9])|((1|2)[0-9])|30|31)?"),
  
  PASSWORD("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,18}$"),
  
  CHINESE("^[一-龥]+$"),
  
  EMAIL("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$"),
  
  IP("^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$"),

  
  URL("(http://|ftp://|https://|www){0,1}[^一-龥\\s]*?\\.(com|net|cn|me|tw|fr)[^一-龥\\s]*"),
  
  NUMBER("^(-?\\d+)(\\.\\d+)?$"),
  
  PHONE("^((1[3,5,8][0-9])|(14[5,7])|(16[6])|(17[0,6,7,8])|(19[7,8,9]))\\d{8}$"),
  
  TELEPHONE("^(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)$");
  
  private String value;

  
  RegexType(String value) { this.value = value; }


  
  public String value() { return this.value; }
}
