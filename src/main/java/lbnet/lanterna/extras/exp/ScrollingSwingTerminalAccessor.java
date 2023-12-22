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

    public static void forceRepaint(ScrollingSwingTerminal ssTerm) {
        try {
            SwingTerminal st = getSwingTerminal(ssTerm);

            Field SwingTerminal_terminalImplementation = st.getClass().getDeclaredField("terminalImplementation");
            SwingTerminal_terminalImplementation.setAccessible(true);
            Object terminalImplementation = SwingTerminal_terminalImplementation.get(st);

            Class class_GraphicalTerminalImplementation = terminalImplementation.getClass().getSuperclass();

            Field GraphicalTerminalImplementation_virtualTerminal = class_GraphicalTerminalImplementation
                    .getDeclaredField("virtualTerminal");
            GraphicalTerminalImplementation_virtualTerminal.setAccessible(true);

            Object virtualTerminal = GraphicalTerminalImplementation_virtualTerminal.get(terminalImplementation);

            Field GraphicalTerminalImplementation_needFullRedraw = class_GraphicalTerminalImplementation
                    .getDeclaredField("needFullRedraw");
            GraphicalTerminalImplementation_needFullRedraw.setAccessible(true);

            GraphicalTerminalImplementation_needFullRedraw.set(terminalImplementation, true);

            st.repaint();
        } catch (Exception e) {
            throw new IllegalStateException("Reflection problem: " + e.toString(), e);
            // if (!reflectionProblemsLogged) {
            // String s = "Shouldn't happen. If it does either the implementation changed or reflection is not allowed.
            // Either way ignore silently.";
            // log.warn(s, e);
            // reflectionProblemsLogged = true;
            // }
        }
    }

}
