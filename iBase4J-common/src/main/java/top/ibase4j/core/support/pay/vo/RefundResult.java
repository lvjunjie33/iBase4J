package top.ibase4j.core.support.pay.vo;

import java.io.Serializable;
import java.util.Date;








public class RefundResult
  implements Serializable
{
  private String refundId;
  private String outRefundNo;
  private String refundFee;
  private Date refundTime;
  private String refundResult = "1";
  
  public RefundResult(String refundId, String outRefundNo, String refundFee, Date refundTime) {
    this.refundId = refundId;
    this.outRefundNo = outRefundNo;
    this.refundFee = refundFee;
    this.refundTime = refundTime;
  }

  
  public RefundResult(String refundId, String outRefundNo, String refundFee, Date refundTime, String refundResult) {
    this.refundId = refundId;
    this.outRefundNo = outRefundNo;
    this.refundFee = refundFee;
    this.refundTime = refundTime;
    this.refundResult = refundResult;
  }

  
  public String getRefundId() { return this.refundId; }


  
  public void setRefundId(String refundId) { this.refundId = refundId; }


  
  public String getOutRefundNo() { return this.outRefundNo; }


  
  public void setOutRefundNo(String outRefundNo) { this.outRefundNo = outRefundNo; }


  
  public String getRefundFee() { return this.refundFee; }


  
  public void setRefundFee(String refundFee) { this.refundFee = refundFee; }


  
  public Date getRefundTime() { return this.refundTime; }


  
  public void setRefundTime(Date refundTime) { this.refundTime = refundTime; }


  
  public String getRefundResult() { return this.refundResult; }


  
  public void setRefundResult(String refundResult) { this.refundResult = refundResult; }
}
