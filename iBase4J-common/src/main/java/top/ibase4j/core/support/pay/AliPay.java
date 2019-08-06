package top.ibase4j.core.support.pay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayDataDataserviceBillDownloadurlQueryModel;
import com.alipay.api.domain.AlipayFundTransOrderQueryModel;
import com.alipay.api.domain.AlipayFundTransToaccountTransferModel;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeCancelModel;
import com.alipay.api.domain.AlipayTradeCloseModel;
import com.alipay.api.domain.AlipayTradeCreateModel;
import com.alipay.api.domain.AlipayTradeFastpayRefundQueryModel;
import com.alipay.api.domain.AlipayTradeOrderSettleModel;
import com.alipay.api.domain.AlipayTradePayModel;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.request.AlipayFundTransOrderQueryRequest;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeCancelRequest;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradeCreateRequest;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeOrderSettleRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.alipay.api.response.AlipayFundTransOrderQueryResponse;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeCancelResponse;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradeCreateResponse;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeOrderSettleResponse;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradePayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.ibase4j.core.exception.BusinessException;



public class AliPay
{
  static Logger logger = LogManager.getLogger();








  
  public static String startAppPayStr(AlipayTradeAppPayModel model, String notifyUrl) throws AlipayApiException {
    AlipayTradeAppPayResponse response = appPay(model, notifyUrl);
    return response.getBody();
  }









  
  public static AlipayTradeAppPayResponse appPay(AlipayTradeAppPayModel model, String notifyUrl) throws AlipayApiException {
    AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
    
    request.setBizModel(model);
    request.setNotifyUrl(notifyUrl);
    
    return (AlipayTradeAppPayResponse)AliPayConfig.build().getAlipayClient().sdkExecute(request);
  }







  
  public static void wapPay(HttpServletResponse response, AlipayTradeWapPayModel model, String returnUrl, String notifyUrl) throws AlipayApiException, IOException {
    String form = wapPayToString(response, model, returnUrl, notifyUrl);
    HttpServletResponse httpResponse = response;
    httpResponse.setContentType("text/html;charset=" + AliPayConfig.build().getCharset());
    httpResponse.getWriter().write(form);
    httpResponse.getWriter().flush();
  }

  
  public static String wapPayToString(HttpServletResponse response, AlipayTradeWapPayModel model, String returnUrl, String notifyUrl) throws AlipayApiException, IOException {
    AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
    alipayRequest.setReturnUrl(returnUrl);
    alipayRequest.setNotifyUrl(notifyUrl);
    alipayRequest.setBizModel(model);
    return ((AlipayTradeWapPayResponse)AliPayConfig.build().getAlipayClient().pageExecute(alipayRequest)).getBody();
  }






  
  public static String tradePay(AlipayTradePayModel model, String notifyUrl) {
    AlipayTradePayResponse response = tradePayToResponse(model, notifyUrl);
    logger.info(response.getBody());
    if (!response.isSuccess()) {
      throw new BusinessException(response.getSubMsg());
    }
    return response.getBody();
  }

  
  public static AlipayTradePayResponse tradePayToResponse(AlipayTradePayModel model, String notifyUrl) {
    AlipayTradePayRequest request = new AlipayTradePayRequest();
    request.setBizModel(model);
    request.setNotifyUrl(notifyUrl);
    try {
      return (AlipayTradePayResponse)AliPayConfig.build().getAlipayClient().execute(request);
    } catch (AlipayApiException e) {
      throw new BusinessException("付款失败", e);
    } 
  }








  
  public static String tradePrecreatePay(AlipayTradePrecreateModel model, String notifyUrl) throws AlipayApiException {
    AlipayTradePrecreateResponse response = tradePrecreatePayToResponse(model, notifyUrl);
    return response.getBody();
  }

  
  public static AlipayTradePrecreateResponse tradePrecreatePayToResponse(AlipayTradePrecreateModel model, String notifyUrl) throws AlipayApiException {
    AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
    request.setBizModel(model);
    request.setNotifyUrl(notifyUrl);
    return (AlipayTradePrecreateResponse)AliPayConfig.build().getAlipayClient().execute(request);
  }







  
  public static boolean transfer(AlipayFundTransToaccountTransferModel model) throws AlipayApiException {
    AlipayFundTransToaccountTransferResponse response = transferToResponse(model);
    String result = response.getBody();
    logger.info("transfer result>" + result);
    if (response.isSuccess()) {
      return true;
    }
    
    JSONObject jsonObject = JSON.parseObject(result);
    
    String outBizNo = jsonObject.getJSONObject("alipay_fund_trans_toaccount_transfer_response").getString("out_biz_no");
    AlipayFundTransOrderQueryModel queryModel = new AlipayFundTransOrderQueryModel();
    model.setOutBizNo(outBizNo);
    boolean isSuccess = transferQuery(queryModel);
    if (isSuccess) {
      return true;
    }
    
    return false;
  }

  
  public static AlipayFundTransToaccountTransferResponse transferToResponse(AlipayFundTransToaccountTransferModel model) throws AlipayApiException {
    AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
    request.setBizModel(model);
    return (AlipayFundTransToaccountTransferResponse)AliPayConfig.build().getAlipayClient().execute(request);
  }







  
  public static boolean transferQuery(AlipayFundTransOrderQueryModel model) throws AlipayApiException {
    AlipayFundTransOrderQueryResponse response = transferQueryToResponse(model);
    logger.info("transferQuery result>" + response.getBody());
    if (response.isSuccess()) {
      return true;
    }
    return false;
  }

  
  public static AlipayFundTransOrderQueryResponse transferQueryToResponse(AlipayFundTransOrderQueryModel model) throws AlipayApiException {
    AlipayFundTransOrderQueryRequest request = new AlipayFundTransOrderQueryRequest();
    request.setBizModel(model);
    return (AlipayFundTransOrderQueryResponse)AliPayConfig.build().getAlipayClient().execute(request);
  }







  
  public static boolean isTradeQuery(AlipayTradeQueryModel model) throws AlipayApiException {
    AlipayTradeQueryResponse response = tradeQuery(model);
    if (response.isSuccess()) {
      return true;
    }
    return false;
  }
  
  public static AlipayTradeQueryResponse tradeQuery(AlipayTradeQueryModel model) throws AlipayApiException {
    AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
    request.setBizModel(model);
    return (AlipayTradeQueryResponse)AliPayConfig.build().getAlipayClient().execute(request);
  }







  
  public static boolean isTradeCancel(AlipayTradeCancelModel model) throws AlipayApiException {
    AlipayTradeCancelResponse response = tradeCancel(model);
    if (response.isSuccess()) {
      return true;
    }
    return false;
  }
  
  public static AlipayTradeCancelResponse tradeCancel(AlipayTradeCancelModel model) throws AlipayApiException {
    AlipayTradeCancelRequest request = new AlipayTradeCancelRequest();
    request.setBizModel(model);
    return (AlipayTradeCancelResponse)AliPayConfig.build().getAlipayClient().execute(request);
  }








  
  public static boolean isTradeClose(AlipayTradeCloseModel model) throws AlipayApiException {
    AlipayTradeCloseResponse response = tradeClose(model);
    if (response.isSuccess()) {
      return true;
    }
    return false;
  }
  
  public static AlipayTradeCloseResponse tradeClose(AlipayTradeCloseModel model) throws AlipayApiException {
    AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
    request.setBizModel(model);
    return (AlipayTradeCloseResponse)AliPayConfig.build().getAlipayClient().execute(request);
  }










  
  public static AlipayTradeCreateResponse tradeCreate(AlipayTradeCreateModel model, String notifyUrl) throws AlipayApiException {
    AlipayTradeCreateRequest request = new AlipayTradeCreateRequest();
    request.setBizModel(model);
    request.setNotifyUrl(notifyUrl);
    return (AlipayTradeCreateResponse)AliPayConfig.build().getAlipayClient().execute(request);
  }







  
  public static String tradeRefund(AlipayTradeRefundModel model) throws AlipayApiException {
    AlipayTradeRefundResponse response = tradeRefundToResponse(model);
    return response.getBody();
  }

  
  public static AlipayTradeRefundResponse tradeRefundToResponse(AlipayTradeRefundModel model) throws AlipayApiException {
    AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
    request.setBizModel(model);
    return (AlipayTradeRefundResponse)AliPayConfig.build().getAlipayClient().execute(request);
  }







  
  public static String tradeRefundQuery(AlipayTradeFastpayRefundQueryModel model) throws AlipayApiException {
    AlipayTradeFastpayRefundQueryResponse response = tradeRefundQueryToResponse(model);
    return response.getBody();
  }

  
  public static AlipayTradeFastpayRefundQueryResponse tradeRefundQueryToResponse(AlipayTradeFastpayRefundQueryModel model) throws AlipayApiException {
    AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
    request.setBizModel(model);
    return (AlipayTradeFastpayRefundQueryResponse)AliPayConfig.build().getAlipayClient().execute(request);
  }








  
  public static String billDownloadurlQuery(AlipayDataDataserviceBillDownloadurlQueryModel model) throws AlipayApiException {
    AlipayDataDataserviceBillDownloadurlQueryResponse response = billDownloadurlQueryToResponse(model);
    return response.getBillDownloadUrl();
  }

  
  public static AlipayDataDataserviceBillDownloadurlQueryResponse billDownloadurlQueryToResponse(AlipayDataDataserviceBillDownloadurlQueryModel model) throws AlipayApiException {
    AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
    request.setBizModel(model);
    return (AlipayDataDataserviceBillDownloadurlQueryResponse)AliPayConfig.build().getAlipayClient().execute(request);
  }







  
  public static boolean isTradeOrderSettle(AlipayTradeOrderSettleModel model) throws AlipayApiException {
    AlipayTradeOrderSettleResponse response = tradeOrderSettle(model);
    if (response.isSuccess()) {
      return true;
    }
    return false;
  }

  
  public static AlipayTradeOrderSettleResponse tradeOrderSettle(AlipayTradeOrderSettleModel model) throws AlipayApiException {
    AlipayTradeOrderSettleRequest request = new AlipayTradeOrderSettleRequest();
    request.setBizModel(model);
    return (AlipayTradeOrderSettleResponse)AliPayConfig.build().getAlipayClient().execute(request);
  }











  
  public static void tradePage(HttpServletResponse httpResponse, AlipayTradePayModel model, String notifyUrl, String returnUrl) throws AlipayApiException, IOException {
    AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
    request.setBizModel(model);
    request.setNotifyUrl(notifyUrl);
    request.setReturnUrl(returnUrl);
    String form = ((AlipayTradePagePayResponse)AliPayConfig.build().getAlipayClient().pageExecute(request)).getBody();
    httpResponse.setContentType("text/html;charset=" + AliPayConfig.build().getCharset());
    httpResponse.getWriter().write(form);
    httpResponse.getWriter().flush();
    httpResponse.getWriter().close();
  }






  
  public static Map<String, String> toMap(HttpServletRequest request) {
    Map<String, String> params = new HashMap<String, String>();
    Map<String, String[]> requestParams = request.getParameterMap();
    for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
      String[] values = (String[])entry.getValue();
      String valueStr = "";
      for (int i = 0; i < values.length; i++) {
        valueStr = (i == values.length - 1) ? (valueStr + values[i]) : (valueStr + values[i] + ",");
      }

      
      params.put(entry.getKey(), valueStr);
    } 
    return params;
  }
}
