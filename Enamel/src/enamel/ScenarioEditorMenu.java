package enamel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ScenarioEditorMenu implements MouseListener {

	public static ScenarioEditorMenu instance;	

	private static JPanel pane; 
	private static JLabel title, instruction, welcome;
	private static JButton create,modify,back;

	private static int sizeX;
	private static int sizeY;

	private ScenarioEditorMenu() {

		sizeX = MainFrame.dimension.width;
		sizeY = MainFrame.dimension.height;

		GridLayout gridLayout = new GridLayout(3,1);			

		pane = new JPanel();
		pane.setSize(MainFrame.dimension);
		pane.setBackground(MainFrame.primColor);
		pane.setLayout(gridLayout);
		createTitle();	
		createButtons();

	}

	public static JPanel getScreen() {
		instance = new ScenarioEditorMenu();
		return pane;
	}

	private void createTitle() {		
		title = new JLabel("Scenario Editor");
		title.setFont(new Font("calibri", Font.PLAIN, 50));
		title.setHorizontalAlignment(JLabel.CENTER);
		//title.setVerticalAlignment(JLabel.NORTH);
		pane.add(title);		
	}

	private void createButtons() {
		create = new JButton("Create");
		modify = new JButton("Modify");
		modifyButtonSettings(create);
		modifyButtonSettings(modify);

		FlowLayout flow = new FlowLayout();		
		flow.setHgap(sizeX/4);
		flow.setAlignment(FlowLayout.CENTER);

		JPanel buttonsPane = new JPanel(flow);
		buttonsPane.setBackground(MainFrame.primColor);
		buttonsPane.add(modify);
		buttonsPane.add(create);

		pane.add(buttonsPane);

		back = new JButton("Back");
		modifyButtonSettings(back);
		JPanel lowerPanel = new JPanel(flow);
		lowerPanel.add(back);
		lowerPanel.setBackground(MainFrame.primColor);
		pane.add(lowerPanel);

	}

	private void modifyButtonSettings(JButton button) {

		Dimension size = new Dimension(sizeX/5, sizeY/10);

		button.setSize(size);
		button.setPreferredSize(size);
		button.addMouseListener(this);
		button.setFont(new Font("Calibri", Font.PLAIN, 30));
		button.setBackground(Color.white);
		button.setForeground(Color.black);
		button.setVerticalAlignment(JButton.CENTER);
	}


	public static int getNumCells() { 

		while(true) {
			String numCells = JOptionPane.showInputDialog(MainFrame.getMainPanel(), "Please Enter The Number of Cells You Wish To Use: ");
			if (numCells.matches("^[1-9][0-9]*$")) {
				return Integer.parseInt(numCells);
			}else {
				JOptionPane.showMessageDialog(MainFrame.getMainPanel(),"This is an invalid number. Please try again");
			}
		}
	}
	
	public static int getNumButtons() { 

		while(true) {
			String numButton = JOptionPane.showInputDialog(MainFrame.getMainPanel(),"Please Enter The Number of Response Buttons You Wish To Use: ");
			if (numButton.matches("^[1-9][0-9]*$")) {
				return Integer.parseInt(numButton);
			}else {
				JOptionPane.showMessageDialog(MainFrame.getMainPanel(),"This is an invalid number. Please try again");
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if (e.getSource().equals(create)) {			
		}



	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}


}
