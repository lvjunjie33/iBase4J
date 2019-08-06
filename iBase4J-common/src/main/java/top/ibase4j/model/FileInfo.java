package top.ibase4j.model;

import java.io.Serializable;









public class FileInfo
  implements Serializable
{
  private String orgName;
  private String fileType;
  private String fileName;
  private Long fileSize;
  
  public String getOrgName() { return this.orgName; }


  
  public void setOrgName(String orgName) { this.orgName = orgName; }


  
  public String getFileType() { return this.fileType; }


  
  public void setFileType(String fileType) { this.fileType = fileType; }


  
  public String getFileName() { return this.fileName; }


  
  public void setFileName(String fileName) { this.fileName = fileName; }


  
  public Long getFileSize() { return this.fileSize; }


  
  public void setFileSize(Long fileSize) { this.fileSize = fileSize; }
}
