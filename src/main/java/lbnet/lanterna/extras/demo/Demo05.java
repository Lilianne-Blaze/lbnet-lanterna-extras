package lbnet.lanterna.extras.demo;

import lbnet.lanterna.extras.swing.SwingTerminalFrame;
import com.googlecode.lanterna.SGR;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import lbnet.lanterna.extras.swing.TermConstrArgs;
import lbnet.lanterna.extras.swing.ScrollingSwingTerminal2;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Demo05 {

    protected SwingTerminalFrame frame;

    protected ScrollingSwingTerminal2 ssTerm;

    public static void main(String[] args) {
        new Demo05().start(args);
    }

    public void start(String[] args) {

        // -----
        // use simplified constructor arguments
        TermConstrArgs tca = new TermConstrArgs();

        // recommended size:
        // 18-24 for 1366 x 768 (100%)
        tca = tca.withFontSize(20);
        tca = tca.withCursorBlinking(true);

        ssTerm = new ScrollingSwingTerminal2(tca);
        // -----

        // ssTerm.enableSGR(SGR.BOLD);

        frame = new SwingTerminalFrame("Demo5", ssTerm);
        addKeyEvents();

        frame.makeFixedSize();
        frame.showCentered(0.2d, 0.4d);

        ssTerm.putString("Press [d] for time+date, [t] for test data.\n");

    }

    protected void addKeyEvents() {

        frame.getTerminalComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == 'd') {
                    DemoShared.printDebugTimeDate(ssTerm);
                    ssTerm.repaint();
                } else if (e.getKeyChar() == 't') {
                    DemoShared.printTestData1(ssTerm);
                    ssTerm.repaint();
                }
            }
        });

    }

}
