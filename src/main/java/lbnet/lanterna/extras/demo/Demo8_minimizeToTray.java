package lbnet.lanterna.extras.demo;

import lbnet.lanterna.extras.swing.SwingTerminalFrame;
import com.googlecode.lanterna.SGR;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import javax.swing.WindowConstants;
import lbnet.lanterna.extras.swing.JFrameUtils;
import lbnet.lanterna.extras.swing.TermConstrArgs;
import lbnet.lanterna.extras.swing.ScrollingSwingTerminal2;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Demo8_minimizeToTray {

    protected SwingTerminalFrame frame;

    protected ScrollingSwingTerminal2 term;

    protected BufferedImage imageIcoPng;

    protected SystemTray systemTray;

    protected TrayIcon trayIcon;

    public static void main(String[] args) {
        new Demo8_minimizeToTray().start(args);
    }

    public void start(String[] args) {

        imageIcoPng = DemoShared.loadImageResource("META-INF/ico_dark_red.png");

        try {

            systemTray = SystemTray.getSystemTray();
            trayIcon = new TrayIcon(imageIcoPng, "Demo8");
            systemTray.add(trayIcon);

            DemoShared.addClickListener(trayIcon, this::onTrayIconClick);
            DemoShared.addDoubleClickListener(trayIcon, this::onTrayIconDoubleClick);

        } catch (Exception e) {
            throw new RuntimeException(e.toString(), e);
        }

        // -----
        // use simplified constructor arguments
        TermConstrArgs tca = new TermConstrArgs();

        // recommended size:
        // 18-24 for 1366 x 768 (100%)
        tca = tca.fontSize(20);
        tca = tca.cursorBlinking(true);

        term = new ScrollingSwingTerminal2(tca);
        // -----

        term.enableSGR(SGR.BOLD);

        frame = new SwingTerminalFrame("Demo8", term);

        frame.setIconImage(imageIcoPng);
        JFrameUtils.addIconifiedListener(frame, this::onWindowIconified);
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        addKeyEvents();

        // make the frame have the optimal size for the terminal,
        // and make it non-resizeable
        frame.makeFixedSize();

        // show the frame slightly to the up-left from center
        frame.showCentered(0.2d, 0.4d);

        term.putString("Press [d] for time+date, [t] for test data.\n");

    }

    protected void addKeyEvents() {

        frame.getTerminalComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == 'd') {
                    DemoShared.printDebugTimeDate(term);
                    term.repaint();
                } else if (e.getKeyChar() == 't') {
                    DemoShared.printTestData1(term);
                    term.repaint();
                }
            }
        });

    }

    protected void onTrayIconClick(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == 3) {
            log.info("R-Click, exiting.");
            System.exit(0);
        }

        log.info("Click.");
    }

    protected void onTrayIconDoubleClick(ActionEvent actionEvent) {
        log.info("Double-Click");
        frame.unhideDeiconifyAndFocus();
    }

    protected void onWindowIconified(WindowEvent windowEvent) {
        log.info("Iconified, hiding.");
        frame.setVisible(false);
    }

}
