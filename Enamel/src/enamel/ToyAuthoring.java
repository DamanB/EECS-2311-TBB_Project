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
    }

    public static void open() {
        /*// Initialising objects for file explorer and the ScenarioParser
		JButton openfile = new JButton();
		JFileChooser fileChooser = new JFileChooser();
		ScenarioParser s;

		// Starts JFileChooser at project directory
		fileChooser.setCurrentDirectory(new java.io.File("."));
		// Shows only text files in the file chooser
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.txt", "txt"));

		if (fileChooser.showOpenDialog(openfile) == JFileChooser.APPROVE_OPTION) {
			s = new ScenarioParser(true);
			// Using regex checks if the file name has the following: ["Scenario_" + (a
			// positive integer) + ".txt"]
			if (fileChooser.getSelectedFile().getName().matches("^Scenario_[1-9][0-9]*.txt$")) {
				try {
					// Reads the file and records the first 2 lines
					BufferedReader br = new BufferedReader(
							new FileReader(fileChooser.getSelectedFile().getAbsolutePath()));
					String[] lines = new String[2];
					lines[0] = br.readLine();
					lines[1] = br.readLine();
					// Checks if first two lines if they follow the format. Cell + (positive
					// integer) and Button + (positive integer)
					if (lines[0].matches("^Cell [1-9][0-9]*$") && lines[1].matches("^Button [1-9][0-9]*$")) {
						br.close();
						// frame.setVisible(false);
						s.setScenarioFile(fileChooser.getSelectedFile().getAbsolutePath());
					} else {
						// exit
						System.out.println("Error: Invalid file format. Cell and Button numbers not found");
					}
				} catch (IOException e) {
					System.out.println("File error");
					return;
				}
			} else {
				// exit
				System.out.println("Error: Invalid file name");
				return;
			}
		}*/
    }
}