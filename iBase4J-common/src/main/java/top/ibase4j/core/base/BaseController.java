package top.ibase4j.core.base;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import top.ibase4j.core.support.context.ApplicationContextHolder;
import top.ibase4j.core.util.ThreadUtil;













public abstract class BaseController<T extends BaseModel, S extends BaseService<T>>
  extends AbstractController
  implements InitializingBean
{
  protected BaseService service;
  
  public void afterPropertiesSet() {
    if (this.service == null) {
      ParameterizedType type = (ParameterizedType)getClass().getGenericSuperclass();
      Class<?> cls = (Class)type.getActualTypeArguments()[1];
      try {
        this.service = (BaseService)ApplicationContextHolder.getService(cls);
      } catch (Exception e) {
        this.logger.error("", e);
        ThreadUtil.sleep(1, 5);
        afterPropertiesSet();
      } 
    } 
    super.afterPropertiesSet();
  }


  
  public Object query(Map<String, Object> param) { return query(new ModelMap(), param); }


  
  public Object query(ModelMap modelMap, Map<String, Object> param) {
    if (param.get("keyword") == null && param.get("search") != null) {
      param.put("keyword", param.get("search"));
      param.remove("search");
    } 
    Object page = this.service.query(param);
    return setSuccessModelMap(modelMap, page);
  }


  
  public Object queryList(Map<String, Object> param) { return queryList(new ModelMap(), param); }


  
  public Object queryList(ModelMap modelMap, Map<String, Object> param) {
    List<?> list = this.service.queryList(param);
    return setSuccessModelMap(modelMap, list);
  }

  
  public Object get(BaseModel param) { return get(new ModelMap(), param); }

  
  public Object get(ModelMap modelMap, BaseModel param) {
    Object result = this.service.queryById(param.getId());
    return setSuccessModelMap(modelMap, result);
  }

  
  public Object update(T param) { return update(new ModelMap(), param); }

  
  public Object update(ModelMap modelMap, T param) {
    Long userId = getCurrUser().getId();
    if (param.getId() == null) {
      param.setCreateBy(userId);
      param.setCreateTime(new Date());
    } 
    param.setUpdateBy(userId);
    param.setUpdateTime(new Date());
    T result = (T)this.service.update(param);
    param.setId(result.getId());
    return setSuccessModelMap(modelMap);
  }


  
  public Object delete(T param) { return delete(new ModelMap(), param); }


  
  public Object delete(ModelMap modelMap, T param) {
    Assert.notNull(param.getId(), "ID不能为空");
    this.service.delete(param.getId());
    return setSuccessModelMap(modelMap);
  }


  
  public Object del(HttpServletRequest request, T param) { return del(new ModelMap(), request, param); }


  
  public Object del(ModelMap modelMap, HttpServletRequest request, T param) {
    if (param.getId() != null) {
      Assert.notNull(param.getId(), "ID不能为空");
      this.service.del(param.getId(), getCurrUser(request));
    } 
    if (param.getIds() != null) {
      Assert.notNull(param.getIds(), "ID不能为空");
      this.service.del(param.getIds(), getCurrUser(request));
    } 
    return setSuccessModelMap(modelMap);
  }
}
