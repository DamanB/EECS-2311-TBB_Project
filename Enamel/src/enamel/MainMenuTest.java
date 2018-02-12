package enamel;

<<<<<<< HEAD
import static javax.imageio.ImageIO.getCacheDirectory;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Random;

class MainMenuTest {

	Random rand;
	@Test
	void test() {
		rand = new Random();
	}

	@Test (expected = Exception.class)
	public void testRandomFileName() {
		String testString = "";
		int index = rand.nextInt(10) + 1;
		for (int i = 0; i < index; i++) {
			testString += (char) (rand.nextInt(26) + 'a');
		}
		testString += ".txt";
		File testFile = new File(getCacheDirectory(), "FactoryScenarios/" + testString);

		try {
			testFile.createNewFile();
		} catch (Exception e) {
			System.out.println(e.toString());
		}		
		fail("Exception not thrown");
=======
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MainMenuTest {

	MainMenu m;

	@BeforeEach
	void setUp() throws Exception {
		MainMenu.getScreen();
		m = MainMenu.instance;
	}

	@Test
	void testPlay() {
		m.getPlayer().doClick();
	}

	@Test
	void testEdit() {
		m.getEditor().doClick();
>>>>>>> develop
	}

}
