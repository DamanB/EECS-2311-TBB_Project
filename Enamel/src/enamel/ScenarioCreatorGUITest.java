package enamel;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.awt.AWTException;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ScenarioCreatorGUITest {

	String[] text = { "These are pins 1, 2 and 3, the 3 pins on the left side. \nPress button 1 to continue.",
			"These are pins 4, 5 and 6, the 3 pins on the right side. \nPress button 1 to continue.",
			"These are pins 1 and 2, the top two pins on the left side. \nPress button 1 to continue.",
			"These are pins 4 and 5, the top two pins on the right side. Press button 1 to continue.",
			"These are pins 1 and 4, the two pins on the top. Press button 1 to continue.",
			"These are pins 3 and 6, the two pins on the bottom. Press button 1 to continue.",
			"That's the end of directional orientation!" };

	int[][] cell = { { 0, 1, 2 }, { 4, 5, 6 }, { 0, 1 }, { 4, 5 }, { 0, 4 }, { 2, 6 } };

	int height = 75;

	int[] commandIndex = { 1, 2, 4, 6, 3, 9, 10, 3, 12, 0, 7, 3, 5, 11, 13, 15, 0, 14, 8 };

	TestRobot r;
	int index;
	int scenario;
	int k;

	@BeforeEach
	void setUpBeforeClass() throws Exception {
		new MainFrame();
		MainFrame.changeScreen(ScenarioCreatorGUI.getScreen());
		r = new TestRobot();
		index = 0;
	}

	@Test
	void testBuild() {
		add(0);
		add(0);
		r.moveMouse(ScenarioCreatorGUI.getTextBox());
		r.pressMouse();
		r.pressKey(KeyEvent.VK_UP, 1);
		r.pressKey(KeyEvent.VK_TAB);
		r.pressKey(KeyEvent.VK_UP, 1);
		r.pressKey(KeyEvent.VK_TAB, 2);
		r.enterText("Test");
		r.pressKey(KeyEvent.VK_TAB, 4);
		r.enterText("A");
		r.pressKey(KeyEvent.VK_TAB, 7);
		r.enterText("B");
		for (int i = 0; i < commandIndex.length; i++) {
			if (commandIndex[i] == 0)
				continue;
			command(commandIndex[i]);
			add(i);
			r.rollMouseDown();
			configure(i + 1);
			edit(commandIndex[i]);
			defaultEdit(commandIndex[i]);
		}
		r.moveMouse(ScenarioCreatorGUI.getBuild());
		r.pressMouse();
		r.pressKey(KeyEvent.VK_ENTER);
		r.delay(3000);
		r.pressKey(KeyEvent.VK_ENTER);
		assertTrue(ScenarioCreatorManagerTest.test("FactoryScenarios/Scenario_99.txt", "Scenario_1.txt"));
	}

	void defaultEdit(int i) {
		switch (i) {
		case 1:
			r.enterText("1");
			break;
		case 2:
			r.pressKey(KeyEvent.VK_DOWN, 2);
			r.pressKey(KeyEvent.VK_ENTER);
			r.pressKey(KeyEvent.VK_DOWN);
			r.pressKey(KeyEvent.VK_ENTER);
			r.pressKey(KeyEvent.VK_DOWN);
			r.pressKey(KeyEvent.VK_ENTER);
			break;
		case 3:
			r.pressKey(KeyEvent.VK_UP, 2);
			break;
		case 4:
			setPin(0);
			setPin(1);
			setPin(2);
			break;
		case 5:
			r.enterText("ab");
			break;
		case 6:
			r.pressKey(KeyEvent.VK_X);
			r.pressKey(KeyEvent.VK_TAB);
			r.pressKey(KeyEvent.VK_UP);
			break;
		case 8:
			r.pressKey(KeyEvent.VK_UP);
			break;
		case 9:
			r.pressKey(KeyEvent.VK_TAB);
			r.pressKey(KeyEvent.VK_DOWN, 3);
			r.pressKey(KeyEvent.VK_ENTER);
			break;
		case 10:
			r.pressKey(KeyEvent.VK_UP);
			r.pressKey(KeyEvent.VK_TAB);
			r.pressKey(KeyEvent.VK_DOWN, 5);
			r.pressKey(KeyEvent.VK_ENTER);
			break;
		case 11:
			r.enterText("ONEE");
			break;
		case 12:
			r.pressKey(KeyEvent.VK_UP, 9);
			break;
		case 13:
			r.pressKey(KeyEvent.VK_UP, 16);
			r.pressKey(KeyEvent.VK_TAB);
			r.pressKey(KeyEvent.VK_UP);
			break;
		}
	}

	@Test
	void testRecord() {
		fail("This does not work");
	}

	@Test
	void testPlay() {
		r.moveMouse(ScenarioCreatorGUI.getPlayer());
		r.pressMouse(KeyEvent.BUTTON1_MASK);
		r.pressKey(KeyEvent.VK_DOWN, 2);
		r.pressKey(KeyEvent.VK_ENTER);
		r.pressKey(KeyEvent.VK_DOWN, 2);
		r.pressKey(KeyEvent.VK_ENTER);
	}

	// this test to copy Scenario_1.txt
	// @Test
	void testCopyOf1() throws AWTException {
		k = 0;
		scenario = 7;
		k++;
		for (int i = 0; i < 6; i++) {
			add(0);
		}
		r.pressKey(KeyEvent.VK_TAB);
		r.pressKey(KeyEvent.VK_UP, 3);
		r.pressKey(KeyEvent.VK_TAB);
		r.enterText("Directional orientation");
		for (int i = 1; i < 7; i++) {
			if (i == 2)
				r.pressKey(KeyEvent.VK_TAB, 3);
			r.pressKey(KeyEvent.VK_TAB, 3);
			r.enterText("ONEE");
		}
		command(8);
		k++;
		for (int i = 1; i < 7; i++) {
			add(i * 2 - 1);
			down();
		}
		toTop();
		command(3);
		for (int i = 1; i < 6; i++) {
			add(i * 3 - 1);
			down();
		}
		toTop();
		command(14);
		k++;
		for (int i = 1; i < 6; i++) {
			add(i * 4 - 1);
			down();
		}
		toTop();
		command(4);
		k++;
		add(0);
		r.rollMouseDown(k * height);
		for (int i = 1; i < 6; i++) {
			add(i * 5);
			down();
		}
		toTop();
		command(1);
		k++;
		for (int i = 0; i < 6; i++) {
			add(i * 6 + 1);
			down();
		}
		add(33);
		toTop();
		command(13);
		k++;
		for (int i = 0; i < 6; i++) {
			add(i * 7 + 2);
			down();
		}
		toTop();
		command(15);
		k++;
		for (int i = 0; i < 6; i++) {
			add(i * 8 + 3);
			down();
		}
		toTop();
		for (int i = 0; i < 6; i++) {
			configure(i * 8 + 1);
			if (i == 5)
				toTop();
			else
				down();
			for (int j : cell[i]) {
				setPin(j);
			}
		}
		for (int i = 0; i < 6; i++) {
			configure(i * 8 + 2);
			down();
			edit(1);
			r.enterText(text[i]);
		}
		configure(46);
		toTop();
		edit(1);
		r.enterText(text[6]);
		for (int i = 0; i < 6; i++) {
			configure(i * 8 + 3);
			down();
			edit(13);
			r.pressKey(KeyEvent.VK_UP, i * 8 + 4);
		}
		r.moveMouse(ScenarioCreatorGUI.getBuild());
		r.pressMouse();
		r.pressKey(KeyEvent.VK_ENTER);
		r.delay(3000);
		r.pressKey(KeyEvent.VK_ENTER);
		assertTrue(ScenarioCreatorManagerTest.test("FactoryScenarios/Scenario_1.txt", "Scenario_1.txt"));
	}

	void add(int i) {
		r.moveMouse(ScenarioCreatorGUI.nodes.get(i).getAdd());
		r.pressMouse();
	}

	void configure(int i) {
		r.moveMouse(ScenarioCreatorGUI.nodes.get(i).getConfigure());
		r.pressMouse();
	}

	void command(int i) {
		r.moveMouse(((JPanel) ScenarioCreatorGUI.getMainPanel().getComponent(1)).getComponent(0));
		r.pressMouse();
		if (i < index) {
			r.pressKey(KeyEvent.VK_TAB);
			r.pressKey(KeyEvent.VK_UP, index - i);
		} else {
			r.pressKey(KeyEvent.VK_TAB);
			r.pressKey(KeyEvent.VK_DOWN, i - index);
		}
		index = i;
	}

	void edit(int i) {
		switch (i) {
		case 1:
		case 3:
		case 5:
		case 6:
		case 8:
		case 11:
			r.moveMouse(((JPanel) ScenarioCreatorGUI.configuration.getComponent(0)).getComponent(2));
			break;
		case 2:
		case 4:
		case 9:
		case 10:
		case 12:
		case 13:
			r.moveMouse(((JPanel) ScenarioCreatorGUI.configuration.getComponent(0)).getComponent(3));
			break;
		default:
			r.moveMouse(((JPanel) ScenarioCreatorGUI.configuration.getComponent(0)).getComponent(0));
			break;
		}
		r.pressMouse();
	}

	void setPin(int i) {
		r.moveMouse(
				((JPanel) ScenarioCreatorGUI.configuration.getComponent(0)).getComponent(4).getLocationOnScreen().x
						+ i / 4 * 25 + 5,
				((JPanel) ScenarioCreatorGUI.configuration.getComponent(0)).getComponent(4).getLocationOnScreen().y
						+ i % 4 * 25 + 5);
		r.pressMouse();
	}

	void toTop() {
		r.rollMouseUp(scenario * k * height);
	}

	void down() {
		r.rollMouseDown(k * height);
	}
}
