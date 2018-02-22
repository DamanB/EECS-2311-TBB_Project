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
		MainFrame.editJButton(button);
		button.setVerticalAlignment(JButton.CENTER);
	}

	public static void getEditorInfo() {

		boolean asking = true;

		while(asking) {
			String numCells = JOptionPane.showInputDialog(MainFrame.getMainPanel(), "Please Enter The Number of Cells You Wish To Use: ", "Config", JOptionPane.INFORMATION_MESSAGE);
			if (numCells == null) {
				MainFrame.changeScreen(getScreen());
				break;
			}
			else if (numCells.matches("^[1-9][0-9]*$")) {
				ScenarioCreatorGUI.numCells = (Integer.parseInt(numCells));

				while(asking) {
					String numButton = JOptionPane.showInputDialog(MainFrame.getMainPanel(),"Please Enter The Number of Response Buttons You Wish To Use: ", "Config", JOptionPane.INFORMATION_MESSAGE);
					if (numButton == null) {
						break;
					}
					if (numButton.matches("^[1-9][0-9]*$")) {
						ScenarioCreatorGUI.numButtons = (Integer.parseInt(numButton));

						while (true) {
							String title = JOptionPane.showInputDialog(MainFrame.getMainPanel(), "Please Input the Scenarios Title", "Title", JOptionPane.INFORMATION_MESSAGE);
							if ( title == null){
								break;
							}else if(title.length() == 0) {
								JOptionPane.showMessageDialog(MainFrame.getMainPanel(), "Please input a valid title", "Invalid Input", JOptionPane.INFORMATION_MESSAGE);
							}else{
								ScenarioCreatorGUI.ScenarioTitle = title;
								asking = false;
								break;
							}		
						}
					}else {
						JOptionPane.showMessageDialog(MainFrame.getMainPanel(),"This is an invalid number. Please try again", "Invalid Number", JOptionPane.INFORMATION_MESSAGE);
					}
				}	
			}else {
				JOptionPane.showMessageDialog(MainFrame.getMainPanel(),"This is an invalid number. Please try again",  "Invalid Number", JOptionPane.INFORMATION_MESSAGE);
			}		
			
		}



	}




	@Override
	public void mouseClicked(MouseEvent e) {

		if (e.getSource().equals(create)) {
			MainFrame.changeScreen(ScenarioCreatorGUI.getScreen());
			getEditorInfo();
		}else if (e.getSource().equals(modify)) {
			JOptionPane.showMessageDialog(MainFrame.getMainPanel(), "Sorry This Is Under Construction", "Sorry", JOptionPane.PLAIN_MESSAGE, null);
		}else if (e.getSource().equals(back)) {
			MainFrame.changeScreen(MainMenu.getScreen());
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
