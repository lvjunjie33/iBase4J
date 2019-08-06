package top.ibase4j.core.base.provider;

public interface BaseProvider {
  Parameter execute(Parameter paramParameter);
  
  Object execute(String paramString1, String paramString2, Object... paramVarArgs);
}
