package lbnet.lanterna.extras.swing;

import com.googlecode.lanterna.terminal.swing.ScrollingSwingTerminal;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.TerminalEmulatorColorConfiguration;
import com.googlecode.lanterna.terminal.swing.TerminalEmulatorDeviceConfiguration;
import javax.swing.JScrollBar;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExposingScrollingSwingTerminal extends ScrollingSwingTerminal {

    public ExposingScrollingSwingTerminal() {
        this(TerminalEmulatorDeviceConfiguration.getDefault(), SwingTerminalFontConfiguration.getDefault(),
                TerminalEmulatorColorConfiguration.getDefault());

    }

    public ExposingScrollingSwingTerminal(TerminalEmulatorDeviceConfiguration deviceConfiguration,
            SwingTerminalFontConfiguration fontConfiguration, TerminalEmulatorColorConfiguration colorConfiguration) {
        super(deviceConfiguration, fontConfiguration, colorConfiguration);
    }

    protected SwingTerminal getSwingTerminal() {
        return ScrollingSwingTerminalAccessor.getSwingTerminal(this);
    }

    protected JScrollBar getScrollBar() {
        return ScrollingSwingTerminalAccessor.getScrollBar(this);
    }

}
