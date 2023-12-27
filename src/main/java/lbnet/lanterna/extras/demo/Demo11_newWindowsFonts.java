package lbnet.lanterna.extras.demo;

import lbnet.lanterna.extras.swing.SwingTerminalFrame;
import com.googlecode.lanterna.SGR;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import lbnet.lanterna.extras.swing.TermConstrArgs;
import lbnet.lanterna.extras.swing.ScrollingSwingTerminal2;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Demo11_newWindowsFonts {

    protected SwingTerminalFrame frame;

    protected ScrollingSwingTerminal2 term;

    public static void main(String[] args) {
        new Demo11_newWindowsFonts().start(args);
    }

    public void start(String[] args) {

        TermConstrArgs tca = new TermConstrArgs();

        // recommended size:
        // 18-24 for 1366 x 768 (100%)
        tca = tca.withFontSize(20);

        tca = tca.withCursorBlinking(true);

        // try "Cascadia Mono" and "Consolas" from
        // newer Windows versions first
        tca = tca.withPreferNewFonts(true);

        term = new ScrollingSwingTerminal2(tca);

        frame = new SwingTerminalFrame("Demo11", term);

        addKeyEvents();

        // make the frame have the optimal size for the terminal,
        // and make it non-resizeable
        frame.makeFixedSize();

        frame.configDisposeOnClose();

        term.putString("qwerty QWERTY 123456.\n");
        term.enableSGR(SGR.BOLD);
        term.putString("qwerty QWERTY 123456.\n");

        term.disableSGR(SGR.BOLD);
        term.putString("This should be shown using Cascadia Mono,");
        term.enableSGR(SGR.BOLD);
        term.putString(" at least when ran on newer versions of Windows.\n");

        // show the frame slightly to the up-left from center,
        // "0.5d, 0.5d" (or no params) would be centered
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

}
