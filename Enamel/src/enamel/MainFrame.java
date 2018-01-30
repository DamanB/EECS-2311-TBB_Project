package enamel;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainFrame {
    private JFrame frame;

    private JPanel panel;
    private JButton open;
    private JButton create;

    public MainFrame() {
        frame = new JFrame();
        frame.setTitle("Main");
        frame.setBounds(100, 100, 627, 459);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        panel = new JPanel();
        panel.setSize(200, 50);

        open = new JButton("Open");
        open.addMouseListener(new Click());
        panel.add(this.open);

        create = new JButton("Create");
        create.addMouseListener(new Click());
        this.panel.add(this.create);

        frame.add(panel);
        frame.repaint();
        frame.setVisible(true);
    }

    public JButton getCreate() {
        return create;
    }

    public JButton getOpen() {
        return open;
    }

    class Click implements MouseListener {
        private void open() {
            // Initialising objects for file explorer and the ScenarioParser
            JButton openfile = new JButton();
            JFileChooser fileChooser = new JFileChooser();
            ScenarioParser s;

            // Starts JFileChooser at project directory
            fileChooser.setCurrentDirectory(new java.io.File("."));
            // Shows only text files in the file chooser
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.txt", "txt"));

            if (fileChooser.showOpenDialog(openfile) == JFileChooser.APPROVE_OPTION) {
                s = new ScenarioParser(true);
                // Using regex checks if the file name has the following: ["Scenario_" + (a positive integer) + ".txt"]
                if (fileChooser.getSelectedFile().getName().matches("^Scenario_[1-9][0-9]*.txt$")) {
                    try {
                        // Reads the file and records the first 2 lines
                        BufferedReader br = new BufferedReader(new FileReader(fileChooser.getSelectedFile().getAbsolutePath()));
                        String[] lines = new String[2];
                        lines[0] = br.readLine();
                        lines[1] = br.readLine();
                        // Checks if first two lines if they follow the format. Cell + (positive integer) and Button + (positive integer)
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
            }
        }

        private void create() {
            ScenarioCreator c = new ScenarioCreator();
            frame.setVisible(false);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                if (e.getSource().equals(open)) {
                    open();
                } else if (e.getSource().equals(create)) {
                    create();
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}