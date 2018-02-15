package enamel;

import static javax.imageio.ImageIO.getCacheDirectory;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

class MainMenuTest {

	Random rand;

	@Before
	void start() {
		rand = new Random();
	}

	@Test(expected = Exception.class)
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
	}

	@Test(expected = Exception.class)
	public void testInvalidHeader() {
		String testString = "Scenario_100.txt";
		File testFile = new File(getCacheDirectory(), "FactoryScenarios/" + testString);
		try {
			testFile.createNewFile();
		} catch (Exception e) {
			System.out.println(e.toString());  
		}
		try {
			PrintWriter writer = new PrintWriter(testFile);
			writer.print("");
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.toString());
		}
		
		try {
			PrintWriter writer = new PrintWriter(testFile);
			writer.print("Cell -1");
			writer.print("Button -1");
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.toString());
		}
		fail("Exception not thrown");
	}

}
