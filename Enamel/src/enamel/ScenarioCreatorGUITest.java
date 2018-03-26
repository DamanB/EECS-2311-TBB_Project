package enamel;

import java.awt.AWTException;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import org.junit.jupiter.api.BeforeAll;
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

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		new MainFrame();
		MainFrame.changeScreen(ScenarioCreatorGUI.getScreen());
	}

	// this test to copy Scenario_1.txt
	@Test
	void testCopyOf1() throws AWTException {
		TestRobot r = new TestRobot();
		r.moveMouse(ScenarioCreatorGUI.getTextBox());
		r.pressMouse();
		r.pressKey(KeyEvent.VK_TAB);
		r.pressKey(KeyEvent.VK_UP, 3);
		r.pressKey(KeyEvent.VK_TAB);
		r.enterText("Directional orientation");
		for (int i = 0; i < 6; i++) {
			r.moveMouse(ScenarioCreatorGUI.nodes.get(0).getAdd());
			r.pressMouse();
		}
		r.pressKey(KeyEvent.VK_TAB, 5);
		r.enterText("ONEE");
		r.pressKey(KeyEvent.VK_TAB, 3);
		for (int i = 2; i < 7; i++) {
			r.pressKey(KeyEvent.VK_TAB, 3);
			r.enterText("ONEE");
		}
		r.pressKey(KeyEvent.VK_TAB, 4);
		r.pressKey(KeyEvent.VK_DOWN, 8);
		for (int i = 1; i < 7; i++) {
			r.moveMouse(ScenarioCreatorGUI.nodes.get(i * 2 - 1).getAdd());
			r.pressMouse();
		}
		r.moveMouse(((JPanel) ScenarioCreatorGUI.getMainPanel().getComponent(1)).getComponent(1));
		r.pressMouse();
		r.pressKey(KeyEvent.VK_TAB);
		r.pressKey(KeyEvent.VK_UP, 5);
		for (int i = 1; i < 6; i++) {
			r.moveMouse(ScenarioCreatorGUI.nodes.get(i * 3 - 1).getAdd());
			r.pressMouse();
		}
		r.moveMouse(((JPanel) ScenarioCreatorGUI.getMainPanel().getComponent(1)).getComponent(1));
		r.pressMouse();
		r.pressKey(KeyEvent.VK_TAB);
		r.pressKey(KeyEvent.VK_DOWN, 11);
		for (int i = 1; i < 6; i++) {
			r.moveMouse(ScenarioCreatorGUI.nodes.get(i * 4 - 1).getAdd());
			r.pressMouse();
		}
		r.moveMouse(((JPanel) ScenarioCreatorGUI.getMainPanel().getComponent(1)).getComponent(1));
		r.pressMouse();
		r.pressKey(KeyEvent.VK_TAB);
		r.pressKey(KeyEvent.VK_UP, 10);
		r.moveMouse(ScenarioCreatorGUI.nodes.get(0).getAdd());
		r.pressMouse();
		for (int i = 1; i < 6; i++) {
			r.moveMouse(ScenarioCreatorGUI.nodes.get(i * 5).getAdd());
			r.pressMouse();
		}
		r.moveMouse(((JPanel) ScenarioCreatorGUI.getMainPanel().getComponent(1)).getComponent(1));
		r.pressMouse();
		r.pressKey(KeyEvent.VK_TAB);
		r.pressKey(KeyEvent.VK_UP, 3);
		for (int i = 0; i < 6; i++) {
			r.moveMouse(ScenarioCreatorGUI.nodes.get(i * 6 + 1).getAdd());
			r.pressMouse();
		}
		r.moveMouse(ScenarioCreatorGUI.nodes.get(33).getAdd());
		r.pressMouse();
		r.moveMouse(((JPanel) ScenarioCreatorGUI.getMainPanel().getComponent(1)).getComponent(1));
		r.pressMouse();
		r.pressKey(KeyEvent.VK_TAB);
		r.pressKey(KeyEvent.VK_DOWN, 12);
		for (int i = 0; i < 6; i++) {
			r.moveMouse(ScenarioCreatorGUI.nodes.get(i * 7 + 2).getAdd());
			r.pressMouse();
		}
		r.moveMouse(((JPanel) ScenarioCreatorGUI.getMainPanel().getComponent(1)).getComponent(1));
		r.pressMouse();
		r.pressKey(KeyEvent.VK_TAB);
		r.pressKey(KeyEvent.VK_DOWN, 2);
		for (int i = 0; i < 6; i++) {
			r.moveMouse(ScenarioCreatorGUI.nodes.get(i * 8 + 3).getAdd());
			r.pressMouse();
		}
		for (int i = 0; i < 6; i++) {
			r.moveMouse(ScenarioCreatorGUI.nodes.get(i * 8 + 1).getConfigure());
			r.pressMouse();
			for (int j = 0; j < 6; j++) {
				for (int k : cell[j]) {
					r.moveMouse(
							ScenarioCreatorGUI.configuration.getComponent(3).getLocationOnScreen().x + k / 4 * 25 + 5,
							ScenarioCreatorGUI.configuration.getComponent(3).getLocationOnScreen().y + k % 4 * 25 + 5);
					r.pressMouse();
				}
			}
		}
		for (int i = 0; i < 6; i++) {
			r.moveMouse(ScenarioCreatorGUI.nodes.get(i * 8 + 2).getConfigure());
			r.pressMouse();
			r.moveMouse(ScenarioCreatorGUI.configuration.getComponent(2));
			r.pressMouse();
			r.enterText(text[i]);
		}
		r.moveMouse(ScenarioCreatorGUI.nodes.get(46).getConfigure());
		r.pressMouse();
		r.moveMouse(ScenarioCreatorGUI.configuration.getComponent(2));
		r.pressMouse();
		r.enterText(text[6]);
		for (int i = 0; i < 6; i++) {
			r.moveMouse(ScenarioCreatorGUI.nodes.get(i * 8 + 3).getConfigure());
			r.pressMouse();
			r.moveMouse(ScenarioCreatorGUI.configuration.getComponent(4));
			r.pressMouse();
			r.pressKey(KeyEvent.VK_UP, i * 8 + 4);
		}
		r.moveMouse(ScenarioCreatorGUI.getBuild());
		r.pressMouse();
		r.pressKey(KeyEvent.VK_ENTER);
		r.delay(3000);
		r.pressKey(KeyEvent.VK_ENTER);
		ScenarioCreatorManagerTest.test("FactoryScenarios/Scenario_1.txt", "Scenario_1.txt");
	}

}
