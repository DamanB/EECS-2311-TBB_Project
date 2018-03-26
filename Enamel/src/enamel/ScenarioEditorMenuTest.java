package enamel;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ScenarioEditorMenuTest {

	@BeforeEach
	void setUp() throws Exception {
		new MainFrame();
		MainFrame.changeScreen(ScenarioEditorMenu.getScreen());
	}

	@Test
	void test() throws AWTException {
		Robot r = new Robot();
		r.delay(100);
		r.mouseMove(MainFrame.frame.getX() + ScenarioEditorMenu.create.getX() + 20,
					MainFrame.frame.getY() + ScenarioEditorMenu.create.getY() + 40);
		r.delay(100);
		r.mousePress(KeyEvent.BUTTON1_MASK);
		r.delay(100);
		r.mouseRelease(KeyEvent.BUTTON1_MASK);
		r.delay(100);
		assertEquals(ScenarioCreatorGUI.getMainPanel(), MainFrame.getMainPanel().getComponent(0));
		r.delay(100);
		r.mouseMove(MainFrame.frame.getX() + 1200, MainFrame.frame.getY() + 400);
		r.delay(100);
		r.mousePress(KeyEvent.BUTTON1_MASK);
		r.delay(100);
		r.mouseRelease(KeyEvent.BUTTON1_MASK);
		r.delay(100);
		r.keyPress(KeyEvent.VK_ENTER);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_ENTER);
		r.delay(100);
		assertEquals(ScenarioEditorMenu.pane, MainFrame.getMainPanel().getComponent(0));
	}

	@Test
	void testEdit() throws AWTException {
		Robot r = new Robot();
		r.delay(100);
		r.mouseMove(MainFrame.frame.getX() + ScenarioEditorMenu.modify.getX() + 20,
				MainFrame.frame.getY() + ScenarioEditorMenu.modify.getY() + 40);
		r.delay(100);
		r.mousePress(KeyEvent.BUTTON1_MASK);
		r.delay(100);
		r.mouseRelease(KeyEvent.BUTTON1_MASK);
		r.delay(100);
		r.keyPress(KeyEvent.VK_ENTER);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_ENTER);
		r.delay(100);
		fail("Undo");
	}
}
