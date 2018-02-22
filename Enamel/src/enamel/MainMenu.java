package enamel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainMenu {
	//private JFrame frame;
	private int sizeX;
	private int sizeY;

	private static JPanel panel;
	private JButton player;
	private JButton editor;
	private JLabel title;
	private Color primaryColor;
	private ImageIcon logoImage;
	private JLabel logo;
	private JLabel instruction;

	public static MainMenu instance;

	private MainMenu() {

		primaryColor = MainFrame.primColor;
		sizeX = MainFrame.dimension.width;
		sizeY = MainFrame.dimension.height;
		
		panel = new JPanel(null);	
		panel.setSize(MainFrame.dimension);
		panel.setBackground(primaryColor);


		//logoImage = new ImageIcon("Images/CompanyLogo.png");
		//logo = new JLabel(logoImage);
		//Dimension dLogo = new Dimension(sizeX/4,sizeY/7);
		//logo.setSize(dLogo);
		//logo.setPreferredSize(dLogo);
		//logo.setLocation((int)(sizeX*0.01),(int)(sizeY*0.01));
		


		title = new JLabel("Treasure Box Braille");
		Dimension dTitle = new Dimension(sizeX,sizeY/7);
		title.setSize(dTitle);
		title.setPreferredSize(dTitle);
		title.setFont(new Font("calibri", Font.PLAIN, 50));
		title.setForeground(Color.black);
		title.setLocation((int)(sizeX*0.05),(int)(sizeY*0.3));

		instruction = new JLabel("Choose whether to open the Scenario Editor or the Scenario Player");
		Dimension dInstruc = new Dimension(sizeX,sizeY/7);
		instruction.setSize(dInstruc);
		instruction.setPreferredSize(dInstruc);
		instruction.setFont(new Font("calibri", Font.ITALIC, 30));
		instruction.setForeground(Color.black);
		instruction.setLocation((int)(sizeX*0.05),(int)(sizeY*0.4));

		
		player = new JButton("Player");
		player.setLocation(sizeX-(sizeX/4), sizeY/3);
		customizeButton(player);		

		editor = new JButton("Editor");
		editor.setLocation(sizeX-(sizeX/4), sizeY/2);
		customizeButton(editor);
		
		//panel.add(logo);
		panel.add(title);
		panel.add(instruction);
		panel.add(this.player);
		panel.add(this.editor);
	}

	public static JPanel getScreen() {
		instance = new MainMenu();
		return panel;
	}

	private void customizeButton(JButton button) {
		Dimension d = new Dimension(sizeX/6,sizeY/12);		
		button.addMouseListener(new Click());
		button.setSize(d);
		button.setPreferredSize(d);
		button.setFont(new Font("calibri", Font.PLAIN, 22));
		MainFrame.editJButton(button);
	}

	public JButton getEditor() {
		return editor;
	}

	public JButton getPlayer() {
		return player;
	}

	class Click implements MouseListener {
		private void player() {
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
				// TESTING
				System.out.println("***Parser Created***");
				// TESTING

				// Using regex checks if the file name has the following: ["Scenario_" + (a positive integer) + ".txt"]
				if (fileChooser.getSelectedFile().getName().matches("^Scenario_[1-9][0-9]*.txt$")) {
					try {
						// Reads the file and records the first 2 lines
						BufferedReader br = new BufferedReader(
								new FileReader(fileChooser.getSelectedFile().getAbsolutePath()));
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

		private void editor() {
			//ScenarioCreator c = new ScenarioCreator(main);
			//frame.setVisible(false);
			MainFrame.changeScreen(ScenarioEditorMenu.instance.getScreen());
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				if (e.getSource().equals(player)) {
					player();
				} else if (e.getSource().equals(editor)) {
					editor();
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