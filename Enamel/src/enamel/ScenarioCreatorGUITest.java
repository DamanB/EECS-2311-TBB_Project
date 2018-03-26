package enamel;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.AWTException;
import java.awt.event.KeyEvent;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ScenarioCreatorGUITest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		new MainFrame();
		MainFrame.changeScreen(ScenarioCreatorGUI.getScreen());
	}

	@Test
	void test() throws AWTException {
		TestRobot r = new TestRobot();
		r.moveMouse(ScenarioCreatorGUI.getMainPanel().getLocation());
		r.pressMouse(KeyEvent.BUTTON1_MASK);
		r.pressKey(KeyEvent.VK_TAB);
		r.pressKey(KeyEvent.VK_UP);
		r.pressKey(KeyEvent.VK_UP);
		r.pressKey(KeyEvent.VK_UP);
		
	}

}
