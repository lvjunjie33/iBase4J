package top.ibase4j.core.support.file.ftp;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.ibase4j.core.exception.FtpException;












public class FtpClient
{
  private Logger logger = LogManager.getLogger();
  private static final byte[] LOCK = { 0 };
  private static FTPClient ftpClient = null;
  private Properties properties = null;
  private static final String FILELOCK = "Token.lock";
  
  public static void main(String[] args) throws Exception {
    String host = "10.116.1.65";
    int port = 21;
    String username = "ftpUser";
    String password = "123456";
    String localUpPath = "C:/bankData/Send/";
    String localDnPath = "C:/bankData/Feedback";
    String remotePath = "Feedback";
    FtpClient ftpClient = new FtpClient(host, port, username, password);

    
    ftpClient.uploadFile("send", localUpPath);
    
    ftpClient.downLoadFile(remotePath, localDnPath);
    
    ftpClient.close();
  }






  
  public FtpClient() {}





  
  public FtpClient(String host, int port, String username, String password) throws FtpException { init(host, port, username, password); }




  
  public void open() {
    init(this.properties.getProperty("FTPHOSTNAME"), Integer.valueOf(this.properties.getProperty("FTPPORT")).intValue(), this.properties
        .getProperty("FTPUSERNAME"), this.properties.getProperty("FTPPASSWORD"));
  }








  
  private void init(String host, int port, String username, String password) throws FtpException {
    synchronized (LOCK) {
      if (ftpClient == null) {
        ftpClient = new FTPClient();
      }
      try {
        ftpClient.connect(host, port);
      } catch (Exception e) {
        throw new FtpException("FTP[" + host + ":" + port + "]连接失败!", e);
      } 
      
      if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
        try {
          ftpClient.login(username, password);
        } catch (Exception e) {
          throw new FtpException("FTP用户[" + username + "]登陆失败!", e);
        } 
      } else {
        throw new FtpException("FTP连接出错!");
      } 
      this.logger.info("用户[" + username + "]登陆[" + host + "]成功.");
      this.properties.setProperty("userName", username);
      this.properties.setProperty("hostName", host);
      
      try {
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileTransferMode(10);
        ftpClient.setFileType(2);
      } catch (Exception e) {
        this.logger.error("", e);
        throw new FtpException("FTP初始化出错!", e);
      } 
    } 
  }




  
  public void close() {
    synchronized (LOCK) {
      try {
        ftpClient.logout();
      } catch (IOException e) {
        this.logger.error("", e);
        ftpClient = null;
        throw new FtpException("FTP退出登录出错!", e);
      } 
      this.logger.info("用户[" + this.properties
          .getProperty("userName") + "]退出登录[" + this.properties.getProperty("hostName") + "].");
    } 
  }








  
  public boolean uploadFile(String remotePath, String localPath) throws FtpException {
    synchronized (LOCK) {
      File file = new File(localPath);
      File[] files = file.listFiles();
      for (int i = 0; i < files.length; i++) {
        if (!uploadFiles(files[i], remotePath)) {
          return false;
        }
      } 
      return (files.length > 0);
    } 
  }



































  
  public boolean uploadFiles(File localeFile, String remotePath) throws FtpException {
	return false; 
	  // Byte code:
  }
    //   0: getstatic top/ibase4j/core/support/file/ftp/FtpClient.LOCK : [B
    //   3: dup
    //   4: astore_3
    //   5: monitorenter
    //   6: aconst_null
    //   7: astore #4
    //   9: aload_1
    //   10: invokevirtual isDirectory : ()Z
    //   13: ifeq -> 210
    //   16: iconst_0
    //   17: istore #5
    //   19: getstatic top/ibase4j/core/support/file/ftp/FtpClient.ftpClient : Lorg/apache/commons/net/ftp/FTPClient;
    //   22: aload_1
    //   23: invokevirtual getName : ()Ljava/lang/String;
    //   26: invokevirtual makeDirectory : (Ljava/lang/String;)Z
    //   29: pop
    //   30: getstatic top/ibase4j/core/support/file/ftp/FtpClient.ftpClient : Lorg/apache/commons/net/ftp/FTPClient;
    //   33: aload_1
    //   34: invokevirtual getName : ()Ljava/lang/String;
    //   37: invokevirtual changeWorkingDirectory : (Ljava/lang/String;)Z
    //   40: pop
    //   41: aload_0
    //   42: getfield logger : Lorg/apache/logging/log4j/Logger;
    //   45: new java/lang/StringBuilder
    //   48: dup
    //   49: invokespecial <init> : ()V
    //   52: ldc '['
    //   54: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   57: aload_1
    //   58: invokevirtual getAbsolutePath : ()Ljava/lang/String;
    //   61: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   64: ldc ']目录'
    //   66: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   69: invokevirtual toString : ()Ljava/lang/String;
    //   72: invokeinterface info : (Ljava/lang/String;)V
    //   77: aload_1
    //   78: invokevirtual listFiles : ()[Ljava/io/File;
    //   81: astore #6
    //   83: aload #6
    //   85: astore #7
    //   87: aload #7
    //   89: arraylength
    //   90: istore #8
    //   92: iconst_0
    //   93: istore #9
    //   95: iload #9
    //   97: iload #8
    //   99: if_icmpge -> 179
    //   102: aload #7
    //   104: iload #9
    //   106: aaload
    //   107: astore #10
    //   109: aload_0
    //   110: aload #10
    //   112: new java/lang/StringBuilder
    //   115: dup
    //   116: invokespecial <init> : ()V
    //   119: aload_2
    //   120: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   123: ldc '/'
    //   125: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   128: aload_1
    //   129: invokevirtual getName : ()Ljava/lang/String;
    //   132: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   135: invokevirtual toString : ()Ljava/lang/String;
    //   138: invokevirtual uploadFiles : (Ljava/io/File;Ljava/lang/String;)Z
    //   141: ifeq -> 150
    //   144: iconst_1
    //   145: istore #5
    //   147: goto -> 173
    //   150: iconst_0
    //   151: istore #11
    //   153: aload #4
    //   155: ifnull -> 168
    //   158: aload #4
    //   160: invokevirtual close : ()V
    //   163: goto -> 168
    //   166: astore #12
    //   168: aload_3
    //   169: monitorexit
    //   170: iload #11
    //   172: ireturn
    //   173: iinc #9, 1
    //   176: goto -> 95
    //   179: getstatic top/ibase4j/core/support/file/ftp/FtpClient.ftpClient : Lorg/apache/commons/net/ftp/FTPClient;
    //   182: invokevirtual changeToParentDirectory : ()Z
    //   185: pop
    //   186: iload #5
    //   188: istore #7
    //   190: aload #4
    //   192: ifnull -> 205
    //   195: aload #4
    //   197: invokevirtual close : ()V
    //   200: goto -> 205
    //   203: astore #8
    //   205: aload_3
    //   206: monitorexit
    //   207: iload #7
    //   209: ireturn
    //   210: ldc 'Token.lock'
    //   212: aload_1
    //   213: invokevirtual getName : ()Ljava/lang/String;
    //   216: invokevirtual equals : (Ljava/lang/Object;)Z
    //   219: ifne -> 304
    //   222: new java/io/FileInputStream
    //   225: dup
    //   226: aload_1
    //   227: invokespecial <init> : (Ljava/io/File;)V
    //   230: astore #4
    //   232: getstatic top/ibase4j/core/support/file/ftp/FtpClient.ftpClient : Lorg/apache/commons/net/ftp/FTPClient;
    //   235: aload_1
    //   236: invokevirtual getName : ()Ljava/lang/String;
    //   239: aload #4
    //   241: invokevirtual storeFile : (Ljava/lang/String;Ljava/io/InputStream;)Z
    //   244: pop
    //   245: aload_0
    //   246: getfield logger : Lorg/apache/logging/log4j/Logger;
    //   249: new java/lang/StringBuilder
    //   252: dup
    //   253: invokespecial <init> : ()V
    //   256: ldc '['
    //   258: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   261: aload_1
    //   262: invokevirtual getAbsolutePath : ()Ljava/lang/String;
    //   265: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   268: ldc ']上传成功!'
    //   270: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   273: invokevirtual toString : ()Ljava/lang/String;
    //   276: invokeinterface info : (Ljava/lang/String;)V
    //   281: iconst_1
    //   282: istore #5
    //   284: aload #4
    //   286: ifnull -> 299
    //   289: aload #4
    //   291: invokevirtual close : ()V
    //   294: goto -> 299
    //   297: astore #6
    //   299: aload_3
    //   300: monitorexit
    //   301: iload #5
    //   303: ireturn
    //   304: iconst_1
    //   305: istore #5
    //   307: aload #4
    //   309: ifnull -> 322
    //   312: aload #4
    //   314: invokevirtual close : ()V
    //   317: goto -> 322
    //   320: astore #6
    //   322: aload_3
    //   323: monitorexit
    //   324: iload #5
    //   326: ireturn
    //   327: astore #5
    //   329: aload_0
    //   330: getfield logger : Lorg/apache/logging/log4j/Logger;
    //   333: ldc ''
    //   335: aload #5
    //   337: invokeinterface error : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   342: new top/ibase4j/core/exception/FtpException
    //   345: dup
    //   346: new java/lang/StringBuilder
    //   349: dup
    //   350: invokespecial <init> : ()V
    //   353: ldc 'FTP上传['
    //   355: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   358: aload_1
    //   359: invokevirtual getAbsolutePath : ()Ljava/lang/String;
    //   362: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   365: ldc ']出错!'
    //   367: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   370: invokevirtual toString : ()Ljava/lang/String;
    //   373: aload #5
    //   375: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   378: athrow
    //   379: astore #13
    //   381: aload #4
    //   383: ifnull -> 396
    //   386: aload #4
    //   388: invokevirtual close : ()V
    //   391: goto -> 396
    //   394: astore #14
    //   396: aload #13
    //   398: athrow
    //   399: astore #15
    //   401: aload_3
    //   402: monitorexit
    //   403: aload #15
    //   405: athrow
    // Line number table:
    //   Java source line number -> byte code offset
    //   #168	-> 0
    //   #169	-> 6
    //   #171	-> 9
    //   #172	-> 16
    //   #173	-> 19
    //   #174	-> 30
    //   #175	-> 41
    //   #176	-> 77
    //   #177	-> 83
    //   #178	-> 109
    //   #179	-> 144
    //   #181	-> 150
    //   #198	-> 153
    //   #200	-> 158
    //   #202	-> 163
    //   #201	-> 166
    //   #202	-> 168
    //   #181	-> 170
    //   #177	-> 173
    //   #184	-> 179
    //   #186	-> 186
    //   #198	-> 190
    //   #200	-> 195
    //   #202	-> 200
    //   #201	-> 203
    //   #202	-> 205
    //   #186	-> 207
    //   #187	-> 210
    //   #188	-> 222
    //   #189	-> 232
    //   #190	-> 245
    //   #191	-> 281
    //   #198	-> 284
    //   #200	-> 289
    //   #202	-> 294
    //   #201	-> 297
    //   #202	-> 299
    //   #191	-> 301
    //   #193	-> 304
    //   #198	-> 307
    //   #200	-> 312
    //   #202	-> 317
    //   #201	-> 320
    //   #202	-> 322
    //   #193	-> 324
    //   #194	-> 327
    //   #195	-> 329
    //   #196	-> 342
    //   #198	-> 379
    //   #200	-> 386
    //   #202	-> 391
    //   #201	-> 394
    //   #204	-> 396
    //   #205	-> 399
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   109	64	10	file	Ljava/io/File;
    //   19	191	5	flag	Z
    //   83	127	6	files	[Ljava/io/File;
    //   329	50	5	e	Ljava/io/IOException;
    //   9	390	4	fis	Ljava/io/FileInputStream;
    //   0	406	0	this	Ltop/ibase4j/core/support/file/ftp/FtpClient;
    //   0	406	1	localeFile	Ljava/io/File;
    //   0	406	2	remotePath	Ljava/lang/String;
    // Exception table:
    //   from	to	target	type
    //   6	170	399	finally
    //   9	153	327	java/io/IOException
    //   9	153	379	finally
    //   158	163	166	java/io/IOException
    //   173	190	327	java/io/IOException
    //   173	190	379	finally
    //   173	207	399	finally
    //   195	200	203	java/io/IOException
    //   210	284	327	java/io/IOException
    //   210	284	379	finally
    //   210	301	399	finally
    //   289	294	297	java/io/IOException
    //   304	307	327	java/io/IOException
    //   304	307	379	finally
    //   304	324	399	finally
    //   312	317	320	java/io/IOException
    //   327	381	379	finally
    //   327	403	399	finally
    //   386	391	394	java/io/IOException }



































  
  public boolean downLoadFile(String remotePath, String localPath) throws FtpException {
	return false;} // Byte code:
    //   0: getstatic top/ibase4j/core/support/file/ftp/FtpClient.LOCK : [B
    //   3: dup
    //   4: astore_3
    //   5: monitorenter
    //   6: getstatic top/ibase4j/core/support/file/ftp/FtpClient.ftpClient : Lorg/apache/commons/net/ftp/FTPClient;
    //   9: aload_1
    //   10: invokevirtual changeWorkingDirectory : (Ljava/lang/String;)Z
    //   13: ifeq -> 114
    //   16: getstatic top/ibase4j/core/support/file/ftp/FtpClient.ftpClient : Lorg/apache/commons/net/ftp/FTPClient;
    //   19: invokevirtual listFiles : ()[Lorg/apache/commons/net/ftp/FTPFile;
    //   22: astore #4
    //   24: aload #4
    //   26: arraylength
    //   27: ifle -> 54
    //   30: new java/io/File
    //   33: dup
    //   34: aload_2
    //   35: invokespecial <init> : (Ljava/lang/String;)V
    //   38: astore #5
    //   40: aload #5
    //   42: invokevirtual exists : ()Z
    //   45: ifne -> 54
    //   48: aload #5
    //   50: invokevirtual mkdir : ()Z
    //   53: pop
    //   54: aload #4
    //   56: astore #5
    //   58: aload #5
    //   60: arraylength
    //   61: istore #6
    //   63: iconst_0
    //   64: istore #7
    //   66: iload #7
    //   68: iload #6
    //   70: if_icmpge -> 100
    //   73: aload #5
    //   75: iload #7
    //   77: aaload
    //   78: astore #8
    //   80: aload_0
    //   81: aload #8
    //   83: aload_2
    //   84: invokevirtual downLoadFile : (Lorg/apache/commons/net/ftp/FTPFile;Ljava/lang/String;)Z
    //   87: ifne -> 94
    //   90: iconst_0
    //   91: aload_3
    //   92: monitorexit
    //   93: ireturn
    //   94: iinc #7, 1
    //   97: goto -> 66
    //   100: aload #4
    //   102: arraylength
    //   103: ifle -> 110
    //   106: iconst_1
    //   107: goto -> 111
    //   110: iconst_0
    //   111: aload_3
    //   112: monitorexit
    //   113: ireturn
    //   114: goto -> 166
    //   117: astore #4
    //   119: aload_0
    //   120: getfield logger : Lorg/apache/logging/log4j/Logger;
    //   123: ldc ''
    //   125: aload #4
    //   127: invokeinterface error : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   132: new top/ibase4j/core/exception/FtpException
    //   135: dup
    //   136: new java/lang/StringBuilder
    //   139: dup
    //   140: invokespecial <init> : ()V
    //   143: ldc 'FTP下载['
    //   145: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   148: aload_2
    //   149: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   152: ldc ']出错!'
    //   154: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   157: invokevirtual toString : ()Ljava/lang/String;
    //   160: aload #4
    //   162: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   165: athrow
    //   166: iconst_0
    //   167: aload_3
    //   168: monitorexit
    //   169: ireturn
    //   170: astore #9
    //   172: aload_3
    //   173: monitorexit
    //   174: aload #9
    //   176: athrow
    // Line number table:
    //   Java source line number -> byte code offset
    //   #217	-> 0
    //   #219	-> 6
    //   #220	-> 16
    //   #221	-> 24
    //   #222	-> 30
    //   #223	-> 40
    //   #224	-> 48
    //   #227	-> 54
    //   #228	-> 80
    //   #229	-> 90
    //   #227	-> 94
    //   #232	-> 100
    //   #237	-> 114
    //   #234	-> 117
    //   #235	-> 119
    //   #236	-> 132
    //   #238	-> 166
    //   #239	-> 170
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   40	14	5	localdir	Ljava/io/File;
    //   80	14	8	ff	Lorg/apache/commons/net/ftp/FTPFile;
    //   24	90	4	files	[Lorg/apache/commons/net/ftp/FTPFile;
    //   119	47	4	e	Ljava/io/IOException;
    //   0	177	0	this	Ltop/ibase4j/core/support/file/ftp/FtpClient;
    //   0	177	1	remotePath	Ljava/lang/String;
    //   0	177	2	localPath	Ljava/lang/String;
    // Exception table:
    //   from	to	target	type
    //   6	91	117	java/io/IOException
    //   6	93	170	finally
    //   94	111	117	java/io/IOException
    //   94	113	170	finally
    //   114	169	170	finally
    //   170	174	170	finally }



































  
  public boolean downLoadFile(FTPFile ftpFile, String localPath) {
	return false; }// Byte code:
    //   0: new java/lang/StringBuilder
    //   3: dup
    //   4: invokespecial <init> : ()V
    //   7: aload_2
    //   8: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   11: ldc '/'
    //   13: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   16: aload_1
    //   17: invokevirtual getName : ()Ljava/lang/String;
    //   20: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   23: invokevirtual toString : ()Ljava/lang/String;
    //   26: astore_3
    //   27: aload_1
    //   28: invokevirtual isFile : ()Z
    //   31: ifeq -> 241
    //   34: aload_1
    //   35: invokevirtual getName : ()Ljava/lang/String;
    //   38: ldc '?'
    //   40: invokevirtual indexOf : (Ljava/lang/String;)I
    //   43: iconst_m1
    //   44: if_icmpne -> 427
    //   47: aconst_null
    //   48: astore #4
    //   50: new java/io/File
    //   53: dup
    //   54: aload_3
    //   55: invokespecial <init> : (Ljava/lang/String;)V
    //   58: astore #5
    //   60: aload #5
    //   62: invokevirtual getParentFile : ()Ljava/io/File;
    //   65: invokevirtual exists : ()Z
    //   68: ifne -> 80
    //   71: aload #5
    //   73: invokevirtual getParentFile : ()Ljava/io/File;
    //   76: invokevirtual mkdirs : ()Z
    //   79: pop
    //   80: new java/io/FileOutputStream
    //   83: dup
    //   84: aload #5
    //   86: invokespecial <init> : (Ljava/io/File;)V
    //   89: astore #4
    //   91: getstatic top/ibase4j/core/support/file/ftp/FtpClient.ftpClient : Lorg/apache/commons/net/ftp/FTPClient;
    //   94: aload_1
    //   95: invokevirtual getName : ()Ljava/lang/String;
    //   98: aload #4
    //   100: invokevirtual retrieveFile : (Ljava/lang/String;Ljava/io/OutputStream;)Z
    //   103: pop
    //   104: aload #4
    //   106: invokevirtual flush : ()V
    //   109: aload #4
    //   111: invokevirtual close : ()V
    //   114: aload_0
    //   115: getfield logger : Lorg/apache/logging/log4j/Logger;
    //   118: new java/lang/StringBuilder
    //   121: dup
    //   122: invokespecial <init> : ()V
    //   125: ldc '['
    //   127: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   130: aload #5
    //   132: invokevirtual getAbsolutePath : ()Ljava/lang/String;
    //   135: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   138: ldc ']下载成功!'
    //   140: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   143: invokevirtual toString : ()Ljava/lang/String;
    //   146: invokeinterface info : (Ljava/lang/String;)V
    //   151: iconst_1
    //   152: istore #6
    //   154: aload #4
    //   156: ifnull -> 164
    //   159: aload #4
    //   161: invokevirtual close : ()V
    //   164: goto -> 169
    //   167: astore #7
    //   169: iload #6
    //   171: ireturn
    //   172: astore #5
    //   174: aload_0
    //   175: getfield logger : Lorg/apache/logging/log4j/Logger;
    //   178: ldc ''
    //   180: aload #5
    //   182: invokeinterface error : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   187: new top/ibase4j/core/exception/FtpException
    //   190: dup
    //   191: new java/lang/StringBuilder
    //   194: dup
    //   195: invokespecial <init> : ()V
    //   198: ldc 'FTP下载['
    //   200: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   203: aload_3
    //   204: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   207: ldc ']出错!'
    //   209: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   212: invokevirtual toString : ()Ljava/lang/String;
    //   215: aload #5
    //   217: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   220: athrow
    //   221: astore #8
    //   223: aload #4
    //   225: ifnull -> 233
    //   228: aload #4
    //   230: invokevirtual close : ()V
    //   233: goto -> 238
    //   236: astore #9
    //   238: aload #8
    //   240: athrow
    //   241: new java/io/File
    //   244: dup
    //   245: aload_3
    //   246: invokespecial <init> : (Ljava/lang/String;)V
    //   249: astore #4
    //   251: aload #4
    //   253: invokevirtual exists : ()Z
    //   256: ifne -> 265
    //   259: aload #4
    //   261: invokevirtual mkdirs : ()Z
    //   264: pop
    //   265: getstatic top/ibase4j/core/support/file/ftp/FtpClient.ftpClient : Lorg/apache/commons/net/ftp/FTPClient;
    //   268: aload_1
    //   269: invokevirtual getName : ()Ljava/lang/String;
    //   272: invokevirtual changeWorkingDirectory : (Ljava/lang/String;)Z
    //   275: ifeq -> 375
    //   278: aload_0
    //   279: getfield logger : Lorg/apache/logging/log4j/Logger;
    //   282: new java/lang/StringBuilder
    //   285: dup
    //   286: invokespecial <init> : ()V
    //   289: ldc '['
    //   291: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   294: aload #4
    //   296: invokevirtual getAbsolutePath : ()Ljava/lang/String;
    //   299: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   302: ldc ']目录'
    //   304: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   307: invokevirtual toString : ()Ljava/lang/String;
    //   310: invokeinterface info : (Ljava/lang/String;)V
    //   315: aconst_null
    //   316: astore #5
    //   318: getstatic top/ibase4j/core/support/file/ftp/FtpClient.ftpClient : Lorg/apache/commons/net/ftp/FTPClient;
    //   321: invokevirtual listFiles : ()[Lorg/apache/commons/net/ftp/FTPFile;
    //   324: astore #5
    //   326: aload #5
    //   328: astore #6
    //   330: aload #6
    //   332: arraylength
    //   333: istore #7
    //   335: iconst_0
    //   336: istore #8
    //   338: iload #8
    //   340: iload #7
    //   342: if_icmpge -> 366
    //   345: aload #6
    //   347: iload #8
    //   349: aaload
    //   350: astore #9
    //   352: aload_0
    //   353: aload #9
    //   355: aload_3
    //   356: invokevirtual downLoadFile : (Lorg/apache/commons/net/ftp/FTPFile;Ljava/lang/String;)Z
    //   359: pop
    //   360: iinc #8, 1
    //   363: goto -> 338
    //   366: getstatic top/ibase4j/core/support/file/ftp/FtpClient.ftpClient : Lorg/apache/commons/net/ftp/FTPClient;
    //   369: invokevirtual changeToParentDirectory : ()Z
    //   372: pop
    //   373: iconst_1
    //   374: ireturn
    //   375: goto -> 427
    //   378: astore #5
    //   380: aload_0
    //   381: getfield logger : Lorg/apache/logging/log4j/Logger;
    //   384: ldc ''
    //   386: aload #5
    //   388: invokeinterface error : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   393: new top/ibase4j/core/exception/FtpException
    //   396: dup
    //   397: new java/lang/StringBuilder
    //   400: dup
    //   401: invokespecial <init> : ()V
    //   404: ldc 'FTP下载['
    //   406: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   409: aload_3
    //   410: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   413: ldc ']出错!'
    //   415: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   418: invokevirtual toString : ()Ljava/lang/String;
    //   421: aload #5
    //   423: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   426: athrow
    //   427: iconst_0
    //   428: ireturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #251	-> 0
    //   #252	-> 27
    //   #253	-> 34
    //   #254	-> 47
    //   #256	-> 50
    //   #257	-> 60
    //   #258	-> 71
    //   #260	-> 80
    //   #261	-> 91
    //   #262	-> 104
    //   #263	-> 109
    //   #264	-> 114
    //   #265	-> 151
    //   #271	-> 154
    //   #272	-> 159
    //   #275	-> 164
    //   #274	-> 167
    //   #265	-> 169
    //   #266	-> 172
    //   #267	-> 174
    //   #268	-> 187
    //   #270	-> 221
    //   #271	-> 223
    //   #272	-> 228
    //   #275	-> 233
    //   #274	-> 236
    //   #276	-> 238
    //   #279	-> 241
    //   #280	-> 251
    //   #281	-> 259
    //   #285	-> 265
    //   #286	-> 278
    //   #287	-> 315
    //   #288	-> 318
    //   #289	-> 326
    //   #290	-> 352
    //   #289	-> 360
    //   #292	-> 366
    //   #294	-> 373
    //   #299	-> 375
    //   #296	-> 378
    //   #297	-> 380
    //   #298	-> 393
    //   #301	-> 427
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   60	112	5	localFile	Ljava/io/File;
    //   174	47	5	e	Ljava/lang/Exception;
    //   50	191	4	outputStream	Ljava/io/OutputStream;
    //   352	8	9	file2	Lorg/apache/commons/net/ftp/FTPFile;
    //   318	57	5	files	[Lorg/apache/commons/net/ftp/FTPFile;
    //   380	47	5	e	Ljava/lang/Exception;
    //   251	176	4	file	Ljava/io/File;
    //   0	429	0	this	Ltop/ibase4j/core/support/file/ftp/FtpClient;
    //   0	429	1	ftpFile	Lorg/apache/commons/net/ftp/FTPFile;
    //   0	429	2	localPath	Ljava/lang/String;
    //   27	402	3	fileLocalPath	Ljava/lang/String;
    // Exception table:
    //   from	to	target	type
    //   50	154	172	java/lang/Exception
    //   50	154	221	finally
    //   154	164	167	java/io/IOException
    //   172	223	221	finally
    //   223	233	236	java/io/IOException
    //   265	374	378	java/lang/Exception }



































  
  public String getMaxFileName(String remotePath) {
    try {
      ftpClient.changeWorkingDirectory(remotePath);
      FTPFile[] files = ftpClient.listFiles();
      Arrays.sort(files, new Comparator<FTPFile>()
          {
            public int compare(FTPFile o1, FTPFile o2) {
              return o2.getName().compareTo(o1.getName());
            }
          });
      return files[0].getName();
    } catch (IOException e) {
      this.logger.error("", e);
      throw new FtpException("FTP访问目录[" + remotePath + "]出错!", e);
    } 
  }







  
  public void setProperties(Properties properties) { this.properties = properties; }
  
  public void setProperties(Map<String, String> properties) {
  // Byte code:
  }
    //   0: aload_0
    //   1: new java/util/Properties
    //   4: dup
    //   5: invokespecial <init> : ()V
    //   8: putfield properties : Ljava/util/Properties;
    //   11: iconst_4
    //   12: anewarray java/lang/String
    //   15: dup
    //   16: iconst_0
    //   17: ldc 'FTPHOSTNAME'
    //   19: aastore
    //   20: dup
    //   21: iconst_1
    //   22: ldc 'FTPPORT'
    //   24: aastore
    //   25: dup
    //   26: iconst_2
    //   27: ldc 'FTPUSERNAME'
    //   29: aastore
    //   30: dup
    //   31: iconst_3
    //   32: ldc 'FTPPASSWORD'
    //   34: aastore
    //   35: astore_2
    //   36: aload_2
    //   37: astore_3
    //   38: aload_3
    //   39: arraylength
    //   40: istore #4
    //   42: iconst_0
    //   43: istore #5
    //   45: iload #5
    //   47: iload #4
    //   49: if_icmpge -> 82
    //   52: aload_3
    //   53: iload #5
    //   55: aaload
    //   56: astore #6
    //   58: aload_0
    //   59: getfield properties : Ljava/util/Properties;
    //   62: aload #6
    //   64: aload_1
    //   65: aload #6
    //   67: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   72: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   75: pop
    //   76: iinc #5, 1
    //   79: goto -> 45
    //   82: return
    // Line number table:
    //   Java source line number -> byte code offset
    //   #339	-> 0
    //   #340	-> 11
    //   #341	-> 36
    //   #342	-> 58
    //   #341	-> 76
    //   #344	-> 82
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   58	18	6	element	Ljava/lang/String;
    //   0	83	0	this	Ltop/ibase4j/core/support/file/ftp/FtpClient;
    //   0	83	1	properties	Ljava/util/Map;
    //   36	47	2	key	[Ljava/lang/String;
    // Local variable type table:
    //   start	length	slot	name	signature
    //   0	83	1	properties	Ljava/util/Map<Ljava/lang/String;Ljava/lang/String;>; }
}
