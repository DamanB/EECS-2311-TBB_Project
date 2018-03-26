package enamel;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.KeyEvent;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ScenarioEditorMenuTest {

	static TestRobot r;

	@BeforeAll
	static void setUp() throws Exception {
		new MainFrame();
		MainFrame.changeScreen(ScenarioEditorMenu.getScreen());
		r = new TestRobot();
	}

	@Test
	void test() {
		r.moveMouse(ScenarioEditorMenu.create);
		r.pressMouse(KeyEvent.BUTTON1_MASK);
		assertEquals(ScenarioCreatorGUI.getMainPanel(), MainFrame.getMainPanel().getComponent(0));
		r.moveMouse(ScenarioCreatorGUI.getBack());
		r.pressMouse(KeyEvent.BUTTON1_MASK);
		r.pressKey(KeyEvent.VK_ENTER);
		assertEquals(ScenarioEditorMenu.pane, MainFrame.getMainPanel().getComponent(0));
	}

	@Test
	void testEdit() {
		r.moveMouse(ScenarioEditorMenu.modify);
		r.pressMouse(KeyEvent.BUTTON1_MASK);
		r.pressKey(KeyEvent.VK_ENTER);
		fail("Undo");
	}
}
