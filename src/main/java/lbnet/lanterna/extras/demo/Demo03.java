package lbnet.lanterna.extras.demo;

import lbnet.lanterna.extras.swing.SwingTerminalFrame;
import com.googlecode.lanterna.SGR;
import lbnet.lanterna.extras.swing.TermConstrArgs;
import lbnet.lanterna.extras.swing.ScrollingSwingTerminal2;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Demo03 {

    protected SwingTerminalFrame frame;

    protected ScrollingSwingTerminal2 ssTerm;

    public static void main(String[] args) {
        new Demo03().start(args);
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

        ssTerm.enableSGR(SGR.BOLD);

        frame = new SwingTerminalFrame("Demo3", ssTerm);

        frame.makeFixedSize();
        frame.showCentered(0.2d, 0.4d);

        DemoShared.printDebugTimeDate(ssTerm);
        DemoShared.printTestData1(ssTerm);
        DemoShared.printDebugTimeDate(ssTerm);

        ssTerm.flush();

    }

}
