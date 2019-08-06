package top.ibase4j.core.support.rpc;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import top.ibase4j.core.util.PropertiesUtil;






public class EnableMotan
  implements Condition
{
  public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) { return "motan".equals(PropertiesUtil.getString("rpc.type")); }
}
