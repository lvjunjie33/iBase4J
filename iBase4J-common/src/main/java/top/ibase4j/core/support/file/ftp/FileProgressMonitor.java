package top.ibase4j.core.support.file.ftp;

import com.jcraft.jsch.SftpProgressMonitor;
import java.text.DecimalFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;







public class FileProgressMonitor
  implements SftpProgressMonitor
{
  private final Logger logger = LogManager.getLogger();
  private long transfered;
  private long fileSize;
  private int minInterval = 100;
  private long start;
  private DecimalFormat df = new DecimalFormat("#.##");
  
  private long preTime;

  
  public void init(int op, String src, String dest, long max) {
    this.fileSize = max;
    this.logger.info("Transferring begin.");
    this.start = System.currentTimeMillis();
  }


  
  public boolean count(long count) {
    if (this.fileSize != 0L && this.transfered == 0L) {
      this.logger.info("Transferring progress message: {}%", this.df.format(0L));
      this.preTime = System.currentTimeMillis();
    } 
    this.transfered += count;
    if (this.fileSize != 0L) {
      long interval = System.currentTimeMillis() - this.preTime;
      if (this.transfered == this.fileSize || interval > this.minInterval) {
        this.preTime = System.currentTimeMillis();
        double d = this.transfered * 100.0D / this.fileSize;
        this.logger.info("Transferring progress message: {}%", this.df.format(d));
      } 
    } else {
      this.logger.info("Transferring progress message: " + this.transfered);
    } 
    return true;
  }



  
  public void end() { this.logger.info("Transferring end. used time: {}ms", String.valueOf(System.currentTimeMillis() - this.start)); }
}
