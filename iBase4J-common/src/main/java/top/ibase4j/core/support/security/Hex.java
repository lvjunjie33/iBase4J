package top.ibase4j.core.support.security;
















public class Hex
{
  public static final String DEFAULT_CHARSET_NAME = "UTF-8";
  private static final char[] DIGITS_LOWER = { 
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };



  
  private static final char[] DIGITS_UPPER = { 
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };















  
  public static byte[] decodeHex(char[] data) throws Exception {
    int len = data.length;
    
    if ((len) != 0) {
      throw new Exception("Odd number of characters.");
    }
    
    byte[] out = new byte[len >> 1];

    
    for (int i = 0, j = 0; j < len; i++) {
      int f = toDigit(data[j], j) << 4;
      j++;
      f |= toDigit(data[j], j);
      j++;
      out[i] = (byte)(f & 0xFF);
    } 
    
    return out;
  }










  
  public static char[] encodeHex(byte[] data) { return encodeHex(data, true); }














  
  public static char[] encodeHex(byte[] data, boolean toLowerCase) { return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER); }












  
  protected static char[] encodeHex(byte[] data, char[] toDigits) {
    int l = data.length;
    char[] out = new char[l << 1];
    
    for (int i = 0, j = 0; i < l; i++) {
      out[j++] = toDigits[(0xF0 & data[i]) >>> '\004'];
      out[j++] = toDigits[0xF & data[i]];
    } 
    return out;
  }











  
  public static String encodeHexString(byte[] data) { return new String(encodeHex(data)); }









  
  protected static int toDigit(char ch, int index) throws Exception {
    int digit = Character.digit(ch, 16);
    if (digit == -1) {
      throw new Exception("Illegal hexadecimal charcter " + ch + " at index " + index);
    }
    return digit;
  }
  
  private static String charsetName = "UTF-8";






  
  public Hex() {}






  
  public Hex(String csName) { charsetName = csName; }















  
  public byte[] decode(byte[] array) throws Exception {
    try {
      return decodeHex((new String(array, getCharsetName())).toCharArray());
    } catch (Exception e) {
      throw new Exception(e.getMessage(), e);
    } 
  }















  
  public Object decode(Object object) throws Exception {
    try {
      char[] charArray = (object instanceof String) ? ((String)object).toCharArray() : (char[])object;
      return decodeHex(charArray);
    } catch (ClassCastException e) {
      throw new Exception(e.getMessage(), e);
    } 
  }

















  
  public static byte[] encode(byte[] array) throws Exception {
    String string = encodeHexString(array);
    if (string == null) {
      return null;
    }
    return string.getBytes(charsetName);
  }















  
  public Object encode(Object object) throws Exception {
    try {
      byte[] byteArray = (object instanceof String) ? ((String)object).getBytes(getCharsetName()) : (byte[])object;
      
      return encodeHex(byteArray);
    } catch (ClassCastException e) {
      throw new Exception(e.getMessage(), e);
    } catch (Exception e) {
      throw new Exception(e.getMessage(), e);
    } 
  }







  
  public String getCharsetName() { return charsetName; }









  
  public String toString() { return super.toString() + "[charsetName=" + charsetName + "]"; }
}
