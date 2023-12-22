package lbnet.lanterna.extras.demo;

import java.awt.Component;
import java.util.function.Consumer;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JComponentUtils {

    public static void printComponents(JComponent parent) {
        printComponents(parent, System.out::println);
    }

    public static void printComponents(JComponent parent, Consumer<String> out) {
        printComponents(parent, "", out);
    }

    public static void printComponents(JComponent parent, String indent, Consumer<String> out) {
        out.accept(indent + parent.toString());
        for (Component child : parent.getComponents()) {
            printComponents((JComponent) child, ".." + indent, out);
        }

    }

    public static JComponent findFirstDescendantByClass(JComponent ancestor, Class cls) {
        if (ancestor == null) {
            return null;
        } else if (cls.isAssignableFrom(ancestor.getClass())) {
            return ancestor;
        }

        for (Component descendant : ancestor.getComponents()) {
            JComponent jc = (JComponent) descendant;
            JComponent jc2 = findFirstDescendantByClass(jc, cls);
            if (jc2 != null) {
                return jc2;
            }
        }

        return null;
    }

}
