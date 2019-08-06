package top.ibase4j.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;









public class FileUtil
{
  private static Logger logger = LogManager.getLogger();
  
  public static List<String> readFile(String fileName) {
	return null;
//    List<String> list = new ArrayList<String>();
//    reader = null;
//    fis = null;
//    try {
//      f = new File(fileName);
//      if (f.isFile() && f.exists()) {
//        fis = new FileInputStream(f);
//        reader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
//        String line;
//        while ((line = reader.readLine()) != null) {
//          if (!"".equals(line)) {
//            list.add(line);
//          }
//        } 
//      } 
//    } catch (Exception e) {
//      logger.error("readFile", e);
//    } finally {
//      try {
//        if (reader != null) {
//          reader.close();
//        }
//      } catch (IOException e) {
//        logger.error("InputStream关闭异常", e);
//      } 
//      try {
//        if (fis != null) {
//          fis.close();
//        }
//      } catch (IOException e) {
//        logger.error("FileInputStream关闭异常", e);
//      } 
//    } 
//    return list;
//  }
  }
}
