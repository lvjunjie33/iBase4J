package top.ibase4j.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


















public final class XmlUtil
{
  public static final Map parseXml2Map(String pStrXml) {
    Map map = new HashMap();
    String strTitle = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    Document document = null;
    try {
      if (pStrXml.indexOf("<?xml") < 0) {
        pStrXml = strTitle + pStrXml;
      }
      document = DocumentHelper.parseText(pStrXml);
    } catch (DocumentException e) {
      throw new RuntimeException(e);
    } 
    
    Element elNode = document.getRootElement();
    
    for (Iterator it = elNode.elementIterator(); it.hasNext(); ) {
      Element leaf = (Element)it.next();
      map.put(leaf.getName().toLowerCase(), leaf.getData());
    } 
    return map;
  }
  
  public static Map dom2Map(Element e) {
    Map map = new HashMap();
    List list = e.elements();
    if (list.size() > 0) {
      for (int i = 0; i < list.size(); i++) {
        Element iter = (Element)list.get(i);
        List mapList = new ArrayList();
        
        if (iter.elements().size() > 0) {
          Map m = dom2Map(iter);
          if (map.get(iter.getName()) != null) {
            Object obj = map.get(iter.getName());
            if (!"java.util.ArrayList".equals(obj.getClass().getName())) {
              mapList = new ArrayList();
              mapList.add(obj);
              mapList.add(m);
            } else if ("java.util.ArrayList".equals(obj.getClass().getName())) {
              mapList = (List)obj;
              mapList.add(m);
            } 
            map.put(iter.getName(), mapList);
          } else {
            map.put(iter.getName(), m);
          }
        
        } else if (map.get(iter.getName()) != null) {
          Object obj = map.get(iter.getName());
          if (!"java.util.ArrayList".equals(obj.getClass().getName())) {
            mapList = new ArrayList();
            mapList.add(obj);
            mapList.add(iter.getText());
          } else if ("java.util.ArrayList".equals(obj.getClass().getName())) {
            mapList = (List)obj;
            mapList.add(iter.getText());
          } 
          map.put(iter.getName(), mapList);
        } else {
          map.put(iter.getName(), iter.getText());
        } 
      } 
    } else {
      
      map.put(e.getName(), e.getText());
    } 
    return map;
  }







  
  public static final Map parseXml2Map(String pStrXml, String pXPath) {
    Map map = new HashMap();
    String strTitle = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    Document document = null;
    try {
      if (pStrXml.indexOf("<?xml") < 0) {
        pStrXml = strTitle + pStrXml;
      }
      document = DocumentHelper.parseText(pStrXml);
    } catch (DocumentException e) {
      throw new RuntimeException(e);
    } 
    
    Element elNode = document.getRootElement();
    
    for (Iterator it = elNode.elementIterator(); it.hasNext(); ) {
      Element leaf = (Element)it.next();
      map.put(leaf.getName().toLowerCase(), leaf.getData());
    } 
    return map;
  }







  
  public static final String parseDto2Xml(Map<String, String> map, String pRootNodeName) {
    Document document = DocumentHelper.createDocument();
    
    document.addElement(pRootNodeName);
    Element root = document.getRootElement();
    for (Map.Entry<String, String> entry : map.entrySet()) {
      Element leaf = root.addElement((String)entry.getKey());
      leaf.setText((String)entry.getValue());
    } 
    
    return document.asXML().substring(39);
  }








  
  public static final String parseDto2XmlHasHead(Map<String, String> map, String pRootNodeName) {
    Document document = DocumentHelper.createDocument();
    
    document.addElement(pRootNodeName);
    Element root = document.getRootElement();
    for (Map.Entry<String, String> entry : map.entrySet()) {
      Element leaf = root.addElement((String)entry.getKey());
      leaf.setText((String)entry.getValue());
    } 

    
    return document.asXML();
  }









  
  public static final String parseMap2Xml(Map<String, String> map, String pRootNodeName, String pFirstNodeName) {
    Document document = DocumentHelper.createDocument();
    
    document.addElement(pRootNodeName);
    Element root = document.getRootElement();
    root.addElement(pFirstNodeName);
    Element firstEl = (Element)document.selectSingleNode("/" + pRootNodeName + "/" + pFirstNodeName);
    for (Map.Entry<String, String> entry : map.entrySet()) {
      firstEl.addAttribute((String)entry.getKey(), (String)entry.getValue());
    }
    
    return document.asXML().substring(39);
  }









  
  public static final String parseList2Xml(List pList, String pRootNodeName, String pFirstNodeName) {
    Document document = DocumentHelper.createDocument();
    Element elRoot = document.addElement(pRootNodeName);
    for (int i = 0; i < pList.size(); i++) {
      Map map = (Map)pList.get(i);
      Element elRow = elRoot.addElement(pFirstNodeName);
      Iterator it = map.entrySet().iterator();
      while (it.hasNext()) {
        Map.Entry entry = (Map.Entry)it.next();
        elRow.addAttribute((String)entry.getKey(), String.valueOf(entry.getValue()));
      } 
    } 
    return document.asXML().substring(39);
  }









  
  public static final String parseList2XmlBasedNode(List pList, String pRootNodeName, String pFirstNodeName) {
    Document document = DocumentHelper.createDocument();
    Element output = document.addElement(pRootNodeName);
    for (int i = 0; i < pList.size(); i++) {
      Map map = (Map)pList.get(i);
      Element elRow = output.addElement(pFirstNodeName);
      Iterator it = map.entrySet().iterator();
      while (it.hasNext()) {
        Map.Entry entry = (Map.Entry)it.next();
        Element leaf = elRow.addElement((String)entry.getKey());
        leaf.setText(String.valueOf(entry.getValue()));
      } 
    } 
    return document.asXML().substring(39);
  }







  
  public static final List parseXml2List(String pStrXml) {
    List lst = new ArrayList();
    String strTitle = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    Document document = null;
    try {
      if (pStrXml.indexOf("<?xml") < 0) {
        pStrXml = strTitle + pStrXml;
      }
      document = DocumentHelper.parseText(pStrXml);
    } catch (DocumentException e) {
      throw new RuntimeException(e);
    } 
    
    Element elRoot = document.getRootElement();
    
    Iterator elIt = elRoot.elementIterator();
    while (elIt.hasNext()) {
      Element el = (Element)elIt.next();
      Iterator attrIt = el.attributeIterator();
      Map map = new HashMap();
      while (attrIt.hasNext()) {
        Attribute attribute = (Attribute)attrIt.next();
        map.put(attribute.getName().toLowerCase(), attribute.getData());
      } 
      lst.add(map);
    } 
    return lst;
  }






  
  public static Map<String, Object> dom2Map(Document doc) {
    Map<String, Object> maproot = new HashMap<String, Object>();
    if (doc == null) {
      return maproot;
    }
    Element root = doc.getRootElement();
    
    List list1 = root.elements();
    for (Object obj : list1) {
      Element element = (Element)obj;
      Map<String, Object> map = new HashMap<String, Object>();
      element2Map(element, map);
      maproot.put(element.getName(), map);
    } 
    return maproot;
  }





  
  public static void element2Map(Element e, Map<String, Object> map) {
    List<Element> list = e.elements();
    if (e.attributeCount() > 0) {
      for (Object attri : e.attributes()) {
        Attribute at = (Attribute)attri;
        map.put(at.getName(), at.getValue());
      } 
    }
    if (list.size() < 1 && DataUtil.isEmpty(e.getText()))
      return; 
    if (list.size() < 1 && !DataUtil.isEmpty(e.getText())) {
      map.put("text", e.getText());
    }
    for (Object aList : list) {
      Element iter = (Element)aList;
      Map<String, Object> cMap = new HashMap<String, Object>();
      element2Map(iter, cMap);
      map.put(iter.getName(), cMap);
    } 
  }
}
