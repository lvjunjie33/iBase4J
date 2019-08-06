package top.ibase4j.core.support.pay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.ibase4j.core.support.generator.Sequence;
import top.ibase4j.core.util.SecurityUtil;
import top.ibase4j.core.util.XmlUtil;



public class WxPayment
{
  private static final Logger logger = LogManager.getLogger();




















  
  public static Map<String, String> buildMicropayParas(String appid, String sub_appid, String mch_id, String sub_mch_id, String device_info, String body, String detail, String attach, String out_trade_no, String total_fee, String spbill_create_ip, String auth_code, String paternerKey) {
    Map<String, String> params = new HashMap<String, String>();
    params.put("appid", appid);
    params.put("sub_appid", sub_appid);
    params.put("mch_id", mch_id);
    params.put("sub_mch_id", sub_mch_id);
    params.put("device_info", device_info);
    params.put("body", body);
    params.put("detail", detail);
    params.put("attach", attach);
    params.put("out_trade_no", out_trade_no);
    params.put("total_fee", total_fee);
    params.put("spbill_create_ip", spbill_create_ip);
    params.put("auth_code", auth_code);
    return buildSignAfterParasMap(params, paternerKey);
  }




























  
  public static Map<String, String> buildUnifiedOrderParasMap(String appid, String sub_appid, String mch_id, String sub_mch_id, String device_info, String body, String detail, String attach, String out_trade_no, String total_fee, String spbill_create_ip, String notify_url, String trade_type, String paternerKey, String product_id, String scene_info, String openid) {
    Map<String, String> params = new HashMap<String, String>();
    params.put("appid", appid);
    params.put("sub_appid", sub_appid);
    params.put("mch_id", mch_id);
    params.put("sub_mch_id", sub_mch_id);
    params.put("device_info", device_info);
    params.put("body", body);
    params.put("detail", detail);
    params.put("attach", attach);
    
    params.put("total_fee", total_fee);
    params.put("out_trade_no", out_trade_no);
    params.put("spbill_create_ip", spbill_create_ip);
    params.put("notify_url", notify_url);
    params.put("trade_type", trade_type);
    params.put("product_id", product_id);
    params.put("scene_info", scene_info);
    params.put("openid", openid);
    
    return buildSignAfterParasMap(params, paternerKey);
  }













  
  public static Map<String, String> buildParasMap(String appid, String sub_appid, String mch_id, String sub_mch_id, String transaction_id, String out_trade_no, String paternerKey) {
    Map<String, String> params = new HashMap<String, String>();
    
    params.put("appid", appid);
    params.put("sub_appid", sub_appid);
    params.put("mch_id", mch_id);
    params.put("sub_mch_id", sub_mch_id);
    params.put("transaction_id", transaction_id);
    params.put("out_trade_no", out_trade_no);
    
    return buildSignAfterParasMap(params, paternerKey);
  }












  
  public static Map<String, String> buildShortUrlParasMap(String appid, String sub_appid, String mch_id, String sub_mch_id, String long_url, String paternerKey) {
    Map<String, String> params = new HashMap<String, String>();
    params.put("appid", appid);
    params.put("sub_appid", sub_appid);
    params.put("mch_id", mch_id);
    params.put("sub_mch_id", sub_mch_id);
    params.put("long_url", long_url);
    
    return buildSignAfterParasMap(params, paternerKey);
  }




















  
  public static Map<String, String> buildRefundParams(String appid, String mch_id, String sub_appid, String sub_mch_id, String transaction_id, String out_trade_no, String out_refund_no, String total_fee, String refund_fee, String refund_fee_type, String refund_account, String refund_desc, String paternerKey) {
    Map<String, String> params = new HashMap<String, String>();
    params.put("appid", appid);
    params.put("mch_id", mch_id);
    params.put("sub_appid", sub_appid);
    params.put("sub_mch_id", sub_mch_id);
    params.put("transaction_id", transaction_id);
    params.put("out_trade_no", out_trade_no);
    params.put("out_refund_no", out_refund_no);
    params.put("total_fee", total_fee);
    params.put("refund_fee", refund_fee);
    params.put("refund_fee_type", refund_fee_type);
    params.put("refund_account", refund_account);
    params.put("refund_desc", refund_desc);
    
    return buildSignAfterParasMap(params, paternerKey);
  }










  
  public static String packageSign(Map<String, String> params, boolean urlEncoder) {
    TreeMap<String, String> sortedParams = new TreeMap<String, String>(params);
    
    StringBuilder sb = new StringBuilder();
    boolean first = true;
    for (Map.Entry<String, String> param : sortedParams.entrySet()) {
      String value = (String)param.getValue();
      if (StringUtils.isBlank(value)) {
        continue;
      }
      if (first) {
        first = false;
      } else {
        sb.append("&");
      } 
      sb.append((String)param.getKey()).append("=");
      if (urlEncoder) {
        try {
          value = urlEncode(value);
        } catch (UnsupportedEncodingException unsupportedEncodingException) {}
      }
      
      sb.append(value);
    } 
    return sb.toString();
  }










  
  public static String urlEncode(String src) throws UnsupportedEncodingException { return URLEncoder.encode(src, Charsets.toCharset("UTF-8").name()).replace("+", "%20"); }








  
  public static Map<String, String> buildSignAfterParasMap(Map<String, String> params, String paternerKey) {
    params.put("nonce_str", Sequence.uuid());
    String sign = createSign(params, paternerKey);
    params.put("sign", sign);
    return params;
  }










  
  public static String createSign(Map<String, String> params, String partnerKey) {
    params.remove("sign");
    String stringA = packageSign(params, false);
    String stringSignTemp = stringA + "&key=" + partnerKey;
    logger.info(stringSignTemp);
    return SecurityUtil.md5(stringSignTemp).toUpperCase();
  }









  
  public static boolean verifyNotify(Map<String, String> params, String paternerKey) {
    String sign = (String)params.get("sign");
    String localSign = createSign(params, paternerKey);
    return sign.equals(localSign);
  }







  
  public static boolean codeIsOK(String return_code) { return (StringUtils.isNotBlank(return_code) && "SUCCESS".equals(return_code)); }








  
  public static String toXml(Map<String, String> params) {
    StringBuilder xml = new StringBuilder();
    xml.append("<xml>");
    for (Map.Entry<String, String> entry : params.entrySet()) {
      String key = (String)entry.getKey();
      String value = (String)entry.getValue();
      
      if (StringUtils.isBlank(value)) {
        continue;
      }
      xml.append("<").append(key).append(">");
      xml.append((String)entry.getValue());
      xml.append("</").append(key).append(">");
    } 
    xml.append("</xml>");
    return xml.toString();
  }









  
  public static Map<String, String> xmlToMap(String xmlStr) { return XmlUtil.parseXml2Map(xmlStr); }









  
  public static String replace(String str, String regex, String... args) {
    int length = args.length;
    for (int i = 0; i < length; i++) {
      str = str.replaceFirst(regex, args[i]);
    }
    return str;
  }











  
  public static Map<String, String> buildOrderPaySign(String appid, String partnerid, String prepayid, String trade_type, String partnerKey) {
    Map<String, String> params = new HashMap<String, String>();
    if ("APP".equals(trade_type)) {
      params.put("appid", appid);
      params.put("partnerid", partnerid);
      params.put("prepayid", prepayid);
      params.put("package", "Sign=WXPay");
      params.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000L));
      params.put("noncestr", Sequence.uuid());
      String sign = createSign(params, partnerKey);
      params.put("sign", sign);
    } else if ("JSAPI".equals(trade_type)) {
      params.put("appId", appid);
      params.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000L));
      params.put("nonceStr", Sequence.uuid());
      params.put("package", "prepay_id=" + prepayid);
      params.put("signType", "MD5");
      String sign = createSign(params, partnerKey);
      params.put("paySign", sign);
    } 
    return params;
  }
}
