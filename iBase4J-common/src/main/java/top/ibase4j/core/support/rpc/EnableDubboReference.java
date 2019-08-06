package top.ibase4j.core.support.rpc;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import top.ibase4j.core.util.PropertiesUtil;





public class EnableDubboReference
  implements Condition
{
  public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
    return ("dubbo".equals(PropertiesUtil.getString("rpc.type")) && "Y"
      .equals(PropertiesUtil.getString("rpc.dubbo.reference")));
  }
}
