package enamel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainMenu {
	//private JFrame frame;
	private int sizeX = 1280;
	private int sizeY = 720;

	private JPanel panel;
	private JButton player;
	private JButton editor;
	private JLabel title;
	private Color primaryColor;
	private ImageIcon logoImage;
	private JLabel logo;
	private JLabel instruction;

	public MainMenu() {
		/*--------------------------------------------
		frame = new JFrame();
		frame.setTitle("Main Menu");
		//frame.setBounds(100, 100, 627, 459);
		frame.setSize(sizeX,sizeY);
		frame.setPreferredSize(new Dimension(sizeX, sizeY));
		//frame.getContentPane().setLayout(new BorderLayout());		
		frame.setLayout(null);
		frame.setResizable(false);
		-----------------------------------------------*/
		
		primaryColor = new Color(153,197, 217);
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setSize(sizeX, sizeY);
		
		logoImage = new ImageIcon("Images/CompanyLogo.png");
		logo = new JLabel(logoImage);
		logo.setLayout(null);
		logo.setSize(300,100);
		logo.setLocation(10, 10);
				
		player = new JButton("Player");
		player.setLocation(sizeX-(sizeX/4), sizeY/3);
		customizeButton(player);		
		
		editor = new JButton("Editor");
		editor.setLocation(sizeX-(sizeX/4), sizeY/2);
		customizeButton(editor);

		title = new JLabel("Treasure Box Braille");
		title.setLayout(null);
		title.setSize(sizeX,sizeY/3);
		title.setLocation(sizeX/20,sizeY/4);
		title.setFont(new Font("calibri", Font.PLAIN, 50));
		title.setForeground(Color.black);
		
		
		instruction = new JLabel("Choose whether to open the Scenario Editor or the Scenario Player");
		instruction.setLayout(null);
		instruction.setSize(sizeX,sizeY/3);
		instruction.setLocation(sizeX/20,sizeY/3);
		instruction.setFont(new Font("calibri", Font.ITALIC, 30));
		instruction.setForeground(Color.black);
		
		panel.setBackground(primaryColor);
		panel.add(this.player);
		panel.add(this.editor);
		panel.add(title);
		panel.add(instruction);
		panel.add(logo);
		
		/*frame.add(panel);		
		frame.repaint();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		*/
	}
	
	private void customizeButton(JButton button) {
		button.setLayout(null);
		button.addMouseListener(new Click());
		button.setSize(200,60);
		button.setFont(new Font("calibri", Font.PLAIN, 22));
		button.setBackground(Color.white);
		button.setForeground(Color.black);
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
			ScenarioCreator c = new ScenarioCreator(frame);
			frame.setVisible(false);
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