package enamel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CommandTest {
    Command testCommand;
    private final int BRAILLE_CELL_SIZE = 5;
    private final int BUTTON_SIZE = 5;

    @Test
    public void testCtorSetInput() {
        try {
            testCommand = new DispCellChar("/~disp-cell-char:", "1 P", this.BRAILLE_CELL_SIZE);
            testCommand = new DispCellClear("/~disp-clear-cell:", "1", this.BRAILLE_CELL_SIZE);
            testCommand = new DispCellLower("/~disp-cell-lower:", "0 3", this.BRAILLE_CELL_SIZE);
            testCommand = new DispCellPins("/~disp-cell-pins:", "1 10101011", this.BRAILLE_CELL_SIZE);
            testCommand = new DispCellLower("/~disp-cell-lower:", "0 3", this.BRAILLE_CELL_SIZE);

            testCommand = new RepeatButton("/~repeat-button:", "3", this.BUTTON_SIZE);
            testCommand = new SkipButton("/~skip-button:", "0 GoHere", this.BUTTON_SIZE);

            testCommand = new DispClearAll("/~disp-clearAll", "");
            testCommand = new DispCellLowerPins("/~disp-cell-lowerPins:", "");
            testCommand = new Repeat("/~repeat", "");
            testCommand = new EndRepeat("/~end-repeat", "");
            testCommand = new ResetButtons("/~reset-buttons", "");
            testCommand = new UserInput("/~user-input", "");
            testCommand = new DispString("/~disp-string:", "yes");
            testCommand = new Pause("/~pause:", "3");
            testCommand = new Skip("/~skip:", "Yes");
            testCommand = new SkipPos("Yes");
            testCommand = new Sound("/~sound:", "SoundFile.wav");
            testCommand = new Space();
            testCommand = new Text("myNameaJeff2353");
        } catch (IllegalArgumentException e) {
            fail(e.toString());
        }
    }

    @Test
    public void testGetSetCommand() {
        String commandName = "/~disp-cell-char:";
        testCommand = new DispCellChar("jeff", "1 P", this.BRAILLE_CELL_SIZE);
        testCommand.setCommand(commandName);
        assertEquals(commandName, testCommand.getCommand());
    }

    @Test
    public void testToString() {
        String commandName = "/~disp-cell-char:";
        String input = "1 P";
        testCommand = new DispCellChar(commandName, input, this.BRAILLE_CELL_SIZE);
        assertEquals(commandName + input, testCommand.toString());

        input = "SDfSDFSDF";
        testCommand = new SkipPos(input);
        assertEquals("/~" + input, testCommand.toString());

        input="afSF23SDaafa";
        testCommand = new Text(input);
        assertEquals(input, testCommand.toString());
    }
}
