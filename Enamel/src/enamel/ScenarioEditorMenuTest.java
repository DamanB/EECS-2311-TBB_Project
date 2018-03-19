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

	ByteArrayOutputStream b;
	PrintStream c;

	@BeforeEach
	void setUp() throws Exception {
		new MainFrame();
		MainFrame.changeScreen(ScenarioEditorMenu.getScreen());
		b = new ByteArrayOutputStream();
		System.setOut(new PrintStream(b));
		c = System.out;
	}

	@AfterEach
	void clear() throws IOException {
		b.flush();
		b.close();
		System.setOut(c);
	}

	@Test
	void test() throws AWTException {
		Robot r = new Robot();
		r.delay(100);
		assertEquals(ScenarioCreatorGUI.getMainPanel(), MainFrame.getMainPanel().getComponent(0));
	}

	@Test
	void testCannel() throws AWTException {
		Robot r = new Robot();
		r.delay(100);
		r.mouseMove(MainFrame.frame.getX() + ScenarioEditorMenu.create.getX() + 20,
				MainFrame.frame.getY() + ScenarioEditorMenu.create.getY() + 40);
		r.delay(100);
		r.mousePress(KeyEvent.BUTTON1_MASK);
		r.delay(100);
		r.mouseRelease(KeyEvent.BUTTON1_MASK);
		r.delay(100);
		r.mouseMove(MainFrame.frame.getX() + ScenarioEditorMenu.back.getX() + 20,
				MainFrame.frame.getY() + ScenarioEditorMenu.back.getY() + 40);
		r.delay(100);
		r.mousePress(KeyEvent.BUTTON1_MASK);
		r.delay(100);
		r.mouseRelease(KeyEvent.BUTTON1_MASK);
		r.delay(100);
		assertEquals(ScenarioEditorMenu.pane, MainFrame.getMainPanel().getComponent(0));
	}

}
