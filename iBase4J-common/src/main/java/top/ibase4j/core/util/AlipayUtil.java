package top.ibase4j.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradePayModel;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradePayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.ibase4j.core.exception.BusinessException;
import top.ibase4j.core.support.pay.AliPay;
import top.ibase4j.core.support.pay.AliPayConfig;
import top.ibase4j.core.support.pay.vo.PayResult;
import top.ibase4j.core.support.pay.vo.RefundResult;










public final class AlipayUtil
{
  private static final Logger logger = LogManager.getLogger(AlipayUtil.class);













  
  public static String precreate(String out_trade_no, String subject, String body, BigDecimal amount, String ip, String timeout, String callBack) {
    AlipayClient alipayClient = AliPayConfig.build().getAlipayClient();
    return precreate(alipayClient, out_trade_no, subject, body, amount, ip, timeout, callBack);
  }













  
  public static String precreate(AlipayClient alipayClient, String out_trade_no, String subject, String body, BigDecimal amount, String ip, String timeout, String callBack) {
    AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
    
    AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
    model.setSubject(subject);
    model.setBody(body);
    model.setOutTradeNo(out_trade_no);
    model.setTimeoutExpress(timeout);
    model.setTotalAmount(amount.toString());
    model.setQrCodeTimeoutExpress(timeout);
    request.setBizModel(model);
    request.setNotifyUrl(callBack);
    
    try {
      AlipayTradePrecreateResponse response = (AlipayTradePrecreateResponse)alipayClient.sdkExecute(request);
      logger.info(response.getBody());
      if (!response.isSuccess()) {
        throw new RuntimeException(response.getSubMsg());
      }
      return response.getQrCode();
    } catch (AlipayApiException e) {
      throw new RuntimeException(e);
    } 
  }













  
  public static String getSign(String out_trade_no, String subject, String body, BigDecimal amount, String ip, String timeout, String callBack) {
    AlipayClient alipayClient = AliPayConfig.build().getAlipayClient();
    return getSign(alipayClient, out_trade_no, subject, body, amount, ip, timeout, callBack);
  }













  
  public static String getSign(AlipayClient alipayClient, String out_trade_no, String subject, String body, BigDecimal amount, String ip, String timeout, String callBack) {
    AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
    
    AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
    model.setSubject(subject);
    model.setBody(body);
    model.setOutTradeNo(out_trade_no);
    model.setTimeoutExpress(timeout);
    model.setTotalAmount(amount.toString());
    model.setProductCode("QUICK_MSECURITY_PAY");
    request.setBizModel(model);
    request.setNotifyUrl(callBack);
    
    try {
      AlipayTradeAppPayResponse response = (AlipayTradeAppPayResponse)alipayClient.sdkExecute(request);
      logger.info(response.getBody());
      if (!response.isSuccess()) {
        throw new RuntimeException(response.getSubMsg());
      }
      return response.getBody();
    } catch (AlipayApiException e) {
      throw new RuntimeException(e);
    } 
  }

  
  public static Map<?, ?> searchTreade(String outTradeNo, String tradeNo) {
    AlipayClient alipayClient = AliPayConfig.build().getAlipayClient();
    return searchTreade(alipayClient, outTradeNo, tradeNo);
  }

  
  public static Map<?, ?> searchTreade(AlipayClient alipayClient, String outTradeNo, String tradeNo) {
    AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
    AlipayTradeQueryModel model = new AlipayTradeQueryModel();
    model.setOutTradeNo(outTradeNo);
    model.setTradeNo(tradeNo);
    request.setBizModel(model);
    Map<String, Object> result = InstanceUtil.newHashMap();
    try {
      AlipayTradeQueryResponse response = (AlipayTradeQueryResponse)alipayClient.execute(request);
      if (!response.isSuccess()) {
        result.put("trade_state_desc", response.getSubMsg());
        result.put("trade_state", "0");
      } else {
        Map<?, ?> body = (Map)JSON.parseObject(response.getBody(), Map.class);
        JSONObject jSONObject = JSON.parseObject(body.get("alipay_trade_query_response").toString());
        Object trade_status = jSONObject.get("trade_status");
        if ("TRADE_SUCCESS".equals(trade_status) || "TRADE_FINISHED".equals(trade_status)) {
          Date date = DateUtil.stringToDate((String)jSONObject.get("send_pay_date"));
          result.put("time_end", date);
          result.put("trade_no", jSONObject.get("trade_no"));
          result.put("trade_state", "1");
        } else {
          result.put("trade_state_desc", jSONObject.get("msg"));
          result.put("trade_state", "2");
        } 
      } 
    } catch (AlipayApiException e) {
      logger.error("", e);
      result.put("trade_state_desc", e.getMessage());
      result.put("trade_state", "0");
    } 
    return result;
  }











  
  public static RefundResult refund(String outTradeNo, String tradeNo, String outRequestNo, BigDecimal refundAmount, String refundReason) {
    AlipayClient alipayClient = AliPayConfig.build().getAlipayClient();
    return refund(alipayClient, outTradeNo, tradeNo, outRequestNo, refundAmount, refundReason);
  }











  
  public static RefundResult refund(AlipayClient alipayClient, String outTradeNo, String tradeNo, String outRequestNo, BigDecimal refundAmount, String refundReason) {
    AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
    
    AlipayTradeRefundModel model = new AlipayTradeRefundModel();
    model.setOutTradeNo(outTradeNo);
    model.setTradeNo(tradeNo);
    model.setRefundAmount(refundAmount.toString());
    model.setRefundReason(refundReason);
    model.setOutRequestNo(outRequestNo);
    request.setBizModel(model);
    
    try {
      AlipayTradeRefundResponse response = (AlipayTradeRefundResponse)alipayClient.execute(request);
      logger.info(response.getBody());
      if (!response.isSuccess()) {
        throw new RuntimeException(response.getSubMsg());
      }
      Map<?, ?> body = (Map)JSON.parseObject(response.getBody(), Map.class);
      JSONObject jSONObject = JSON.parseObject(body.get("alipay_trade_refund_response").toString());
      return new RefundResult((String)jSONObject.get("trade_no"), outTradeNo, refundAmount.toString(), 
          DateUtil.stringToDate((String)jSONObject.get("gmt_refund_pay")), 
          "Y".equals(jSONObject.get("fund_change")) ? "1" : "2");
    } catch (AlipayApiException e) {
      throw new RuntimeException(e);
    } 
  }











  
  public static PayResult micropay(String authCode, String subject, String outTradeNo, BigDecimal totalAmount, String callBack) { return micropay(AliPayConfig.build().getAlipayClient(), authCode, subject, outTradeNo, totalAmount, callBack); }












  
  public static PayResult micropay(AlipayClient alipayClient, String authCode, String subject, String outTradeNo, BigDecimal totalAmount, String callBack) {
    AlipayTradePayRequest request = new AlipayTradePayRequest();
    AlipayTradePayModel model = new AlipayTradePayModel();
    model.setScene("bar_code");
    model.setAuthCode(authCode);
    model.setSubject(subject);
    model.setOutTradeNo(outTradeNo);
    model.setTimeoutExpress("3m");
    model.setTotalAmount(totalAmount.toString());
    request.setBizModel(model);
    request.setNotifyUrl(callBack);
    try {
      AlipayTradePayResponse response = (AlipayTradePayResponse)alipayClient.execute(request);
      logger.info(response.getBody());
      if (!response.isSuccess()) {
        throw new BusinessException(response.getSubMsg());
      }
      String result = AliPay.tradePay(model, callBack);
      Map<?, ?> body = (Map)JSON.parseObject(result, Map.class);
      JSONObject jSONObject = JSON.parseObject(body.get("alipay_trade_query_response").toString());
      Object trade_status = jSONObject.get("trade_status");
      if ("TRADE_SUCCESS".equals(trade_status) || "TRADE_FINISHED".equals(trade_status)) {
        String tradeNo = (String)jSONObject.get("trade_no");
        String gmtCreate = (String)jSONObject.get("gmt_create");
        return new PayResult(tradeNo, DateUtil.stringToDate(gmtCreate), (String)jSONObject
            .get("buyer_logon_id"), "TRADE_SUCCESS".equals(trade_status) ? "1" : "2");
      } 
      throw new RuntimeException((String)jSONObject.get("sub_msg"));
    
    }
    catch (AlipayApiException e) {
      throw new BusinessException("付款失败", e);
    } 
  }
}
