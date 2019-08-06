package top.ibase4j.core.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;











public final class SerializeUtil
{
  private static final Logger logger = LogManager.getLogger();






  
  public static final byte[] serialize(Object object) {
	return null;
//    baos = new ByteArrayOutputStream();
//    oos = null;
//    try {
//      oos = new ObjectOutputStream(baos);
//      oos.writeObject(object);
//      return baos.toByteArray();
//    } catch (IOException ex) {
//      throw new RuntimeException(ex.getMessage(), ex);
//    } finally {
//      try {
//        if (oos != null) {
//          oos.close();
//        }
//      } catch (Exception e) {
//        logger.error("", e);
//      } 
//      try {
//        if (baos != null) {
//          baos.close();
//        }
//      } catch (Exception e) {
//        logger.error("", e);
//      } 
//    } 
  }







  
  public static final Object deserialize(byte[] bytes) { return deserialize(bytes, Object.class); }








  
  public static final <K> K deserialize(byte[] bytes, Class<K> cls) {
	return null;
//    bais = new ByteArrayInputStream(bytes);
//    ois = null;
//    try {
//      ois = new ObjectInputStream(bais);
//      object = ois.readObject(); return (K)object;
//    } catch (IOException ex) {
//      throw new RuntimeException(ex.getMessage(), ex);
//    } catch (ClassNotFoundException ex) {
//      throw new RuntimeException(ex.getMessage(), ex);
//    } finally {
//      try {
//        if (ois != null) {
//          ois.close();
//        }
//      } catch (Exception e) {
//        logger.error("", e);
//      } 
//      try {
//        if (bais != null) {
//          bais.close();
//        }
//      } catch (Exception e) {
//        logger.error("", e);
//      } 
//    } 
  }
}
