package top.ibase4j.core.util;

import top.ibase4j.core.support.email.Email;
import top.ibase4j.core.support.email.EmailSender;













public final class EmailUtil
{
  public static final boolean sendEmail(Email email) {
    EmailSender sender = new EmailSender(email.getHost(), email.getPort(), email.isSSL());
    if (!sender.setNamePass(email.getName(), email.getPassword(), email.getUserKey())) {
      return false;
    }
    if (!sender.setFrom(email.getFrom())) {
      return false;
    }
    if (!sender.setTo(email.getSendTo())) {
      return false;
    }
    if (email.getCopyTo() != null && !sender.setCopyTo(email.getCopyTo())) {
      return false;
    }
    if (!sender.setSubject(email.getTopic())) {
      return false;
    }
    if (!sender.setBody(email.getBody())) {
      return false;
    }
    if (email.getFileAffix() != null) {
      for (int i = 0; i < email.getFileAffix().length; i++) {
        if (!sender.addFileAffix(email.getFileAffix()[i])) {
          return false;
        }
      } 
    }
    
    return sender.sendout();
  }
}
