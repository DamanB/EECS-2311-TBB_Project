package enamel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ScenarioEditorMenu implements MouseListener {

	public static ScenarioEditorMenu instance;	

	public static JPanel pane; 
	private static JLabel title, instruction;
	public static JButton create,modify,back;

	private static int sizeX;
	private static int sizeY;

	private static 	GridBagConstraints c = new GridBagConstraints();


	private ScenarioEditorMenu() {

		sizeX = MainFrame.dimension.width;
		sizeY = MainFrame.dimension.height;

		GridBagLayout layout = new GridBagLayout();	
		c = new GridBagConstraints();
		c.insets=new Insets(10,10,10,10);

		pane = new JPanel();
		pane.setBackground(MainFrame.primColor);
		pane.setLayout(layout);
		createLabels();	
		createButtons();

	}

	public static JPanel getScreen() {
		instance = new ScenarioEditorMenu();
		return pane;
	}

	private void createLabels() {		
		title = new JLabel("Scenario Editor Menu");
		title.setFont(new Font("calibri", Font.PLAIN, 35));
		title.setForeground(Color.BLACK);
		title.setHorizontalAlignment(JLabel.CENTER);

		instruction = new JLabel("Would You Like to Open an Already Exisitng Scenario or Create a New One?");
		instruction.setFont(new Font("calibri", Font.PLAIN, 20));
		instruction.setForeground(Color.BLACK);
		instruction.setHorizontalAlignment(JLabel.CENTER);

		c.gridx=0;
		c.gridy=0;
		pane.add(title,c);	
		c.gridx=0;
		c.gridy=1;
		pane.add(instruction,c);
	}

	private void createButtons() {
		create = new JButton("Create");
		modify = new JButton("Modify");
		back = new JButton("Back");
		modifyButtonSettings(create);
		modifyButtonSettings(modify);		
		modifyButtonSettings(back);

		c.gridx=0;
		c.gridy=2;
		pane.add(create,c);	
		c.gridx=0;
		c.gridy=3;
		pane.add(modify,c);	
		c.gridx=0;
		c.gridy=4;
		pane.add(back,c);	


	}

	private void modifyButtonSettings(JButton button) {

		Dimension size = new Dimension(190, 55);

		button.setPreferredSize(size);
		button.setMinimumSize(size);
		button.addMouseListener(this);
		button.setFont(new Font("Calibri", Font.PLAIN, 20));
		MainFrame.editJButton(button);
		button.setVerticalAlignment(JButton.CENTER);
	}


	@Override
	public void mouseClicked(MouseEvent e) {

		if (e.getSource().equals(create)) {

			EditorInfo info = new EditorInfo();			

		}else if (e.getSource().equals(modify)) {
			JOptionPane.showMessageDialog(MainFrame.getMainPanel(), "Sorry This Is Under Construction", "Sorry", JOptionPane.PLAIN_MESSAGE, null);
		}else if (e.getSource().equals(back)) {
			MainFrame.changeScreen(MainMenu.getScreen());
		}



	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}


	private class EditorInfo extends JPanel implements ActionListener,ChangeListener{

		private JLabel infoTitle;
		private JLabel cellNumberInst;
		private JLabel responseButtonInst;
		private JLabel titleInst;

		private JSpinner cellNumber;
		private JSpinner responseButtonNumber; 
		private JTextField title;	

		private JButton begin;
		private JButton back2;


		private EditorInfo() {	
			this.setBackground(MainFrame.primColor);
			GridBagLayout l = new GridBagLayout();
			GridBagConstraints c = new GridBagConstraints();
			this.setLayout(l);

			cellNumber = new JSpinner(new SpinnerNumberModel());
			responseButtonNumber = new JSpinner(new SpinnerNumberModel());
			cellNumber.setPreferredSize(new Dimension (40,20));
			responseButtonNumber.setPreferredSize(new Dimension (40,20));
			cellNumber.addChangeListener(this);
			responseButtonNumber.addChangeListener(this);
			cellNumber.setValue((int)1);
			responseButtonNumber.setValue((int)1);

			title = new JTextField();
			title.setSize(MainFrame.getSizeX()/10,20);
			title.setPreferredSize(title.getSize());
			begin = new JButton("Begin!");
			modifyButton(begin);	
			back2 = new JButton("Back");
			modifyButton(back2);

			infoTitle = new JLabel("Configure Scenario Settings");
			cellNumberInst = new JLabel("Number of Braille Cells: ");
			responseButtonInst = new JLabel("Number of Response Buttons: ");
			titleInst = new JLabel("Title of Scenario: ");
			setSize(infoTitle);
			infoTitle.setFont(new Font("calibri", Font.PLAIN, 30));
			setSize(cellNumberInst);
			setSize(responseButtonInst);
			setSize(titleInst);	
			infoTitle.setHorizontalAlignment(JLabel.CENTER);


			c.insets = new Insets(20,20,20,20);
			c.anchor=c.CENTER;
			c.fill=c.HORIZONTAL;
			c.gridwidth=2;
			c.gridx=0;
			c.gridy=0;	
			this.add(infoTitle,c);

			c.anchor=c.FIRST_LINE_START;
			c.gridwidth=1;
			c.gridx=0;
			c.gridy=1;	
			this.add(cellNumberInst,c);
			c.gridx=1;
			c.gridy=1;
			this.add(cellNumber,c);
			c.gridx=0;
			c.gridy=2;
			this.add(responseButtonInst,c);
			c.gridx=1;
			c.gridy=2;
			this.add(responseButtonNumber,c);
			c.gridx=0;
			c.gridy=3;
			this.add(titleInst,c);
			c.gridx=1;
			c.gridy=3;
			this.add(title,c);			

			//BUTTONS
			c.fill=c.NONE;
			c.anchor=c.CENTER;
			c.gridx=0;
			c.gridy=4;
			c.gridwidth=3;
			this.add(begin,c);
			c.gridx=0;
			c.gridy=5;
			this.add(back,c);			
			MainFrame.changeScreen(this);			
		}		

		private void setSize(JLabel label) {
			//label.setSize(MainFrame.getSizeX(), 20);
			//label.setPreferredSize(label.getSize());
			label.setFont(new Font("calibri", Font.PLAIN, 20));
			label.setForeground(Color.BLACK);
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(begin)) {
				ScenarioCreatorGUI.numCells = (int)cellNumber.getValue();
				ScenarioCreatorGUI.numButtons = (int)responseButtonNumber.getValue();
				ScenarioCreatorGUI.ScenarioTitle = title.getText();
				MainFrame.changeScreen(ScenarioCreatorGUI.getScreen());
			}else if (e.getSource().equals(back)) {
				MainFrame.changeScreen(getScreen());
			}

		}

		private void modifyButton(JButton button) {
			Dimension size = new Dimension(190, 55);
			button.setPreferredSize(size);
			button.setMinimumSize(size);
			button.addActionListener(this);
			button.setFont(new Font("Calibri", Font.PLAIN, 20));
			MainFrame.editJButton(button);
			button.setVerticalAlignment(JButton.CENTER);
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			if (e.getSource().equals(cellNumber)) {
				if ((int)cellNumber.getValue()<1) {
					cellNumber.setValue((int)1);
				}
			}else if (e.getSource().equals(responseButtonNumber)) {
				if ((int)responseButtonNumber.getValue()<1) {
					responseButtonNumber.setValue((int)1);
				}
			}
		}
	}



	/*
	 * boolean asking = true;

		while(asking) {
			String numCells = JOptionPane.showInputDialog(MainFrame.getMainPanel(), "Please Enter The Number of Braille Cells You Wish To Use: ", "Config", JOptionPane.INFORMATION_MESSAGE);
			if (numCells == null) {
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

		if (asking) {
			MainFrame.changeScreen(getScreen());
		}

		return asking;
	 */







}
