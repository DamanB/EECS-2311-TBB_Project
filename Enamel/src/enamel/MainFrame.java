package enamel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class MainFrame {

	// TODO Delete Commented Code for resizing
	public static JFrame frame;
	private static JPanel mainPanel;
	private static int sizeX = 1280;
	private static int sizeY = 720;
	public static Dimension dimension = new Dimension(sizeX, sizeY);
	public static Color primColor = new Color(153, 197, 217);
	public static boolean runScen = false;

	// private static JScrollPane scroll;

	public MainFrame() {

		frame = new JFrame("SDP-16 Treasure Box Braille");
		frame.setSize(dimension);
		frame.setMinimumSize(new Dimension(200, 200));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(1, 0));

		mainPanel = new JPanel();

		// scroll = new JScrollPane(mainPanel,
		// JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		// frame.add(scroll);
		frame.add(mainPanel);
		frame.setVisible(true);
		frame.repaint();

		changeScreen(MainMenu.getScreen());
	}

	public static void changeScreen(JPanel screen) {
		mainPanel.removeAll();
		mainPanel.setLayout(new GridLayout(1, 0));
		frame.repaint();
		mainPanel.add(screen);
		mainPanel.validate();
		frame.repaint();
	}

	public static JPanel getMainPanel() {
		return mainPanel;
	}

	public static int getSizeX() {
		return dimension.width;
	}

	public static int getSizeY() {
		return dimension.height;
	}

	public static void editJButton(JButton button) {
		button.setBackground(Color.WHITE);
		button.setForeground(Color.BLACK);
		button.setBorder(new LineBorder(Color.BLACK));
	}

}
