package lbnet.lanterna.extras.demo;

import com.googlecode.lanterna.terminal.swing.ScrollingSwingTerminal;
import javax.swing.JFrame;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Demo1 {

    protected JFrame frame;

    protected ScrollingSwingTerminal ssTerm;

    public static void main(String[] args) {
        new Demo1().start(args);
    }

    public void start(String[] args) {

        ssTerm = new ScrollingSwingTerminal();

        frame = new JFrame("Demo1");
        frame.setSize(960, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(ssTerm);

        frame.setVisible(true);

        DemoShared.printTestData1(ssTerm);
        ssTerm.flush();

    }

}
