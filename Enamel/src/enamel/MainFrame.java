package enamel;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame {

	private static JFrame frame;
	private static JPanel mainPanel;
	private static int sizeX = 1280;
	private static int sizeY = 720;	
	public static Dimension dimension = new Dimension(sizeX,sizeY);
	public static Color primColor = new Color(153,197, 217);


	public MainFrame() {		
		
		frame = new JFrame("SDP-16 Treasure Box Braille");		
		frame.setSize(dimension);
		frame.setPreferredSize(dimension);
		frame.setLayout(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainPanel = new JPanel();
		mainPanel.setSize(dimension);
		mainPanel.setPreferredSize(dimension);
		mainPanel.setLayout(null);
		
		frame.add(mainPanel);
		frame.setVisible(true);
		frame.repaint();
		
		changeScreen(MainMenu.getScreen());
	}
	
	public static void changeScreen(JPanel screen) {
		mainPanel.removeAll();
		mainPanel.add(screen);
		mainPanel.validate();
		frame.repaint();		
	}
	
	
}
