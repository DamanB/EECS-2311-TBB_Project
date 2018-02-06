package enamel;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame {

	private static JFrame frame;
	private static JPanel mainPanel;
	private static int sizeX = 1280;
	private static int sizeY = 720;	
	public static Dimension d = new Dimension(sizeX,sizeY);

	public MainFrame() {		
		
		frame = new JFrame("SDP-16 Treasure Box Braille");		
		frame.setSize(d);
		frame.setPreferredSize(d);
		frame.setLayout(null);
		frame.setResizable(false);
		
		mainPanel = new JPanel();
		mainPanel.setSize(d);
		mainPanel.setPreferredSize(d);
		mainPanel.setLayout(null);
		
		frame.add(mainPanel);
		frame.setVisible(true);
		frame.repaint();
		
		changeScreen(MainMenu.instance.getScreen());
	}
	
	public static void changeScreen(JPanel screen) {
		mainPanel.removeAll();
		mainPanel.add(screen);
		mainPanel.validate();
		frame.repaint();		
	}
	
	
}
