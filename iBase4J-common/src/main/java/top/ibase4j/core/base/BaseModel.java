package top.ibase4j.core.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;











@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseModel
  implements Serializable
{
  @TableId("id_")
  private Long id;
  @TableField("enable_")
  private Integer enable;
  @TableField("remark_")
  private String remark;
  @TableField("create_by")
  @ApiModelProperty(hidden = true)
  private Long createBy;
  @TableField("create_time")
  @ApiModelProperty(hidden = true)
  private Date createTime;
  @TableField("update_by")
  @ApiModelProperty(hidden = true)
  private Long updateBy;
  @TableField("update_time")
  @ApiModelProperty(hidden = true)
  private Date updateTime;
  @TableField(exist = false)
  @ApiModelProperty(hidden = true)
  private String keyword;
  @TableField(exist = false)
  @ApiModelProperty(hidden = true)
  private String orderBy;
  @TableField(exist = false)
  @ApiModelProperty(hidden = true)
  private List<Long> ids;
  
  public Long getId() { return this.id; }






  
  public void setId(Long id) { this.id = id; }





  
  public Integer getEnable() { return this.enable; }






  
  public void setEnable(Integer enable) { this.enable = enable; }





  
  public String getRemark() { return this.remark; }






  
  public void setRemark(String remark) { this.remark = (remark == null) ? null : remark.trim(); }





  
  public Long getCreateBy() { return this.createBy; }






  
  public void setCreateBy(Long createBy) { this.createBy = createBy; }





  
  public Date getCreateTime() { return this.createTime; }






  
  public void setCreateTime(Date createTime) { this.createTime = createTime; }





  
  public Long getUpdateBy() { return this.updateBy; }






  
  public void setUpdateBy(Long updateBy) { this.updateBy = updateBy; }





  
  public Date getUpdateTime() { return this.updateTime; }






  
  public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }


  
  public String getKeyword() { return this.keyword; }


  
  public void setKeyword(String keyword) { this.keyword = keyword; }


  
  public String getOrderBy() { return this.orderBy; }


  
  public void setOrderBy(String orderBy) { this.orderBy = orderBy; }


  
  public List<Long> getIds() { return this.ids; }


  
  public void setIds(List<Long> ids) { this.ids = ids; }
}
