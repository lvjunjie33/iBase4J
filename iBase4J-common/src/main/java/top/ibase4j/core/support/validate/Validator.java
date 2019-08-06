package top.ibase4j.core.support.validate;

import top.ibase4j.core.exception.ValidateException;
import top.ibase4j.core.util.DataUtil;
import top.ibase4j.core.util.IDCardUtil;




























public class Validator
{
  public static void valid(Object object) throws Exception { }// Byte code:
   
  
  public static void validate(Validate validate, Object value, String filedName) throws ValidateException {
    String description = DataUtil.isEmpty(validate.desc()) ? filedName : validate.desc();
    
    if (validate.nullable() && DataUtil.isEmpty(value)) {
      return;
    }
    
    if (!validate.nullable() && DataUtil.isEmpty(value)) {
      throw new ValidateException(description + "不能为空");
    }
    
    if (validate.min() != 0.0D && (new Double(value.toString())).doubleValue() < validate.min()) {
      throw new ValidateException(description + "不能小于" + validate.min());
    }
    
    if (validate.max() != 0.0D && (new Double(value.toString())).doubleValue() > validate.max()) {
      throw new ValidateException(description + "不能大于" + validate.max());
    }
    
    if (validate.minLength() != 0 && value.toString().length() < validate.minLength()) {
      throw new ValidateException(description + "长度不能小于" + validate.minLength());
    }
    
    if (validate.maxLength() != 0 && value.toString().length() > validate.maxLength()) {
      throw new ValidateException(description + "长度不能超过" + validate.maxLength());
    }
    
    validate(validate.type(), value, description);
    
    if (DataUtil.isNotEmpty(validate.regex()) && 
      !value.toString().matches(validate.regex())) {
      throw new ValidateException(description + "格式不正确");
    }
  }

  
  public static void validate(RegexType type, Object value, String description) throws ValidateException {
    switch (type) {
    
    }

      
//      case null:
//        if (!IDCardUtil.isIdentity(value.toString())) {
//          throw new ValidateException(description + "格式不正确");
//        }
//        break;
//      case null:
//        if (!value.toString().matches(RegexType.DATE.value())) {
//          throw new ValidateException(description + "格式不正确");
//        }
//        break;
//      case null:
//        if (DataUtil.hasSpecialChar(value.toString())) {
//          throw new ValidateException(description + "不能含有特殊字符");
//        }
//        break;
//      case null:
//        if (!DataUtil.isPassword(value.toString())) {
//          throw new ValidateException(description + "必须是大小写字母和数字的组合，长度在8-16之间");
//        }
//        break;
//      case null:
//        if (!DataUtil.isChinese(value.toString())) {
//          throw new ValidateException(description + "只能输入中文字符");
//        }
//        break;
//      case null:
//        if (DataUtil.isChinese2(value.toString())) {
//          throw new ValidateException(description + "不能含有中文字符");
//        }
//        break;
//      case null:
//        if (!DataUtil.isEmail(value.toString())) {
//          throw new ValidateException(description + "格式不正确");
//        }
//        break;
//      case null:
//        if (!DataUtil.isIp(value.toString())) {
//          throw new ValidateException(description + "格式不正确");
//        }
//        break;
//      case null:
//        if (!DataUtil.isNumber(value.toString())) {
//          throw new ValidateException(description + "不是数字");
//        }
//        break;
//      case null:
//        if (!DataUtil.isPhone(value.toString())) {
//          throw new ValidateException(description + "格式不正确");
//        }
//        break;
//      case null:
//        if (!DataUtil.isTelephone(value.toString()))
//          throw new ValidateException(description + "格式不正确"); 
//        break;
//    } 
//  }
//}
}
}