package top.ibase4j.core.base;

import java.util.List;
import java.util.Map;
import top.ibase4j.core.exception.BusinessException;
import top.ibase4j.core.exception.ValidateException;
import top.ibase4j.core.support.Pagination;

public interface BaseService<T extends BaseModel> {
  T update(T paramT) throws BusinessException, ValidateException;
  
  void del(List<Long> paramList, Long paramLong) throws BusinessException, ValidateException;
  
  void del(Long paramLong1, Long paramLong2) throws BusinessException, ValidateException;
  
  void delete(Long paramLong) throws BusinessException, ValidateException;
  
  Integer deleteByEntity(T paramT) throws BusinessException, ValidateException;
  
  Integer deleteByMap(Map<String, Object> paramMap) throws BusinessException, ValidateException;
  
  T queryById(Long paramLong);
  
  Pagination<T> query(Map<String, Object> paramMap);
  
  Pagination<T> query(T paramT, Pagination<T> paramPagination);
  
  List<T> queryList(Map<String, Object> paramMap);
  
  List<T> queryList(List<Long> paramList);
  
  <K> List<K> queryList(List<Long> paramList, Class<K> paramClass);
  
  List<T> queryList(T paramT);
  
  Pagination<T> queryFromDB(Map<String, Object> paramMap);
  
  Pagination<T> queryFromDB(T paramT, Pagination<T> paramPagination);
  
  List<T> queryListFromDB(Map<String, Object> paramMap);
  
  List<T> queryListFromDB(T paramT);
  
  T selectOne(T paramT) throws BusinessException, ValidateException;
  
  Integer count(T paramT) throws BusinessException, ValidateException;
  
  Integer count(Map<String, Object> paramMap) throws BusinessException, ValidateException;
  
  boolean updateBatch(List<T> paramList) throws BusinessException, ValidateException;
  
  boolean updateBatch(List<T> paramList, int paramInt) throws BusinessException, ValidateException;
}
