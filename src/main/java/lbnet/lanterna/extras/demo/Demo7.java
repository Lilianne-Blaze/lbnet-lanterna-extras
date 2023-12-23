package lbnet.lanterna.extras.demo;

import lbnet.lanterna.extras.swing.SwingTerminalFrame;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import lbnet.lanterna.extras.swing.TermConstrArgs;
import lbnet.lanterna.extras.swing.ScrollingSwingTerminal2;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Demo7 {

    protected SwingTerminalFrame frame;

    protected ScrollingSwingTerminal2 term;

    public static void main(String[] args) {
        new Demo7().start(args);
    }

    public void start(String[] args) {

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

        frame = new SwingTerminalFrame("Demo7", term);
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
            public void keyPressed(KeyEvent e) {
                log.info("------");
                log.info("e={}", e);
                log.info("e.keyCode={}", e.getKeyCode());
                log.info("e.keyChar={}", e.getKeyChar());

                if (e.getKeyCode() == 8) {
                    TerminalPosition tp = term.getCursorPosition();
                    TerminalPosition tp2 = tp.withRelativeColumn(-1);
                    term.setCursorPosition(tp2);
                    term.putCharacter(' ');
                    term.setCursorPosition(tp2);
                    term.flush();

                }

            }

            @Override
            public void keyTyped(KeyEvent e) {

                log.info("------");
                log.info("e={}", e);
                log.info("e.keyCode={}", e.getKeyCode());
                log.info("e.keyChar={}", e.getKeyChar());

                if (e.getKeyChar() >= 32) {
                    term.putCharacter(e.getKeyChar());
                    term.flush();
                }

                // if (e.getKeyChar() == 'd') {
                // DemoShared.printDebugTimeDate(term);
                // term.repaint();
                // } else if (e.getKeyChar() == 't') {
                // DemoShared.printTestData1(term);
                // term.repaint();
                // }
            }
        });

    }

}
