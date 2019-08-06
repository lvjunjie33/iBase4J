package top.ibase4j.core.support.file.excel;

import org.apache.poi.hssf.usermodel.HSSFSheet;

public interface WriteDone {
  void invoke(HSSFSheet paramHSSFSheet);
}
