package top.ibase4j.core.support;

import java.beans.Transient;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import top.ibase4j.core.util.InstanceUtil;

public class Pagination<T>
  extends Object implements Serializable {
  public static final int NO_ROW_OFFSET = 0;
  public static final int NO_ROW_LIMIT = 2147483647;
  private final long offset;
  private final long limit;
  private long total;
  
  public Pagination() {
    this.offset = 0L;
    this.limit = 2147483647L;
  }

  
  public Pagination(long current, long size) { this(current, size, true); }


  
  public Pagination(long current, long size, boolean searchCount) { this(current, size, searchCount, true); }

  
  public Pagination(long current, long size, boolean searchCount, boolean openSort) {
    this.offset = offsetCurrent(current, size);
    this.limit = size;
    if (current > 1L) {
      this.current = current;
    }
    this.size = size;
    this.searchCount = searchCount;
    this.openSort = openSort;
  }
  
  public Pagination(int current, int size, String orderByField) {
    this(current, size);
    setOrderByField(orderByField);
  }
  
  public Pagination(int current, int size, String orderByField, boolean isAsc) {
    this(current, size, orderByField);
    setAsc(isAsc);
  }













  
  private long size = 10L;



  
  private long current = 1L;





  
  private boolean searchCount = true;





  
  private boolean openSort = true;





  
  private boolean optimizeCountSql = true;





  
  private List<String> ascs;





  
  private List<String> descs;





  
  private boolean isAsc = true;





  
  private String orderByField;





  
  private List<T> records = InstanceUtil.newArrayList();


  
  private Map<String, Object> condition;


  
  public Long getTotal() { return Long.valueOf(this.total); }


  
  public void setTotal(long total) { this.total = total; }


  
  public long getSize() { return this.size; }


  
  public void setSize(long size) { this.size = size; }

  
  public long getPages() {
    if (this.size == 0L) {
      return 0L;
    }
    long pages = (this.total - 1L) / this.size;
    pages++;
    return pages;
  }

  
  public long getCurrent() { return this.current; }


  
  public void setCurrent(int current) { this.current = current; }


  
  public boolean isSearchCount() { return this.searchCount; }


  
  public void setSearchCount(boolean searchCount) { this.searchCount = searchCount; }


  
  public boolean isOpenSort() { return this.openSort; }


  
  public void setOpenSort(boolean openSort) { this.openSort = openSort; }


  
  public boolean isOptimizeCountSql() { return this.optimizeCountSql; }


  
  public void setOptimizeCountSql(boolean optimizeCountSql) { this.optimizeCountSql = optimizeCountSql; }


  
  public List<String> getAscs() { return this.ascs; }


  
  public void setAscs(List<String> ascs) { this.ascs = ascs; }


  
  public List<String> getDescs() { return this.descs; }


  
  public void setDescs(List<String> descs) { this.descs = descs; }


  
  public boolean isAsc() { return this.isAsc; }


  
  public void setAsc(boolean isAsc) { this.isAsc = isAsc; }


  
  public String getOrderByField() { return this.orderByField; }


  
  public void setOrderByField(String orderByField) { this.orderByField = orderByField; }


  
  public long getOffset() { return this.offset; }


  
  public long getLimit() { return this.limit; }


  
  public List<T> getRecords() { return this.records; }

  
  public Pagination<T> setRecords(List<T> records) {
    this.records = records;
    return this;
  }

  
  @Transient
  public Map<String, Object> getCondition() { return this.condition; }

  
  public Pagination<T> setCondition(Map<String, Object> condition) {
    this.condition = condition;
    return this;
  }









  
  public long offsetCurrent(long current, long size) {
    if (current > 0L) {
      return (current - 1L) * size;
    }
    return 0L;
  }





  
  public long offsetCurrent(Pagination<T> page) {
    if (null == page) {
      return 0L;
    }
    return offsetCurrent(page.getCurrent(), page.getSize());
  }
}
