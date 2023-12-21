package lbnet.lanterna.extras.demo;

import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;

public class DemoShared {

    public static void printTestData1(Terminal term) {
        try {
            for (int i = 0; i < 5; i++) {
                for (int i2 = 0; i2 < 8; i2++) {
                    term.putCharacter('_');
                    for (int i3 = 0; i3 < 9; i3++) {
                        term.putCharacter(Character.forDigit(i2, 10));
                    }
                }
                term.putCharacter('\n');

                for (int i4 = 1; i4 < 10; i4++) {
                    term.putString("---");
                    term.putCharacter(Character.forDigit(i4, 10));
                    term.putCharacter('\n');
                }

            }
        } catch (IOException e) {
        }
    }

}
