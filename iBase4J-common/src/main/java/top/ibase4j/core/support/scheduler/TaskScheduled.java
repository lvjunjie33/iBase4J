package top.ibase4j.core.support.scheduler;

import java.util.Date;
import top.ibase4j.core.base.BaseModel;



public class TaskScheduled
  extends BaseModel
{
  private String taskName;
  private String taskGroup;
  private String status;
  private String taskCron;
  private Date previousFireTime;
  private Date nextFireTime;
  private String taskDesc;
  private String jobType;
  private String taskType;
  private String targetSystem;
  private String targetObject;
  private String targetMethod;
  private String contactName;
  private String contactEmail;
  
  public TaskScheduled() {}
  
  public TaskScheduled(String taskGroup, String taskName) {
    this.taskGroup = taskGroup;
    this.taskName = taskName;
  }





























  
  public String getTaskName() { return this.taskName; }


  
  public void setTaskName(String taskName) { this.taskName = taskName; }


  
  public String getTaskGroup() { return this.taskGroup; }


  
  public void setTaskGroup(String taskGroup) { this.taskGroup = taskGroup; }


  
  public String getStatus() { return this.status; }


  
  public void setStatus(String status) { this.status = status; }


  
  public String getTaskCron() { return this.taskCron; }


  
  public void setTaskCron(String taskCron) { this.taskCron = taskCron; }


  
  public Date getPreviousFireTime() { return this.previousFireTime; }


  
  public void setPreviousFireTime(Date previousFireTime) { this.previousFireTime = previousFireTime; }


  
  public Date getNextFireTime() { return this.nextFireTime; }


  
  public void setNextFireTime(Date nextFireTime) { this.nextFireTime = nextFireTime; }


  
  public String getTaskDesc() { return this.taskDesc; }


  
  public void setTaskDesc(String taskDesc) { this.taskDesc = taskDesc; }





  
  public String getJobType() { return this.jobType; }






  
  public void setJobType(String jobType) { this.jobType = jobType; }





  
  public String getTaskType() { return this.taskType; }






  
  public void setTaskType(String taskType) { this.taskType = taskType; }





  
  public String getTargetSystem() { return this.targetSystem; }






  
  public void setTargetSystem(String targetSystem) { this.targetSystem = targetSystem; }





  
  public String getTargetObject() { return this.targetObject; }






  
  public void setTargetObject(String targetObject) { this.targetObject = targetObject; }





  
  public String getTargetMethod() { return this.targetMethod; }






  
  public void setTargetMethod(String targetMethod) { this.targetMethod = targetMethod; }


  
  public String getContactName() { return this.contactName; }


  
  public void setContactName(String contactName) { this.contactName = contactName; }





  
  public String getContactEmail() { return this.contactEmail; }






  
  public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }
  
  public static interface TaskType {
    public static final String local = "LOCAL";
    public static final String dubbo = "DUBBO";
  }
  
  public static interface JobType {
    public static final String job = "job";
    public static final String statefulJob = "statefulJob";
  }
}
