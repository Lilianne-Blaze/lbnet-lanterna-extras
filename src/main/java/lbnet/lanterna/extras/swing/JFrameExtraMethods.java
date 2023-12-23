package lbnet.lanterna.extras.swing;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public interface JFrameExtraMethods {

    public default JFrame thisAsJFrame() {
        return (JFrame) this;
    }

    public default void makeFixedSize() {
        JFrame f = thisAsJFrame();
        f.pack();
        f.setResizable(false);
    }

    public default void showCentered() {
        JFrame f = thisAsJFrame();
        showCentered(0.0d, 0.0d);
    }

    public default void showCentered(double xOffset, double yOffset) {
        JFrame f = thisAsJFrame();
        f.setLocationRelativeTo(null);
        if (xOffset != 0.0d || yOffset != 0.0d) {
            int x = f.getX();
            int y = f.getY();
            int newX = (int) (x * 2 * xOffset);
            int newY = (int) (y * 2 * yOffset);
            f.setLocation(newX, newY);
        }
        f.setAutoRequestFocus(true);
        f.setVisible(true);
    }

    public default void attemptFocusFirstComponent() {
        try {
            JFrame f = thisAsJFrame();
            JComponent jcRootPane = f.getRootPane();
            JComponent jcLayeredPane = JComponentUtils.findFirstDescendantByClass(jcRootPane, JLayeredPane.class);
            JComponent jcPanel = JComponentUtils.findFirstDescendantByClass(jcLayeredPane, JPanel.class);
            jcPanel.getComponent(0).requestFocusInWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
