package lbnet.lanterna.extras.demo;

import com.googlecode.lanterna.terminal.Terminal;
import java.awt.Frame;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.function.Consumer;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class DemoShared {

    public static void printTestData1(Terminal term) {
        try {
            for (int i = 0; i < 5; i++) {
                for (int i2 = 0; i2 < 8; i2++) {
                    term.putCharacter('_');
                    for (int i3 = 0; i3 < 9; i3++) {
                        term.putCharacter(Character.forDigit(i2, 10));
                    }
                }
                term.putCharacter('\n');

                for (int i4 = 1; i4 < 10; i4++) {
                    term.putString("---");
                    term.putCharacter(Character.forDigit(i4, 10));
                    term.putCharacter('\n');
                }

            }
        } catch (IOException e) {
        }
    }

    public static void printDebugTimeDate(Terminal term) {
        try {
            // OffsetDateTime odt = OffsetDateTime.now();
            ZonedDateTime zdt = ZonedDateTime.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);
            term.putString(dtf.format(zdt));
            term.putCharacter('\n');
        } catch (IOException e) {
        }
    }

    public static BufferedImage loadImageResource(String path) {
        try {
            try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path)) {
                BufferedImage bi = ImageIO.read(is);
                return bi;
            }
        } catch (Exception e) {
            throw new RuntimeException(e.toString(), e);
        }
    }

    public static void addClickListener(TrayIcon trayIcon, Consumer<MouseEvent> mouseEventListener) {
        trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mouseEventListener.accept(e);
            }
        });
    }

    public static void addDoubleClickListener(TrayIcon trayIcon, Consumer<ActionEvent> actionEventListener) {
        trayIcon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionEventListener.accept(e);
            }
        });
    }

    public static void addIconifiedListener(Frame frame, Consumer<WindowEvent> windowEventListener) {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowIconified(WindowEvent e) {
                windowEventListener.accept(e);
            }
        });
    }

    public static void addWindowClosedListener(Frame frame, Consumer<WindowEvent> windowEventListener) {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                windowEventListener.accept(e);
            }
        });
    }

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

    public static void configHideOnCloseOrIconify(JFrame frame) {
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        addIconifiedListener(frame, (t) -> {
            frame.setVisible(false);
        });
    }

}
