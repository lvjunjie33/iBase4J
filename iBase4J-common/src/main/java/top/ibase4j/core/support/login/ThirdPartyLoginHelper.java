package top.ibase4j.core.support.login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.ibase4j.core.support.context.Resources;
import top.ibase4j.core.util.HttpUtil;









public final class ThirdPartyLoginHelper
{
  private static final Logger logger = LogManager.getLogger();






  
  public static final ThirdPartyUser getQQUserinfo(String token, String openid) throws Exception {
    ThirdPartyUser user = new ThirdPartyUser();
    String url = Resources.THIRDPARTY.getString("getUserInfoURL_qq");
    
    url = url + "?format=json&access_token=" + token + "&oauth_consumer_key=" + Resources.THIRDPARTY.getString("app_id_qq") + "&openid=" + openid;
    String res = HttpUtil.get(url);
    JSONObject json = JSON.parseObject(res);
    if (json.getIntValue("ret") == 0) {
      user.setUserName(json.getString("nickname"));
      String img = json.getString("figureurl_qq_2");
      if (img == null || "".equals(img)) {
        img = json.getString("figureurl_qq_1");
      }
      user.setAvatarUrl(img);
      String sex = json.getString("gender");
      if ("女".equals(sex)) {
        user.setGender("0");
      } else {
        user.setGender("1");
      } 
    } else {
      throw new IllegalArgumentException(json.getString("msg"));
    } 
    return user;
  }

  
  public static final ThirdPartyUser getWxUserinfo(String token, String openid) throws Exception {
    ThirdPartyUser user = new ThirdPartyUser();
    String url = Resources.THIRDPARTY.getString("getUserInfoURL_wx");
    url = url + "?access_token=" + token + "&openid=" + openid;
    String res = HttpUtil.get(url);
    JSONObject json = JSON.parseObject(res);
    if (json.getString("errcode") == null) {
      user.setUserName(json.getString("nickname"));
      String img = json.getString("headimgurl");
      if (img != null && !"".equals(img)) {
        user.setAvatarUrl(img);
      }
      String sex = json.getString("sex");
      if ("0".equals(sex)) {
        user.setGender("0");
      } else {
        user.setGender("1");
      } 
    } else {
      throw new IllegalArgumentException(json.getString("errmsg"));
    } 
    return user;
  }

  
  public static final String getWxAppOpenid(String code) throws Exception {
    String url = Resources.THIRDPARTY.getString("jscode2session_wx");
    NameValuePair appid = new NameValuePair("appid", Resources.THIRDPARTY.getString("app_id_wx"));
    NameValuePair secret = new NameValuePair("secret", Resources.THIRDPARTY.getString("app_secret_wx"));
    NameValuePair jsCode = new NameValuePair("js_code", code);
    NameValuePair grantType = new NameValuePair("grant_type", "authorization_code");
    String res = HttpUtil.post(url, new NameValuePair[] { appid, secret, jsCode, grantType });
    JSONObject json = JSON.parseObject(res);
    if (json.getString("errcode") == null || "0".equals(json.getString("errcode"))) {
      return json.getString("openid");
    }
    throw new IllegalArgumentException(json.getString("errmsg"));
  }








  
  public static final ThirdPartyUser getSinaUserinfo(String token, String uid) throws Exception {
    ThirdPartyUser user = new ThirdPartyUser();
    String url = Resources.THIRDPARTY.getString("getUserInfoURL_sina");
    url = url + "?access_token=" + token + "&uid=" + uid;
    String res = HttpUtil.get(url);
    JSONObject json = JSON.parseObject(res);
    String name = json.getString("name");
    String nickName = StringUtils.isBlank(json.getString("screen_name")) ? name : json.getString("screen_name");
    user.setAvatarUrl(json.getString("avatar_large"));
    user.setUserName(nickName);
    if ("f".equals(json.getString("gender"))) {
      user.setGender("0");
    } else {
      user.setGender("1");
    } 
    user.setToken(token);
    user.setOpenid(uid);
    user.setProvider("sina");
    return user;
  }







  
  public static final Map<String, String> getQQTokenAndOpenid(String code, String host) throws Exception {
    Map<String, String> map = new HashMap<String, String>();
    
    String tokenUrl = Resources.THIRDPARTY.getString("accessTokenURL_qq");

    
    tokenUrl = tokenUrl + "?grant_type=authorization_code&client_id=" + Resources.THIRDPARTY.getString("app_id_qq") + "&client_secret=" + Resources.THIRDPARTY.getString("app_key_qq") + "&code=" + code + "&redirect_uri=http://" + host + Resources.THIRDPARTY.getString("redirect_url_qq");
    String tokenRes = HttpUtil.get(tokenUrl);
    if (tokenRes != null && tokenRes.indexOf("access_token") > -1) {
      Map<String, String> tokenMap = toMap(tokenRes);
      map.put("access_token", tokenMap.get("access_token"));
      
      String openIdUrl = Resources.THIRDPARTY.getString("getOpenIDURL_qq");
      openIdUrl = openIdUrl + "?access_token=" + (String)tokenMap.get("access_token");
      String openIdRes = HttpUtil.get(openIdUrl);
      int i = openIdRes.indexOf("(");
      int j = openIdRes.indexOf(")");
      openIdRes = openIdRes.substring(i + 1, j);
      JSONObject openidObj = JSON.parseObject(openIdRes);
      map.put("openId", openidObj.getString("openid"));
    } else {
      throw new IllegalArgumentException(Resources.getMessage("THIRDPARTY.LOGIN.NOTOKEN", new Object[] { "QQ" }));
    } 
    return map;
  }







  
  public static final Map<String, String> getWxTokenAndOpenid(String code, String host) throws Exception {
    Map<String, String> map = new HashMap<String, String>();
    
    String tokenUrl = Resources.THIRDPARTY.getString("accessTokenURL_wx");
    
    tokenUrl = tokenUrl + "?appid=" + Resources.THIRDPARTY.getString("app_id_wx") + "&secret=" + Resources.THIRDPARTY.getString("app_key_wx") + "&code=" + code + "&grant_type=authorization_code";
    String tokenRes = HttpUtil.get(tokenUrl);
    if (tokenRes != null && tokenRes.indexOf("access_token") > -1) {
      Map<String, String> tokenMap = toMap(tokenRes);
      map.put("access_token", tokenMap.get("access_token"));
      
      map.put("openId", tokenMap.get("openid"));
    } else {
      throw new IllegalArgumentException(Resources.getMessage("THIRDPARTY.LOGIN.NOTOKEN", new Object[] { "weixin" }));
    } 
    return map;
  }







  
  public static final JSONObject getSinaTokenAndUid(String code, String host) {
    JSONObject json = null;
    
    try {
      String tokenUrl = Resources.THIRDPARTY.getString("accessTokenURL_sina");
      NameValuePair params1 = new NameValuePair();
      params1.setName("client_id");
      params1.setValue(Resources.THIRDPARTY.getString("app_id_sina"));
      NameValuePair params2 = new NameValuePair();
      params2.setName("client_secret");
      params2.setValue(Resources.THIRDPARTY.getString("app_key_sina"));
      NameValuePair params3 = new NameValuePair();
      params3.setName("grant_type");
      params3.setValue("authorization_code");
      NameValuePair params4 = new NameValuePair();
      params4.setName("redirect_uri");
      params4.setValue("http://" + host + Resources.THIRDPARTY.getString("redirect_url_sina"));
      NameValuePair params5 = new NameValuePair();
      params5.setName("code");
      params5.setValue(code);
      String tokenRes = HttpUtil.post(tokenUrl, new NameValuePair[] { params1, params2, params3, params4, params5 });

      
      if (tokenRes != null && tokenRes.indexOf("access_token") > -1) {
        json = JSON.parseObject(tokenRes);
      } else {
        throw new IllegalArgumentException(Resources.getMessage("THIRDPARTY.LOGIN.NOTOKEN", new Object[] { "sina" }));
      } 
    } catch (Exception e) {
      logger.error("", e);
    } 
    return json;
  }
  
  private static final Map<String, String> toMap(String str) {
	return null; // Byte code:
    //   0: new java/util/HashMap
    //   3: dup
    //   4: invokespecial <init> : ()V
    //   7: astore_1
    //   8: aload_0
    //   9: ldc '&'
    //   11: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   14: astore_2
    //   15: aload_2
    //   16: astore_3
    //   17: aload_3
    //   18: arraylength
    //   19: istore #4
    //   21: iconst_0
    //   22: istore #5
    //   24: iload #5
    //   26: iload #4
    //   28: if_icmpge -> 67
    //   31: aload_3
    //   32: iload #5
    //   34: aaload
    //   35: astore #6
    //   37: aload #6
    //   39: ldc '='
    //   41: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   44: astore #7
    //   46: aload_1
    //   47: aload #7
    //   49: iconst_0
    //   50: aaload
    //   51: aload #7
    //   53: iconst_1
    //   54: aaload
    //   55: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   60: pop
    //   61: iinc #5, 1
    //   64: goto -> 24
    //   67: aload_1
    //   68: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #233	-> 0
    //   #234	-> 8
    //   #235	-> 15
    //   #236	-> 37
    //   #237	-> 46
    //   #235	-> 61
    //   #239	-> 67
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   46	15	7	ss	[Ljava/lang/String;
    //   37	24	6	str2	Ljava/lang/String;
    //   0	69	0	str	Ljava/lang/String;
    //   8	61	1	map	Ljava/util/Map;
    //   15	54	2	strs	[Ljava/lang/String;
    // Local variable type table:
    //   start	length	slot	name	signature
    //   8	61	1	map	Ljava/util/Map<Ljava/lang/String;Ljava/lang/String;>; 
	  }
}
