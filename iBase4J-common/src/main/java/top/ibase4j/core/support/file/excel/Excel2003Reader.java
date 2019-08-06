package top.ibase4j.core.support.file.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.eventusermodel.EventWorkbookBuilder;
import org.apache.poi.hssf.eventusermodel.FormatTrackingHSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.eventusermodel.MissingRecordAwareHSSFListener;
import org.apache.poi.hssf.eventusermodel.dummyrecord.MissingCellDummyRecord;
import org.apache.poi.hssf.model.HSSFFormulaParser;
import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.BlankRecord;
import org.apache.poi.hssf.record.BoolErrRecord;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.FormulaRecord;
import org.apache.poi.hssf.record.LabelRecord;
import org.apache.poi.hssf.record.LabelSSTRecord;
import org.apache.poi.hssf.record.NumberRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.SSTRecord;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;






public class Excel2003Reader
  implements HSSFListener
{
  private static final Logger logger = LogManager.getLogger();
  private int minColumns = -1;

  
  private int lastRowNumber;
  
  private int lastColumnNumber;
  
  private boolean outputFormulaValues = true;
  
  private EventWorkbookBuilder.SheetRecordCollectingListener workbookBuildingListener;
  
  private HSSFWorkbook stubWorkbook;
  
  private SSTRecord sstRecord;
  
  private FormatTrackingHSSFListener formatListener;
  
  private int sheetIndex = -1;
  private BoundSheetRecord[] orderedBSRs;
  private List<BoundSheetRecord> boundSheetRecords = new ArrayList();
  
  private int nextRow;
  
  private int nextColumn;
  
  private boolean outputNextStringRecord;
  private int curRow = 0;
  
  private List<String> rowlist = new ArrayList();
  
  private String sheetName;
  
  private RowReader rowReader;

  
  public void setRowReader(RowReader rowReader) { this.rowReader = rowReader; }


  
  public Excel2003Reader() {}

  
  public Excel2003Reader(RowReader rowReader) { this.rowReader = rowReader; }






  
  public void process(String fileName) throws Throwable {
    try {
      process(new FileInputStream(fileName));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } 
  }
  
  public void process(InputStream stream) throws Throwable { 
    try { 
    	POIFSFileSystem fs = new POIFSFileSystem(stream); 
    	Object throwable = null; 
      try { MissingRecordAwareHSSFListener listener = new MissingRecordAwareHSSFListener(this);
        this.formatListener = new FormatTrackingHSSFListener(listener);
        HSSFEventFactory factory = new HSSFEventFactory();
        HSSFRequest request = new HSSFRequest();
        if (this.outputFormulaValues) {
          request.addListenerForAllRecords(this.formatListener);
        } else {
          this.workbookBuildingListener = new EventWorkbookBuilder.SheetRecordCollectingListener(this.formatListener);
          request.addListenerForAllRecords(this.workbookBuildingListener);
        } 
        factory.processWorkbookEvents(request, fs); } catch (Throwable throwable1) { throwable = throwable1 = null; throw throwable1; }
      finally { if (fs != null) if (throwable != null) { try { fs.close(); } catch (Throwable throwable1) {
//    	  throwable.addSuppressed(throwable1); 
      }  } else { fs.close(); }   }  } catch (IOException e)
    { throw new RuntimeException(e); }
     } public void processRecord(Record record) {
    NumberRecord numrec;
    LabelSSTRecord lsrec;
    LabelRecord lrec;
    FormulaRecord frec;
    BoolErrRecord berec;
    BlankRecord brec;
    String value;
    int thisRow = -1;
    int thisColumn = -1;
    String thisStr = null;
    
    BOFRecord br;
	switch (record.getSid()) {
      case 133:
        this.boundSheetRecords.add((BoundSheetRecord)record);
        break;
      case 2057:
        br = (BOFRecord)record;
        if (br.getType() == 16) {
          
          if (this.workbookBuildingListener != null && this.stubWorkbook == null) {
            this.stubWorkbook = this.workbookBuildingListener.getStubHSSFWorkbook();
          }
          
          this.sheetIndex++;
          if (this.orderedBSRs == null) {
            this.orderedBSRs = BoundSheetRecord.orderByBofPosition(this.boundSheetRecords);
          }
          this.sheetName = this.orderedBSRs[this.sheetIndex].getSheetname();
        } 
        break;
      
      case 252:
        this.sstRecord = (SSTRecord)record;
        break;
      
      case 513:
        brec = (BlankRecord)record;
        thisRow = brec.getRow();
        thisColumn = brec.getColumn();
        thisStr = "";
        this.rowlist.add(thisColumn, thisStr);
        break;
      case 517:
        berec = (BoolErrRecord)record;
        thisRow = berec.getRow();
        thisColumn = berec.getColumn();
        thisStr = berec.getBooleanValue() + "";
        this.rowlist.add(thisColumn, thisStr);
        break;
      
      case 6:
        frec = (FormulaRecord)record;
        thisRow = frec.getRow();
        thisColumn = frec.getColumn();
        if (this.outputFormulaValues) {
          if (Double.isNaN(frec.getValue())) {

            
            this.outputNextStringRecord = true;
            this.nextRow = frec.getRow();
            this.nextColumn = frec.getColumn();
          } else {
            thisStr = this.formatListener.formatNumberDateCell(frec);
          } 
        } else {
          thisStr = '"' + HSSFFormulaParser.toFormulaString(this.stubWorkbook, frec.getParsedExpression()) + '"';
        } 
        this.rowlist.add(thisColumn, thisStr);
        break;
      case 519:
        if (this.outputNextStringRecord) {
          
          thisRow = this.nextRow;
          thisColumn = this.nextColumn;
          this.outputNextStringRecord = false;
        } 
        break;
      case 516:
        lrec = (LabelRecord)record;
        this.curRow = thisRow = lrec.getRow();
        thisColumn = lrec.getColumn();
        value = lrec.getValue().trim();
        value = "".equals(value) ? " " : value;
        this.rowlist.add(thisColumn, value);
        break;
      case 253:
        lsrec = (LabelSSTRecord)record;
        this.curRow = thisRow = lsrec.getRow();
        thisColumn = lsrec.getColumn();
        if (this.sstRecord == null) {
          this.rowlist.add(thisColumn, " "); break;
        } 
        value = this.sstRecord.getString(lsrec.getSSTIndex()).toString().trim();
        value = "".equals(value) ? " " : value;
        this.rowlist.add(thisColumn, value);
        break;
      
      case 515:
        numrec = (NumberRecord)record;
        this.curRow = thisRow = numrec.getRow();
        thisColumn = numrec.getColumn();
        value = this.formatListener.formatNumberDateCell(numrec).trim();
        value = "".equals(value) ? " " : value;
        
        this.rowlist.add(thisColumn, value);
        break;
    } 



    
    if (thisRow != -1 && thisRow != this.lastRowNumber) {
      this.lastColumnNumber = -1;
    }

    
    if (record instanceof MissingCellDummyRecord) {
      MissingCellDummyRecord mc = (MissingCellDummyRecord)record;
      this.curRow = thisRow = mc.getRow();
      thisColumn = mc.getColumn();
      this.rowlist.add(thisColumn, " ");
    } 

    
    if (thisRow > -1) {
      this.lastRowNumber = thisRow;
    }
    if (thisColumn > -1) {
      this.lastColumnNumber = thisColumn;
    }

    
    if (record instanceof org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord) {
      if (this.minColumns > 0)
      {
        if (this.lastColumnNumber == -1) {
          this.lastColumnNumber = 0;
        }
      }
      this.lastColumnNumber = -1;
      if (this.rowReader != null && !this.rowlist.isEmpty()) {
        try {
          this.rowReader.invoke(this.sheetIndex, this.curRow, this.rowlist);
        } catch (Exception s) {
          Exception e; logger.error("", "", s);
        } 
      }
      
      this.rowlist.clear();
    } 
  }
}
