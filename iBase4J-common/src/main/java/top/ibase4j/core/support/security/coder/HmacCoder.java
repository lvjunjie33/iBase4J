package top.ibase4j.core.support.security.coder;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import top.ibase4j.core.support.security.Hex;
import top.ibase4j.core.support.security.SecurityCoder;













public abstract class HmacCoder
  extends SecurityCoder
{
  public static final String MD2 = "HmacMD2";
  public static final String MD4 = "HmacMD4";
  public static final String MD5 = "HmacMD5";
  public static final String SHA1 = "HmacSHA1";
  public static final String SHA224 = "HmacSHA224";
  public static final String SHA256 = "HmacSHA256";
  public static final String SHA512 = "HmacSHA512";
  
  public static byte[] initHmacKey(String type) throws Exception {
    KeyGenerator keyGenerator = KeyGenerator.getInstance(type);
    
    SecretKey secretKey = keyGenerator.generateKey();
    
    return secretKey.getEncoded();
  }







  
  public static byte[] encodeHmac(String type, byte[] data, byte[] key) throws Exception {
    SecretKey secretKey = new SecretKeySpec(key, type);
    
    Mac mac = Mac.getInstance(secretKey.getAlgorithm());
    
    mac.init(secretKey);
    
    return mac.doFinal(data);
  }







  
  public static String encodeHmacHex(String type, byte[] data, byte[] key) throws Exception {
    byte[] b = encodeHmac(type, data, key);
    
    return new String(Hex.encode(b));
  }
}
