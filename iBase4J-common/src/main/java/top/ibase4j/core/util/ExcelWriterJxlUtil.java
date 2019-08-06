package top.ibase4j.core.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import jxl.Cell;
import jxl.CellType;
import jxl.CellView;
import jxl.Range;
import jxl.Workbook;
import jxl.biff.DisplayFormat;
import jxl.format.Alignment;
import jxl.format.BoldStyle;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;








public class ExcelWriterJxlUtil
{
  private static final Logger logger = LogManager.getLogger();
  
  private Integer icol = Integer.valueOf(0);
  private Integer irow = Integer.valueOf(-1);
  
  private OutputStream os;
  private WritableWorkbook wbook;
  private WritableSheet wsheet;
  private WritableCellFormat wcfFC;
  private WritableFont wfont;
  private Integer trow = Integer.valueOf(-1);
  private Integer titleCols = Integer.valueOf(0);
  private long startTime;
  private int sheetIndex = 0;





  
  private String sheetName;





  
  public ExcelWriterJxlUtil(HttpServletResponse response, String fileName, String sheetName) throws IOException {
    this.startTime = System.currentTimeMillis();
    if (fileName.indexOf(".xls") < 0) {
      fileName = fileName + ".xls";
    }
    if (response != null && response instanceof HttpServletResponse) {
      logger.warn("Write Excel To Memory.Please wait...");
      response.reset();
      response.setContentType("application/vnd.ms-excel;charset=UTF-8");
      response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName
            .getBytes("GB2312"), "ISO8859-1"));
      
      this.os = response.getOutputStream();
      this.os.flush();
      this.wbook = Workbook.createWorkbook(this.os);
    } else {
      logger.warn("Write Excel To Disk.Please wait...");
      this.wbook = Workbook.createWorkbook(new File(fileName));
    } 
    this.sheetName = sheetName;
    this.wsheet = this.wbook.createSheet(sheetName, this.sheetIndex++);
  }
  
  public void addSheet(String sheetName) {
    this.irow = Integer.valueOf(-1);
    this.sheetName = sheetName;
    this.wsheet = this.wbook.createSheet(sheetName, this.sheetIndex++);
  }









  
  public void setReportTitle(String reportTitle) {
    try {
      Integer integer1, integer2 = this.irow = (integer1 = this.irow).valueOf(this.irow.intValue() + 1);
      this.wfont = new WritableFont(WritableFont.createFont("宋体"), 12, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
      
      this.wcfFC = new WritableCellFormat(this.wfont);
      this.wcfFC.setAlignment(Alignment.CENTRE);
      
      this.wcfFC.setVerticalAlignment(VerticalAlignment.CENTRE);


      
      this.wsheet.addCell(new Label(this.icol.intValue(), this.irow.intValue(), reportTitle, this.wcfFC));
      this.trow = this.irow;
    } catch (Exception e) {
      logger.error("", e);
      
    } 
  }








  
  @Deprecated
  public void setExcelListTitle(String[] listTitle) throws WriteException, IOException {
    try {
//      Integer integer1, integer2 = this.irow = (integer1 = this.irow).valueOf(this.irow.intValue() + 1); integer1;
      long start = System.currentTimeMillis();
      this.wfont = new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
      
      this.wcfFC = new WritableCellFormat(this.wfont);
      this.wcfFC.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
      this.wcfFC.setAlignment(Alignment.CENTRE);
      this.wcfFC.setVerticalAlignment(VerticalAlignment.CENTRE);
      for (int i = this.icol.intValue(); i < listTitle.length; i++) {
        this.wsheet.addCell(new Label(i, this.irow.intValue(), listTitle[i], this.wcfFC));
      }
      this.trow = this.irow;
      logger.info("title use time:" + (System.currentTimeMillis() - start));
    } catch (Exception e) {
      logger.error("", e);
      close();
    } 
  }









  
  public void addRow(Object[] strings, BorderLineStyle borderLineStyle, Alignment alignment, String bold) throws WriteException, IOException {
    try {
//      Integer integer = this.irow = (null = this.irow).valueOf(this.irow.intValue() + 1); null;
      bold = StringUtils.isEmpty(bold) ? "" : bold;
      for (int i = 0; i < strings.length; i++) {
        if ("bold".equalsIgnoreCase(bold)) {
          this.wfont = new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.BOLD, false);
        } else {
          this.wfont = new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.NO_BOLD, false);
        } 
        this.wcfFC = new WritableCellFormat(this.wfont);
        this.wcfFC.setAlignment(alignment);
        this.wcfFC.setVerticalAlignment(VerticalAlignment.CENTRE);
        if (borderLineStyle == BorderLineStyle.THIN && i == strings.length - 1) {
          this.wcfFC.setBorder(Border.ALL, borderLineStyle);
          this.wcfFC.setBorder(Border.RIGHT, BorderLineStyle.MEDIUM);
        } else {
          this.wcfFC.setBorder(Border.ALL, borderLineStyle);
        } 
        this.wsheet.addCell(new Label(i, this.irow.intValue(), (strings[i] == null) ? "" : strings[i].toString(), this.wcfFC));
      } 
    } catch (Exception e) {
      logger.error("", e);
      close();
    } 
  }









  
  public void addRow(Object[] strings, CellType[] cellTypes, DisplayFormat... dFormat) throws WriteException, IOException {
    try {
//      this.irow = (null = this.irow).valueOf(this.irow.intValue() + 1);
      DisplayFormat format = null;
      for (int i = 0; i < strings.length; i++) {
        if (dFormat != null) {
          if (dFormat.length > i) {
            format = dFormat[i];
          } else if (dFormat.length > 0) {
            format = dFormat[0];
          } 
        }
        addCell(Integer.valueOf(i), this.irow, (strings[i] == null) ? "" : strings[i].toString(), cellTypes[i], format, Boolean.valueOf(false), 
            Boolean.valueOf((i + 1 == strings.length)));
      } 
    } catch (Exception e) {
      logger.error("", e);
      close();
    } 
  }











  
  public void addRows(List<?> infoList, CellType[] cellTypes, DisplayFormat... dFormat) throws WriteException, IOException {
    if (infoList != null && !infoList.isEmpty()) {

      
      DisplayFormat format = null;
      while (!infoList.isEmpty()) {
        if (this.irow.intValue() == 50000) {
          write();
          addSheet(this.sheetName);
        } 
//        null = this.irow = (null = this.irow).valueOf(this.irow.intValue() + 1); null;
        Object[] rowInfo = (Object[])infoList.get(0);
        if (rowInfo.length > this.titleCols.intValue()) {
          this.titleCols = Integer.valueOf(rowInfo.length);
        }
        for (int j = this.icol.intValue(); j < rowInfo.length; j++) {
          CellType cellType; rowInfo[j] = (rowInfo[j] == null) ? "" : rowInfo[j];
          if (cellTypes != null && j < cellTypes.length) {
            cellType = (cellTypes[j] == null) ? CellType.EMPTY : cellTypes[j];
          } else {
            cellType = CellType.EMPTY;
          } 
          if (dFormat != null) {
            if (dFormat.length > j) {
              format = dFormat[j];
            } else if (dFormat.length > 0) {
              format = dFormat[0];
            } 
          }
          addCell(Integer.valueOf(j), this.irow, rowInfo[j], cellType, format, Boolean.valueOf((1 == infoList.size())), Boolean.valueOf((j == rowInfo.length - 1)));
        } 
        infoList.remove(0);
      } 
      try {
        if (this.os != null) {
          this.os.flush();
        }
        if (this.trow.intValue() >= 0)
        {
          this.wsheet.mergeCells(this.icol.intValue(), this.trow.intValue(), this.titleCols.intValue() + this.icol.intValue() - 1, this.trow.intValue());
        }
      } catch (Exception e) {
        logger.error("", e);
        close();
      } 
    } 
  }






  
  public void reportExcel() throws WriteException, IOException {
    logger.info("Use time:" + MathUtil.divide(Long.valueOf(System.currentTimeMillis() - this.startTime), Integer.valueOf(1000)) + "s");
    flush();
    logger.info("ReportExcel Successful!!!");
  }














  
  public void setMergeCells(int col, int row, int toCol, int toRow) throws WriteException, IOException {
    try {
      this.wsheet.mergeCells(col, row, toCol, toRow);
    } catch (Exception e) {
      logger.error("", e);
      close();
    } 
  }






  
  public void close() throws WriteException, IOException {
    if (this.wbook != null) {
      this.wbook.write();
      this.wbook.close();
    } 
    if (this.os != null) {
      this.os.flush();
      this.os.close();
    } 
  }






  
  public void write() throws WriteException, IOException {
    setRowView();
    setColumnView();
    if (this.os != null) {
      this.os.flush();
    }
  }






  
  private void flush() throws WriteException, IOException {
    setRowView();
    setColumnView();
    close();
  }






  
  public void osFlush() throws WriteException, IOException {
    if (this.os != null) {
      this.os.flush();
    }
  }







  
  public void addCell(Integer col, Integer row, Object o, CellType type, DisplayFormat format, Boolean isLastRow, Boolean isLastCols) throws WriteException, IOException {
    WritableFont wfont = new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
    
    try {
      if (o instanceof java.util.ArrayList) {
        Label label = new Label(col.intValue(), row.intValue(), "", this.wcfFC);
        WritableCellFeatures wcf = new WritableCellFeatures();
        if (!((List)o).isEmpty()) {
          wcf.setDataValidationList((List)o);
        }
        label.setCellFeatures(wcf);
        this.wsheet.addCell(label);
      } else {
        
        if (type == CellType.LABEL) {
          wfont = new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
          
          this.wcfFC = new WritableCellFormat(wfont);
          this.wcfFC.setAlignment(Alignment.CENTRE);
        } else if (type == CellType.STRING_FORMULA) {
          this.wcfFC = new WritableCellFormat(wfont);
          this.wcfFC.setAlignment(Alignment.LEFT);
        } else if (type == CellType.NUMBER) {
          this.wcfFC = new WritableCellFormat(wfont, format);
          this.wcfFC.setAlignment(Alignment.RIGHT);
        } else if (type == CellType.DATE || type == CellType.DATE_FORMULA) {
          this.wcfFC = new WritableCellFormat(wfont, format);
          this.wcfFC.setAlignment(Alignment.CENTRE);
        } else {
          this.wcfFC = new WritableCellFormat(wfont);
          this.wcfFC.setAlignment(Alignment.CENTRE);
        } 
        this.wcfFC.setVerticalAlignment(VerticalAlignment.CENTRE);
        this.wcfFC.setBorder(Border.ALL, BorderLineStyle.THIN);
        if (isLastCols.booleanValue()) {
          this.wcfFC.setBorder(Border.RIGHT, BorderLineStyle.MEDIUM);
        }
        if (isLastRow.booleanValue()) {
          this.wcfFC.setBorder(Border.BOTTOM, BorderLineStyle.MEDIUM);
        }
        if (o == null) {
          this.wsheet.addCell(new Label(col.intValue(), row.intValue(), ""));
        } else if (StringUtils.isEmpty(String.valueOf(o))) {
          this.wsheet.addCell(new Label(col.intValue(), row.intValue(), o.toString(), this.wcfFC));
        } else if (type == CellType.NUMBER) {
          this.wsheet.addCell(new Number(col.intValue(), row.intValue(), Double.valueOf(String.valueOf(o)).doubleValue(), this.wcfFC));
        } else if (type == CellType.DATE || type == CellType.DATE_FORMULA) {
          this.wsheet.addCell(new DateTime(col.intValue(), row.intValue(), DateUtil.stringToDate(o.toString()), this.wcfFC));
        } else {
          this.wsheet.addCell(new Label(col.intValue(), row.intValue(), o.toString(), this.wcfFC));
        } 
      } 
    } catch (Exception e) {
      logger.error("", e);
      close();
    } 
  }







  
  private void setRowView() throws WriteException, IOException {
    try {
      for (int i = 0; i < this.wsheet.getRows(); i++) {
        this.wsheet.setRowView(i, (int)(this.wsheet.getRowView(i).getSize() * 1.3D));
      }
    } catch (Exception e) {
      logger.error("", e);
      close();
    } 
  }









  
  private void setColumnView() throws WriteException, IOException {
    for (int i = 0; i < this.wsheet.getRows(); i++) {
      int j; label48: for (j = 0; j < this.wsheet.getColumns(); j++) {
        int infoWidth;
        Range[] range = this.wsheet.getMergedCells();
        for (int k = 0; k < range.length; k++) {
          if (range[k].getTopLeft().getRow() == i && range[k].getTopLeft().getColumn() == j && range[k]
            .getBottomRight().getColumn() != j) {
            continue label48;
          }
        } 
        Cell cell = this.wsheet.getCell(j, i);
        String value = cell.getContents();
        if (cell.getType() == CellType.DATE) {
          infoWidth = (int)Math.round(value.length() * 0.5D);
        } else if (cell.getType() == CellType.NUMBER) {
          int p = 0;
          for (int k = 0; k < value.split("\\.")[0].length(); k++) {
            if (value.charAt(k) == '0') {
              p++;
            }
          } 
          infoWidth = (int)Math.round((value.length() * 2) + p * 0.2D);
        } else if (DataUtil.isNumber(value)) {
          infoWidth = (int)Math.round(value.length() * 1.2D);
        } else if (cell.getCellFormat() != null && cell
          .getCellFormat().getFont().getBoldWeight() == BoldStyle.BOLD.getValue()) {
          infoWidth = (int)Math.round(value.getBytes().length * 1.13D);
        } else if (value.getBytes().length != value.length()) {
          infoWidth = (int)Math.round(value.length() * 1.9D);
        } else {
          infoWidth = (int)Math.round(value.length() * 1.05D);
        } 
        int cellWidth = this.wsheet.getColumnView(j).getSize();
        if (cellWidth < infoWidth) {
          this.wsheet.setColumnView(j, infoWidth);
        }
      } 
    } 
  }

  
  public void setHideCol(int rols) {
    CellView view = new CellView();
    view.setHidden(true);
    this.wsheet.setColumnView(rols, view);
  }

  
  public void setHideRow(int row) {
    CellView view = new CellView();
    view.setHidden(true);
//    this.wsheet.setRowView(row, view);
  }


  
  public void deleteCol(int rols) { this.wsheet.removeColumn(rols); }



  
  public void deleteRow(int row) { this.wsheet.removeRow(row); }


  
  public void setIrow(Integer row) { this.irow = row; }


  
  public int getIrow() { return this.irow.intValue(); }


  
  public void setIcol(Integer col) { this.icol = col; }


  
  public Integer getIcol() { return this.icol; }


  
  public Integer getTitleCols() { return this.titleCols; }


  
  public int getSheetIndex() { return this.sheetIndex; }
}
