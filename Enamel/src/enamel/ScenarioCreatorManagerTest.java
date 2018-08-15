package enamel;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class ScenarioCreatorManagerTest {

	// MAKE SURE THE PATH "Enamel/FactoryScenarios/*" IS AVAILABLE IN YOUR PROJECT
	// PATH.
	// IF NOT REOPEN THE PROJECT WITH THE VALID PATH
	// Newest Version

	private ScenarioCreatorManager s;

	@Test
	void testParseFile() {
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
	void testAllCommands() {
		generateAllCommands();
	}

	@Test
	void testParseFile1() {
		generateAllCommands();

		ScenarioCreatorManager s = new ScenarioCreatorManager(new File("Enamel/FactoryScenarios/Scenario_65.txt"));

		s.parseFile();

		System.out.println(s.toString()); // TODO fix this idk what its supposed to do
	}

	@Test
	void testCtor1() {
		boolean result = false;

		try {
			s = new ScenarioCreatorManager(new File("invalidFile.pdf"));
		} catch (IllegalArgumentException e) {
			result = true;
		}

		assertTrue(result);
	}

	@Test
	void testCtor2() {
		boolean result = true;

		try {
			s = new ScenarioCreatorManager(new File("Scenario_55.txt"));
		} catch (IllegalArgumentException e) {
			result = false;
		}

		assertTrue(result);
	}

	@Test
	void testAccessors() {
		int cellNum = 5, buttonNum = 5;
		Integer questionIndex = 5;

		s = new ScenarioCreatorManager(new File("Scenario_10.txt"));

		s.setCellNum(cellNum);
		s.setButtonNum(buttonNum);
		s.setQuestionIndex(questionIndex);

		assertEquals(s.getCellNum(), cellNum);
		assertEquals(ScenarioCreatorManager.getNumButtons(), buttonNum);
		assertEquals(s.getButtonNum(), buttonNum);
		assertEquals(ScenarioCreatorManager.getNumCells(), cellNum);
		assertEquals(s.getQuestionIndex(), questionIndex);
		assertEquals(s.getErrorMessage(), "Nothing");
	}

	@Test
	void testFalseInputCommands() {
		s = new ScenarioCreatorManager(new File("Enamel/FactoryScenarios/Scenario_65.txt"));
		s.setButtonNum(5);
		s.setCellNum(5);
		s.setTitle("Scenario 65 File");

		assertFalse(s.addRepeatButton("d"));

		assertFalse(s.addDispCellPins("d", "d"));
		assertFalse(s.addSound(new File("file.pdf")));
		assertFalse(s.addSkip("@@"));

		s.nextQuestion();

		assertFalse(s.addSkipPos("@"));
		assertFalse(s.addDispCellClear("a"));
		assertFalse(s.addPause("a"));
		assertFalse(s.addPause("a"));
		assertFalse(s.addDispCellPins("a", "a"));
		assertFalse(s.addSkipButton("a", "@@"));
		assertFalse(s.addRepeatButton("15"));
		assertFalse(s.addDispString("123"));
		assertFalse(s.addDispCellChar("s", "1"));
		assertFalse(s.addDispCellRaise("a", "a"));
		assertFalse(s.addDispCellLower("a", "a"));

		assertFalse(s.addSkipPos("@@"));
		assertFalse(s.addPause("$"));
		assertFalse(s.addDispCellPins("a", "a"));
		assertFalse(s.addSkipButton("a", "@@"));

		assertFalse(s.addPause("@"));
		assertFalse(s.addDispCellPins("@", "@"));
		assertFalse(s.addSkipButton("@", "@"));

		assertFalse(s.addSkipPos("@"));
		assertFalse(s.addDispCellClear("@"));
		assertFalse(s.addDispCellRaise("@", "@"));
		assertFalse(s.addPause("@"));
		assertFalse(s.addDispCellPins("@", "10010@@"));
		assertFalse(s.addSkipButton("@", "@"));

		assertFalse(s.addSkipPos("@"));
		assertFalse(s.addDispCellClear("*"));
		assertFalse(s.addPause("#"));
		assertFalse(s.addDispCellPins("#", "#"));
		assertFalse(s.addSkipButton("#", "O#"));
	}

	// TODO change checkResult to assertTrue
	private void generateAllCommands() {
		s = new ScenarioCreatorManager(new File("Enamel/FactoryScenarios/Scenario_65.txt"));
		s.setButtonNum(5);
		s.setCellNum(5);
		s.setTitle("Scenario 65 File");

		checkResult(s.addRepeatButton("0"));

		checkResult(s.addDispCellPins("0", "11100000"));
		checkResult(s.addText(Arrays.asList("These are pins 1, 2 and 3, the 3 pins on the left side. ",
				"Press button 1 to continue.")));
		checkResult(s.addSkipButton("0", "ONEE"));
		checkResult(s.addUserInput());
		checkResult(s.addSound(new File("file.wav")));
		checkResult(s.addSkip("button"));

		s.nextQuestion();

		checkResult(s.addSkipPos("ONEE"));
		checkResult(s.addDispCellClear("0"));
		checkResult(s.addDispClearAll());
		checkResult(s.addPause("1"));
		checkResult(s.addResetButtons());
		checkResult(s.addRepeat());
		checkResult(s.addEndRepeat());
		checkResult(s.addPause("13"));
		checkResult(s.addPause("13"));
		checkResult(s.addDispCellPins("0", "00011100"));
		checkResult(s.addText(Arrays.asList("These are pins 4, 5 and 6, the 3 pins on the right side. ",
				"Press button 1 to continue.")));
		checkResult(s.addSkipButton("0", "ONEE"));
		checkResult(s.addUserInput());
		checkResult(s.addRepeatButton("0"));
		checkResult(s.addDispString("REEEEEee"));
		checkResult(s.addDispCellChar("0", "e"));
		checkResult(s.addDispCellRaise("0", "3"));
		checkResult(s.addDispCellLower("0", "3"));
		checkResult(s.addDispCellLowerPins());

		s.nextQuestion();

		checkResult(s.addSkipPos("ONEE"));
		checkResult(s.addDispCellClear("0"));
		checkResult(s.addPause("1"));
		checkResult(s.addResetButtons());
		checkResult(s.addDispCellPins("0", "11000000"));
		checkResult(s.addText(Arrays.asList("These are pins 4, 5 and 6, the 3 pins on the right side.",
				"Press button 1 to continue.")));
		checkResult(s.addSkipButton("0", "ONEE"));
		checkResult(s.addUserInput());

		s.nextQuestion();

		checkResult(s.addSkipPos("ONEE"));
		checkResult(s.addDispCellClear("0"));
		checkResult(s.addPause("1"));
		checkResult(s.addResetButtons());
		checkResult(s.addDispCellPins("0", "00011000"));
		checkResult(s.addText(Arrays
				.asList("These are pins 4 and 5, the top two pins on the right side. Press button 1 to continue.")));
		checkResult(s.addSkipButton("0", "ONEE"));
		checkResult(s.addUserInput());

		s.nextQuestion();

		checkResult(s.addSkipPos("ONEE"));
		checkResult(s.addDispCellClear("0"));
		checkResult(s.addPause("1"));
		checkResult(s.addResetButtons());
		checkResult(s.addDispCellPins("0", "10010000"));
		checkResult(s.addText(
				Arrays.asList("These are pins 1 and 4, the two pins on the top. Press button 1 to continue.")));
		checkResult(s.addSkipButton("0", "ONEE"));
		checkResult(s.addUserInput());

		s.nextQuestion();

		checkResult(s.addSkipPos("ONEE"));
		checkResult(s.addDispCellClear("0"));
		checkResult(s.addPause("1"));
		checkResult(s.addResetButtons());
		checkResult(s.addDispCellPins("0", "10010000"));
		checkResult(s.addText(
				Arrays.asList("These are pins 3 and 6, the two pins on the bottom. Press button 1 to continue.")));
		checkResult(s.addSkipButton("0", "ONEE"));
		checkResult(s.addUserInput());

		s.nextQuestion();

		checkResult(s.addSkipPos("ONEE"));
		checkResult(s.addText(Arrays.asList("That's the end of directional orientation")));
		checkResult(s.addDispCellClear("0"));

		// After all the commands have been added saveFile()
		assertTrue(s.saveToFile());
	}

	private void checkResult(boolean result) {
		if (!result) {
			fail("Error on command " + s.getErrorMessage());
		}
	}

	@Test
	public static boolean test(String f1, String f2) {
		ScenarioCreatorManager s1 = new ScenarioCreatorManager(new File(f1));
		ScenarioCreatorManager s2 = new ScenarioCreatorManager(new File(f2));
		s1.parseFile();
		s2.parseFile();
		return s1.equals(s2);
	}
}
