import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.util.Enumeration;

public class AppFont {
    public static void setGlobalFont(FontUIResource fontResource) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontResource);
            }
        }
    }
}