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
        ScenarioCreatorManager s1, s4;
        s1 = new ScenarioCreatorManager(new File("Enamel/FactoryScenarios/Scenario_1.txt"));
        s1.parseFile();


        s4 = new ScenarioCreatorManager(new File("Enamel/FactoryScenarios/Scenario_4.txt"));
        s4.setCellNum(s1.getCellNum());
        s4.setButtonNum(s1.getButtonNum());
        s4.setTitle(s1.getTitle());
        s4.setQuestions(s1.getQuestions());

        assertEquals(s1.toString(), s4.toString());
    }

    @Test
    void testFile1() {
        s = new ScenarioCreatorManager(new File("Enamel/FactoryScenarios/Scenario_1.txt"));
        s.parseFile();
        assertEquals(1, s.getCellNum());
        assertEquals(4, s.getButtonNum());
    }

    @Test
    void testFile2() {
        s = new ScenarioCreatorManager(new File("Enamel/FactoryScenarios/Scenario_2.txt"));
        s.parseFile();
        assertEquals(1, s.getCellNum());
        assertEquals(4, s.getButtonNum());
    }

    @Test
    void testFile3() {
        s = new ScenarioCreatorManager(new File("Enamel/FactoryScenarios/Scenario_3.txt"));
        s.parseFile();
        assertEquals(1, s.getCellNum());
        assertEquals(4, s.getButtonNum());
    }

}
