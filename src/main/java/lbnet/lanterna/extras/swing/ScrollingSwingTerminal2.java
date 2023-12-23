package lbnet.lanterna.extras.swing;

import com.googlecode.lanterna.terminal.swing.SwingTerminal;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.TerminalEmulatorColorConfiguration;
import com.googlecode.lanterna.terminal.swing.TerminalEmulatorDeviceConfiguration;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import javax.swing.JScrollBar;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScrollingSwingTerminal2 extends ExposingScrollingSwingTerminal {

    @Setter
    @Getter
    protected boolean autoScrollToBottom = true;

    @Setter
    @Getter
    protected boolean forceRepaintOnNewline = true;

    public ScrollingSwingTerminal2() {
        this(TerminalEmulatorDeviceConfiguration.getDefault(), SwingTerminalFontConfiguration.getDefault(),
                TerminalEmulatorColorConfiguration.getDefault());
    }

    public ScrollingSwingTerminal2(TerminalEmulatorDeviceConfiguration deviceConfiguration,
            SwingTerminalFontConfiguration fontConfiguration, TerminalEmulatorColorConfiguration colorConfiguration) {
        super(deviceConfiguration, fontConfiguration, colorConfiguration);
        init();
    }

    public ScrollingSwingTerminal2(TermConstrArgs tca) {
        this(tca.getDeviceConfig(), tca.getSwingFontConfig(), tca.getColorConfig());
    }

    @Override
    public SwingTerminal getSwingTerminal() {
        return super.getSwingTerminal();
    }

    @Override
    public JScrollBar getScrollBar() {
        return super.getScrollBar();
    }

    protected void init() {
        SwingTerminal st = getSwingTerminal();
        JScrollBar sb = getScrollBar();

        st.addMouseWheelListener(this::mouseWheelMoved);
        sb.addMouseWheelListener(this::mouseWheelMoved);

    }

    @Override
    public boolean requestFocusInWindow() {
        return getSwingTerminal().requestFocusInWindow();
    }

    public void scrollToTop() {
        getScrollBar().setValue(0);
    }

    public void scrollToBottom() {
        getScrollBar().setValue(getScrollBar().getMaximum());
    }

    /**
     * @param x
     *            0.0 - 1.0
     */
    public void scrollToX(double x) {
        int max = getScrollBar().getMaximum();
        int newValue = (int) (max * x);
        getScrollBar().setValue(newValue);
    }

    @Override
    public void putString(String str) {
        if (str.indexOf('\n') == -1) {
            super.putString(str);
        } else {
            // note ScrollingSwingTerminal 3.1.1 supports newlines but only as chars, not in
            // Strings
            for (char ch : str.toCharArray()) {
                super.putCharacter(ch);
            }
            handleNewline();
        }
    }

    @Override
    public void putCharacter(char c) {
        super.putCharacter(c);
        if (c == '\n') {
            handleNewline();
        }
    }

    protected void handleNewline() {
        flush();
        if (autoScrollToBottom) {
            scrollToBottom();
        }
        if (forceRepaintOnNewline) {
            // forceRepaint();
            ScrollingSwingTerminalAccessor.forceRepaint(this);
        }
    }

    /**
     * During tests scroll amount was 3, so the default implementation causes mouse wheel to scroll 30% of the visible
     * part at one time.
     */
    protected double getScrollAmount() {
        int scrollFactor = 15; // scroll by approx this percent per finger move
        double d = getSwingTerminal().getHeight();
        return d / scrollFactor;
    }

    protected void mouseWheelMoved(MouseWheelEvent e) {
        double rotations = e.getPreciseWheelRotation();
        int eventScrollAmount = e.getScrollAmount();
        int valueDelta = (int) (rotations * eventScrollAmount * getScrollAmount());
        // log.info("mouseWheelMoved valueDelta={}", valueDelta);

        JScrollBar sbar = getScrollBar();
        sbar.setValue(sbar.getValue() + valueDelta);
    }

    @Override
    public synchronized void addKeyListener(KeyListener l) {
        getSwingTerminal().addKeyListener(l);
    }

    @Override
    public synchronized void removeKeyListener(KeyListener l) {
        getSwingTerminal().removeKeyListener(l);
    }

    @Override
    public synchronized KeyListener[] getKeyListeners() {
        return getSwingTerminal().getKeyListeners();
    }

}
