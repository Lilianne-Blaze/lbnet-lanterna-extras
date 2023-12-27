package lbnet.lanterna.extras.demo;

import lbnet.lanterna.extras.swing.SwingTerminalFrame;
import com.googlecode.lanterna.SGR;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import lbnet.lanterna.extras.swing.TermConstrArgs;
import lbnet.lanterna.extras.swing.ScrollingSwingTerminal2;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Demo09_minimizeToTray {

    protected SwingTerminalFrame frame;

    protected ScrollingSwingTerminal2 term;

    protected BufferedImage icoDarkRedPng;

    protected BufferedImage icoDarkRedIco;

    protected SystemTray systemTray;

    protected TrayIcon trayIcon;

    public static void main(String[] args) {
        new Demo09_minimizeToTray().start(args);
    }

    public void start(String[] args) {

        icoDarkRedPng = DemoShared.loadImageResource("META-INF/ico_dark_red.png");
        icoDarkRedIco = DemoShared.loadImageResource("META-INF/ico_dark_red.ico");

        try {

            systemTray = SystemTray.getSystemTray();

            BufferedImage scaledTrayIconPng = DemoShared.resizeImage(icoDarkRedPng, systemTray);
            trayIcon = new TrayIcon(scaledTrayIconPng, "Demo9");

            systemTray.add(trayIcon);

            DemoShared.addRightClickListener(trayIcon, this::onTrayIconRightClick);
            DemoShared.addDoubleClickListener(trayIcon, this::onTrayIconDoubleClick);

        } catch (Exception e) {
            throw new RuntimeException(e.toString(), e);
        }

        // -----
        // use simplified constructor arguments
        TermConstrArgs tca = new TermConstrArgs();

        // recommended size:
        // 18-24 for 1366 x 768 (100%)
        tca = tca.withFontSize(20);
        tca = tca.withCursorBlinking(true);

        term = new ScrollingSwingTerminal2(tca);
        // -----

        frame = new SwingTerminalFrame("Demo9", term);

        // term.putString("qwerty QWERTY 123456.\n");
        // term.enableSGR(SGR.BOLD);
        // term.putString("qwerty QWERTY 123456.\n");

        frame.setIconImage(icoDarkRedPng);
        frame.configHideOnCloseOrIconify();

        addKeyEvents();

        // make the frame have the optimal size for the terminal,
        // and make it non-resizeable
        frame.makeFixedSize();

        term.putString("qwerty QWERTY 123456.\n");
        term.enableSGR(SGR.BOLD);
        term.putString("qwerty QWERTY 123456.\n");

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

    protected void onTrayIconRightClick(MouseEvent mouseEvent) {
        System.exit(0);
    }

    protected void onTrayIconDoubleClick(ActionEvent actionEvent) {
        log.info("Double-Click");
        frame.unhideDeiconifyAndFocus();
    }

}
