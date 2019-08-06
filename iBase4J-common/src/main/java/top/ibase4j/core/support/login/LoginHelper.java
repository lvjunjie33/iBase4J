package top.ibase4j.core.support.login;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import top.ibase4j.core.exception.LoginException;
import top.ibase4j.core.support.context.Resources;
import top.ibase4j.model.Login;









public final class LoginHelper
{
  public static final Boolean login(Login user, String host) {
    UsernamePasswordToken token = new UsernamePasswordToken(user.getAccount(), user.getPassword(), host);
    token.setRememberMe(user.getRememberMe().booleanValue());
    Subject subject = SecurityUtils.getSubject();
    try {
      subject.login(token);
      return Boolean.valueOf(subject.isAuthenticated());
    } catch (LockedAccountException e) {
      throw new LoginException(Resources.getMessage("ACCOUNT_LOCKED", new Object[] { token.getPrincipal() }));
    } catch (DisabledAccountException e) {
      throw new LoginException(Resources.getMessage("ACCOUNT_DISABLED", new Object[] { token.getPrincipal() }));
    } catch (ExpiredCredentialsException e) {
      throw new LoginException(Resources.getMessage("ACCOUNT_EXPIRED", new Object[] { token.getPrincipal() }));
    } catch (Exception e) {
      throw new LoginException(Resources.getMessage("LOGIN_FAIL", new Object[0]), e);
    } 
  }
}
