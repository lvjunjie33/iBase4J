package top.ibase4j.core.support.scheduler;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.InitializingBean;
import top.ibase4j.core.exception.BusinessException;
import top.ibase4j.core.util.DataUtil;












public class SchedulerManager
  implements InitializingBean
{
  private Logger logger = LogManager.getLogger();
  
  private Scheduler scheduler;
  
  private List<JobListener> jobListeners;

  
  public void setScheduler(Scheduler scheduler) { this.scheduler = scheduler; }


  
  public void setJobListeners(List<JobListener> jobListeners) { this.jobListeners = jobListeners; }


  
  public void afterPropertiesSet() throws SchedulerException {
    if (this.jobListeners != null && this.jobListeners.size() > 0) {
      this.logger.debug("Initing task scheduler[" + this.scheduler.getSchedulerName() + "] , add listener size ：" + this.jobListeners
          .size());
      for (JobListener listener : this.jobListeners) {
        this.logger.debug("Add JobListener : " + listener.getName());
        this.scheduler.getListenerManager().addJobListener(listener);
      } 
    } 
  }
  
  public List<TaskScheduled> getAllJobDetail() {
    List<TaskScheduled> result = new LinkedList<TaskScheduled>();
    try {
      GroupMatcher<JobKey> matcher = GroupMatcher.jobGroupContains("");
      Set<JobKey> jobKeys = this.scheduler.getJobKeys(matcher);
      for (JobKey jobKey : jobKeys) {
        JobDetail jobDetail = this.scheduler.getJobDetail(jobKey);
        List<? extends Trigger> triggers = this.scheduler.getTriggersOfJob(jobKey);
        for (Trigger trigger : triggers) {
          TaskScheduled job = new TaskScheduled();
          job.setTaskName(jobKey.getName());
          job.setTaskGroup(jobKey.getGroup());
          Trigger.TriggerState triggerState = this.scheduler.getTriggerState(trigger.getKey());
          job.setStatus(triggerState.name());
          if (trigger instanceof CronTrigger) {
            CronTrigger cronTrigger = (CronTrigger)trigger;
            String cronExpression = cronTrigger.getCronExpression();
            job.setTaskCron(cronExpression);
          } 
          job.setPreviousFireTime(trigger.getPreviousFireTime());
          job.setNextFireTime(trigger.getNextFireTime());
          JobDataMap jobDataMap = trigger.getJobDataMap();
          job.setTaskType(jobDataMap.getString("taskType"));
          job.setTargetSystem(jobDataMap.getString("targetSystem"));
          job.setTargetObject(jobDataMap.getString("targetObject"));
          job.setTargetMethod(jobDataMap.getString("targetMethod"));
          job.setContactName(jobDataMap.getString("contactName"));
          job.setContactEmail(jobDataMap.getString("contactEmail"));
          job.setTaskDesc(jobDetail.getDescription());
          String jobClass = jobDetail.getJobClass().getSimpleName();
          if ("StatefulJob".equals(jobClass)) {
            job.setJobType("statefulJob");
          } else if ("DefaultJob".equals(jobClass)) {
            job.setJobType("job");
          } 
          result.add(job);
        } 
      } 
    } catch (Exception e) {
      this.logger.error("Try to load All JobDetail cause error : ", e);
    } 
    return result;
  }
  
  public JobDetail getJobDetailByTriggerName(Trigger trigger) {
    try {
      return this.scheduler.getJobDetail(trigger.getJobKey());
    } catch (Exception e) {
      this.logger.error(e.getMessage(), e);
      
      return null;
    } 
  }




  
  public boolean updateTask(TaskScheduled taskScheduled) {
    String jobGroup = taskScheduled.getTaskGroup();
    if (DataUtil.isEmpty(jobGroup)) {
      jobGroup = "ds_job";
    }
    String jobName = taskScheduled.getTaskName();
    if (DataUtil.isEmpty(jobName)) {
      jobName = String.valueOf(System.currentTimeMillis());
    }
    String cronExpression = taskScheduled.getTaskCron();
    String targetObject = taskScheduled.getTargetObject();
    String targetMethod = taskScheduled.getTargetMethod();
    String jobDescription = taskScheduled.getTaskDesc();
    String jobType = taskScheduled.getJobType();
    String taskType = taskScheduled.getTaskType();
    JobDataMap jobDataMap = new JobDataMap();
    if ("DUBBO".equals(taskType)) {
      jobDataMap.put("targetSystem", taskScheduled.getTargetSystem());
    }
    jobDataMap.put("targetObject", targetObject);
    jobDataMap.put("targetMethod", targetMethod);
    jobDataMap.put("taskType", taskType);
    jobDataMap.put("contactName", taskScheduled.getContactName());
    jobDataMap.put("contactEmail", taskScheduled.getContactEmail());
    
    JobBuilder jobBuilder = null;
    if ("job".equals(jobType)) {
      jobBuilder = JobBuilder.newJob(top.ibase4j.core.support.scheduler.job.BaseJob.class);
    } else if ("statefulJob".equals(jobType)) {
      jobBuilder = JobBuilder.newJob(top.ibase4j.core.support.scheduler.job.StatefulJob.class);
    } 
    if (jobBuilder != null) {
      
      JobDetail jobDetail = jobBuilder.withIdentity(jobName, jobGroup).withDescription(jobDescription).storeDurably(true).usingJobData(jobDataMap).build();


      
      Trigger trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).withIdentity(jobName, jobGroup).withDescription(jobDescription).forJob(jobDetail).usingJobData(jobDataMap).build();
      
      try {
        JobDetail detail = this.scheduler.getJobDetail(new JobKey(jobName, jobGroup));
        if (detail == null) {
          this.scheduler.scheduleJob(jobDetail, trigger);
        } else {
          this.scheduler.addJob(jobDetail, true);
          this.scheduler.rescheduleJob(new TriggerKey(jobName, jobGroup), trigger);
        } 
        return true;
      } catch (SchedulerException e) {
        this.logger.error("SchedulerException", e);
        throw new BusinessException(e);
      } 
    } 
    return false;
  }



  
  public void pauseAllTrigger() {
    try {
      this.scheduler.standby();
    } catch (SchedulerException e) {
      this.logger.error("SchedulerException", e);
      throw new BusinessException(e);
    } 
  }



  
  public void startAllTrigger() {
    try {
      if (this.scheduler.isInStandbyMode()) {
        this.scheduler.start();
      }
    } catch (SchedulerException e) {
      this.logger.error("SchedulerException", e);
      throw new BusinessException(e);
    } 
  }

  
  public void stopJob(TaskScheduled scheduleJob) {
    try {
      TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getTaskName(), scheduleJob.getTaskGroup());
      this.scheduler.pauseTrigger(triggerKey);
    } catch (Exception e) {
      this.logger.error("Try to stop Job cause error : ", e);
      throw new BusinessException(e);
    } 
  }

  
  public void resumeJob(TaskScheduled scheduleJob) {
    try {
      TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getTaskName(), scheduleJob.getTaskGroup());
      this.scheduler.resumeTrigger(triggerKey);
    } catch (Exception e) {
      this.logger.error("Try to resume Job cause error : ", e);
      throw new BusinessException(e);
    } 
  }

  
  public void runJob(TaskScheduled scheduleJob) {
    try {
      JobKey jobKey = JobKey.jobKey(scheduleJob.getTaskName(), scheduleJob.getTaskGroup());
      this.scheduler.triggerJob(jobKey);
    } catch (Exception e) {
      this.logger.error("Try to resume Job cause error : ", e);
      throw new BusinessException(e);
    } 
  }

  
  public void delJob(TaskScheduled scheduleJob) {
    try {
      JobKey jobKey = JobKey.jobKey(scheduleJob.getTaskName(), scheduleJob.getTaskGroup());
      TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getTaskName(), scheduleJob.getTaskGroup());
      this.scheduler.pauseTrigger(triggerKey);
      this.scheduler.unscheduleJob(triggerKey);
      this.scheduler.deleteJob(jobKey);
    } catch (Exception e) {
      this.logger.error("Try to resume Job cause error : ", e);
      throw new BusinessException(e);
    } 
  }
}
