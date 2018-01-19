package enamel;

import java.io.File;
import javax.swing.JFileChooser;

public class ToyAuthoring {

	public static void main(String[] args) {
	    File directory = new File("C:\\Users\\User\\AppData\\Roaming\\SPB_16.6\\git\\EECS-2311-TBB_Project");
	    JFileChooser explorer = new JFileChooser(directory); 
	    int returnedValue = explorer.showOpenDialog(null);
	    if (returnedValue == explorer.getApproveButtonMnemonic()) {
    	    ScenarioParser s = new ScenarioParser(true);
	    	String path = explorer.getSelectedFile().getPath();
    	    s.setScenarioFile(path);
	    }
	    int[] array = {1,2,3,4,5};
	     // s.setScenarioFile("FactoryScenarios/Scenario_" + 1 + ".txt");
	}
}