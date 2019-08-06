package top.ibase4j.core.util;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;

import com.alibaba.fastjson.JSON;

import top.ibase4j.core.exception.DataParseException;


















public final class TypeParseUtil
{
  static final String message = "Could not convert %1$s to %2$s";
  static final String support = "Convert from %1$s to %2$s not currently supported";
  
  public static final Object convert(Object value, Class<?> type, String format) { return convert(value, type.getName(), format); }













  
  public static final Object convert(Object value, String type, String format) {
    Locale locale = Locale.getDefault();
    if (value == null)
      return null; 
    if (value.getClass().getName().equalsIgnoreCase(type) || value
      .getClass().getSimpleName().equalsIgnoreCase(type))
      return value; 
    if ("Object".equalsIgnoreCase(type) || "java.lang.Object".equals(type))
      return value; 
    if (value instanceof String)
      return string2Object(value, type, format, locale); 
    if (value instanceof BigDecimal)
      return decimal2Obj(value, type, locale); 
    if (value instanceof Double)
      return double2Obj(value, type, locale); 
    if (value instanceof Float)
      return float2Obj(value, type, locale); 
    if (value instanceof Long)
      return long2Obj(value, type, locale); 
    if (value instanceof Integer)
      return intr2Obj(value, type, locale); 
    if (value instanceof Date)
      return date2Obj(value, type, format); 
    if (value instanceof Date)
      return sqlDate2Obj(value, type, format); 
    if (value instanceof Timestamp)
      return time2Obj(value, type, format); 
    if (value instanceof Boolean)
      return bool2Obj(value, type); 
    if ("String".equalsIgnoreCase(type) || "java.lang.String".equalsIgnoreCase(type)) {
      return value.toString();
    }
    throw new DataParseException(String.format("Convert from %1$s to %2$s not currently supported", new Object[] { value.getClass().getName(), type }));
  }


  
  private static Object bool2Obj(Object value, String type) {
    String fromType = "Boolean";
    Boolean bol = (Boolean)value;
    if ("Boolean".equalsIgnoreCase(type) || "java.lang.Boolean".equalsIgnoreCase(type))
      return bol; 
    if ("String".equalsIgnoreCase(type) || "java.lang.String".equalsIgnoreCase(type))
      return bol.toString(); 
    if ("Integer".equalsIgnoreCase(type) || "java.lang.Integer".equalsIgnoreCase(type)) {
      if (bol.booleanValue()) {
        return new Integer(0);
      }
      return new Integer(0);
    } 
    
    throw new DataParseException(String.format("Convert from %1$s to %2$s not currently supported", new Object[] { fromType, type }));
  }


  
  private static Object time2Obj(Object value, String type, String format) {
    String fromType = "Timestamp";
    Timestamp tme = (Timestamp)value;
    if ("String".equalsIgnoreCase(type) || "java.lang.String".equalsIgnoreCase(type)) {
      if (format == null || format.length() == 0) {
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(tme).toString();
      }
      SimpleDateFormat sdf = new SimpleDateFormat(format);
      return sdf.format(new Date(tme.getTime()));
    } 
    if ("Date".equalsIgnoreCase(type) || "java.util.Date".equalsIgnoreCase(type))
      return new Date(tme.getTime()); 
    if ("java.sql.Date".equalsIgnoreCase(type))
      return new Date(tme.getTime()); 
    if ("Time".equalsIgnoreCase(type) || "java.sql.Time".equalsIgnoreCase(type))
      return new Time(tme.getTime()); 
    if ("Timestamp".equalsIgnoreCase(type) || "java.sql.Timestamp".equalsIgnoreCase(type)) {
      return value;
    }
    throw new DataParseException(String.format("Convert from %1$s to %2$s not currently supported", new Object[] { fromType, type }));
  }


  
  private static Object sqlDate2Obj(Object value, String type, String format) {
    String fromType = "Date";
    Date dte = (Date)value;
    if ("String".equalsIgnoreCase(type) || "java.lang.String".equalsIgnoreCase(type)) {
      if (format == null || format.length() == 0) {
        return dte.toString();
      }
      SimpleDateFormat sdf = new SimpleDateFormat(format);
      return sdf.format(new Date(dte.getTime()));
    } 
    if ("Date".equalsIgnoreCase(type) || "java.util.Date".equalsIgnoreCase(type))
      return new Date(dte.getTime()); 
    if ("java.sql.Date".equalsIgnoreCase(type))
      return value; 
    if ("Time".equalsIgnoreCase(type) || "java.sql.Time".equalsIgnoreCase(type))
      throw new DataParseException("Conversion from " + fromType + " to " + type + " not currently supported"); 
    if ("Timestamp".equalsIgnoreCase(type) || "java.sql.Timestamp".equalsIgnoreCase(type)) {
      return new Timestamp(dte.getTime());
    }
    throw new DataParseException(String.format("Convert from %1$s to %2$s not currently supported", new Object[] { fromType, type }));
  }


  
  private static Object date2Obj(Object value, String type, String format) {
    String fromType = "Date";
    Date dte = (Date)value;
    if ("String".equalsIgnoreCase(type) || "java.lang.String".equalsIgnoreCase(type)) {
      if (format == null || format.length() == 0) {
        return dte.toString();
      }
      SimpleDateFormat sdf = new SimpleDateFormat(format);
      return sdf.format(dte);
    } 
    if ("Date".equalsIgnoreCase(type) || "java.util.Date".equalsIgnoreCase(type))
      return value; 
    if ("java.sql.Date".equalsIgnoreCase(type))
      return new Date(dte.getTime()); 
    if ("Time".equalsIgnoreCase(type) || "java.sql.Time".equalsIgnoreCase(type))
      return new Time(dte.getTime()); 
    if ("Timestamp".equalsIgnoreCase(type) || "java.sql.Timestamp".equalsIgnoreCase(type)) {
      return new Timestamp(dte.getTime());
    }
    throw new DataParseException(String.format("Convert from %1$s to %2$s not currently supported", new Object[] { fromType, type }));
  }


  
  private static Object intr2Obj(Object value, String type, Locale locale) {
    String fromType = "Integer";
    Integer intgr = (Integer)value;
    if ("String".equalsIgnoreCase(type) || "java.lang.String".equalsIgnoreCase(type))
      return getNf(locale).format(intgr.toString()); 
    if ("Double".equalsIgnoreCase(type) || "java.lang.Double".equalsIgnoreCase(type))
      return new Double(intgr.toString()); 
    if ("Float".equalsIgnoreCase(type) || "java.lang.Float".equalsIgnoreCase(type))
      return new Float(intgr.toString()); 
    if ("BigDecimal".equalsIgnoreCase(type) || "java.math.BigDecimal".equalsIgnoreCase(type)) {
      String str = intgr.toString();
      BigDecimal retBig = new BigDecimal(intgr.toString());
      int iscale = str.indexOf(".");
      int keylen = str.length();
      if (iscale > -1) {
        iscale = keylen - iscale + 1;
        return retBig.setScale(iscale, 5);
      } 
      return retBig.setScale(0, 5);
    } 
    if ("Long".equalsIgnoreCase(type) || "java.lang.Long".equalsIgnoreCase(type))
      return new Long(intgr.toString()); 
    if ("Integer".equalsIgnoreCase(type) || "java.lang.Integer".equalsIgnoreCase(type)) {
      return value;
    }
    throw new DataParseException(String.format("Convert from %1$s to %2$s not currently supported", new Object[] { fromType, type }));
  }


  
  private static Object long2Obj(Object value, String type, Locale locale) {
    String fromType = "Long";
    Long lng = (Long)value;
    if ("String".equalsIgnoreCase(type) || "java.lang.String".equalsIgnoreCase(type))
      return getNf(locale).format(lng.toString()); 
    if ("Double".equalsIgnoreCase(type) || "java.lang.Double".equalsIgnoreCase(type))
      return new Double(lng.toString()); 
    if ("Float".equalsIgnoreCase(type) || "java.lang.Float".equalsIgnoreCase(type))
      return new Float(lng.toString()); 
    if ("BigDecimal".equalsIgnoreCase(type) || "java.math.BigDecimal".equalsIgnoreCase(type))
      return new BigDecimal(lng.toString()); 
    if ("Long".equalsIgnoreCase(type) || "java.lang.Long".equalsIgnoreCase(type))
      return value; 
    if ("Integer".equalsIgnoreCase(type) || "java.lang.Integer".equalsIgnoreCase(type))
      return new Integer(lng.toString()); 
    if ("Date".equalsIgnoreCase(type) || "java.util.Date".equalsIgnoreCase(type))
      return new Date(lng.longValue()); 
    if ("java.sql.Date".equalsIgnoreCase(type))
      return new Date(lng.longValue()); 
    if ("Time".equalsIgnoreCase(type) || "java.sql.Time".equalsIgnoreCase(type))
      return new Time(lng.longValue()); 
    if ("Timestamp".equalsIgnoreCase(type) || "java.sql.Timestamp".equalsIgnoreCase(type)) {
      return new Timestamp(lng.longValue());
    }
    throw new DataParseException(String.format("Convert from %1$s to %2$s not currently supported", new Object[] { fromType, type }));
  }

  
  private static Object float2Obj(Object value, String type, Locale locale) {
    String fromType = "Float";
    Float flt = (Float)value;
    if ("String".equalsIgnoreCase(type) || "java.lang.String".equalsIgnoreCase(type))
      return getNf(locale).format(flt.toString()); 
    if ("BigDecimal".equalsIgnoreCase(type) || "java.math.BigDecimal".equalsIgnoreCase(type))
      return new BigDecimal(flt.toString()); 
    if ("Double".equalsIgnoreCase(type) || "java.lang.Double".equalsIgnoreCase(type))
      return new Double(flt.toString()); 
    if ("Float".equalsIgnoreCase(type) || "java.lang.Float".equalsIgnoreCase(type))
      return value; 
    if ("Long".equalsIgnoreCase(type) || "java.lang.Long".equalsIgnoreCase(type))
      return new Long(flt.toString()); 
    if ("Integer".equalsIgnoreCase(type) || "java.lang.Integer".equalsIgnoreCase(type)) {
      return new Integer(flt.toString());
    }
    throw new DataParseException(String.format("Convert from %1$s to %2$s not currently supported", new Object[] { fromType, type }));
  }


  
  private static Object double2Obj(Object value, String type, Locale locale) {
    String fromType = "Double";
    Double dbl = (Double)value;
    if ("String".equalsIgnoreCase(type) || "java.lang.String".equalsIgnoreCase(type))
      return getNf(locale).format(dbl.toString()); 
    if ("Double".equalsIgnoreCase(type) || "java.lang.Double".equalsIgnoreCase(type))
      return value; 
    if ("Float".equalsIgnoreCase(type) || "java.lang.Float".equalsIgnoreCase(type))
      return new Float(dbl.toString()); 
    if ("Long".equalsIgnoreCase(type) || "java.lang.Long".equalsIgnoreCase(type))
      return new Long(dbl.toString()); 
    if ("Integer".equalsIgnoreCase(type) || "java.lang.Integer".equalsIgnoreCase(type))
      return new Integer(dbl.toString()); 
    if ("BigDecimal".equalsIgnoreCase(type) || "java.math.BigDecimal".equalsIgnoreCase(type)) {
      return new BigDecimal(dbl.toString());
    }
    throw new DataParseException(String.format("Convert from %1$s to %2$s not currently supported", new Object[] { fromType, type }));
  }


  
  private static Object decimal2Obj(Object value, String type, Locale locale) {
    String fromType = "BigDecimal";
    BigDecimal bigD = (BigDecimal)value;
    if ("String".equalsIgnoreCase(type) || "java.lang.String".equalsIgnoreCase(type))
      return getNf(locale).format(bigD.toString()); 
    if ("BigDecimal".equalsIgnoreCase(type) || "java.math.BigDecimal".equalsIgnoreCase(type))
      return value; 
    if ("Double".equalsIgnoreCase(type) || "java.lang.Double".equalsIgnoreCase(type))
      return new Double(bigD.toString()); 
    if ("Float".equalsIgnoreCase(type) || "java.lang.Float".equalsIgnoreCase(type))
      return new Float(bigD.toString()); 
    if ("Long".equalsIgnoreCase(type) || "java.lang.Long".equalsIgnoreCase(type))
      return new Long(bigD.toString()); 
    if ("Integer".equals(type) || "java.lang.Integer".equalsIgnoreCase(type)) {
      return new Integer(bigD.toString());
    }
    throw new DataParseException(String.format("Convert from %1$s to %2$s not currently supported", new Object[] { fromType, type }));
  }


  
  private static Object string2Object(Object value, String type, String format, Locale locale) {
    String fromType = "String";
    String str = (String)value;
    try {
      if ("String".equalsIgnoreCase(type) || "java.lang.String".equalsIgnoreCase(type))
        return value; 
      if (str.length() == 0)
        return null; 
      if ("Boolean".equalsIgnoreCase(type) || "java.lang.Boolean".equals(type)) {
        if (str.equalsIgnoreCase("TRUE")) {
          return new Boolean(true);
        }
        return new Boolean(false);
      } 
      if ("Double".equalsIgnoreCase(type) || "java.lang.Double".equalsIgnoreCase(type)) {
        Number tempNum = getNf(locale).parse(str.replaceAll(",", ""));
        return new Double(tempNum.toString());
      }  if ("BigDecimal".equalsIgnoreCase(type) || "java.math.BigDecimal".equalsIgnoreCase(type)) {
        BigDecimal retBig = new BigDecimal(str.replaceAll(",", ""));
        int iscale = str.indexOf(".");
        int keylen = str.length();
        if (iscale > -1) {
          iscale = keylen - iscale + 1;
          return retBig.setScale(iscale, 5);
        } 
        return retBig.setScale(0, 5);
      } 
      if ("Float".equalsIgnoreCase(type) || "java.lang.Float".equalsIgnoreCase(type)) {
        Number tempNum = getNf(locale).parse(str.replaceAll(",", ""));
        return new Float(tempNum.toString());
      } 
      if ("Long".equalsIgnoreCase(type) || "java.lang.Long".equalsIgnoreCase(type)) {
        NumberFormat nf = getNf(locale);
        nf.setMaximumFractionDigits(0);
        Number tempNum = nf.parse(str.replaceAll(",", ""));
        return new Long(tempNum.toString());
      }  if ("Integer".equalsIgnoreCase(type) || "java.lang.Integer".equalsIgnoreCase(type)) {
        NumberFormat nf = getNf(locale);
        nf.setMaximumFractionDigits(0);
        Number tempNum = nf.parse(str.replaceAll(",", ""));
        return new Integer(tempNum.toString());
      }  if ("Date".equalsIgnoreCase(type) || "java.util.Date".equalsIgnoreCase(type)) {
        if (format == null || format.length() == 0) {
          String separator = String.valueOf(str.charAt(4));
          
          if (separator.matches("\\d*")) {
            StringBuilder pattern = new StringBuilder("yyyyMMdd HH:mm:ss");
            format = pattern.substring(0, str.length());
          } else {
            
            StringBuilder pattern = (new StringBuilder("yyyy")).append(separator).append("MM").append(separator).append("dd HH:mm:ss");
            format = pattern.substring(0, str.length());
          } 
        } 
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(str);
      }  if ("java.sql.Date".equalsIgnoreCase(type)) {
        if (format == null || format.length() == 0) {
          try {
            return Date.valueOf(str);
          } catch (Exception e) {
            DateFormat df = null;
              if (locale != null) {
                df = DateFormat.getDateInstance(3, locale);
              } else {
                df = DateFormat.getDateInstance(3);
              } 
//              Date fieldDate = df.parse(str);
//              return new Date(fieldDate.getTime()); 
          } 
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
//        Date fieldDate = sdf.parse(str);
//        return new Date(fieldDate.getTime());
      }  if ("Timestamp".equalsIgnoreCase(type) || "java.sql.Timestamp".equalsIgnoreCase(type)) {
        if (str.length() == 10) {
          str = str + " 00:00:00";
        }
        if (format == null || format.length() == 0) {
          try {
            return Timestamp.valueOf(str);
          } catch (Exception e) {
            DateFormat df = null;
              if (locale != null) {
                df = DateFormat.getDateTimeInstance(3, 3, locale);
              } else {
                df = DateFormat.getDateTimeInstance(3, 3);
              } 
//              Date fieldDate = df.parse(str);
//              return new Timestamp(fieldDate.getTime()); 
          } 
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
//          Date fieldDate = sdf.parse(str);
//          return new Timestamp(fieldDate.getTime()); 
      } 
      throw new DataParseException(String.format("Convert from %1$s to %2$s not currently supported", new Object[] { fromType, type }));
    }
    catch (Exception e) {
      throw new DataParseException(String.format("Could not convert %1$s to %2$s", new Object[] { str, type }), e);
    } 
  }
  
  private static NumberFormat getNf(Locale locale) {
    NumberFormat nf = null;
    if (locale == null) {
      nf = NumberFormat.getNumberInstance();
    } else {
      nf = NumberFormat.getNumberInstance(locale);
    } 
    nf.setGroupingUsed(false);
    return nf;
  }


  
  public static final Boolean convertToBoolean(Object obj) { return (Boolean)convert(obj, "Boolean", null); }



  
  public static final Integer convertToInteger(Object obj) { return (Integer)convert(obj, "Integer", null); }



  
  public static final String convertToString(Object obj) { return (String)convert(obj, "String", null); }


  
  public static final String convertToString(Object obj, String defaultValue) {
    Object s = convert(obj, "String", null);
    if (s != null) {
      return (String)s;
    }
    return "";
  }



  
  public static final Long convertToLong(Object obj) { return (Long)convert(obj, "Long", null); }



  
  public static final Double convertToDouble(Object obj) { return (Double)convert(obj, "Double", null); }



  
  public static final Double convertToFloat(Object obj) { return (Double)convert(obj, "Float", null); }



  
  public static final BigDecimal convertToBigDecimal(Object obj, int scale) { return ((BigDecimal)convert(obj, "BigDecimal", null)).setScale(scale, 5); }



  
  public static final Date convertToDate(Object obj, String format) { return (Date)convert(obj, "Date", format); }



  
  public static final Date convertToSqlDate(Object obj, String format) { return (Date)convert(obj, "java.sql.Date", format); }



  
  public static final Timestamp convertToTimestamp(Object obj, String format) { return (Timestamp)convert(obj, "Timestamp", format); }

  
  public static String[] convertToStringArray(Object object) {
    if (object instanceof List) {
      
      List list = (List)object;
      String[] ss = new String[list.size()];
      IntStream.range(0, list.size()).forEach(i -> 
          ss[i] = JSON.toJSONString(list.get(i)));
      
      return ss;
    } 
    return new String[] { JSON.toJSONString(object) };
  }
}
