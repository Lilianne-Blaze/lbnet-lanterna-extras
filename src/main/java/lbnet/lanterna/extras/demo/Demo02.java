package lbnet.lanterna.extras.demo;

import com.googlecode.lanterna.SGR;
import javax.swing.JFrame;
import lbnet.lanterna.extras.swing.TermConstrArgs;
import lbnet.lanterna.extras.swing.ScrollingSwingTerminal2;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Demo02 {

    protected JFrame frame;

    protected ScrollingSwingTerminal2 ssTerm;

    public static void main(String[] args) {
        new Demo02().start(args);
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

        frame = new JFrame("Demo2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(ssTerm);

        // set size to terminal's preferred size
        frame.pack();
        frame.setResizable(false);

        // center on screen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        DemoShared.printTestData1(ssTerm);
        ssTerm.flush();

    }

}
