package top.ibase4j.core.base;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface BaseMapper<T extends BaseModel>{
  List<Long> selectIdPage(@Param("cm") T paramT);
  
  List<Long> selectIdPage(@Param("cm") Map<String, Object> paramMap);
  
  List<Long> selectIdPage(Page<Long> paramPage, @Param("cm") Map<String, Object> paramMap);
  
  List<Long> selectIdPage(Page<Long> paramPage, @Param("cm") T paramT);
  
  List<T> selectPage(Page<Long> paramPage, @Param("cm") Map<String, Object> paramMap);
  
  Integer selectCount(@Param("cm") Map<String, Object> paramMap);
}
