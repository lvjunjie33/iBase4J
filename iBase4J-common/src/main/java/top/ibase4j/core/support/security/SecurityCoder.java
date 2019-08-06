package top.ibase4j.core.support.security;

import java.security.MessageDigest;
import java.security.Security;










public abstract class SecurityCoder
{
  private static Byte ADDFLAG = Byte.valueOf((byte)0);
  static  {
    if (ADDFLAG.byteValue() == 0) {
      
      Security.addProvider(new BouncyCastleProvider());
      ADDFLAG = Byte.valueOf((byte)1);
    } 
  }

  
  public static byte[] digest(String algorithm, String data) throws Exception {
    MessageDigest md = MessageDigest.getInstance(algorithm);
    
    return md.digest(data.getBytes("UTF-8"));
  }
}
