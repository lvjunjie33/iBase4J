package top.ibase4j.core.base;

import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ui.ModelMap;
import top.ibase4j.core.util.InstanceUtil;








public abstract class AppBaseController<T extends BaseModel, S extends BaseService<T>>
  extends BaseController<T, S>
{
  protected final Logger logger = LogManager.getLogger(getClass());

  
  public Object update(HttpServletRequest request, T param) { return update(request, new ModelMap(), param); }

  
  public Object update(HttpServletRequest request, ModelMap modelMap, T param) {
    Long userId = getCurrUser(request);
    if (param.getId() == null) {
      param.setCreateBy(userId);
      param.setCreateTime(new Date());
    } 
    param.setUpdateBy(userId);
    param.setUpdateTime(new Date());
    Object record = this.service.update(param);
    Map<String, Object> result = InstanceUtil.newHashMap("bizeCode", Integer.valueOf(1));
    result.put("record", record);
    return setSuccessModelMap(modelMap, result);
  }
}
