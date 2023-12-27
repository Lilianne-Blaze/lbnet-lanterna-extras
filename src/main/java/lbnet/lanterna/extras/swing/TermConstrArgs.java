package lbnet.lanterna.extras.swing;

import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.TerminalEmulatorColorConfiguration;
import com.googlecode.lanterna.terminal.swing.TerminalEmulatorDeviceConfiguration;
import java.awt.Font;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class TermConstrArgs {

    @Getter
    @Setter
    @With
    private int fontSize = -1;

    @Getter
    @Setter
    @With
    private int bufferLines = -1;

    @Getter
    @Setter
    @With
    private boolean cursorBlinking = false;

    @Getter
    @Setter
    @With
    private boolean preferNewFonts = false;

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
        int wantedFontSize = fontSize != -1 ? fontSize : SwingTerminalFontConfiguration.DEFAULT_FONT_SIZE;

        if (preferNewFonts) {
            SwingTerminalFontConfiguration stfc = SwingTerminalFontConfiguration.getDefaultOfSize(wantedFontSize);
            stfc = attemptPrependFonts(stfc, "Cascadia Mono", "Consolas", "Lucida Console");
            return stfc;
        } else {
            return SwingTerminalFontConfiguration.getDefaultOfSize(wantedFontSize);
        }

    }

    public TerminalEmulatorColorConfiguration getColorConfig() {
        return TerminalEmulatorColorConfiguration.getDefault();
    }

    public static SwingTerminalFontConfiguration attemptPrependFonts(SwingTerminalFontConfiguration stfc,
            String... fontNames) {
        try {
            Field fFontPriority = AWTTerminalFontConfiguration.class.getDeclaredField("fontPriority");
            Field fBoldMode = AWTTerminalFontConfiguration.class.getDeclaredField("boldMode");
            fFontPriority.setAccessible(true);
            fBoldMode.setAccessible(true);

            List<Font> fonts = new ArrayList((List<Font>) fFontPriority.get(stfc));
            AWTTerminalFontConfiguration.BoldMode boldMode = (AWTTerminalFontConfiguration.BoldMode) fBoldMode
                    .get(stfc);

            Font fontOne = fonts.get(0);
            for (int i = 0; i < fontNames.length; i++) {
                Font font = new Font(fontNames[i], fontOne.getStyle(), fontOne.getSize());
                fonts.add(i, font);
            }

            return new SwingTerminalFontConfiguration(true, boldMode, fonts.toArray(new Font[0]));
        } catch (Exception e) {
            log.warn("Reflection problems: {}", e.toString());
        }
        return stfc;
    }

}
