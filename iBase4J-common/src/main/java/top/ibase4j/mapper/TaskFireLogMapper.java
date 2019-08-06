package top.ibase4j.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import top.ibase4j.model.TaskFireLog;

public interface TaskFireLogMapper extends BaseMapper<TaskFireLog> {
  List<Long> selectIdByMap(Page<Long> paramPage, @Param("cm") Map<String, Object> paramMap);
}
