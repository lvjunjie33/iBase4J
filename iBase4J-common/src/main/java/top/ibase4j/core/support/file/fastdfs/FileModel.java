package top.ibase4j.core.support.file.fastdfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import javax.activation.MimetypesFileTypeMap;








public class FileModel
  implements Serializable
{
  private String groupName;
  private byte[] content;
  private String ext;
  private String mime;
  private String size;
  private String filename;
  private String remotePath;
  
  public FileModel() {}
  
  public FileModel(String filePath) { this(null, filePath); }

  
  public FileModel(String groupName, String filePath) {
    this.groupName = groupName;
    if (filePath != null && !"".equals(filePath.trim())) {
      this.ext = filePath.substring(filePath.lastIndexOf(".") + 1);
      InputStream is = null;
      byte[] fileFuff = null;
      FileInputStream fileInputStream = null;
      try {
    	  File file = new File(filePath);
        this.size = String.valueOf(file.length());
        this.filename = file.getName();
        
        fileInputStream = new FileInputStream(file);
        if (fileInputStream != null) {
          int len = fileInputStream.available();
          fileFuff = new byte[len];
          fileInputStream.read(fileFuff);
        } 
        this.content = fileFuff;
        is = getClass().getResourceAsStream("/config/mime.types");
        MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap(is);
        this.mime = mimetypesFileTypeMap.getContentType(this.filename);
      } catch (Exception e) {
        throw new RuntimeException(e);
      } finally {
        if (is != null) {
          try {
            is.close();
          } catch (IOException iOException) {}
        }
        
        if (fileInputStream != null) {
          try {
            fileInputStream.close();
          } catch (IOException iOException) {}
        }
      } 
    } 
  }


  
  public String getGroupName() { return this.groupName; }


  
  public void setGroupName(String groupName) { this.groupName = groupName; }


  
  protected byte[] getContent() { return this.content; }


  
  protected void setContent(byte[] content) { this.content = content; }


  
  protected String getExt() { return this.ext; }


  
  protected void setExt(String ext) { this.ext = ext; }


  
  protected String getMime() { return this.mime; }


  
  protected void setMime(String mime) { this.mime = mime; }


  
  public String getSize() { return this.size; }


  
  protected void setSize(String size) { this.size = size; }


  
  protected String getFilename() { return this.filename; }


  
  protected void setFilename(String filename) { this.filename = filename; }


  
  public String getRemotePath() { return this.remotePath; }


  
  protected void setRemotePath(String remotePath) { this.remotePath = remotePath; }
}
