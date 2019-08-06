package top.ibase4j.core.support.security.coder;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import top.ibase4j.core.support.security.SecurityCoder;
































public abstract class DESCoder
  extends SecurityCoder
{
  public static final String KEY_ALGORITHM = "DES";
  public static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5PADDING";
  
  private static Key toKey(byte[] key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
    DESKeySpec dks = new DESKeySpec(key);
    
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
    
    return keyFactory.generateSecret(dks);
  }
















  
  public static byte[] decrypt(byte[] data, byte[] key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
    Key k = toKey(key);
    
    Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5PADDING");
    
    cipher.init(2, k);
    
    return cipher.doFinal(data);
  }















  
  public static byte[] encrypt(byte[] data, byte[] key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
    Key k = toKey(key);
    
    Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5PADDING");
    
    cipher.init(1, k);
    
    return cipher.doFinal(data);
  }














  
  public static byte[] initKey() throws NoSuchAlgorithmException {
    KeyGenerator kg = KeyGenerator.getInstance("DES");


    
    kg.init(56, new SecureRandom());
    
    SecretKey secretKey = kg.generateKey();
    
    return secretKey.getEncoded();
  }
}
