package lbnet.lanterna.extras.swing;

import java.awt.Frame;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Consumer;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class JFrameUtils {

    public static void iconify(Frame frame) {
        try {
            int es = frame.getExtendedState();
            if ((es & Frame.ICONIFIED) == 0) {
                frame.setExtendedState(es | Frame.ICONIFIED);
            }
        } catch (Exception e) {
        }
    }

    public static void deiconify(Frame frame) {
        try {
            int es = frame.getExtendedState();
            if ((es & Frame.ICONIFIED) != 0) {
                frame.setExtendedState(es & ~Frame.ICONIFIED);
            }
        } catch (Exception e) {
        }
    }

    public static void unhideDeiconifyAndFocus(Frame frame) {
        frame.setVisible(true);
        deiconify(frame);
        frame.requestFocus(); // probably not needed, just in case
    }

    public static void addIconifiedListener(Window frame, Consumer<WindowEvent> windowEventListener) {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowIconified(WindowEvent e) {
                windowEventListener.accept(e);
            }
        });
    }

    public static void addWindowClosedListener(Window frame, Consumer<WindowEvent> windowEventListener) {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                windowEventListener.accept(e);
            }
        });
    }

    public static void addWindowClosingListener(Window frame, Consumer<WindowEvent> windowEventListener) {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                windowEventListener.accept(e);
            }
        });
    }

    public static void configHideOnCloseOrIconify(JFrame frame) {
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        addIconifiedListener(frame, (t) -> {
            frame.setVisible(false);
        });
    }

}
