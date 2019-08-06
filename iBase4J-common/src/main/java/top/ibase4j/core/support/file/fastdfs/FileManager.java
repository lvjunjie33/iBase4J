package top.ibase4j.core.support.file.fastdfs;

import com.luhuiguo.fastdfs.conn.ConnectionManager;
import com.luhuiguo.fastdfs.conn.ConnectionPoolConfig;
import com.luhuiguo.fastdfs.conn.FdfsConnectionPool;
import com.luhuiguo.fastdfs.conn.PooledConnectionFactory;
import com.luhuiguo.fastdfs.conn.TrackerConnectionManager;
import com.luhuiguo.fastdfs.service.DefaultFastFileStorageClient;
import com.luhuiguo.fastdfs.service.DefaultTrackerClient;
import com.luhuiguo.fastdfs.service.FastFileStorageClient;
import java.io.Serializable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.ibase4j.core.util.DataUtil;
import top.ibase4j.core.util.InstanceUtil;
import top.ibase4j.core.util.PropertiesUtil;








public class FileManager
  implements Serializable
{
  private static Logger logger = LogManager.getLogger();
  private static FileManager fileManager;
  private FastFileStorageClient fastFileStorageClient;
  
  public static FileManager getInstance() {
    if (fileManager == null) {
      synchronized (FileManager.class) {
        fileManager = new FileManager();
      } 
    }
    return fileManager;
  }
  
  private FileManager() {
    PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
    pooledConnectionFactory.setSoTimeout(PropertiesUtil.getInt("fdfs.soTimeout", 1000));
    pooledConnectionFactory.setConnectTimeout(PropertiesUtil.getInt("fdfs.connectTimeout", 1000));
    ConnectionPoolConfig connectionPoolConfig = new ConnectionPoolConfig();
    FdfsConnectionPool pool = new FdfsConnectionPool(pooledConnectionFactory, connectionPoolConfig);
    
    TrackerConnectionManager trackerConnectionManager = new TrackerConnectionManager(pool, InstanceUtil.newArrayList(PropertiesUtil.getString("fdfs.trackerList").split(",")));
    DefaultTrackerClient defaultTrackerClient = new DefaultTrackerClient(trackerConnectionManager);
    ConnectionManager connectionManager = new ConnectionManager(pool);
    this.fastFileStorageClient = new DefaultFastFileStorageClient(defaultTrackerClient, connectionManager);
  }
  
  public void upload(FileModel file) {
    if (DataUtil.isEmpty(file.getGroupName())) {
      String path = this.fastFileStorageClient.uploadFile(file.getContent(), file.getExt()).getFullPath();
      logger.info("Upload to fastdfs success =>" + path);
      file.setRemotePath(PropertiesUtil.getString("fdfs.fileHost") + path);
    } else {
      
      String path = this.fastFileStorageClient.uploadFile(file.getGroupName(), file.getContent(), file.getExt()).getFullPath();
      logger.info("Upload to fastdfs success =>" + path);
      file.setRemotePath(PropertiesUtil.getString("fdfs.fileHost") + path);
    } 
  }
  
  public FileModel getFile(String groupName, String path) {
    FileModel file = new FileModel();
    file.setContent(this.fastFileStorageClient.downloadFile(groupName, path));
    return file;
  }

  
  public void deleteFile(String groupName, String path) throws Exception { this.fastFileStorageClient.deleteFile(groupName, path); }
}
