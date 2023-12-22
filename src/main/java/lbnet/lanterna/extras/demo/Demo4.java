package lbnet.lanterna.extras.demo;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import lbnet.lanterna.extras.exp.ScrollingSwingTerminalAccessor;
import lbnet.lanterna.extras.exp.TermConstrArgs;
import lbnet.lanterna.extras.swing.ScrollingSwingTerminal2;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Demo4 {

    protected SwingTerminalFrame frame;

    protected ScrollingSwingTerminal2 ssTerm;

    public static void main(String[] args) {
        new Demo4().start(args);
    }

    public void start(String[] args) {

        // -----
        // use simplified constructor arguments
        TermConstrArgs tca = new TermConstrArgs();

        // recommended size:
        // 18-24 for 1366 x 768 (100%)
        tca = tca.fontSize(20);
        tca = tca.cursorBlinking(true);

        ssTerm = new ScrollingSwingTerminal2(tca);
        // -----

        addKeyEvents();

        ssTerm.enableSGR(SGR.BOLD);

        frame = new SwingTerminalFrame("Demo4", ssTerm);

        // frame.add(ssTerm);

        frame.makeFixedSize();
        frame.showCentered(0.2d, 0.4d);

        DemoShared.printDebugTimeDate(ssTerm);
        DemoShared.printTestData1(ssTerm);
        DemoShared.printDebugTimeDate(ssTerm);

        ssTerm.flush();

    }

    protected void addKeyEvents() {
        ssTerm.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                log.debug("ssTerm keyCode={}", e.getKeyChar());
                super.keyTyped(e); // Generated from
            }
        });

        ssTerm.getSwingTerminal().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                log.debug("ssTerm.swingTerm keyCode={}", e.getKeyChar());
                super.keyTyped(e); // Generated from
            }
        });

        ssTerm.getScrollBar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                log.debug("ssTerm.scrollBar keyCode={}", e.getKeyChar());
                super.keyTyped(e); // Generated from
            }
        });

    }

}
