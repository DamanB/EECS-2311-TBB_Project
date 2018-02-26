package enamel;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JOptionPane;

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
		r.mouseMove(
				MainFrame.frame.getX() + ScenarioEditorMenu.pane.getComponent(1).getX()
						+ ScenarioEditorMenu.create.getX() + 20,
				MainFrame.frame.getY() + ScenarioEditorMenu.pane.getComponent(1).getY()
						+ ScenarioEditorMenu.create.getY() + 40);
		r.delay(100);
		r.mousePress(KeyEvent.BUTTON1_MASK);
		r.delay(100);
		r.mouseRelease(KeyEvent.BUTTON1_MASK);
		r.delay(100);
		assertEquals("Please Enter The Number of Braille Cells You Wish To Use: ", b.toString());
		r.delay(100);
		b.reset();
		r.delay(100);
		r.keyPress(KeyEvent.VK_ENTER);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_ENTER);
		r.delay(100);
		assertEquals("This is an invalid number. Please try again", b.toString());
		r.delay(100);
		b.reset();
		r.delay(100);
		r.keyPress(KeyEvent.VK_ENTER);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_ENTER);
		r.delay(100);
		r.keyPress(KeyEvent.VK_A);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_A);
		r.delay(100);
		r.keyPress(KeyEvent.VK_ENTER);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_ENTER);
		r.delay(100);
		assertEquals("This is an invalid number. Please try again", b.toString());
		r.delay(100);
		b.reset();
		r.delay(100);
		r.keyPress(KeyEvent.VK_ENTER);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_ENTER);
		r.delay(100);
		r.keyPress(KeyEvent.VK_0);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_0);
		r.delay(100);
		r.keyPress(KeyEvent.VK_ENTER);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_ENTER);
		r.delay(100);
		assertEquals("This is an invalid number. Please try again", b.toString());
		r.delay(100);
		b.reset();
		r.delay(100);
		r.keyPress(KeyEvent.VK_ENTER);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_ENTER);
		r.delay(100);
		r.keyPress(KeyEvent.VK_1);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_1);
		r.delay(100);
		r.keyPress(KeyEvent.VK_ENTER);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_ENTER);
		r.delay(100);
		assertEquals("Please Enter The Number of Response Buttons You Wish To Use: ", b.toString());
		r.delay(100);
		b.reset();
		r.delay(100);
		r.keyPress(KeyEvent.VK_ENTER);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_ENTER);
		r.delay(100);
		assertEquals("This is an invalid number. Please try again", b.toString());
		r.delay(100);
		b.reset();
		r.delay(100);
		r.keyPress(KeyEvent.VK_ENTER);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_ENTER);
		r.delay(100);
		r.keyPress(KeyEvent.VK_A);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_A);
		r.delay(100);
		r.keyPress(KeyEvent.VK_ENTER);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_ENTER);
		r.delay(100);
		assertEquals("This is an invalid number. Please try again", b.toString());
		r.delay(100);
		b.reset();
		r.delay(100);
		r.keyPress(KeyEvent.VK_ENTER);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_ENTER);
		r.delay(100);
		r.keyPress(KeyEvent.VK_0);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_0);
		r.delay(100);
		r.keyPress(KeyEvent.VK_ENTER);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_ENTER);
		r.delay(100);
		assertEquals("This is an invalid number. Please try again", b.toString());
		r.delay(100);
		b.reset();
		r.delay(100);
		r.keyPress(KeyEvent.VK_ENTER);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_ENTER);
		r.delay(100);
		r.keyPress(KeyEvent.VK_4);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_4);
		r.delay(100);
		r.keyPress(KeyEvent.VK_ENTER);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_ENTER);
		r.delay(100);
		assertEquals("Please Input the Scenarios Title", b.toString());
		r.delay(100);
		b.reset();
		r.delay(100);
		r.keyPress(KeyEvent.VK_ENTER);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_ENTER);
		r.delay(100);
		assertEquals("Please input a valid title", b.toString());
		r.delay(100);
		b.reset();
		r.delay(100);
		r.keyPress(KeyEvent.VK_ENTER);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_ENTER);
		r.delay(100);
		r.keyPress(KeyEvent.VK_A);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_A);
		r.delay(100);
		r.keyPress(KeyEvent.VK_ENTER);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_ENTER);
		r.delay(100);
		assertEquals(ScenarioCreatorGUI.mainPanel, MainFrame.getMainPanel());
	}

	@Test
	void testCannel() throws AWTException {
		Robot r = new Robot();
		r.delay(100);
		r.mouseMove(
				MainFrame.frame.getX() + ScenarioEditorMenu.pane.getComponent(1).getX()
						+ ScenarioEditorMenu.create.getX() + 20,
				MainFrame.frame.getY() + ScenarioEditorMenu.pane.getComponent(1).getY()
						+ ScenarioEditorMenu.create.getY() + 40);
		r.delay(100);
		r.mousePress(KeyEvent.BUTTON1_MASK);
		r.delay(100);
		r.mouseRelease(KeyEvent.BUTTON1_MASK);
		r.delay(100);
		r.keyPress(KeyEvent.VK_ESCAPE);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_ESCAPE);
		r.delay(100);
		assertEquals(ScenarioEditorMenu.pane, MainFrame.getMainPanel().getComponent(0));
	}

}
