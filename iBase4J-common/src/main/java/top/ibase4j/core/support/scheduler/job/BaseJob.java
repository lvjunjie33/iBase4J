package top.ibase4j.core.support.scheduler.job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import top.ibase4j.core.base.provider.BaseProvider;
import top.ibase4j.core.base.provider.Parameter;
import top.ibase4j.core.support.context.ApplicationContextHolder;
import top.ibase4j.core.support.generator.Sequence;
import top.ibase4j.core.util.CacheUtil;
import top.ibase4j.core.util.DataUtil;
import top.ibase4j.core.util.MathUtil;










public class BaseJob implements Job{
  private final Logger logger = LogManager.getLogger();

  
  public void execute(JobExecutionContext context) throws JobExecutionException {
    long start = System.currentTimeMillis();
    JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
    String taskType = jobDataMap.getString("taskType");
    String targetObject = jobDataMap.getString("targetObject");
    String targetMethod = jobDataMap.getString("targetMethod");
    String key = targetMethod + "." + targetObject;
    try {
      this.logger.info("定时任务[{}.{}]开始", targetObject, targetMethod);
      String requestId = Sequence.next().toString();
      if (CacheUtil.getCache().lock(key, requestId, 18000L)) {
        try {
          if ("LOCAL".equals(taskType)) {
            Object refer = ApplicationContextHolder.getBean(targetObject);
            refer.getClass().getDeclaredMethod(targetMethod, new Class[0]).invoke(refer, new Object[0]);
          } else if ("DUBBO".equals(taskType)) {
            String targetSystem = jobDataMap.getString("targetSystem");
            if (DataUtil.isEmpty(targetSystem) || !targetSystem.endsWith("Provider")) {
              Object refer = ApplicationContextHolder.getBean(targetObject);
              refer.getClass().getDeclaredMethod(targetMethod, new Class[0]).invoke(refer, new Object[0]);
            } else {
              
              BaseProvider provider = (BaseProvider)ApplicationContextHolder.getBean(targetSystem);
              provider.execute(new Parameter(targetObject, targetMethod));
            } 
          } else {
            Object refer = ApplicationContextHolder.getBean(targetObject);
            refer.getClass().getDeclaredMethod(targetMethod, new Class[0]).invoke(refer, new Object[0]);
          } 
          Double time = Double.valueOf((System.currentTimeMillis() - start) / 1000.0D);
          this.logger.info("定时任务[{}.{}]用时：{}s", targetObject, targetMethod, time.toString());
        } finally {
          unLock(key, requestId);
        } 
      }
    } catch (Exception e) {
      throw new JobExecutionException(e);
    } 
  }
  
  private void unLock(String key, String requestId) {
    try {
      CacheUtil.getCache().unlock(key, requestId);
    } catch (Exception e) {
      this.logger.error("", e);
      try {
        Thread.sleep(MathUtil.getRandom(100.0D, 2000.0D).longValue());
      } catch (Exception e2) {
        this.logger.error("", e2);
      } 
      unLock(key, requestId);
    } 
  }
}
