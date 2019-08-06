package top.ibase4j.core.util;

import com.google.common.collect.Maps;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import top.ibase4j.core.exception.BusinessException;
import top.ibase4j.core.support.file.excel.WriteDone;









public class ExcelWriterPoiUtil
{
  private static final Logger logger = LogManager.getLogger();








  
  public static void setResponseFileName(HttpServletRequest request, HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
    String displayName, userAgent = request.getHeader("User-Agent");
    boolean isIE = false;
    if (userAgent != null && (userAgent.toLowerCase().contains("msie") || userAgent.toLowerCase().contains("rv"))) {
      isIE = true;
    }
    
    if (isIE) {
      displayName = URLEncoder.encode(fileName, "UTF-8");
      displayName = displayName.replaceAll("\\+", "%20");
      response.setHeader("Content-Disposition", "attachment;filename=" + displayName);
    } else {
      displayName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
      response.setHeader("Content-Disposition", "attachment;filename=\"" + displayName + "\"");
    } 
    String extStr = displayName.substring(displayName.indexOf(".") + 1);
    if ("xls".equalsIgnoreCase(extStr)) {
      response.setContentType("application/vnd.ms-excel");
    } else {
      response.setContentType("application/octet-stream");
    } 
  }










//  
//  public static void createExcelTemplate(String filePath, String[] handers, List<String[]> downData, String[] downRows) throws BusinessException {
//    out = null; 
//    try { wb = new HSSFWorkbook(); throwable = null; 
//      try { out = writeTemplate(filePath, handers, downData, downRows, wb); } catch (Throwable throwable1) { throwable = throwable1 = null; throw throwable1; }
//      finally { if (wb != null) if (throwable != null) { try { wb.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  } else { wb.close(); }   }  } catch (IOException e)
//    { throw new BusinessException(e); }
//    catch (Exception e)
//    { throw new BusinessException(e); }
//    finally
//    { if (out != null) {
//        try {
//          out.close();
//        } catch (IOException e) {
//          logger.error("关闭输出流异常", e);
//        } 
//      } }
//  
//  }










//  
//  public static void createExcelTemplate(URL tplPath, String filePath, String[] handers, List<String[]> downData, String[] downRows) throws BusinessException {
//    out = null; 
//    try { wb = new HSSFWorkbook(tplPath.openStream()); throwable = null; 
//      try { out = writeTemplate(filePath, handers, downData, downRows, wb); } catch (Throwable throwable1) { throwable = throwable1 = null; throw throwable1; }
//      finally { if (wb != null) if (throwable != null) { try { wb.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  } else { wb.close(); }   }  } catch (IOException e)
//    { throw new BusinessException(e); }
//    catch (Exception e)
//    { throw new BusinessException(e); }
//    finally
//    { if (out != null) {
//        try {
//          out.close();
//        } catch (IOException e) {
//          logger.error("关闭输出流异常", e);
//        } 
//      } }
//  
//  }



  
  private static OutputStream writeTemplate(String filePath, String[] handers, List<String[]> downData, String[] downRows, HSSFWorkbook wb) throws IOException, FileNotFoundException {
    HSSFCellStyle style = wb.createCellStyle();
    style.setAlignment(HorizontalAlignment.CENTER);
    
    HSSFFont fontStyle = wb.createFont();
    fontStyle.setFontName("微软雅黑");
    fontStyle.setFontHeightInPoints((short)12);
    fontStyle.setBold(true);
    style.setFont(fontStyle);

    
    HSSFSheet sheet1 = wb.createSheet("Sheet1");
    HSSFSheet sheet2 = wb.createSheet("Sheet2");

    
    HSSFRow rowFirst = sheet1.createRow(0);
    
//    for (i = 0; i < handers.length; i++) {
//      HSSFCell cell = rowFirst.createCell(i);
//      sheet1.setColumnWidth(i, 4000);
//      cell.setCellStyle(style);
//      cell.setCellValue(handers[i]);
//    } 

    
    String[] arr = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
    
    int index = 0;
    HSSFRow row = null;
    for (int r = 0; r < downRows.length; r++) {
      String[] dlData = (String[])downData.get(r);
      int rownum = Integer.parseInt(downRows[r]);
      
      if (dlData.length < 5) {
        
        sheet1.addValidationData(setDataValidation(sheet1, dlData, 1, 5, rownum, rownum));
      }
      else {
        
        String strFormula = "Sheet2!$" + arr[index] + "$1:$" + arr[index] + "$5000";
        sheet2.setColumnWidth(r, 4000);
        
        sheet1.addValidationData(setDataValidation(strFormula, 1, 50000, rownum, rownum));

        
        for (int j = 0; j < dlData.length; j++) {
          if (index == 0) {
            row = sheet2.createRow(j);
            sheet2.setColumnWidth(j, 4000);
            row.createCell(0).setCellValue(dlData[j]);
          } else {
            int rowCount = sheet2.getLastRowNum();
            
            if (j <= rowCount) {
              
              sheet2.getRow(j).createCell(index).setCellValue(dlData[j]);
            } else {
              sheet2.setColumnWidth(j, 4000);
              
              sheet2.createRow(j).createCell(index).setCellValue(dlData[j]);
            } 
          } 
        } 
        index++;
      } 
    } 
    File f = new File(filePath);
    
    if (!f.getParentFile().exists() && !f.getParentFile().mkdirs()) {
      throw new BusinessException("");
    }
    if (!f.exists() && !f.createNewFile()) {
      throw new BusinessException("");
    }
    OutputStream out = new FileOutputStream(f);
    out.flush();
    wb.write(out);
    return out;
  }













  
  private static HSSFDataValidation setDataValidation(String strFormula, int firstRow, int endRow, int firstCol, int endCol) {
    CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
    DVConstraint constraint = DVConstraint.createFormulaListConstraint(strFormula);
    HSSFDataValidation dataValidation = new HSSFDataValidation(regions, constraint);
    
    dataValidation.createErrorBox("Error", "Error");
    dataValidation.createPromptBox("", null);
    
    return dataValidation;
  }













  
  private static DataValidation setDataValidation(Sheet sheet, String[] textList, int firstRow, int endRow, int firstCol, int endCol) {
    DataValidationHelper helper = sheet.getDataValidationHelper();
    
    DataValidationConstraint constraint = helper.createExplicitListConstraint(textList);
    
    constraint.setExplicitListValues(textList);
    
    CellRangeAddressList regions = new CellRangeAddressList((short)firstRow, (short)endRow, (short)firstCol, (short)endCol);

    
    return helper.createValidation(constraint, regions);
  }







  
  public static void exportToExcel(OutputStream os, List<List<String>> data) throws IOException { exportToExcel(os, data, null); }








  
  public static void exportToExcel(OutputStream os, List<List<String>> data, String title) throws IOException { exportToExcel(os, data, title, null); }







  
  public static void exportToExcel(OutputStream os, List<List<String>> data, String title, WriteDone writeDone) throws IOException {
    
//    try { wb = new HSSFWorkbook(); throwable = null; 
//      try { sheet = wb.createSheet("Data");
//        Map<String, CellStyle> styles = createStyles(wb);
//        if (StringUtils.isNotBlank(title)) {
//          HSSFRow hSSFRow = sheet.createRow(0);
//          hSSFRow.setHeightInPoints(30.0F);
//          Cell titleCell = hSSFRow.createCell(0);
//          titleCell.setCellStyle((CellStyle)styles.get("title"));
//          titleCell.setCellValue(title);
//          sheet.addMergedRegion(new CellRangeAddress(hSSFRow.getRowNum(), hSSFRow.getRowNum(), hSSFRow
//                .getRowNum(), ((List)data.get(0)).size() - 1));
//        } 
//        int col = 0;
//        for (r = 0; r < data.size(); r++) {
//          HSSFRow row = sheet.createRow(r + 1);
//          List<String> cols = (List)data.get(r);
//          for (int c = 0; c < cols.size(); c++) {
//            HSSFCell cell = row.createCell(c);
//            if (r == 0) {
//              cell.setCellStyle((CellStyle)styles.get("header"));
//            } else {
//              cell.setCellStyle((CellStyle)styles.get("data"));
//            } 
//            cell.setCellValue(new HSSFRichTextString((String)cols.get(c)));
//          } 
//          col = Math.max(col, cols.size());
//        } 
//        for (int i = 0; i < col; i++) {
//          sheet.autoSizeColumn((short)i);
//        }
//        if (writeDone != null) {
//          writeDone.invoke(sheet);
//        }
//        if (wb != null)
//          wb.write(os);  }
//      catch (Throwable throwable1) { throwable = throwable1 = null; throw throwable1; }
//      finally { if (wb != null) if (throwable != null) { try { wb.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  } else { wb.close(); }   }  } catch (Exception e)
//    { throw new IOException(e); }
//    finally
//    { os.flush();
//      os.close(); }
  
  }
  
  private static Map<String, CellStyle> createStyles(HSSFWorkbook wb) {
    Map<String, CellStyle> styleMap = Maps.newHashMap();
    HSSFCellStyle hSSFCellStyle = wb.createCellStyle();
    hSSFCellStyle.setAlignment(HorizontalAlignment.CENTER);
    hSSFCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    HSSFFont hSSFFont1 = wb.createFont();
    hSSFFont1.setFontName("Arial");
    hSSFFont1.setFontHeightInPoints((short)16);
    hSSFFont1.setBold(true);
    hSSFCellStyle.setFont(hSSFFont1);
    styleMap.put("title", hSSFCellStyle);
    
    hSSFCellStyle = wb.createCellStyle();
    hSSFCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    hSSFCellStyle.setBorderRight(BorderStyle.THIN);
    hSSFCellStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
    hSSFCellStyle.setBorderLeft(BorderStyle.THIN);
    hSSFCellStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
    hSSFCellStyle.setBorderTop(BorderStyle.THIN);
    hSSFCellStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
    hSSFCellStyle.setBorderBottom(BorderStyle.THIN);
    hSSFCellStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
    HSSFFont hSSFFont2 = wb.createFont();
    hSSFFont2.setFontName("Arial");
    hSSFFont2.setFontHeightInPoints((short)10);
    hSSFCellStyle.setFont(hSSFFont2);
    styleMap.put("data", hSSFCellStyle);
    
    hSSFCellStyle = wb.createCellStyle();
    hSSFCellStyle.cloneStyleFrom((CellStyle)styleMap.get("data"));
    hSSFCellStyle.setWrapText(true);
    hSSFCellStyle.setAlignment(HorizontalAlignment.CENTER);
    hSSFCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
    hSSFCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    HSSFFont hSSFFont3 = wb.createFont();
    hSSFFont3.setFontName("Arial");
    hSSFFont3.setFontHeightInPoints((short)10);
    hSSFFont3.setBold(true);
    hSSFFont3.setColor(IndexedColors.BLACK.getIndex());
    hSSFCellStyle.setFont(hSSFFont3);
    styleMap.put("header", hSSFCellStyle);
    return styleMap;
  }
}
