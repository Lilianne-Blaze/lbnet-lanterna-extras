package lbnet.lanterna.extras.swing;

import com.googlecode.lanterna.terminal.IOSafeTerminal;
import com.googlecode.lanterna.terminal.swing.ScrollingSwingTerminal;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SwingTerminalFrame extends JFrame implements JFrameExtraMethods {

    protected SwingTerminal swingTerminal;

    protected ScrollingSwingTerminal scrollingSwingTerminal;

    public SwingTerminalFrame(String title, SwingTerminal st) {
        super(title);
        this.swingTerminal = st;
        init();
    }

    public SwingTerminalFrame(String title, ScrollingSwingTerminal sst) {
        super(title);
        this.scrollingSwingTerminal = sst;
        init();
    }

    public SwingTerminalFrame(String title, SwingTerminal st, GraphicsConfiguration gc) {
        super(title, gc);
        this.swingTerminal = st;
        init();
    }

    public SwingTerminalFrame(String title, ScrollingSwingTerminal sst, GraphicsConfiguration gc) {
        super(title, gc);
        this.scrollingSwingTerminal = sst;
        init();
    }

    protected void init() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        BorderLayout bl = new BorderLayout();
        getContentPane().setLayout(bl);

        add(BorderLayout.CENTER, getTerminalComponent());
    }

    public JComponent getTerminalComponent() {
        return scrollingSwingTerminal != null ? scrollingSwingTerminal : swingTerminal;
    }

    public IOSafeTerminal getIOSafeTerminal() {
        return scrollingSwingTerminal != null ? scrollingSwingTerminal : swingTerminal;
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if (b) {
            SwingUtilities.invokeLater(() -> {
                getTerminalComponent().requestFocusInWindow();
            });
        }
    }

}
