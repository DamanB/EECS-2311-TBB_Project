package enamel;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;

class ScenarioCreatorManagerTest {

    ScenarioCreatorManager s;
    File f = new File("");



    @Test
    void testSaveToFile() {
        s = new ScenarioCreatorManager(new File("FactoryScenarios/Scenario_1.txt"));
        s.parseFile();
        List<Question> q = s.getQuestions();
        s = new ScenarioCreatorManager(new File("FactoryScenarios/Scenario_4.txt"));
        s.setCellNum(1);
        s.setButtonNum(4);
        s.setQuestions(q);
        s.saveToFile();
        File f1 = new File("FactoryScenarios/Scenario_1.txt");
        File f2 = new File("FactoryScenarios/Scenario_4.txt");
        assertEquals(0, f1.compareTo(f2));
    }

    @Test
    void testFile1() {
        s = new ScenarioCreatorManager(new File("FactoryScenarios/Scenario_1.txt"));
        s.parseFile();
        assertEquals(1, s.getCellNum());
        assertEquals(4, s.getButtonNum());
    }

    @Test
    void testFile2() {
        s = new ScenarioCreatorManager(new File("FactoryScenarios/Scenario_2.txt"));
        s.parseFile();
        assertEquals(1, s.getCellNum());
        assertEquals(4, s.getButtonNum());
    }

    @Test
    void testFile3() {
        s = new ScenarioCreatorManager(new File("FactoryScenarios/Scenario_3.txt"));
        s.parseFile();
        assertEquals(1, s.getCellNum());
        assertEquals(4, s.getButtonNum());
    }

}
