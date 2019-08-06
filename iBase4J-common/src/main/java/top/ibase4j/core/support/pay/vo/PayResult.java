package top.ibase4j.core.support.pay.vo;

import java.io.Serializable;
import java.util.Date;

public class PayResult
  implements Serializable {
  private String transactionId;
  private Date timeEnd;
  private String openid;
  private String payResult;
  
  public PayResult(String transactionId, Date timeEnd, String openid, String payResult) {
    this.transactionId = transactionId;
    this.timeEnd = timeEnd;
    this.openid = openid;
    this.payResult = payResult;
  }

  
  public String getTransactionId() { return this.transactionId; }


  
  public void setTransactionId(String transactionId) { this.transactionId = transactionId; }


  
  public Date getTimeEnd() { return this.timeEnd; }


  
  public void setTimeEnd(Date timeEnd) { this.timeEnd = timeEnd; }


  
  public String getOpenid() { return this.openid; }


  
  public void setOpenid(String openid) { this.openid = openid; }


  
  public String getPayResult() { return this.payResult; }


  
  public void setPayResult(String payResult) { this.payResult = payResult; }
}
