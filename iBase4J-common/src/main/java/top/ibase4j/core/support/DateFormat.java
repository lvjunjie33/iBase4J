package top.ibase4j.core.support;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import top.ibase4j.core.util.DateUtil;









public class DateFormat
  extends SimpleDateFormat
{
  public DateFormat(String pattern) { super(pattern); }



  
  public Date parse(String source) throws ParseException { return DateUtil.stringToDate(source); }
}
