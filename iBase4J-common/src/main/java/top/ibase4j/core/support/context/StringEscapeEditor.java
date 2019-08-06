package top.ibase4j.core.support.context;

import java.beans.PropertyEditorSupport;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.JavaScriptUtils;








public class StringEscapeEditor
  extends PropertyEditorSupport
{
  private boolean escapeHTML;
  private boolean escapeJavaScript;
  
  public StringEscapeEditor() {}
  
  public StringEscapeEditor(boolean escapeHTML, boolean escapeJavaScript) {
    this.escapeHTML = escapeHTML;
    this.escapeJavaScript = escapeJavaScript;
  }

  
  public String getAsText() {
    Object value = getValue();
    return (value != null) ? value.toString() : "";
  }

  
  public void setAsText(String text) throws IllegalArgumentException {
    if (text == null) {
      setValue(null);
    } else {
      String value = text;
      if (this.escapeHTML) {
        value = HtmlUtils.htmlEscape(value);
        System.out.println("value:" + value);
      } 
      if (this.escapeJavaScript) {
        value = JavaScriptUtils.javaScriptEscape(value);
        System.out.println("value:" + value);
      } 
      setValue(value);
    } 
  }
}
