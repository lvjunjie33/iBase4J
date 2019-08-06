package top.ibase4j.core.base;

import com.alibaba.fastjson.JSON;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import top.ibase4j.core.support.Pagination;
import top.ibase4j.core.support.http.HttpCode;
import top.ibase4j.core.support.http.SessionUser;
import top.ibase4j.core.util.InstanceUtil;
import top.ibase4j.core.util.WebUtil;
















public abstract class AbstractController implements InitializingBean{
  protected Logger logger = LogManager.getLogger();
  public void afterPropertiesSet() {
	  // Byte code:
    //   0: aload_0
    //   1: invokevirtual getClass : ()Ljava/lang/Class;
    //   4: invokevirtual getDeclaredFields : ()[Ljava/lang/reflect/Field;
    //   7: astore_1
    //   8: aload_1
    //   9: astore_2
    //   10: aload_2
    //   11: arraylength
    //   12: istore_3
    //   13: iconst_0
    //   14: istore #4
    //   16: iload #4
    //   18: iload_3
    //   19: if_icmpge -> 97
    //   22: aload_2
    //   23: iload #4
    //   25: aaload
    //   26: astore #5
    //   28: aload #5
    //   30: iconst_1
    //   31: invokevirtual setAccessible : (Z)V
    //   34: aload #5
    //   36: aload_0
    //   37: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   40: astore #6
    //   42: aload #5
    //   44: invokevirtual getType : ()Ljava/lang/Class;
    //   47: astore #7
    //   49: aload #6
    //   51: ifnonnull -> 85
    //   54: aload #7
    //   56: invokevirtual getSimpleName : ()Ljava/lang/String;
    //   59: invokevirtual toLowerCase : ()Ljava/lang/String;
    //   62: ldc 'service'
    //   64: invokevirtual contains : (Ljava/lang/CharSequence;)Z
    //   67: ifeq -> 85
    //   70: aload #7
    //   72: invokestatic getService : (Ljava/lang/Class;)Ljava/lang/Object;
    //   75: astore #6
    //   77: aload #5
    //   79: aload_0
    //   80: aload #6
    //   82: invokevirtual set : (Ljava/lang/Object;Ljava/lang/Object;)V
    //   85: aload #5
    //   87: iconst_0
    //   88: invokevirtual setAccessible : (Z)V
    //   91: iinc #4, 1
    //   94: goto -> 16
    //   97: goto -> 122
    //   100: astore_2
    //   101: aload_0
    //   102: getfield logger : Lorg/apache/logging/log4j/Logger;
    //   105: ldc ''
    //   107: aload_2
    //   108: invokeinterface error : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   113: iconst_1
    //   114: iconst_5
    //   115: invokestatic sleep : (II)V
    //   118: aload_0
    //   119: invokevirtual afterPropertiesSet : ()V
    //   122: return
    // Line number table:
    //   Java source line number -> byte code offset
    //   #41	-> 0
    //   #43	-> 8
    //   #44	-> 28
    //   #45	-> 34
    //   #46	-> 42
    //   #47	-> 49
    //   #48	-> 70
    //   #49	-> 77
    //   #51	-> 85
    //   #43	-> 91
    //   #57	-> 97
    //   #53	-> 100
    //   #54	-> 101
    //   #55	-> 113
    //   #56	-> 118
    //   #58	-> 122
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   42	49	6	v	Ljava/lang/Object;
    //   49	42	7	cls	Ljava/lang/Class;
    //   28	63	5	field	Ljava/lang/reflect/Field;
    //   101	21	2	e	Ljava/lang/Exception;
    //   0	123	0	this	Ltop/ibase4j/core/base/AbstractController;
    //   8	115	1	fields	[Ljava/lang/reflect/Field;
    // Local variable type table:
    //   start	length	slot	name	signature
    //   49	42	7	cls	Ljava/lang/Class<*>;
    // Exception table:
    //   from	to	target	type
    //   8	97	100	java/lang/Exception }

  }








  
  protected SessionUser getCurrUser() { return (SessionUser)SecurityUtils.getSubject().getPrincipal(); }


  
  protected Long getCurrUser(HttpServletRequest request) {
    SessionUser user = WebUtil.getCurrentUser(request);
    if (user == null) {
      return null;
    }
    return user.getId();
  }



  
  protected ResponseEntity<ModelMap> setSuccessModelMap() { return setSuccessModelMap(new ModelMap(), null); }



  
  protected ResponseEntity<ModelMap> setSuccessModelMap(ModelMap modelMap) { return setSuccessModelMap(modelMap, null); }



  
  protected ResponseEntity<ModelMap> setSuccessModelMap(Object data) { return setModelMap(new ModelMap(), HttpCode.OK, data); }



  
  protected ResponseEntity<ModelMap> setSuccessModelMap(ModelMap modelMap, Object data) { return setModelMap(modelMap, HttpCode.OK, data); }



  
  protected ResponseEntity<ModelMap> setModelMap(HttpCode code) { return setModelMap(new ModelMap(), code, null); }



  
  protected ResponseEntity<ModelMap> setModelMap(String code, String msg) { return setModelMap(new ModelMap(), code, msg, null); }



  
  protected ResponseEntity<ModelMap> setModelMap(ModelMap modelMap, HttpCode code) { return setModelMap(modelMap, code, null); }



  
  protected ResponseEntity<ModelMap> setModelMap(HttpCode code, Object data) { return setModelMap(new ModelMap(), code, data); }



  
  protected ResponseEntity<ModelMap> setModelMap(String code, String msg, Object data) { return setModelMap(new ModelMap(), code, msg, data); }



  
  protected ResponseEntity<ModelMap> setModelMap(ModelMap modelMap, HttpCode code, Object data) { return setModelMap(modelMap, code.value().toString(), code.msg(), data); }


  
  protected ResponseEntity<ModelMap> setModelMap(ModelMap modelMap, String code, String msg, Object data) {
    if (!modelMap.isEmpty()) {
      Map<String, Object> map = InstanceUtil.newLinkedHashMap();
      map.putAll(modelMap);
      modelMap.clear();
      for (Map.Entry<String, Object> entry : map.entrySet()) {
        if (!((String)entry.getKey()).startsWith("org.springframework.validation.BindingResult") && 
          !"void".equals(entry.getKey())) {
          modelMap.put(entry.getKey(), entry.getValue());
        }
      } 
    } 
    if (data != null) {
      if (data instanceof Pagination) {
        Pagination<?> page = (Pagination)data;
        modelMap.put("rows", page.getRecords());
        modelMap.put("current", Long.valueOf(page.getCurrent()));
        modelMap.put("size", Long.valueOf(page.getSize()));
        modelMap.put("pages", Long.valueOf(page.getPages()));
        modelMap.put("total", page.getTotal());
      } else if (data instanceof List) {
        modelMap.put("rows", data);
        modelMap.put("total", Integer.valueOf(((List)data).size()));
      } else {
        modelMap.put("data", data);
      } 
    }
    modelMap.put("code", code);
    modelMap.put("msg", msg);
    modelMap.put("timestamp", Long.valueOf(System.currentTimeMillis()));
    this.logger.info("response===>{}", JSON.toJSONString(modelMap));
    return ResponseEntity.ok(modelMap);
  }
}
