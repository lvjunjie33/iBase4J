package top.ibase4j.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;





@TableName("task_fire_log")
public class TaskFireLog
  implements Serializable
{
  @TableId(value = "id_", type = IdType.AUTO)
  private Long id;
  private String groupName;
  private String taskName;
  private Date startTime;
  private Date endTime;
  @TableField("status_")
  private String status;
  private String serverHost;
  private String serverDuid;
  private String fireInfo;
  
  public Long getId() { return this.id; }





  
  public void setId(Long id) { this.id = id; }





  
  public String getGroupName() { return this.groupName; }





  
  public void setGroupName(String groupName) { this.groupName = (groupName == null) ? null : groupName.trim(); }





  
  public String getTaskName() { return this.taskName; }





  
  public void setTaskName(String taskName) { this.taskName = (taskName == null) ? null : taskName.trim(); }





  
  public Date getStartTime() { return this.startTime; }





  
  public void setStartTime(Date startTime) { this.startTime = startTime; }





  
  public Date getEndTime() { return this.endTime; }





  
  public void setEndTime(Date endTime) { this.endTime = endTime; }





  
  public String getStatus() { return this.status; }





  
  public void setStatus(String status) { this.status = (status == null) ? null : status.trim(); }





  
  public String getServerHost() { return this.serverHost; }





  
  public void setServerHost(String serverHost) { this.serverHost = (serverHost == null) ? null : serverHost.trim(); }





  
  public String getServerDuid() { return this.serverDuid; }





  
  public void setServerDuid(String serverDuid) { this.serverDuid = (serverDuid == null) ? null : serverDuid.trim(); }





  
  public String getFireInfo() { return this.fireInfo; }





  
  public void setFireInfo(String fireInfo) { this.fireInfo = (fireInfo == null) ? null : fireInfo.trim(); }
}
