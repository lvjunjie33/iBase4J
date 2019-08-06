package top.ibase4j.core.support.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface Validate {
  boolean nullable() default true;
  
  double max() default 0.0D;
  
  double min() default 0.0D;
  
  int maxLength() default 0;
  
  int minLength() default 0;
  
  RegexType type() default RegexType.NONE;
  
  String regex() default "";
  
  String desc() default "";
}
