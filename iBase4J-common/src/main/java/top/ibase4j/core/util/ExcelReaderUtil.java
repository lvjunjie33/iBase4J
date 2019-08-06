package top.ibase4j.core.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.fileupload.FileItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.ibase4j.core.support.file.excel.Excel2003Reader;
import top.ibase4j.core.support.file.excel.Excel2007Reader;
import top.ibase4j.core.support.file.excel.RowReader;









public final class ExcelReaderUtil
{
  private static final Logger logger = LogManager.getLogger();



  
  public static final String EXCEL03_EXTENSION = ".xls";



  
  public static final String EXCEL07_EXTENSION = ".xlsx";



  
  public static void readExcel(String fileName, RowReader reader) throws Throwable {
    if (fileName.endsWith(".xls")) {
      Excel2003Reader excel03 = new Excel2003Reader(reader);
      excel03.process(fileName);
    }
    else if (fileName.endsWith(".xlsx")) {
      Excel2007Reader excel07 = new Excel2007Reader(reader);
      excel07.process(fileName);
    } else {
      throw new Exception("文件格式错误，fileName的扩展名只能是xls或xlsx。");
    } 
  }









  
  public static void readExcel(String fileName, InputStream inputStream, RowReader reader) throws Throwable {
    if (fileName.endsWith(".xls")) {
      Excel2003Reader excel03 = new Excel2003Reader(reader);
      excel03.process(inputStream);
    }
    else if (fileName.endsWith(".xlsx")) {
      Excel2007Reader excel07 = new Excel2007Reader(reader);
      excel07.process(inputStream);
    } else {
      throw new Exception("文件格式错误，fileName的扩展名只能是xls或xlsx。");
    } 
  }






  
  public static final List<String[]> excelToArrayList(String filePath, int... sheetNumber) throws Exception {
	return null;
//    List<String[]> resultList = null;
//    is = null;
//    try {
//      is = new FileInputStream(filePath);
//      resultList = excelToArrayList(filePath, is, sheetNumber);
//    } catch (Exception e) {
//      throw e;
//    } finally {
//      if (is != null) {
//        is.close();
//      }
//    } 
//    return resultList;
  }







  
  public static final List<String[]> excelToArrayList(String fileName, FileItem fileItem, int... sheetNumber) throws Exception {
	return null;
//    List<String[]> resultList = null;
//    is = null;
//    try {
//      is = fileItem.getInputStream();
//      resultList = excelToArrayList(fileName, is, sheetNumber);
//    } catch (Exception e) {
//      throw e;
//    } finally {
//      if (is != null) {
//        is.close();
//      }
//    } 
//    return resultList;
  }







  
  public static final List<String[]> excelToArrayList(String fileName, InputStream is, int... sheetNumber) throws Exception {
	return null;
//    final ArrayList<String[]> resultList = new ArrayList<String[]>();
//    try {
//      readExcel(fileName, is, new RowReader() {
//
//
//
//
//
//
//
//            
////            public void invoke(int sheetIndex, int curRow, List<String> rowlist)
////            {
//              // Byte code:
//              //   0: aload_0
//              //   1: getfield val$sheetNumber : [I
//              //   4: ifnull -> 15
//              //   7: aload_0
//              //   8: getfield val$sheetNumber : [I
//              //   11: arraylength
//              //   12: ifne -> 36
//              //   15: aload_0
//              //   16: getfield val$resultList : Ljava/util/ArrayList;
//              //   19: aload_3
//              //   20: iconst_0
//              //   21: anewarray java/lang/String
//              //   24: invokeinterface toArray : ([Ljava/lang/Object;)[Ljava/lang/Object;
//              //   29: invokevirtual add : (Ljava/lang/Object;)Z
//              //   32: pop
//              //   33: goto -> 94
//              //   36: aload_0
//              //   37: getfield val$sheetNumber : [I
//              //   40: astore #4
//              //   42: aload #4
//              //   44: arraylength
//              //   45: istore #5
//              //   47: iconst_0
//              //   48: istore #6
//              //   50: iload #6
//              //   52: iload #5
//              //   54: if_icmpge -> 94
//              //   57: aload #4
//              //   59: iload #6
//              //   61: iaload
//              //   62: istore #7
//              //   64: iload_1
//              //   65: iload #7
//              //   67: if_icmpne -> 88
//              //   70: aload_0
//              //   71: getfield val$resultList : Ljava/util/ArrayList;
//              //   74: aload_3
//              //   75: iconst_0
//              //   76: anewarray java/lang/String
//              //   79: invokeinterface toArray : ([Ljava/lang/Object;)[Ljava/lang/Object;
//              //   84: invokevirtual add : (Ljava/lang/Object;)Z
//              //   87: pop
//              //   88: iinc #6, 1
//              //   91: goto -> 50
//              //   94: return
//              // Line number table:
//              //   Java source line number -> byte code offset
//              //   #132	-> 0
//              //   #133	-> 15
//              //   #135	-> 36
//              //   #136	-> 64
//              //   #137	-> 70
//              //   #135	-> 88
//              //   #141	-> 94
//              // Local variable table:
//              //   start	length	slot	name	descriptor
//              //   64	24	7	element	I
//              //   0	95	0	this	Ltop/ibase4j/core/util/ExcelReaderUtil$1;
//              //   0	95	1	sheetIndex	I
//              //   0	95	2	curRow	I
//              //   0	95	3	rowlist	Ljava/util/List;
//              // Local variable type table:
//              //   start	length	slot	name	signature
//              //   0	95	3	rowlist	Ljava/util/List<Ljava/lang/String;>;
////            }
////          });
//    } catch (Exception e) {
//      logger.error("Read excel error.", e);
//      throw e;
//    } 
//    return resultList;
//  }
  }
}
