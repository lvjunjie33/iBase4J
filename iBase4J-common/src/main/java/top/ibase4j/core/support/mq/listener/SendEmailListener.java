package top.ibase4j.core.support.mq.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.ibase4j.core.support.email.Email;
import top.ibase4j.core.util.EmailUtil;







public class SendEmailListener
  implements MessageListener
{
  private final Logger logger = LogManager.getLogger();

  
  public void onMessage(Message message) {
    try {
      Email email = (Email)((ObjectMessage)message).getObject();
      this.logger.info("将发送邮件至：" + email.getSendTo());
      EmailUtil.sendEmail(email);
    } catch (JMSException e) {
      this.logger.error(e);
    } 
  }
}
