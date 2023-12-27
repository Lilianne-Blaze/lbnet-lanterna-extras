package lbnet.lanterna.extras.demo;

import lbnet.lanterna.extras.swing.SwingTerminalFrame;
import com.googlecode.lanterna.SGR;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import lbnet.lanterna.extras.swing.TermConstrArgs;
import lbnet.lanterna.extras.swing.ScrollingSwingTerminal2;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Demo06_simplest {

    protected SwingTerminalFrame frame;

    protected ScrollingSwingTerminal2 term;

    public static void main(String[] args) {
        new Demo06_simplest().start(args);
    }

    public void start(String[] args) {

        // -----
        // use simplified constructor arguments
        TermConstrArgs tca = new TermConstrArgs();

        // recommended size:
        // 18-24 for 1366 x 768 (100%)
        tca = tca.withFontSize(20);
        tca = tca.withCursorBlinking(true);

        term = new ScrollingSwingTerminal2(tca);
        // -----

        term.enableSGR(SGR.BOLD);

        frame = new SwingTerminalFrame("Demo6", term);
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

}
