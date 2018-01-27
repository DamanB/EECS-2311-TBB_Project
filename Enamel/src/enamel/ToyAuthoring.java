package enamel;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ToyAuthoring {

    //this this the origin version

    public static void main(String[] args) {

        // Initialising objects for file explorer and the ScenarioParser
        JButton open = new JButton();
        JFileChooser fileChooser = new JFileChooser();
        ScenarioParser s;

        // Starts JFileChooser at project directory
        fileChooser.setCurrentDirectory(new java.io.File("."));

        // Shows only text files in the file chooser
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.txt", "txt"));

        if (fileChooser.showOpenDialog(open) == JFileChooser.APPROVE_OPTION) {
            s = new ScenarioParser(true);

            // Using regex checks if the file name has the following: ["Scenario_" + (a positive integer) + ".txt"]
            if (fileChooser.getSelectedFile().getName().matches("^Scenario_[1-9]*.txt$")) {

                try {
                    BufferedReader br = new BufferedReader(new FileReader(fileChooser.getSelectedFile().getName()));
                    String[] lines = new String[2];
                    lines[0] = br.readLine();
                    lines[1] = br.readLine();

                    // Checks if first two lines if they follow the format. Cell + (positive integer) and Button + (positive integer)
                    if (lines[0].matches("^Cell [1-9]*.txt$") &&
                            lines[1].matches("^Button [1-9]*.txt$")) {
                        s.setScenarioFile(fileChooser.getSelectedFile().getAbsolutePath());
                    } else {
                        // exit
                        System.out.println("Error: Invalid file format. Cell and Button numbers not found");
                        return;
                    }

                } catch (IOException e) {
                    System.out.println("File error");
                }

            } else {
                // exit
                System.out.println("Error: Invalid file name");
                return;

            }

        }

    }
}