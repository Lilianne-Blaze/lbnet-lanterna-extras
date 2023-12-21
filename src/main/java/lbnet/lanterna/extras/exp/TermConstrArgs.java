package lbnet.lanterna.extras.exp;

import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.TerminalEmulatorColorConfiguration;
import com.googlecode.lanterna.terminal.swing.TerminalEmulatorDeviceConfiguration;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Accessors(fluent = true)
public class TermConstrArgs {

    @Getter
    @Setter
    private int fontSize = -1;

    @Getter
    @Setter
    private int bufferLines = -1;

    @Getter
    @Setter
    private boolean cursorBlinking = false;

    private TerminalEmulatorDeviceConfiguration.CursorStyle cursorStyle = TerminalEmulatorDeviceConfiguration.CursorStyle.UNDER_BAR;

    public TerminalEmulatorDeviceConfiguration getDeviceConfig() {
        TerminalEmulatorDeviceConfiguration tedc = TerminalEmulatorDeviceConfiguration.getDefault();

        tedc = tedc.withCursorStyle(cursorStyle).withCursorBlinking(cursorBlinking);

        // if (bufferLines != -1) {
        // tedc = tedc.withLineBufferScrollbackSize(bufferLines);
        // }
        return tedc;
    }

    public SwingTerminalFontConfiguration getSwingFontConfig() {
        if (fontSize == -1) {
            return SwingTerminalFontConfiguration.getDefault();
        } else {
            return SwingTerminalFontConfiguration.getDefaultOfSize(fontSize);
        }
    }

    public TerminalEmulatorColorConfiguration getColorConfig() {
        return TerminalEmulatorColorConfiguration.getDefault();
    }

}
