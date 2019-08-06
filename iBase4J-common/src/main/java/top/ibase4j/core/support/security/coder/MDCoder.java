package top.ibase4j.core.support.security.coder;

import top.ibase4j.core.support.security.SecurityCoder;


















public abstract class MDCoder
  extends SecurityCoder
{
  public static byte[] encodeMD2(String data) throws Exception { return digest("MD2", data); }









  
  public static byte[] encodeMD4(String data) throws Exception { return digest("MD4", data); }









  
  public static byte[] encodeMD5(String data) throws Exception { return digest("MD5", data); }









  
  public static byte[] encodeTiger(String data) throws Exception { return digest("Tiger", data); }









  
  public static byte[] encodeWhirlpool(String data) throws Exception { return digest("Whirlpool", data); }









  
  public static byte[] encodeGOST3411(String data) throws Exception { return digest("GOST3411", data); }
}
