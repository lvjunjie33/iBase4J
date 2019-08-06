package top.ibase4j.core;

import java.util.Map;
import top.ibase4j.core.support.cache.CacheKey;
import top.ibase4j.core.util.InstanceUtil;












public interface Constants
{
  public static final String EXCEPTION_HEAD = "OH,MY GOD! SOME ERRORS OCCURED! AS FOLLOWS :";
  public static final Map<Class<?>, CacheKey> CACHEKEYMAP = InstanceUtil.newHashMap();
  public static final String OPERATION_NAME = "OPERATION_NAME";
  public static final String USERLANGUAGE = "userLanguage";
  public static final String WEBTHEME = "webTheme";
  public static final String CURRENT_USER = "CURRENT_USER";
  public static final String USER_AGENT = "USER-AGENT";
  public static final String USER_IP = "USER_IP";
  public static final String LOGIN_URL = "/login.html";
  public static final String CACHE_NAMESPACE = "iBase4J:";
  public static final String SYSTEM_CACHE_NAMESPACE = "S:iBase4J:";
  public static final String CACHE_NAMESPACE_LOCK = "L:iBase4J:";
  public static final String PREREQUEST = "iBase4J:PREREQUEST";
  public static final String PREREQUEST_TIME = "iBase4J:PREREQUEST_TIME";
  public static final String MALICIOUS_REQUEST_TIMES = "iBase4J:MALICIOUS_REQUEST_TIMES";
  public static final String ALLUSER_NUMBER = "S:iBase4J:ALLUSER_NUMBER";
  public static final String TOKEN_KEY = "S:iBase4J:TOKEN_KEY:";
  public static final String REDIS_SHIRO_CACHE = "S:iBase4J:SHIRO-CACHE:";
  public static final String REDIS_SHIRO_SESSION = "S:iBase4J:SHIRO-SESSION:";
  public static final String MYBATIS_CACHE = "D:iBase4J:MYBATIS:";
  public static final String DB_KEY = "90139119";
  public static final String TEMP_DIR = "/temp/";
  public static final String REQUEST_BODY = "iBase4J.requestBody";
  
  public static interface Times {
    public static final long SECOND = 1000L;
    public static final long MINUTE = 60000L;
    public static final long HOUR = 3600000L;
    public static final long DAY = 86400000L;
    public static final long WEEK = 604800000L;
    public static final long YEAR = 31536000000L;
  }
  
  public static interface MsgChkType {
    public static final String REGISTER = "iBase4J:REGISTER:";
    public static final String LOGIN = "iBase4J:LOGIN:";
    public static final String CHGPWD = "iBase4J:CHGPWD:";
    public static final String VLDID = "iBase4J:VLDID:";
    public static final String CHGINFO = "iBase4J:CHGINFO:";
    public static final String AVTCMF = "iBase4J:AVTCMF:";
  }
  
  public static interface JobState {
    public static final String INIT_STATS = "I";
    public static final String SUCCESS_STATS = "S";
    public static final String ERROR_STATS = "E";
    public static final String UN_STATS = "N";
  }
}
