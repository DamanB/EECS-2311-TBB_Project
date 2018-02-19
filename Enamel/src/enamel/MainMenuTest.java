package enamel;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MainMenuTest {

	MainFrame f;
	ByteArrayOutputStream b;
	PrintStream c;
	File file;

	@BeforeEach
	void start() throws IOException {
		f = new MainFrame();
		b = new ByteArrayOutputStream();
		System.setOut(new PrintStream(b));
		c = System.out;
		if ((file != null) && file.exists())
			file.delete();
	}

	@AfterEach
	void clear() throws IOException {
		b.flush();
		b.close();
		System.setOut(c);
		f = null;
		MainFrame.frame.dispose();
		if ((file != null) && file.exists())
			file.delete();
	}

	@Test
	void testPlayWithWrongName() throws AWTException, IOException {
		file = new File("1.txt");
		file.createNewFile();
		Robot r = new Robot();
		r.mouseMove(MainMenu.panel.getComponent(3).getX() + MainFrame.frame.getX() + 20,
				MainMenu.panel.getComponent(3).getY() + MainFrame.frame.getY() + 40);
		r.delay(100);
		r.mousePress(KeyEvent.BUTTON1_MASK);
		r.delay(100);
		r.mouseRelease(KeyEvent.BUTTON1_MASK);
		r.delay(100);
		r.keyPress(KeyEvent.VK_DOWN);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_DOWN);
		r.delay(100);
		r.keyPress(KeyEvent.VK_ENTER);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_ENTER);
		r.delay(100);
		assertEquals("***Parser Created***\nError: Invalid file name\n", b.toString());
	}

	@Test
	void testPlayWithWrongFormat() throws AWTException, IOException {
		file = new File("Scenario_0.txt");
		file.createNewFile();
		Robot r = new Robot();
		r.mouseMove(MainMenu.panel.getComponent(3).getX() + MainFrame.frame.getX() + 20,
				MainMenu.panel.getComponent(3).getY() + MainFrame.frame.getY() + 40);
		r.delay(100);
		r.mousePress(KeyEvent.BUTTON1_MASK);
		r.delay(100);
		r.mouseRelease(KeyEvent.BUTTON1_MASK);
		r.delay(100);
		r.keyPress(KeyEvent.VK_DOWN);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_DOWN);
		r.delay(100);
		r.keyPress(KeyEvent.VK_DOWN);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_DOWN);
		r.delay(100);
		r.keyPress(KeyEvent.VK_DOWN);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_DOWN);
		r.delay(100);
		r.keyPress(KeyEvent.VK_DOWN);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_DOWN);
		r.delay(100);
		r.keyPress(KeyEvent.VK_DOWN);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_DOWN);
		r.delay(100);
		r.keyPress(KeyEvent.VK_DOWN);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_DOWN);
		r.delay(100);
		r.keyPress(KeyEvent.VK_ENTER);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_ENTER);
		r.delay(100);
		assertEquals("***Parser Created***\nError: Invalid file format. Cell and Button numbers not found\n",
				b.toString());
	}

	@Test
	void testPlayCorrect() throws AWTException {
		Robot r = new Robot();
		r.mouseMove(MainMenu.panel.getComponent(3).getX() + MainFrame.frame.getX() + 20,
				MainMenu.panel.getComponent(3).getY() + MainFrame.frame.getY() + 40);
		r.delay(100);
		r.mousePress(KeyEvent.BUTTON1_MASK);
		r.delay(100);
		r.mouseRelease(KeyEvent.BUTTON1_MASK);
		r.delay(100);
		r.keyPress(KeyEvent.VK_DOWN);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_DOWN);
		r.delay(100);
		r.keyPress(KeyEvent.VK_DOWN);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_DOWN);
		r.delay(100);
		r.keyPress(KeyEvent.VK_DOWN);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_DOWN);
		r.delay(100);
		r.keyPress(KeyEvent.VK_ENTER);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_ENTER);
		r.delay(100);
		r.keyPress(KeyEvent.VK_DOWN);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_DOWN);
		r.delay(100);
		r.keyPress(KeyEvent.VK_ENTER);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_ENTER);
		r.delay(100);
		assertEquals("***Parser Created***\n", b.toString());
	}

	@Test
	void testEdit() throws AWTException {
		Robot r = new Robot();
		r.mouseMove(MainMenu.panel.getComponent(4).getX() + MainFrame.frame.getX() + 20,
				MainMenu.panel.getComponent(4).getY() + MainFrame.frame.getY() + 40);
		r.delay(100);
		r.mousePress(KeyEvent.BUTTON1_MASK);
		r.delay(100);
		r.mouseRelease(KeyEvent.BUTTON1_MASK);
		r.delay(100);
		assertEquals(ScenarioEditorMenu.pane, MainFrame.getMainPanel().getComponent(0));
	}

}
