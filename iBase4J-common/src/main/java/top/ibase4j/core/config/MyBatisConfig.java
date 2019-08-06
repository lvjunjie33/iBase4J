package top.ibase4j.core.config;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import java.io.IOException;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import top.ibase4j.core.util.CacheUtil;
import top.ibase4j.core.util.DataUtil;
import top.ibase4j.core.util.PropertiesUtil;
import top.ibase4j.mapper.LockMapper;










@Configuration
@ConditionalOnClass({MapperScannerConfigurer.class, DataSourceTransactionManager.class})
@EnableTransactionManagement(proxyTargetClass = true)
@EnableScheduling
public class MyBatisConfig
{
  private final Logger logger = LogManager.getLogger();
  
  private LockMapper lockMapper;
  
  private Properties config;
  
  @Autowired
  private Environment env;
  
  private String get(String key) throws IOException {
    String value = PropertiesUtil.getString(key);
    if (DataUtil.isEmpty(value)) {
      if (this.config == null) {
        String profile = "dev";
        if (this.env.getActiveProfiles() != null && this.env.getActiveProfiles().length > 0) {
          profile = this.env.getActiveProfiles()[0];
        }
        this.config = PropertiesLoaderUtils.loadAllProperties("config/" + profile + "/jdbc.properties");
      } 
      value = this.config.getProperty(key);
    } 
    return value;
  }



  
  @Bean(name = {"sqlSessionFactory"})
  @ConditionalOnBean({DataSource.class})
  public MybatisSqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) throws Exception {
    MybatisSqlSessionFactoryBean sessionFactory = new MybatisSqlSessionFactoryBean();
    sessionFactory.setDataSource(dataSource);
    
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    sessionFactory.setMapperLocations(resolver.getResources(get("mybatis.mapperLocations")));
    sessionFactory.setTypeAliasesPackage(get("mybatis.typeAliasesPackage"));
    
    PaginationInterceptor page = new PaginationInterceptor();
    page.setDialectType(get("mybatis.dialectType"));
    sessionFactory.setPlugins(new Interceptor[] { page });
    
    MybatisConfiguration configuration = new MybatisConfiguration();
    configuration.setLogImpl(org.apache.ibatis.logging.slf4j.Slf4jImpl.class);
    configuration.setCallSettersOnNulls(true);
    sessionFactory.setConfiguration(configuration);
    
    String idType = get("mybatis.idType");
    GlobalConfig config = new GlobalConfig();
    config.setDbConfig(new GlobalConfig.DbConfig());
    if (DataUtil.isEmpty(idType)) {
      config.getDbConfig().setIdType(IdType.AUTO);
    } else {
      config.getDbConfig().setIdType(IdType.valueOf(idType));
    } 
    sessionFactory.setGlobalConfig(config);
    return sessionFactory;
  }
  
  @Bean
  public MapperScannerConfigurer configurer() throws IOException {
    MapperScannerConfigurer configurer = new MapperScannerConfigurer();
    configurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
    configurer.setBasePackage(get("mybatis.mapperBasePackage"));
    return configurer;
  }

  
  @Bean
  @ConditionalOnBean({DataSource.class})
  public DataSourceTransactionManager transactionManager(DataSource dataSource) { return new DataSourceTransactionManager(dataSource); }

  
  @Bean
  public Object setLockMapper(LockMapper lockMapper) {
    this.lockMapper = lockMapper;
    CacheUtil.setLockMapper(lockMapper);
    return lockMapper;
  }

  
  @Scheduled(cron = "0 0/1 * * * *")
  public void cleanExpiredLock() {
    if (this.lockMapper != null) {
      this.logger.info("cleanExpiredLock");
      this.lockMapper.cleanExpiredLock();
    } 
  }
}
