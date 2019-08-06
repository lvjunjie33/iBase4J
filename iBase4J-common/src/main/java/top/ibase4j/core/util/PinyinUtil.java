package top.ibase4j.core.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;









public final class PinyinUtil
{
  static Logger logger = LogManager.getLogger();






  
  public static final String getPinYin(String src) {
    if (src == null) {
      return "";
    }
    char[] t1 = null;
    t1 = src.toCharArray();
    String[] t2 = new String[t1.length];
    
    HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
    t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
    t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    t3.setVCharType(HanyuPinyinVCharType.WITH_V);
    String t4 = "";
    int t0 = t1.length;
    try {
      for (int i = 0; i < t0; i++) {
        
        if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
          t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
          t4 = t4 + t2[0];
        } else {
          
          t4 = t4 + Character.toString(t1[i]);
        } 
      } 
    } catch (BadHanyuPinyinOutputFormatCombination e) {
      logger.error("", e);
    } 
    return t4;
  }






  
  public static final String getCamelPinYin(String src) {
    char[] t1 = null;
    t1 = src.toCharArray();
    String[] t2 = new String[t1.length];
    
    HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
    t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
    t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    t3.setVCharType(HanyuPinyinVCharType.WITH_V);
    String t4 = "", t = "";
    int t0 = t1.length;
    try {
      for (int i = 0; i < t0; i++) {
        
        if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
          t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
          t = t2[0];
        } else {
          
          t = Character.toString(t1[i]);
        } 
        t = t.substring(0, 1).toUpperCase() + t.substring(1);
        t4 = t4 + t;
      } 
    } catch (BadHanyuPinyinOutputFormatCombination e) {
      logger.error("", e);
    } 
    return t4;
  }






  
  public static final String getPinYinHeadChar(String str) {
    String convert = "";
    for (int j = 0; j < str.length(); j++) {
      char word = str.charAt(j);
      
      String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
      if (pinyinArray != null) {
        convert = convert + pinyinArray[0].charAt(0);
      } else {
        convert = convert + word;
      } 
    } 
    return convert;
  }







  
  public static final String getPinYinHeadUperChar(String str) { return getPinYinHeadChar(str).toUpperCase(); }

  
  public static void main(String[] args) {
    String cnStr = "中华人民共和国";
    System.out.println(getPinYin(cnStr));
    System.out.println(getCamelPinYin(cnStr));
    System.out.println(getPinYinHeadChar(cnStr));
    System.out.println(getPinYinHeadUperChar(cnStr));
  }
}
