package enamel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainMenu implements ActionListener{
	// private JFrame frame;
	private int sizeX;
	private int sizeY;

	private static JPanel panel;
	private JButton player;
	private JButton editor;
	private JLabel title;
	private Color primaryColor;
	private JLabel instruction;

	public static MainMenu instance;

	private MainMenu() {

		primaryColor = MainFrame.primColor;
		sizeX = MainFrame.dimension.width;
		sizeY = MainFrame.dimension.height;

		panel = new JPanel();
		GridBagLayout f = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		panel.setLayout(f);
		panel.setBackground(primaryColor);

		title = new JLabel("Treasure Box Braille");
		// Dimension dTitle = new Dimension(sizeX, sizeY / 7);
		// title.setSize(dTitle);
		// title.setPreferredSize(dTitle);
		title.setFont(new Font("calibri", Font.PLAIN, 35));
		title.setForeground(Color.black);
		title.setHorizontalAlignment(JLabel.CENTER);
		// title.setLocation((int) (sizeX * 0.05), (int) (sizeY * 0.3));

		instruction = new JLabel("Choose whether to open the Scenario Editor or the Scenario Player");
		// Dimension dInstruc = new Dimension(sizeX, sizeY / 7);
		// instruction.setSize(dInstruc);
		// instruction.setPreferredSize(dInstruc);
		instruction.setFont(new Font("calibri", Font.ITALIC, 25));
		instruction.setForeground(Color.black);
		instruction.setHorizontalAlignment(JLabel.CENTER);
		// instruction.setLocation((int) (sizeX * 0.05), (int) (sizeY * 0.4));

		player = new JButton("Player");
		customizeButton(player);

		editor = new JButton("Editor");
		customizeButton(editor);

		c.insets = new Insets(10, 10, 10, 10);

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		panel.add(title, c);

		c.gridy = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		panel.add(instruction, c);

		c.fill = GridBagConstraints.NONE;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 2;
		panel.add(this.player, c);

		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 2;
		c.anchor = GridBagConstraints.LINE_END;
		panel.add(this.editor, c);

	}

	public static JPanel getScreen() {
		instance = new MainMenu();
		return panel;
	}

	private void customizeButton(JButton button) {
		Dimension d = new Dimension(sizeX / 6, sizeY / 12);
		button.addActionListener(this);
		button.setPreferredSize(d);
		button.setMinimumSize(d);
		button.setFont(new Font("calibri", Font.PLAIN, 22));
		MainFrame.editJButton(button);
	}

	public JButton getEditor() {
		return editor;
	}

	public JButton getPlayer() {
		return player;
	}

	public static void player() {
			// Initialising objects for file explorer and the ScenarioParser
			JButton openfile = new JButton();
			JFileChooser fileChooser = new JFileChooser();

			// Starts JFileChooser at project directory
			fileChooser.setCurrentDirectory(new java.io.File("."));
			// Shows only text files in the file chooser
			fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			boolean f = true;
			while (f) {
				if (fileChooser.showOpenDialog(openfile) == JFileChooser.APPROVE_OPTION) {
					// Using regex checks if the file name has the following: ["Scenario_" + (a
					// positive integer) + ".txt"]
					if (fileChooser.getSelectedFile().isDirectory()) {
						fileChooser.setCurrentDirectory(fileChooser.getSelectedFile());
						continue;
					} else {
						f = false;
					}
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
							if (lines[0] != null && lines[1] != null && lines[0].matches("^Cell [1-9][0-9]*$")
									&& lines[1].matches("^Button [1-9][0-9]*$")) {
								br.close();
								// frame.setVisible(false);
								// s.setScenarioFile(fileChooser.getSelectedFile().getAbsolutePath());
								ScenarioThread starterCodeThread = new ScenarioThread(
										fileChooser.getSelectedFile().getAbsolutePath());
								starterCodeThread.start();
							} else {
								// exit
								
								System.out.println("Error: Invalid file format. Cell and Button numbers not found");
							}
						} catch (IOException e) {
							System.out.println("File error");
							JOptionPane.showMessageDialog(MainFrame.frame, "The file you selected does not meet the Scenario requirements", "Invalid File", JOptionPane.OK_OPTION);
							return;
						}
					} else {
						// exit
						System.out.println("Error: Invalid file name");
						JOptionPane.showMessageDialog(MainFrame.frame, "The file you selected does not meet the Scenario_# name format", "Invalid File", JOptionPane.OK_OPTION);
						return;
					}
				} else {
					break;
				}
			}
		}

		private void editor() {
			// ScenarioCreator c = new ScenarioCreator(main);
			// frame.setVisible(false);
			MainFrame.changeScreen(ScenarioEditorMenu.getScreen());
		}

		@Override
		public void actionPerformed(ActionEvent e) {
				if (e.getSource().equals(player)) {
					player();
				} else if (e.getSource().equals(editor)) {
					editor();
				}
		}

	public static JPanel getPanel() {
		return panel;
	}

}