package top.ibase4j.core.support.pay;

import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.ibase4j.core.util.HttpUtil;


public class WxPay
{
  static Logger logger = LogManager.getLogger();

  
  private static final String UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

  
  private static final String ORDERQUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";

  
  private static final String CLOSEORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";

  
  private static final String REVERSE_URL = "https://api.mch.weixin.qq.com/secapi/pay/reverse";

  
  private static final String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";

  
  private static final String REFUNDQUERY_URL = "https://api.mch.weixin.qq.com/pay/refundquery";

  
  private static final String DOWNLOADBILLY_URL = "https://api.mch.weixin.qq.com/pay/downloadbill";

  
  private static final String REPORT_URL = "https://api.mch.weixin.qq.com/payitil/report";
  
  private static final String SHORT_URL = "https://api.mch.weixin.qq.com/tools/shorturl";
  
  private static final String AUTHCODETOOPENID_URL = "https://api.mch.weixin.qq.com/tools/authcodetoopenid";
  
  private static final String MICROPAY_URL = "https://api.mch.weixin.qq.com/pay/micropay";
  
  private static final String TRANSFERS_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
  
  private static final String GETTRANSFERINFO_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/gettransferinfo";

  
  public enum TradeType
  {
    JSAPI, NATIVE, APP, WAP, MICROPAY;
  }









  
  public static String pushOrder(Map<String, String> params) { return doPost("https://api.mch.weixin.qq.com/pay/unifiedorder", params); }











  
  public static String orderQuery(Map<String, String> params) { return doPost("https://api.mch.weixin.qq.com/pay/orderquery", params); }










  
  public static String closeOrder(Map<String, String> params) { return doPost("https://api.mch.weixin.qq.com/pay/closeorder", params); }













  
  public static String orderReverse(Map<String, String> params, String certPath, String certPass) { return doPostSSL("https://api.mch.weixin.qq.com/secapi/pay/reverse", params, certPath, certPass); }















  
  public static String orderRefund(Map<String, String> params, String certPath, String certPass) { return doPostSSL("https://api.mch.weixin.qq.com/secapi/pay/refund", params, certPath, certPass); }











  
  public static String orderRefundQuery(Map<String, String> params) { return doPost("https://api.mch.weixin.qq.com/pay/refundquery", params); }











  
  public static String downloadBill(Map<String, String> params) { return doPost("https://api.mch.weixin.qq.com/pay/downloadbill", params); }









  
  public static String orderReport(Map<String, String> params) { return doPost("https://api.mch.weixin.qq.com/payitil/report", params); }









  
  public static String toShortUrl(Map<String, String> params) { return doPost("https://api.mch.weixin.qq.com/tools/shorturl", params); }









  
  public static String authCodeToOpenid(Map<String, String> params) { return doPost("https://api.mch.weixin.qq.com/tools/authcodetoopenid", params); }









  
  public static String micropay(Map<String, String> params) { return doPost("https://api.mch.weixin.qq.com/pay/micropay", params); }













  
  public static String transfers(Map<String, String> params, String certPath, String certPassword) { return doPostSSL("https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers", params, certPath, certPassword); }













  
  public static String getTransferInfo(Map<String, String> params, String certPath, String certPassword) { return doPostSSL("https://api.mch.weixin.qq.com/mmpaymkttransfers/gettransferinfo", params, certPath, certPassword); }













  
  public static String getCodeUrl(String appid, String mchId, String productId, String partnerKey, boolean isToShortUrl) {
    String url = "weixin://wxpay/bizpayurl?sign=XXXXX&appid=XXXXX&mch_id=XXXXX&product_id=XXXXX&time_stamp=XXXXX&nonce_str=XXXXX";
    String timeStamp = Long.toString(System.currentTimeMillis() / 1000L);
    String nonceStr = Long.toString(System.currentTimeMillis());
    Map<String, String> packageParams = new HashMap<String, String>();
    packageParams.put("appid", appid);
    packageParams.put("mch_id", mchId);
    packageParams.put("product_id", productId);
    packageParams.put("time_stamp", timeStamp);
    packageParams.put("nonce_str", nonceStr);
    String packageSign = WxPayment.createSign(packageParams, partnerKey);
    String qrCodeUrl = WxPayment.replace(url, "XXXXX", new String[] { packageSign, appid, mchId, productId, timeStamp, nonceStr });
    if (isToShortUrl) {
      
      String shortResult = toShortUrl(WxPayment.buildShortUrlParasMap(appid, null, mchId, null, qrCodeUrl, partnerKey));
      logger.info(shortResult);
      Map<String, String> shortMap = WxPayment.xmlToMap(shortResult);
      String returnCode = (String)shortMap.get("return_code");
      if (WxPayment.codeIsOK(returnCode)) {
        String resultCode = (String)shortMap.get("result_code");
        if (WxPayment.codeIsOK(resultCode)) {
          qrCodeUrl = (String)shortMap.get("short_url");
        }
      } 
    } 
    
    return qrCodeUrl;
  }

  
  public static String doPost(String url, Map<String, String> params) { return HttpUtil.post(url, WxPayment.toXml(params)); }


  
  public static String doPostSSL(String url, Map<String, String> params, String certPath, String certPass) { return HttpUtil.postSSL(url, WxPayment.toXml(params), certPath, certPass); }
}
