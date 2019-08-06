package top.ibase4j.core.util;

import java.math.BigDecimal;








public final class MathUtil
{
  private static int DEF_SCALE = 10;





  
  public static final BigDecimal bigDecimal(Object object) {
    BigDecimal result;
    if (object == null) {
      throw new NullPointerException();
    }
    if (object instanceof BigDecimal) {
      return (BigDecimal)object;
    }
    
    try {
      result = new BigDecimal(object.toString().replaceAll(",", ""));
    } catch (NumberFormatException e) {
      throw new NumberFormatException("Please give me a numeral.Not " + object);
    } 
    return result;
  }







  
  public static final BigDecimal add(Number num1, Number num2) {
    BigDecimal result = bigDecimal(num1).add(bigDecimal(num2));
    return result.setScale(DEF_SCALE, 4);
  }







  
  public static final BigDecimal subtract(Number num1, Number num2) {
    BigDecimal result = bigDecimal(num1).subtract(bigDecimal(num2));
    return result.setScale(DEF_SCALE, 4);
  }







  
  public static final BigDecimal multiply(Number num1, Number num2) {
    BigDecimal result = bigDecimal(num1).multiply(bigDecimal(num2));
    return result.setScale(DEF_SCALE, 4);
  }








  
  public static final BigDecimal divide(Number num1, Number num2) { return divide(num1, num2, Integer.valueOf(DEF_SCALE)); }









  
  public static final BigDecimal divide(Number num1, Number num2, Integer scale) {
    if (scale == null) {
      scale = Integer.valueOf(DEF_SCALE);
    }
    num2 = (num2 == null || Math.abs((new Double(num2.toString())).doubleValue()) == 0.0D) ? Integer.valueOf(1) : num2;
    if (scale.intValue() < 0) {
      throw new IllegalArgumentException("The scale must be a positive integer or zero");
    }
    return bigDecimal(num1).divide(bigDecimal(num2), scale.intValue(), 4);
  }








  
  public static final BigDecimal round(Number num, int scale) {
    if (scale < 0) {
      throw new IllegalArgumentException("The scale must be a positive integer or zero");
    }
    return bigDecimal(num).divide(bigDecimal("1"), scale, 4);
  }









  
  public static final BigDecimal getRandom(double start, double end) { return new BigDecimal(start + Math.random() * (end - start)); }


  
  public static final void main(String[] args) { System.out.println(add(Double.valueOf(1.000001D), Double.valueOf(2.1D))); }
}
