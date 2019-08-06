package top.ibase4j.core.support.context;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import org.springframework.util.ResourceUtils;





public class OrderedProperties
  extends Properties
{
  private List<Object> keyList = new ArrayList();



  
  public OrderedProperties() {}


  
  public OrderedProperties(String path) throws IOException {
    URL url = ResourceUtils.getURL(path); 
//    try { is = url.openStream(); throwable = null; 
//      try { load(is); } catch (Throwable throwable1) { throwable = throwable1 = null; throw throwable1; }
//      finally { if (is != null) if (throwable != null) { try { is.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  } else { is.close(); }   }  } catch (FileNotFoundException e)
//    { throw new RuntimeException("指定文件不存在！" + path); }
  
  }




  
  public OrderedProperties(File file) throws IOException {
    
//    try { is = new FileInputStream(file); throwable = null; 
//      try { load(is); } catch (Throwable throwable1) { throwable = throwable1 = null; throw throwable1; }
//      finally { if (is != null) if (throwable != null) { try { is.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  } else { is.close(); }   }  } catch (FileNotFoundException e)
//    { throw new RuntimeException("指定文件不存在！" + file.getAbsolutePath()); }
//  
  }




  
  public Object put(Object key, Object value) {
    removeKeyIfExists(key);
    this.keyList.add(key);
    return super.put(key, value);
  }




  
  public Object remove(Object key) {
    removeKeyIfExists(key);
    return super.remove(key);
  }




  
  private void removeKeyIfExists(Object key) { this.keyList.remove(key); }






  
  public List<Object> getKeyList() { return this.keyList; }







  
  public void store(String path) throws IOException { store(path, "UTF-8"); }







  
  public void store(String path, String charset) throws IOException {
//    if (path != null && !"".equals(path)) { 
//      try { os = new FileOutputStream(path); throwable = null; 
//        try { bw = new BufferedWriter(new OutputStreamWriter(os, charset)); throwable1 = null; 
//          try { store(bw, null); } catch (Throwable throwable2) { throwable1 = throwable2 = null; throw throwable2; }
//          finally { if (bw != null) if (throwable1 != null) { try { bw.close(); } catch (Throwable throwable2) { throwable1.addSuppressed(throwable2); }  } else { bw.close(); }   }  } catch (Throwable throwable1) { throwable = throwable1 = null; throw throwable1; } finally { if (os != null) if (throwable != null) { try { os.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  } else { os.close(); }   }  } catch (FileNotFoundException e)
//      { throw new RuntimeException("指定文件不存在！"); }
//       }
//    else
//    { throw new RuntimeException("存储路径不能为空!"); }
//  
  }






  
  public Enumeration<Object> keys() { return new EnumerationAdapter(this.keyList); }



  
  public Enumeration<?> propertyNames() { return new EnumerationAdapter(this.keyList); }

  
  private class EnumerationAdapter<T>
    extends Object
    implements Enumeration<T>
  {
    private int index = 0;
    private final List<T> list;
    private final boolean isEmpty;
    
    public EnumerationAdapter(List<T> list) {
      this.list = list;
      this.isEmpty = list.isEmpty();
    }






    
    public boolean hasMoreElements() { return (!this.isEmpty && this.index < this.list.size()); }


    
    public T nextElement() {
      if (hasMoreElements()) {
        return (T)this.list.get(this.index++);
      }
      return null;
    }
  }
}
