package lbnet.lanterna.extras.exp;

import com.googlecode.lanterna.terminal.swing.ScrollingSwingTerminal;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;
import java.lang.reflect.Field;
import javax.swing.JScrollBar;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScrollingSwingTerminalAccessor {

    private static Class sstClass;

    private static Field swingTerminalField;

    private static Field scrollBarField;

    private static boolean refInitFailed;

    static {
        initReflection();
    }

    private static void initReflection() {
        try {
            sstClass = ScrollingSwingTerminal.class;
            swingTerminalField = sstClass.getDeclaredField("swingTerminal");
            swingTerminalField.setAccessible(true);
            scrollBarField = sstClass.getDeclaredField("scrollBar");
            scrollBarField.setAccessible(true);
        } catch (Exception e) {
            refInitFailed = true;
            String s1 = "Problems initializing reflection for " + sstClass.getSimpleName();
            String s2 = "Shouldn't happen. If it does either the implementation changed or reflection is not allowed. Either way ignore silently.";
            String s = s1 + ". " + s2;
            log.warn(s, e);
        }
    }

    public static SwingTerminal getSwingTerminal(ScrollingSwingTerminal ssTerm) {
        try {
            return (SwingTerminal) swingTerminalField.get(ssTerm);
        } catch (Exception e) {
            throw new IllegalStateException("Reflection problem: " + e.toString(), e);
        }
    }

    public static JScrollBar getScrollBar(ScrollingSwingTerminal ssTerm) {
        try {
            return (JScrollBar) scrollBarField.get(ssTerm);
        } catch (Exception e) {
            throw new IllegalStateException("Reflection problem: " + e.toString(), e);
        }
    }

}
