package top.ibase4j.core.support.file.ftp;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.ibase4j.core.exception.FtpException;
import top.ibase4j.core.util.PropertiesUtil;








public class SftpClient
{
  private Logger logger = LogManager.getLogger();
  private Session session = null;
  private ChannelSftp channel = null;




  
  public static SftpClient connect() { return (new SftpClient()).init(); }

  
  public SftpClient init() {
    try {
      String host = PropertiesUtil.getString("sftp.host");
      int port = PropertiesUtil.getInt("sftp.port").intValue();
      String userName = PropertiesUtil.getString("sftp.user.name");
      String password = PropertiesUtil.getString("sftp.user.password");
      Integer timeout = PropertiesUtil.getInt("sftp.timeout");
      Integer aliveMax = PropertiesUtil.getInt("sftp.aliveMax");
      JSch jsch = new JSch();
      this.session = jsch.getSession(userName, host, port);
      if (password != null) {
        this.session.setPassword(password);
      }
      this.session.setConfig("StrictHostKeyChecking", "no");
      if (timeout != null) {
        this.session.setTimeout(timeout.intValue());
      }
      if (aliveMax != null) {
        this.session.setServerAliveCountMax(aliveMax.intValue());
      }
      this.session.connect();
      this.channel = (ChannelSftp)this.session.openChannel("sftp");
      this.channel.connect();
      this.logger.info("SSH Channel connected.");
    } catch (JSchException e) {
      throw new FtpException("", e);
    } 
    return this;
  }
  
  public void disconnect() {
    if (this.channel != null) {
      this.channel.disconnect();
    }
    if (this.session != null) {
      this.session.disconnect();
      this.logger.info("SSH Channel disconnected.");
    } 
  }

  
  public void put(String src, String dst) {
    try {
      this.channel.put(src, dst, new FileProgressMonitor());
    } catch (SftpException e) {
      throw new FtpException("", e);
    } 
  }

  
  public void get(String src, String dst) {
    try {
      this.channel.get(src, dst, new FileProgressMonitor());
    } catch (SftpException e) {
      throw new FtpException("", e);
    } 
  }
}
