package lbnet.lanterna.extras.swing;

import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.TerminalEmulatorColorConfiguration;
import com.googlecode.lanterna.terminal.swing.TerminalEmulatorDeviceConfiguration;
import lbnet.lanterna.extras.exp.ExposingScrollingSwingTerminal;
import lbnet.lanterna.extras.exp.TermConstrArgs;

public class ScrollingSwingTerminal2 extends ExposingScrollingSwingTerminal {

    public ScrollingSwingTerminal2() {
        this(TerminalEmulatorDeviceConfiguration.getDefault(), SwingTerminalFontConfiguration.getDefault(),
                TerminalEmulatorColorConfiguration.getDefault());
    }

    public ScrollingSwingTerminal2(TerminalEmulatorDeviceConfiguration deviceConfiguration,
            SwingTerminalFontConfiguration fontConfiguration, TerminalEmulatorColorConfiguration colorConfiguration) {
        super(deviceConfiguration, fontConfiguration, colorConfiguration);
    }

    public ScrollingSwingTerminal2(TermConstrArgs tca) {
        this(tca.getDeviceConfig(), tca.getSwingFontConfig(), tca.getColorConfig());
    }

    public void scrollToTop() {
        getScrollBar().setValue(0);
    }

    public void scrollToBottom() {
        getScrollBar().setValue(getScrollBar().getMaximum());
    }

    /**
     *
     * @param x
     *            0.0 - 1.0
     */
    public void scrollToX(double x) {
        int max = getScrollBar().getMaximum();
        int newValue = (int) (max * x);
        getScrollBar().setValue(newValue);
    }

}
