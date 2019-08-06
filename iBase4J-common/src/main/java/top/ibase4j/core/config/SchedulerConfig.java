package top.ibase4j.core.config;

import java.util.List;

import javax.sql.DataSource;


import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import top.ibase4j.core.support.scheduler.JobListener;
import top.ibase4j.core.support.scheduler.SchedulerManager;
import top.ibase4j.core.util.InstanceUtil;






@Configuration
@ConditionalOnClass({JobListener.class})
public class SchedulerConfig
{
  @Autowired
  Environment env;
  
//  @Bean
//  public SchedulerFactoryBean schedulerFactory(DataSource dataSource) {
//    SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
//    schedulerFactory.setSchedulerName("iBase4J-Scheduler");
//    schedulerFactory.setApplicationContextSchedulerContextKey("applicationContext");
//    schedulerFactory.setDataSource(dataSource);
//    Resource resouce = (new DefaultResourceLoader()).getResource("classpath:quartz.properties");
//    schedulerFactory.setConfigLocation(resouce);
//    return schedulerFactory;
//  }
  
  @Bean
  public SchedulerManager scheduler(Scheduler scheduler, JobListener jobListener) {
    SchedulerManager schedulerManager = new SchedulerManager();
    schedulerManager.setScheduler(scheduler);
    List<JobListener> jobListeners = InstanceUtil.newArrayList();
    jobListeners.add(jobListener);
//    schedulerManager.setJobListeners(jobListeners);
    return schedulerManager;
  }

  
  @Bean
  public JobListener jobListener() { 
	  return new JobListener(); 
	  }
}
