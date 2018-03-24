package enamel;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class ScenarioCreatorManagerTest {
    ScenarioCreatorManager s;
    File f = new File("");


    @Test
    void testSaveToFile() {
        ScenarioCreatorManager s1, s4;
        s1 = new ScenarioCreatorManager(new File("Enamel/FactoryScenarios/Scenario_1.txt"));
        s1.parseFile();


        s4 = new ScenarioCreatorManager(new File("Enamel/FactoryScenarios/Scenario_4.txt"));
        s4.setCellNum(s1.getCellNum());
        s4.setButtonNum(s1.getButtonNum());
        s4.setTitle(s1.getTitle());
        s4.setQuestions(s1.getQuestions());

        assertEquals(s1.toString(), s4.toString());
    }


    @Test
    void testCellButtonNum() {
        s = new ScenarioCreatorManager(new File("Enamel/FactoryScenarios/Scenario_1.txt"));
        s.parseFile();
        assertEquals(1, s.getCellNum());
        assertEquals(4, s.getButtonNum());
    }


    @Test
    void testAllCommands()
    {
        regenerateScenarioOne();
    }

    @Test
    void testParseFile()
    {
        regenerateScenarioOne();

        ScenarioCreatorManager s = new ScenarioCreatorManager(new File("Enamel/FactoryScenarios/Scenario_65.txt"));

        s.parseFile();
    }

    // TODO test for false input on all commands
    @Test
    void testFalseInputCommands()
    {

    }

    // TODO add all commands to the method
    private void regenerateScenarioOne() {
        s = new ScenarioCreatorManager(new File("Enamel/FactoryScenarios/Scenario_65.txt"));
        s.setButtonNum(5);
        s.setCellNum(5);
        s.setTitle("Scenario 65 File");

        if (!(s.addDispCellPins("0", "11100000"))) {
            fail(s.getErrorMessage());
        }

        if (!(s.addText(Arrays.asList("These are pins 1, 2 and 3, the 3 pins on the left side. ", "Press button 1 to continue.")))) {
            fail(s.getErrorMessage());
        }

        if (!(s.addSkipButton("0", "ONEE"))) {
            fail(s.getErrorMessage());
        }

        if (!(s.addUserInput())) {
            fail(s.getErrorMessage());
        }

        // Use next question to increment the question index and put a space in the ScenarioFile for the next question
        s.nextQuestion();

        if (!(s.addSkipPos("ONEE"))) {
            fail(s.getErrorMessage());
        }

        if (!(s.addDispCellClear("0"))) {
            fail(s.getErrorMessage());
        }

        if (!(s.addPause("1"))) {
            fail(s.getErrorMessage());
        }

        if (!(s.addResetButtons())) {
            fail(s.getErrorMessage());
        }

        if (!(s.addDispCellPins("0", "00011100"))) {
            fail(s.getErrorMessage());
        }

        if (!(s.addText(Arrays.asList("These are pins 4, 5 and 6, the 3 pins on the right side. ", "Press button 1 to continue.")))) {
            fail(s.getErrorMessage());
        }

        if (!(s.addSkipButton("0", "ONEE"))) {
            fail(s.getErrorMessage());
        }

        if (!(s.addUserInput())) {
            fail(s.getErrorMessage());
        }

        s.nextQuestion();

        if (!(s.addSkipPos("ONEE"))) {
            fail(s.getErrorMessage());
        }

        if (!(s.addDispCellClear("0"))) {
            fail(s.getErrorMessage());
        }

        if (!(s.addPause("1"))) {
            fail(s.getErrorMessage());
        }

        if (!(s.addResetButtons())) {
            fail(s.getErrorMessage());
        }

        if (!(s.addDispCellPins("0", "11000000"))) {
            fail(s.getErrorMessage());
        }

        if (!(s.addText(Arrays.asList("These are pins 4, 5 and 6, the 3 pins on the right side.", "Press button 1 to continue.")))) {
            fail(s.getErrorMessage());
        }

        if (!(s.addSkipButton("0", "ONEE"))) {
            fail(s.getErrorMessage());
        }

        if (!(s.addUserInput())) {
            fail(s.getErrorMessage());
        }


        s.nextQuestion();

        if (!(s.addSkipPos("ONEE"))) {
            fail(s.getErrorMessage());
        }

        if (!(s.addDispCellClear("0"))) {
            fail(s.getErrorMessage());
        }

        if (!(s.addPause("1"))) {
            fail(s.getErrorMessage());
        }

        if (!(s.addResetButtons())) {
            fail(s.getErrorMessage());
        }

        if (!(s.addDispCellPins("0", "00011000"))) {
            fail(s.getErrorMessage());
        }

        if (!(s.addText(Arrays.asList("These are pins 4 and 5, the top two pins on the right side. Press button 1 to continue.")))) {
            fail(s.getErrorMessage());
        }

        if (!(s.addSkipButton("0", "ONEE"))) {
            fail(s.getErrorMessage());
        }

        if (!(s.addUserInput())) {
            fail(s.getErrorMessage());
        }

        s.nextQuestion();

        if (!(s.addSkipPos("ONEE"))) {
            fail(s.getErrorMessage());
        }

        if (!(s.addDispCellClear("0"))) {
            fail(s.getErrorMessage());
        }

        if (!(s.addPause("1"))) {
            fail(s.getErrorMessage());
        }

        if (!(s.addResetButtons())) {
            fail(s.getErrorMessage());
        }

        if (!(s.addDispCellPins("0", "10010000"))) {
            fail(s.getErrorMessage());
        }

        if (!(s.addText(Arrays.asList("These are pins 1 and 4, the two pins on the top. Press button 1 to continue.")))) {
            fail(s.getErrorMessage());
        }

        if (!(s.addSkipButton("0", "ONEE"))) {
            fail(s.getErrorMessage());
        }

        if (!(s.addUserInput())) {
            fail(s.getErrorMessage());
        }

        s.nextQuestion();

        if (!(s.addSkipPos("ONEE"))) {
            fail(s.getErrorMessage());
        }

        if (!(s.addDispCellClear("0"))) {
            fail(s.getErrorMessage());
        }

        if (!(s.addPause("1"))) {
            fail(s.getErrorMessage());
        }

        if (!(s.addResetButtons())) {
            fail(s.getErrorMessage());
        }

        if (!(s.addDispCellPins("0", "10010000"))) {
            fail(s.getErrorMessage());
        }

        if (!(s.addText(Arrays.asList("These are pins 3 and 6, the two pins on the bottom. Press button 1 to continue.")))) {
            fail(s.getErrorMessage());
        }

        if (!(s.addSkipButton("0", "ONEE"))) {
            fail(s.getErrorMessage());
        }

        if (!(s.addUserInput())) {
            fail(s.getErrorMessage());
        }

        s.nextQuestion();

        if (!(s.addSkipPos("ONEE"))) {
            fail(s.getErrorMessage());
        }

        if (!(s.addText(Arrays.asList("That's the end of directional orientation")))) {
            fail(s.getErrorMessage());
        }

        if (!(s.addDispCellClear("0"))) {
            fail(s.getErrorMessage());
        }


        // After all the commands have been added saveFile()
        s.saveToFile();
    }
}
