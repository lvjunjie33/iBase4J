package top.ibase4j.core.support.mq;

import java.io.Serializable;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;











public class TopicSender
{
  @Autowired
  @Qualifier("jmsTopicTemplate")
  private JmsTemplate jmsTemplate;
  
  public void send(String topicName, final Serializable message) {
    this.jmsTemplate.send(topicName, new MessageCreator()
        {
          public Message createMessage(Session session) throws JMSException {
            return session.createObjectMessage(message);
          }
        });
  }
}
