package enamel;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import org.junit.jupiter.api.Test;

class ScenarioCreatorManagerTest {

	ScenarioCreatorManager s;

	@Test
	void testSaveToFile() {
		fail("Not yet implemented");
	}

	@Test
	void testFile1() {
		s = new ScenarioCreatorManager(new File("FactoryScenarios\\Scenario_1.txt"));
		s.parseFile();
		assertEquals(1, s.getCellNum());
		assertEquals(4, s.getButtonNum());
	}

	@Test
	void testFile2() {
		s = new ScenarioCreatorManager(new File("FactoryScenarios\\Scenario_2.txt"));
		s.parseFile();
		assertEquals(1, s.getCellNum());
		assertEquals(4, s.getButtonNum());
	}

	@Test
	void testFile3() {
		s = new ScenarioCreatorManager(new File("FactoryScenarios\\Scenario_3.txt"));
		s.parseFile();
		assertEquals(1, s.getCellNum());
		assertEquals(4, s.getButtonNum());
	}

}
