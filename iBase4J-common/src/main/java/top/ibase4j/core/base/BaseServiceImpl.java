package top.ibase4j.core.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.stream.IntStream;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.exception.BusinessException;
import top.ibase4j.core.exception.ValidateException;
import top.ibase4j.core.support.Pagination;
import top.ibase4j.core.support.cache.CacheKey;
import top.ibase4j.core.support.dbcp.HandleDataSource;
import top.ibase4j.core.support.generator.Sequence;
import top.ibase4j.core.util.CacheUtil;
import top.ibase4j.core.util.DataUtil;
import top.ibase4j.core.util.ExceptionUtil;
import top.ibase4j.core.util.InstanceUtil;
import top.ibase4j.core.util.PageUtil;
import top.ibase4j.core.util.ThreadUtil;










public class BaseServiceImpl<T extends BaseModel, M extends BaseMapper<T>>
  extends Object
  implements BaseService<T>, InitializingBean
{
  protected Logger logger = LogManager.getLogger();
  @Autowired
  protected M mapper;
  private static ExecutorService executorService = ThreadUtil.threadPool(1, 100, 30);
  
  @Transactional(rollbackFor = {Exception.class})
  public void del(List<Long> ids, Long userId) { ids.forEach(id -> del(id, userId)); }



  
  @Transactional(rollbackFor = {Exception.class})
  public void del(Long id, Long userId) {
    try {
      T record = (T)getById(id);
      record.setEnable(Integer.valueOf(0));
      record.setUpdateTime(new Date());
      record.setUpdateBy(userId);
//      this.mapper.updateById(record);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e);
    } 
  }


  
  @Transactional(rollbackFor = {Exception.class})
  public void delete(Long id) {
    try {
//      this.mapper.deleteById(id);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e);
    } 
  }


  
  @Transactional(rollbackFor = {Exception.class})
  public Integer deleteByEntity(T t) {
    UpdateWrapper updateWrapper = new UpdateWrapper(t);
//    return Integer.valueOf(this.mapper.delete(updateWrapper));
	return null;
  }



  
  @Transactional(rollbackFor = {Exception.class})
  public Integer deleteByMap(Map<String, Object> columnMap) {
	return null; 
	  
//	  return Integer.valueOf(this.mapper.deleteByMap(columnMap)); 
	  }


  
  public Pagination<Map<String, Object>> getPageMap(Page<Long> ids) {
    if (ids != null) {
      
      Pagination<Map<String, Object>> page = new Pagination<Map<String, Object>>(ids.getCurrent(), ids.getSize());
      page.setTotal(ids.getTotal());
      List<Map<String, Object>> records = InstanceUtil.newArrayList();
      String datasource = HandleDataSource.getDataSource();
      IntStream.range(0, ids.getRecords().size()).forEach(i -> records.add(null));
      IntStream.range(0, ids.getRecords().size()).parallel().forEach(i -> {
            HandleDataSource.putDataSource(datasource);
            records.set(i, InstanceUtil.transBean2Map(getById((Long)ids.getRecords().get(i))));
          });
      page.setRecords(records);
      return page;
    } 
    return new Pagination();
  }


  
  public Pagination<T> query(Map<String, Object> params) {
    Page<Long> page = PageUtil.getPage(params);
    page.setRecords(this.mapper.selectIdPage(page, params));
    return getPage(page);
  }


  
  public Pagination<T> query(T entity, Pagination<T> rowBounds) {
    Page<Long> page = new Page<Long>();
    try {
      PropertyUtils.copyProperties(page, rowBounds);
    } catch (Exception e) {
      this.logger.error(ExceptionUtil.getStackTraceAsString(e));
    } 
    List<Long> ids = this.mapper.selectIdPage(page, entity);
    page.setRecords(ids);
    return getPage(page);
  }


  
  public Pagination<T> queryFromDB(T entity, Pagination<T> rowBounds) {
    Page<T> page = new Page<T>();
    try {
      PropertyUtils.copyProperties(page, rowBounds);
    } catch (Exception e) {
      this.logger.error(ExceptionUtil.getStackTraceAsString(e));
    } 
    QueryWrapper queryWrapper = new QueryWrapper(entity);
//    IPage<T> iPage = this.mapper.selectPage(page, queryWrapper);
    Pagination<T> pager = new Pagination<T>(page.getCurrent(), page.getSize());
//    pager.setRecords(iPage.getRecords());
    pager.setTotal(page.getTotal());
    return pager;
  }

  
  public Pagination<T> queryFromDB(Map<String, Object> params) {
    Page<Long> page = PageUtil.getPage(params);
    List<T> list = this.mapper.selectPage(page, params);
    Pagination<T> pager = new Pagination<T>(page.getCurrent(), page.getSize());
    pager.setRecords(list);
    pager.setTotal(page.getTotal());
    return pager;
  }


  
//  public List<T> queryListFromDB(Map<String, Object> params) { return this.mapper.selectByMap(params); }


  
//  public List<T> queryListFromDB(T entity) {
//    QueryWrapper queryWrapper = new QueryWrapper(entity);
//    return this.mapper.selectList(queryWrapper);
//  }

  
//  public Integer count(T entity) {
//    QueryWrapper queryWrapper = new QueryWrapper(entity);
//    return this.mapper.selectCount(queryWrapper);
//  }


  
  public Integer count(Map<String, Object> params) { return this.mapper.selectCount(params); }




  
  public T queryById(Long id) {
	return null; 
//	  return (T)queryById(id, 1); 
	  }


  
  public List<T> queryList(Map<String, Object> params) {
    if (DataUtil.isEmpty(params.get("orderBy"))) {
      params.put("orderBy", "id_");
    }
    List<Long> ids = this.mapper.selectIdPage(params);
    return queryList(ids);
  }


  
  public List<T> queryList(T params) {
    List<Long> ids = this.mapper.selectIdPage(params);
    return queryList(ids);
  }



  
  public List<T> queryList(List<Long> ids) {
    List<T> list = InstanceUtil.newArrayList();
    if (ids != null) {
      String datasource = HandleDataSource.getDataSource();
      IntStream.range(0, ids.size()).forEach(i -> list.add(null));
      IntStream.range(0, ids.size()).parallel().forEach(i -> {
            HandleDataSource.putDataSource(datasource);
            list.set(i, getById((Long)ids.get(i)));
          });
    } 
    return list;
  }


  
  public <K> List<K> queryList(List<Long> ids, Class<K> cls) {
    List<K> list = InstanceUtil.newArrayList();
    if (ids != null) {
      String datasource = HandleDataSource.getDataSource();
      IntStream.range(0, ids.size()).forEach(i -> list.add(null));
      IntStream.range(0, ids.size()).parallel().forEach(i -> {
            HandleDataSource.putDataSource(datasource);
            T t = (T)getById((Long)ids.get(i));
            K k = (K)InstanceUtil.to(t, cls);
            list.set(i, k);
          });
    } 
    return list;
  }

  
//  public T selectOne(T entity) {
//    QueryWrapper queryWrapper = new QueryWrapper(entity);
//    return (T)(BaseModel)this.mapper.selectOne(queryWrapper);
//  }



  
//  @Transactional(rollbackFor = {Exception.class})
//  public T update(T record) {
//    try {
//      record.setUpdateTime(new Date());
//      if (record.getId() == null) {
//        record.setCreateTime(new Date());
//        this.mapper.insert(record);
//        record = (T)(BaseModel)this.mapper.selectById(record.getId());
//        saveCache(record);
//      } else {
//        requestId = Sequence.next().toString();
//        lockKey = getLockKey(record.getId());
//        if (CacheUtil.getLock(lockKey, "更新", requestId)) {
//          try {
//            this.mapper.updateById(record);
//            record = (T)(BaseModel)this.mapper.selectById(record.getId());
//            saveCache(record);
//          } finally {
//            CacheUtil.unLock(lockKey, requestId);
//          } 
//        } else {
//          throw new RuntimeException("数据不一致!请刷新页面重新编辑!");
//        } 
//      } 
//    } catch (DuplicateKeyException e) {
//      this.logger.error(ExceptionUtil.getStackTraceAsString(e));
//      throw new BusinessException("已经存在相同的记录.");
//    } catch (Exception e) {
//      this.logger.error(ExceptionUtil.getStackTraceAsString(e));
//      throw new RuntimeException(ExceptionUtil.getStackTraceAsString(e));
//    } 
//    return record;
//  }



  
  public boolean updateBatch(List<T> entityList) { return updateBatch(entityList, 30); }



  
  protected Class<T> currentModelClass() { return ReflectionKit.getSuperClassGenericType(getClass(), 0); }







  
  protected String getLockKey(Object id) {
    CacheKey cacheKey = CacheKey.getInstance(getClass());
    StringBuilder sb = new StringBuilder();
    if (cacheKey == null) {
      sb.append(getClass().getName());
    } else {
      sb.append(cacheKey.getValue());
    } 
    return sb.append(":LOCK:").append(id).toString();
  }





  
  protected <P> Pagination<P> query(Map<String, Object> params, Class<P> cls) {
    Page<Long> page = PageUtil.getPage(params);
    page.setRecords(this.mapper.selectIdPage(page, params));
    return getPage(page, cls);
  }






  
  protected SqlSession sqlSessionBatch() { return SqlHelper.sqlSessionBatch(currentModelClass()); }








  
  protected String sqlStatement(SqlMethod sqlMethod) { return SqlHelper.table(currentModelClass()).getSqlStatement(sqlMethod.getMethod()); }



  
  private T getById(Long id) {
	return null; 
//	  return (T)queryById(id, 1); 
	  }


  
  protected Pagination<T> getPage(Page<Long> ids) {
    if (ids != null) {
      Pagination<T> page = new Pagination<T>(ids.getCurrent(), ids.getSize());
      page.setTotal(ids.getTotal());
      List<T> records = InstanceUtil.newArrayList();
      String datasource = HandleDataSource.getDataSource();
      IntStream.range(0, ids.getRecords().size()).forEach(i -> records.add(null));
      IntStream.range(0, ids.getRecords().size()).parallel().forEach(i -> {
            HandleDataSource.putDataSource(datasource);
            records.set(i, getById((Long)ids.getRecords().get(i)));
          });
      page.setRecords(records);
      return page;
    } 
    return new Pagination();
  }

  
  private <K> Pagination<K> getPage(Page<Long> ids, Class<K> cls) {
    if (ids != null) {
      Pagination<K> page = new Pagination<K>(ids.getCurrent(), ids.getSize());
      page.setTotal(ids.getTotal());
      List<K> records = InstanceUtil.newArrayList();
      String datasource = HandleDataSource.getDataSource();
      IntStream.range(0, ids.getRecords().size()).forEach(i -> records.add(null));
      IntStream.range(0, ids.getRecords().size()).parallel().forEach(i -> {
            HandleDataSource.putDataSource(datasource);
            records.set(i, InstanceUtil.to(getById((Long)ids.getRecords().get(i)), cls));
          });
      page.setRecords(records);
      return page;
    } 
    return new Pagination();
  }

  
  private void saveCache(final String key, T record, int timeOut) {
    if (key != null && record != null) {
      try {
        CacheUtil.getCache().set(key, record, timeOut);
      } catch (Exception e) {
        this.logger.error(ExceptionUtil.getStackTraceAsString(e));
        executorService.execute(new Runnable()
            {
              public void run() {
                BaseServiceImpl.this.deleteCache(key, 1);
              }
            });
      } 
    }
  }

  
  private void saveCache(T record) {
    if (record != null) {
      CacheKey key = CacheKey.getInstance(getClass());
      if (key != null) {
        saveCache(key.getValue() + ":" + record.getId(), record, key.getTimeToLive());
      }
    } 
  }
  
  private void deleteCache(String key, int times) {
    try {
      CacheUtil.getCache().del(key);
    } catch (Exception e) {
      this.logger.error(ExceptionUtil.getStackTraceAsString(e));
      ThreadUtil.sleep(10, Math.min(2147483647, times * 100));
      deleteCache(key, times + 1);
    } 
  }






@Override
public T update(T paramT) throws BusinessException, ValidateException {
	// TODO Auto-generated method stub
	return null;
}






@Override
public List<T> queryListFromDB(Map<String, Object> paramMap) {
	// TODO Auto-generated method stub
	return null;
}






@Override
public List<T> queryListFromDB(T paramT) {
	// TODO Auto-generated method stub
	return null;
}






@Override
public T selectOne(T paramT) throws BusinessException, ValidateException {
	// TODO Auto-generated method stub
	return null;
}






@Override
public Integer count(T paramT) throws BusinessException, ValidateException {
	// TODO Auto-generated method stub
	return null;
}






@Override
public boolean updateBatch(List<T> paramList, int paramInt) throws BusinessException, ValidateException {
	// TODO Auto-generated method stub
	return false;
}




@Override
public void afterPropertiesSet() throws Exception {
	// TODO Auto-generated method stub
	
}

  
//  private T queryById(Long id, int times) {
//    CacheKey key = CacheKey.getInstance(getClass());
//    T record = null;
//    if (key != null) {
//      try {
//        record = (T)(BaseModel)CacheUtil.getCache().get(key.getValue() + ":" + id, Integer.valueOf(key.getTimeToLive()));
//      } catch (Exception e) {
//        this.logger.error(ExceptionUtil.getStackTraceAsString(e));
//      } 
//      if (record == null) {
//        try {
//          record = (T)(BaseModel)CacheUtil.getCache().get(key.getValue() + ":R:" + id, Integer.valueOf(key.getTimeToLive()));
//        } catch (Exception e) {
//          this.logger.error(ExceptionUtil.getStackTraceAsString(e));
//        } 
//      }
//    } 
//    if (record == null) {
//      lockKey = getLockKey("R:" + id);
//      requestId = Sequence.next().toString();
//      if (CacheUtil.getLock(lockKey, "根据ID查询数据", requestId)) {
//        try {
//          record = (T)(BaseModel)this.mapper.selectById(id);
//          saveCache(key.getValue() + ":R:" + id, record, key.getTimeToLive());
//        } finally {
//          CacheUtil.unLock(lockKey, requestId);
//        }
//      
//      } else if (times > 5) {
//        record = (T)(BaseModel)this.mapper.selectById(id);
//      } else {
//        this.logger.info(getClass().getSimpleName() + ":" + id + " retry getById.");
//        ThreadUtil.sleep(1, 20);
//        return (T)queryById(id, times + 1);
//      } 
//    } 
//    
//    return record;
//  }


  
//  public boolean updateBatch(List<T> entityList, int batchSize) {
//    if (CollectionUtils.isEmpty(entityList))
//      throw new IllegalArgumentException("Error: entityList must not be empty"); 
//    
//    try { batchSqlSession = sqlSessionBatch(); throwable = null; 
//      try { IntStream.range(0, entityList.size()).forEach(i -> {
//              update((BaseModel)entityList.get(i));
//              if (i >= 1 && i % batchSize == 0) {
//                batchSqlSession.flushStatements();
//              }
//            });
//        batchSqlSession.flushStatements(); } catch (Throwable throwable1) { throwable = throwable1 = null; throw throwable1; }
//      finally { if (batchSqlSession != null) if (throwable != null) { try { batchSqlSession.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  } else { batchSqlSession.close(); }   }  } catch (Throwable e)
//    { throw new RuntimeException("Error: Cannot execute insertOrUpdateBatch Method. Cause", e); }
//    
//    return true;
//  }
}
