package top.ibase4j.core.support.pay;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.ibase4j.core.util.PropertiesUtil;


@Configuration
@ConditionalOnClass({AlipayClient.class})
public class AliPayConfig
{
  private final Logger logger = LogManager.getLogger();
  
  private String privateKey;
  private String publicKey;
  private String appId;
  private String serviceUrl;
  private String charset;
  private String signType;
  private String format;
  private AlipayClient alipayClient;
  private static AliPayConfig config;
  
  @Bean
  public AliPayConfig aliPayConfigs() {
    this.logger.info("加载支付宝配置...");
    config = new AliPayConfig();
    config.setPrivateKey(PropertiesUtil.getString("alipay.privateKey"));
    config.setPublicKey(PropertiesUtil.getString("alipay.publicKey"));
    config.setAppId(PropertiesUtil.getString("alipay.appId"));
    config.setServiceUrl(PropertiesUtil.getString("alipay.serverUrl"));
    config.setCharset(PropertiesUtil.getString("alipay.charset"));
    return config;
  }
  
  public static AliPayConfig build() {
    config
      .alipayClient = new DefaultAlipayClient(config.getServiceUrl(), config.getAppId(), config.getPrivateKey(), config.getFormat(), config.getCharset(), config.getPublicKey(), config.getSignType());
    return config;
  }
  
  public String getPrivateKey() {
    if (StringUtils.isBlank(this.privateKey)) {
      throw new IllegalStateException("privateKey 未被赋值");
    }
    return this.privateKey;
  }
  
  public AliPayConfig setPrivateKey(String privateKey) {
    if (StringUtils.isBlank(privateKey)) {
      throw new IllegalArgumentException("privateKey 值不能为空");
    }
    this.privateKey = privateKey;
    return this;
  }
  
  public String getPublicKey() {
    if (StringUtils.isBlank(this.publicKey)) {
      throw new IllegalStateException("publicKey 未被赋值");
    }
    return this.publicKey;
  }
  
  public AliPayConfig setPublicKey(String publicKey) {
    if (StringUtils.isBlank(publicKey)) {
      throw new IllegalArgumentException("publicKey 值不能为空");
    }
    this.publicKey = publicKey;
    return this;
  }
  
  public String getAppId() {
    if (StringUtils.isBlank(this.appId)) {
      throw new IllegalStateException("appId 未被赋值");
    }
    return this.appId;
  }
  
  public AliPayConfig setAppId(String appId) {
    if (StringUtils.isBlank(appId)) {
      throw new IllegalArgumentException("appId 值不能为空");
    }
    this.appId = appId;
    return this;
  }
  
  public String getServiceUrl() {
    if (StringUtils.isBlank(this.serviceUrl)) {
      throw new IllegalStateException("serviceUrl 未被赋值");
    }
    return this.serviceUrl;
  }
  
  public AliPayConfig setServiceUrl(String serviceUrl) {
    if (StringUtils.isBlank(serviceUrl)) {
      serviceUrl = "https://openapi.alipay.com/gateway.do";
    }
    this.serviceUrl = serviceUrl;
    return this;
  }
  
  public String getCharset() {
    if (StringUtils.isBlank(this.charset)) {
      this.charset = "UTF-8";
    }
    return this.charset;
  }
  
  public AliPayConfig setCharset(String charset) {
    if (StringUtils.isBlank(charset)) {
      charset = "UTF-8";
    }
    this.charset = charset;
    return this;
  }
  
  public String getSignType() {
    if (StringUtils.isBlank(this.signType)) {
      this.signType = "RSA2";
    }
    return this.signType;
  }
  
  public AliPayConfig setSignType(String signType) {
    if (StringUtils.isBlank(signType)) {
      signType = "RSA2";
    }
    this.signType = signType;
    return this;
  }
  
  public String getFormat() {
    if (StringUtils.isBlank(this.format)) {
      this.format = "json";
    }
    return this.format;
  }
  
  public AlipayClient getAlipayClient() {
    if (this.alipayClient == null) {
      throw new IllegalStateException("alipayClient 未被初始化");
    }
    return this.alipayClient;
  }
}
