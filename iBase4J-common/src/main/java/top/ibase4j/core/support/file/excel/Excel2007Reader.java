package top.ibase4j.core.support.file.excel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;




public class Excel2007Reader
  extends DefaultHandler
{
  private final Logger logger = LogManager.getLogger(); private RowReader rowReader; private SharedStringsTable sst;
  private String currentCellValue;
  private boolean nextIsString;
  
  public void setRowReader(RowReader rowReader) { this.rowReader = rowReader; }


  
  public Excel2007Reader() {}

  
  public Excel2007Reader(RowReader rowReader) { this.rowReader = rowReader; }





  
  private int sheetIndex = -1;
  private List<String> rowlist = new ArrayList();
  private int curRow = 0;
  private int curCol = 0;
  
  public void processOneSheet(String filename, int sheetId) throws Throwable {
    
    try { OPCPackage pkg = OPCPackage.open(filename); 
    Object throwable = null; 
      try { XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();
        
        XMLReader parser = fetchSheetParser(sst);


        
        InputStream sheet2 = r.getSheet("rId" + sheetId);
        this.sheetIndex++;
        InputSource sheetSource = new InputSource(sheet2);
        parser.parse(sheetSource);
        sheet2.close(); } catch (Throwable throwable1) { throwable = throwable1 = null; throw throwable1; }
      finally { if (pkg != null) if (throwable != null) { try { pkg.close(); } catch (Throwable throwable1) { ((Throwable) throwable).addSuppressed(throwable1); }  } else { pkg.close(); }   }  } catch (Exception e)
    { throw e; }
  
  }


  
  public void process(InputStream stream) throws Throwable {
    
    try { OPCPackage pkg = OPCPackage.open(stream); Object throwable = null; 
      try { XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();
        
        XMLReader parser = fetchSheetParser(sst);
        
        Iterator<InputStream> sheets = r.getSheetsData();
        while (sheets.hasNext())
        { this.curRow = 0;
          this.sheetIndex++;
          InputStream sheet = (InputStream)sheets.next();
          InputSource sheetSource = new InputSource(sheet);
          parser.parse(sheetSource);
          sheet.close(); }  }
      catch (Throwable throwable1) { throwable = throwable1 = null; throw throwable1; }
      finally { if (pkg != null) if (throwable != null) { try { pkg.close(); } catch (Throwable throwable1) { ((Throwable) throwable).addSuppressed(throwable1); }  } else { pkg.close(); }   }  } catch (Exception e)
    { throw e; }
  
  }


  
  public void process(String filename) throws Throwable {
    
    try { OPCPackage pkg = OPCPackage.open(filename); Object throwable = null; 
      try { XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();
        
        XMLReader parser = fetchSheetParser(sst);
        
        Iterator<InputStream> sheets = r.getSheetsData();
        while (sheets.hasNext())
        { this.curRow = 0;
          this.sheetIndex++;
          InputStream sheet = (InputStream)sheets.next();
          InputSource sheetSource = new InputSource(sheet);
          parser.parse(sheetSource);
          sheet.close(); }  }
      catch (Throwable throwable1) { throwable = throwable1 = null; throw throwable1; }
      finally { if (pkg != null) if (throwable != null) { try { pkg.close(); } catch (Throwable throwable1) { ((Throwable) throwable).addSuppressed(throwable1); }  } else { pkg.close(); }   }  } catch (Exception e)
    { throw e; }
  
  }
  
  public XMLReader fetchSheetParser(SharedStringsTable sst) {
    try {
      XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
      this.sst = sst;
      parser.setContentHandler(this);
      return parser;
    } catch (SAXException e) {
      throw new RuntimeException(e);
    } 
  }



  
  public void characters(char[] ch, int start, int length) { this.currentCellValue += new String(ch, start, length); }


  
  public void startElement(String uri, String localName, String name, Attributes attributes) {
    startCell(name, attributes);
    
    this.currentCellValue = "";
  }

  
  private void startCell(String name, Attributes attributes) {
    if ("c".equals(name)) {
      
      String cellType = attributes.getValue("t");
      if (cellType != null && "s".equals(cellType)) {
        this.nextIsString = true;
      } else {
        this.nextIsString = false;
      } 
    } 
  }



  
  public void endElement(String uri, String localName, String name) {
    if ("v".equals(name)) {
      try {
		endCellValue(name);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    } else {
      try {
		endRow(name);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    } 
  }
  
  private void endCellValue(String name) throws Exception {
    if (this.nextIsString) {
      try {
        int idx = Integer.parseInt(this.currentCellValue);
        this.currentCellValue = (new XSSFRichTextString(this.sst.getEntryAt(idx))).toString();
      } catch (Exception e) {
        this.logger.error("", e);
      } 
    }
    String value = this.currentCellValue.trim();
    value = "".equals(value) ? " " : value;
    this.rowlist.add(this.curCol, value);
    this.curCol++;
  }

  
  private void endRow(String name) throws Exception {
    if ("row".equals(name)) {
      if (this.rowReader != null && !this.rowlist.isEmpty()) {
        try {
          this.rowReader.invoke(this.sheetIndex, this.curRow, this.rowlist);
        } catch (Exception e) {
          this.logger.error("", "", e);
        } 
      }
      this.rowlist.clear();
      this.curRow++;
      this.curCol = 0;
    } 
  }
}
