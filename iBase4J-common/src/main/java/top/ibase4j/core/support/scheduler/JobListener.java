package top.ibase4j.core.support.scheduler;

import com.alibaba.fastjson.JSON;
import java.sql.Timestamp;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import top.ibase4j.core.support.email.Email;
import top.ibase4j.core.support.mq.QueueSender;
import top.ibase4j.core.util.EmailUtil;
import top.ibase4j.core.util.NativeUtil;
import top.ibase4j.model.TaskFireLog;









public class JobListener {
  private static Logger logger = LogManager.getLogger(JobListener.class);
  
  @Lazy
  @Autowired
  private SchedulerServiceImpl schedulerService;
  private QueueSender emailQueueSender;
  private static ExecutorService executorService = new ThreadPoolExecutor(2, 20, 5L, TimeUnit.SECONDS, new ArrayBlockingQueue(20), new ThreadPoolExecutor.DiscardOldestPolicy());
  
  private static String JOB_LOG = "jobLog";

  
  public void setEmailQueueSender(QueueSender emailQueueSender) { this.emailQueueSender = emailQueueSender; }



  
  public String getName() { return "taskListener"; }



  
  public void jobExecutionVetoed(JobExecutionContext context) {}


  
  public void jobToBeExecuted(JobExecutionContext context) {
    JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
    String targetObject = jobDataMap.getString("targetObject");
    String targetMethod = jobDataMap.getString("targetMethod");
    if (logger.isInfoEnabled()) {
      logger.info("定时任务开始执行：{}.{}", targetObject, targetMethod);
    }
    
    TaskFireLog log = new TaskFireLog();
    log.setStartTime(context.getFireTime());
    log.setGroupName(targetObject);
    log.setTaskName(targetMethod);
    log.setStatus("I");
    log.setServerHost(NativeUtil.getHostName());
    log.setServerDuid(NativeUtil.getDUID());
    this.schedulerService.updateLog(log);
    jobDataMap.put(JOB_LOG, log);
  }


  
  public void jobWasExecuted(JobExecutionContext context, JobExecutionException exp) {
    Timestamp end = new Timestamp(System.currentTimeMillis());
    JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
    String targetObject = jobDataMap.getString("targetObject");
    String targetMethod = jobDataMap.getString("targetMethod");
    if (logger.isInfoEnabled()) {
      logger.info("定时任务执行结束：{}.{}", targetObject, targetMethod);
    }
    
    final TaskFireLog log = (TaskFireLog)jobDataMap.get(JOB_LOG);
    if (log != null) {
      log.setEndTime(end);
      if (exp != null) {
        logger.error("定时任务失败: [" + targetObject + "." + targetMethod + "]", exp);
        String contactEmail = jobDataMap.getString("contactEmail");
        if (StringUtils.isNotBlank(contactEmail)) {
          String topic = String.format("调度[%s.%s]发生异常", new Object[] { targetMethod, targetMethod });
          sendEmail(new Email(contactEmail, topic, exp.getMessage()));
        } 
        log.setStatus("E");
        log.setFireInfo(exp.getMessage());
      }
      else if (log.getStatus().equals("I")) {
        log.setStatus("S");
      } 
    } 
    
    executorService.submit(new Runnable()
        {
          public void run() {
            if (log != null) {
              try {
                JobListener.this.schedulerService.updateLog(log);
              } catch (Exception e) {
                logger.error("Update TaskRunLog cause error. The log object is : " + JSON.toJSONString(log), e);
              } 
            }
          }
        });
  }
  
  private void sendEmail(final Email email) {
    executorService.submit(new Runnable()
        {
          public void run() {
            if (JobListener.this.emailQueueSender != null) {
              JobListener.this.emailQueueSender.send("iBase4J.emailSender", email);
            } else {
              logger.info("将发送邮件至：" + email.getSendTo());
              EmailUtil.sendEmail(email);
            } 
          }
        });
  }
}
