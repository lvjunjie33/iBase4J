package top.ibase4j.core.util;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import top.ibase4j.core.support.pay.WxPay;
import top.ibase4j.core.support.pay.WxPayment;
import top.ibase4j.core.support.pay.vo.PayResult;
import top.ibase4j.core.support.pay.vo.RefundResult;











public final class WeChatUtil
{
  private static final Logger logger = LogManager.getLogger();













  
  public static Map<String, String> pushOrder(String out_trade_no, String body, String detail, BigDecimal amount, String scene_info, String ip, String callBack, String openId) { return pushOrder("APP", out_trade_no, body, detail, amount, scene_info, ip, callBack, openId); }














  
  public static Map<String, String> pushOrder(String trade_type, String out_trade_no, String body, String detail, BigDecimal amount, String scene_info, String ip, String callBack, String openId) {
    return pushOrder(PropertiesUtil.getString("wx.mch_id"), PropertiesUtil.getString("wx.appId"), 
        PropertiesUtil.getString("wx.partnerKey"), trade_type, out_trade_no, body, detail, amount, scene_info, ip, callBack, openId);
  }


















  
  public static Map<String, String> pushOrder(String mch_id, String appId, String partnerKey, String trade_type, String out_trade_no, String body, String detail, BigDecimal amount, String scene_info, String ip, String callBack, String openId) {
    String total_fee = amount.multiply(new BigDecimal("100")).setScale(0).toString();
    Map<String, String> params = WxPayment.buildUnifiedOrderParasMap(appId, null, mch_id, null, null, body, detail, null, out_trade_no, total_fee, ip, callBack, trade_type, partnerKey, null, scene_info, openId);
    
    logger.info("WeChart order parameter : " + JSON.toJSONString(params));
    String result = WxPay.pushOrder(params);
    logger.info("WeChart order result : " + result);
    Map<String, String> resultMap = WxPayment.xmlToMap(result);
    String return_code = (String)resultMap.get("return_code");
    if (WxPayment.codeIsOK(return_code)) {
      String result_code = (String)resultMap.get("result_code");
      if (WxPayment.codeIsOK(result_code)) {
        String sign = (String)resultMap.get("sign");
        String mySign = WxPayment.createSign(resultMap, partnerKey);
        if (mySign.equals(sign)) {
          String prepay_id = (String)resultMap.get("prepay_id");
          String mweb_url = (String)resultMap.get("mweb_url");
          String code_url = (String)resultMap.get("code_url");
          if (DataUtil.isNotEmpty(mweb_url)) {
            resultMap.clear();
            resultMap.put("mwebUrl", mweb_url);
            return resultMap;
          }  if (DataUtil.isNotEmpty(code_url)) {
            resultMap.clear();
            resultMap.put("prepayId", prepay_id);
            resultMap.put("codeUrl", code_url);
            return resultMap;
          } 
          return WxPayment.buildOrderPaySign(appId, mch_id, prepay_id, trade_type, partnerKey);
        } 
        
        throw new RuntimeException("微信返回数据异常.");
      } 
      
      throw new RuntimeException((String)resultMap.get("err_code_des"));
    } 
    
    throw new RuntimeException((String)resultMap.get("return_msg"));
  }








  
  public static String createSign(Map<String, String> params, String partnerKey) { return WxPayment.createSign(params, partnerKey); }







  
  public static String createSign(Map<String, String> params) { return WxPayment.createSign(params, PropertiesUtil.getString("wx.partnerKey")); }






  
  public static Map<String, String> closeOrder(String out_trade_no) {
    return closeOrder(PropertiesUtil.getString("wx.mch_id"), PropertiesUtil.getString("wx.appId"), 
        PropertiesUtil.getString("wx.partnerKey"), out_trade_no);
  }








  
  public static Map<String, String> closeOrder(String mch_id, String appId, String partnerKey, String out_trade_no) {
    try {
      Map<String, String> params = new HashMap<String, String>();
      params.put("appid", appId);
      params.put("mch_id", mch_id);
      params.put("out_trade_no", out_trade_no);
      params = WxPayment.buildSignAfterParasMap(params, partnerKey);
      String result = WxPay.closeOrder(params);
      Map<String, String> resultMap = WxPayment.xmlToMap(result);
      logger.info(JSON.toJSONString(resultMap));
      return resultMap;
    } catch (Exception e) {
      logger.error("删除微信订单异常", e);
      
      return null;
    } 
  }




  
  public static Map<String, Object> queryOrder(String out_trade_no) {
    return queryOrder(PropertiesUtil.getString("wx.mch_id"), PropertiesUtil.getString("wx.appId"), 
        PropertiesUtil.getString("wx.partnerKey"), out_trade_no);
  }





  
  public static Map<String, Object> queryOrder(String mch_id, String appId, String partnerKey, String out_trade_no) {
    Map<String, Object> result = InstanceUtil.newHashMap();
    Map<String, String> params = WxPayment.buildParasMap(appId, null, mch_id, null, null, out_trade_no, partnerKey);
    Map<String, String> resultMap = WxPayment.xmlToMap(WxPay.orderQuery(params));
    String return_code = (String)resultMap.get("return_code");
    if (WxPayment.codeIsOK(return_code)) {
      String result_code = (String)resultMap.get("result_code");
      if (WxPayment.codeIsOK(result_code)) {
        String trade_state = (String)resultMap.get("trade_state");
        if (WxPayment.codeIsOK(trade_state)) {
          Date date = DateUtil.stringToDate((String)resultMap.get("time_end"));
          result.put("time_end", date);
          result.put("trade_no", resultMap.get("transaction_id"));
          result.put("trade_state", "1");
        } else {
          result.put("trade_state_desc", resultMap.get("trade_state_desc"));
          result.put("trade_state", "2");
        } 
      } else {
        logger.warn((String)resultMap.get("err_code_des"));
        result.put("trade_state_desc", resultMap.get("err_code_des"));
        result.put("trade_state", "0");
      } 
    } else {
      logger.warn((String)resultMap.get("return_msg"));
      result.put("trade_state_desc", resultMap.get("return_msg"));
      result.put("trade_state", "0");
    } 
    return result;
  }











  
  public static RefundResult refund(String transaction_id, String out_trade_no, String out_refund_no, BigDecimal amount, BigDecimal refund, String refund_desc) {
    try {
      PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
      String path = resolver.getResources(PropertiesUtil.getString("wx.certPath"))[0].getFile().getAbsolutePath();
      return refund(path, PropertiesUtil.getString("wx.certPass"), transaction_id, out_trade_no, out_refund_no, amount, refund, refund_desc);
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    } 
  }











  
  public static RefundResult refund(String certPath, String certPass, String transaction_id, String out_trade_no, String out_refund_no, BigDecimal amount, BigDecimal refund, String refund_desc) {
    return refund(PropertiesUtil.getString("wx.mch_id"), PropertiesUtil.getString("wx.appId"), null, null, 
        PropertiesUtil.getString("wx.partnerKey"), certPath, certPass, transaction_id, out_trade_no, out_refund_no, amount, refund, "CNY", null, refund_desc);
  }



















  
  public static RefundResult refund(String mch_id, String appid, String sub_mch_id, String sub_appid, String paternerKey, String certPath, String certPass, String transaction_id, String out_trade_no, String out_refund_no, BigDecimal amount, BigDecimal refund, String refund_fee_type, String refund_account, String refund_desc) {
    String total_fee = amount.multiply(new BigDecimal("100")).setScale(0).toString();
    String refund_fee = refund.multiply(new BigDecimal("100")).setScale(0).toString();
    Map<String, String> params = WxPayment.buildRefundParams(appid, mch_id, null, null, transaction_id, out_trade_no, out_refund_no, total_fee, refund_fee, refund_fee_type, refund_account, refund_desc, paternerKey);

    
    logger.info("WeChart order parameter : " + JSON.toJSONString(params));
    String result = WxPay.orderRefund(params, certPath, certPass);
    logger.info("WeChart order result : " + result);
    Map<String, String> resultMap = WxPayment.xmlToMap(result);
    String return_code = (String)resultMap.get("return_code");
    if (WxPayment.codeIsOK(return_code)) {
      String result_code = (String)resultMap.get("result_code");
      if (WxPayment.codeIsOK(result_code)) {
        String sign = (String)resultMap.get("sign");
        String mySign = WxPayment.createSign(resultMap, paternerKey);
        if (mySign.equals(sign)) {
          String refund_id = (String)resultMap.get("refund_id");
          return new RefundResult(refund_id, out_refund_no, refund_fee, new Date());
        } 
        throw new RuntimeException("微信返回数据异常.");
      } 
      
      throw new RuntimeException((String)resultMap.get("err_code_des"));
    } 
    
    throw new RuntimeException((String)resultMap.get("return_msg"));
  }







  
  public static boolean codeIsOK(String return_code) { return WxPayment.codeIsOK(return_code); }


  
  public static PayResult micropay(String device_info, String body, String detail, String attach, String out_trade_no, BigDecimal total_fee, String spbill_create_ip, String auth_code) {
    return micropay(PropertiesUtil.getString("wx.appId"), null, PropertiesUtil.getString("wx.mch_id"), null, device_info, body, detail, attach, out_trade_no, total_fee
        
        .multiply(new BigDecimal("100")).setScale(0).toString(), spbill_create_ip, auth_code, 
        PropertiesUtil.getString("wx.partnerKey"));
  }


  
  public static PayResult micropay(String appid, String sub_appid, String mch_id, String sub_mch_id, String device_info, String body, String detail, String attach, String out_trade_no, String total_fee, String spbill_create_ip, String auth_code, String paternerKey) {
    Map<String, String> params = WxPayment.buildMicropayParas(appid, sub_appid, mch_id, sub_mch_id, device_info, body, detail, attach, out_trade_no, total_fee, spbill_create_ip, auth_code, paternerKey);
    
    String result = WxPay.micropay(params);
    Map<String, String> resultMap = WxPayment.xmlToMap(result);
    String return_code = (String)resultMap.get("return_code");
    if (WxPayment.codeIsOK(return_code)) {
      String result_code = (String)resultMap.get("result_code");
      if (WxPayment.codeIsOK(result_code)) {
        String transaction_id = (String)resultMap.get("transaction_id");
        String sign = (String)resultMap.get("sign");
        String mySign = WxPayment.createSign(resultMap, PropertiesUtil.getString("wx.partnerKey"));
        if (mySign.equals(sign)) {
          return new PayResult(transaction_id, DateUtil.stringToDate((String)resultMap.get("time_end")), (String)resultMap
              .get("openid"), "SUCCESS".equals(resultMap.get("result_code")) ? "1" : "2");
        }
        throw new RuntimeException("微信返回数据异常.");
      } 
      
      throw new RuntimeException((String)resultMap.get("err_code_des"));
    } 
    
    throw new RuntimeException((String)resultMap.get("return_msg"));
  }
}
