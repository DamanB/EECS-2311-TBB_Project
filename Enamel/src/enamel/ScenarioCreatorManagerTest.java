package enamel;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.AWTException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class ScenarioCreatorManagerTest {

	int cell;
	int button;
	List<Question> q;

	void test(String path, int cell, int button, List<Question> q) {
		ScenarioCreatorManager s = new ScenarioCreatorManager(new File(path));
		s.parseFile();
		assertEquals(cell, s.getCellNum());
		assertEquals(button, s.getButtonNum());
		assertEquals(q, s.getQuestions());
	}

	void test(String path, int cell, int button, String s) {
		test(path, cell, button, toQuestion(s));
	}

	private List<Question> toQuestion(String s) {
		q = new ArrayList<Question>();
		return q;
	}

	void test(String path1, String path2) {
		ScenarioCreatorManager s = new ScenarioCreatorManager(new File(path2));
		s.parseFile();
		test(path1, s.getCellNum(), s.getButtonNum(), s.getQuestions());
	}

	void test(String path) {
		test(path, cell, button, q);
	}

	void test(String path, int cell, int button) {
		test(path, cell, button, q);
	}

	@Test
	void testSaveToFile() throws AWTException {
		ScenarioCreatorManager s1 = new ScenarioCreatorManager(new File("FactoryScenarios/Scenario_1.txt"));
		s1.parseFile();
		ScenarioCreatorManager s2 = new ScenarioCreatorManager(new File("FactoryScenarios/Scenario_4.txt"));
		s2.setCellNum(s1.getCellNum());
		s2.setButtonNum(s1.getButtonNum());
		s2.setTitle(s1.getTitle());
		s2.setQuestions(s1.getQuestions());
		s2.saveToFile();
		test("FactoryScenarios/Scenario_4.txt", "FactoryScenarios/Scenario_1.txt");
	}

	@Test
	void testFile1() {
		q = new ArrayList<Question>();
		test("FactoryScenarios/Scenario_1.txt", 1, 4);
	}

	@Test
	void testFile2() {
		cell = 1;
		button = 4;
		q = new ArrayList<Question>();
		test("FactoryScenarios/Scenario_2.txt");
	}

	@Test
	void testFile3() {
		test("FactoryScenarios/Scenario_3.txt", 1, 4, new ArrayList<Question>());
	}

}
