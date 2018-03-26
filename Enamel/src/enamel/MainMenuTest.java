package enamel;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.AWTException;
import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MainMenuTest {

	ByteArrayOutputStream b;
	PrintStream c;
	File file;
	TestRobot r;

	@BeforeEach
	void start() throws AWTException {
		new MainFrame();
		r = new TestRobot();
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
		MainFrame.frame.dispose();
		if ((file != null) && file.exists())
			file.delete();
	}

	@Test
	void testPlayWithWrongName() throws IOException {
		file = new File("1.txt");
		file.createNewFile();
		r.moveMouse(MainMenu.instance.getPlayer());
		r.pressMouse(KeyEvent.BUTTON1_MASK);
		r.pressKey(KeyEvent.VK_DOWN);
		r.pressKey(KeyEvent.VK_ENTER);
		assertEquals("Error: Invalid file name\n", b.toString());
	}

	@Test
	void testPlayWithWrongFile() throws IOException {
		file = new File("Scenario_1.txt");
		file.createNewFile();
		file.setReadable(false, false);
		r.moveMouse(MainMenu.instance.getPlayer());
		r.pressMouse(KeyEvent.BUTTON1_MASK);
		r.pressKey(KeyEvent.VK_DOWN, 5);
		r.pressKey(KeyEvent.VK_ENTER);
		assertEquals("File error\n", b.toString());
	}

	@Test
	void testPlayWithWrongFormat() throws AWTException, IOException {
		file = new File("Scenario_1.txt");
		file.createNewFile();
		r.moveMouse(MainMenu.instance.getPlayer());
		r.pressMouse(KeyEvent.BUTTON1_MASK);
		r.pressKey(KeyEvent.VK_DOWN, 5);
		r.pressKey(KeyEvent.VK_ENTER);
		assertEquals("Error: Invalid file format. Cell and Button numbers not found\n", b.toString());
	}

	@Test
	void testPlayCorrect() throws AWTException {
		r.moveMouse(MainMenu.instance.getPlayer());
		r.pressMouse(KeyEvent.BUTTON1_MASK);
		r.pressKey(KeyEvent.VK_DOWN, 2);
		r.pressKey(KeyEvent.VK_ENTER);
		r.pressKey(KeyEvent.VK_DOWN, 2);
		r.pressKey(KeyEvent.VK_ENTER);
		assertEquals("", b.toString());
	}

	@Test
	void testEdit() throws AWTException {
		r.moveMouse(MainMenu.instance.getEditor());
		r.pressMouse(KeyEvent.BUTTON1_MASK);
		assertEquals(ScenarioEditorMenu.pane, MainFrame.getMainPanel().getComponent(0));
	}

	@Test
	void testBack() throws AWTException {
		r.moveMouse(MainMenu.instance.getEditor());
		r.pressMouse(KeyEvent.BUTTON1_MASK);
		r.moveMouse(ScenarioEditorMenu.back);
		r.pressMouse(KeyEvent.BUTTON1_MASK);
		assertEquals(MainMenu.getPanel(), MainFrame.getMainPanel().getComponent(0));
	}

}
