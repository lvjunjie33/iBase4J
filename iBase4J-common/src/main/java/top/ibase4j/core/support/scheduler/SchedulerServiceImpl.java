package top.ibase4j.core.support.scheduler;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.support.Pagination;
import top.ibase4j.core.util.InstanceUtil;
import top.ibase4j.core.util.PageUtil;
import top.ibase4j.mapper.TaskFireLogMapper;
import top.ibase4j.model.TaskFireLog;








public class SchedulerServiceImpl
  implements ApplicationContextAware
{
  @Autowired
  private TaskFireLogMapper logMapper;
  @Lazy
  @Autowired
  private SchedulerManager schedulerManager;
  protected ApplicationContext applicationContext;
  
  public void setApplicationContext(ApplicationContext applicationContext) { this.applicationContext = applicationContext; }



  
  public List<TaskScheduled> getAllTaskDetail() { return this.schedulerManager.getAllJobDetail(); }



  
  public void execTask(TaskScheduled taskScheduler) { this.schedulerManager.runJob(taskScheduler); }



  
  public void openTask(TaskScheduled taskScheduled) { this.schedulerManager.resumeJob(taskScheduled); }



  
  public void closeTask(TaskScheduled taskScheduled) { this.schedulerManager.stopJob(taskScheduled); }



  
  public void delTask(TaskScheduled taskScheduled) { this.schedulerManager.delJob(taskScheduled); }



  
  public void updateTask(TaskScheduled taskScheduled) { this.schedulerManager.updateTask(taskScheduled); }


  
  @Cacheable({"taskFireLog"})
  public TaskFireLog getFireLogById(Long id) { return (TaskFireLog)this.logMapper.selectById(id); }

  
  @Transactional(rollbackFor = {Exception.class})
  @CachePut({"taskFireLog"})
  public TaskFireLog updateLog(TaskFireLog record) {
    if (record.getId() == null) {
      this.logMapper.insert(record);
    } else {
      this.logMapper.updateById(record);
    } 
    return record;
  }
  
  public Pagination<TaskFireLog> queryLog(Map<String, Object> params) {
    Page<Long> ids = PageUtil.getPage(params);
    ids.setRecords(this.logMapper.selectIdByMap(ids, params));
    Pagination<TaskFireLog> page = new Pagination<TaskFireLog>(ids.getCurrent(), ids.getSize());
    page.setTotal(ids.getTotal());
    if (ids != null) {
      List<TaskFireLog> records = InstanceUtil.newArrayList();
      for (Long id : ids.getRecords()) {
        records.add(((SchedulerServiceImpl)this.applicationContext.getBean(getClass())).getFireLogById(id));
      }
      page.setRecords(records);
    } 
    return page;
  }
}
