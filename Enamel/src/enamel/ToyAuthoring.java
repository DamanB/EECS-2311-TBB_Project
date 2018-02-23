package enamel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ToyAuthoring {

	// this this the origin version

	public static void main(String[] args) {
		MainFrame main = new MainFrame();
		ScenarioParser s = new ScenarioParser(true);
		s.setScenarioFile("E:\\GitKrakenRepos\\EECS-2311-TBB_Project\\Enamel\\FactoryScenarios\\Scenario_1.txt");
	}
}