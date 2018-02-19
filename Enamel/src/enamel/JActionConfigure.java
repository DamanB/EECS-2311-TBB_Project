package enamel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class JActionConfigure extends JPanel{

	private static JLabel instruction;

	private static Dimension panelSize;

	public JActionConfigure(int index) {		
		this.panelSize = ScenarioCreatorGUI.actionOptions.getSize();
		this.setPreferredSize(panelSize);		
		instruction = new JLabel("Instruction: ");
		instruction.setHorizontalAlignment(JLabel.CENTER);
		this.add(instruction);		
		getOptions(index);
	}

	private void getOptions(int index) {
		this.removeAll();
		this.add(instruction);		
		
		if (index == 0) {
			this.add(new JTextToSpeech());
		}else if (index == 1) {
			this.add(new PlayAudio());
		}else if (index == 2) {
			this.add(new JPauseButton());
		}else if (index == 3) {	
			this.add(new JDisplayPins());
		}else if (index == 4) {
			this.add(new JDisplayWordInBraille());
		}else if (index == 5) {
			this.add(new JDisplayChar());
		}else if (index == 6) {
			this.add(new JClearCells());
		}else if (index == 7) {
			this.add(new JClearSpecificCell());
		}else if (index == 8) {
			this.add(new JLowerPins());
		}else if (index == 9) {
			this.add(new JRaisePins());
		}else if (index == 10) {
			this.add(new JRepeat());
		}else if (index == 11) {
			this.add(new GoToEvent());
		}else if (index == 12) {
			this.add(new GoToEventButton());
		}else if (index == 13) {
			this.add(new JResetButtons());
		}else if (index == 14) {
			this.add(new JUserInput());
		}else {
			throw new IllegalArgumentException("Action Selected DNE");
		}
	}

	private class JPauseButton extends JPanel{		
		//private JTextField pauseTime;
		private JSpinner pauseTime;
		private JPauseButton() {
			this.setPreferredSize(panelSize);
			instruction.setText("Pause: Select the amount of time in seconds (a value greater than 0) you wish to pause");
			SpinnerModel number = new SpinnerNumberModel();
			pauseTime = new JSpinner(number);			
			pauseTime = new JSpinner();			
			pauseTime.setPreferredSize(new Dimension(100,20));
			this.add(pauseTime);
		}		
	}

	private class JDisplayWordInBraille extends JPanel{		
		private JTextField stringToDisp;
		private JDisplayWordInBraille() {
			this.setPreferredSize(panelSize);
			instruction.setText("Display Word: Please enter the word you would like to display in braille. Ensure adequate Braille cells are created beforehand");
			stringToDisp = new JTextField(); 
			stringToDisp.setPreferredSize(new Dimension(panelSize.width/5,20));
			this.add(stringToDisp);
		}		
	}

	private class JRepeat extends JPanel{		
		private JTextField stringToDisp;
		private JLabel selectButton;
		private JResponseButtonBox buttons;

		private JRepeat() {
			this.setPreferredSize(panelSize);
			instruction.setText("Repeat Text: Set the text to what you would like to repeat, as well as button to initalize the repeat");	
			stringToDisp = new JTextField(); 
			stringToDisp.setPreferredSize(new Dimension(panelSize.width/2,20));
			this.add(stringToDisp);		
			selectButton = new JLabel("Select Repeat Button: ");
			buttons = new JResponseButtonBox();
			this.add(selectButton);
			this.add(buttons);

		}			
	}

	private class GoToEventButton extends JPanel implements ActionListener{
		private JLabel goToEvent;
		private JLabel when;
		private JResponseButtonBox buttons;
		private JEventList events;
		private JComboBox eventList;

		private GoToEventButton(){
			this.setPreferredSize(panelSize);
			instruction.setText("Go To Event: Select which event you would like to travel to when a specfifc button is clicked. The event must occur after this one");	
			goToEvent = new JLabel("Go To Event: "); 
			when = new JLabel("When This Button is Clicked: ");
			events = new JEventList();
			buttons = new JResponseButtonBox();
			events.jEvents.addActionListener(this);
			this.add(goToEvent);
			this.add(events);
			this.add(when);
			this.add(buttons);

		}


		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(events.jEvents)) {
				if (events.jEvents.getSelectedIndex() <= ScenarioCreatorGUI.eventsList.indexOf(ScenarioCreatorGUI.activeEvent))
				{
					JOptionPane.showMessageDialog(MainFrame.getMainPanel(), "You are not allowed to traverse to this event!", "Invalid Input", JOptionPane.INFORMATION_MESSAGE);
					events.jEvents.setSelectedIndex(0);
				}
			}
		}	


	}

	private class JClearCells extends JPanel{		
		private JClearCells() {
			this.setPreferredSize(panelSize);
			instruction.setText("Clear Cells: This command will clear all of the Braille Cells");
		}		
	}

	private class JClearSpecificCell extends JPanel{	

		JBrailleList cells;

		private JClearSpecificCell() {
			this.setPreferredSize(panelSize);
			instruction.setText("Clear Specfifc Cell: Select which Braille Cell you would like to clear");
			cells = new JBrailleList();			
			this.add(cells);
		}		
	}

	private class JDisplayChar extends JPanel{		
		private JTextField stringToDisp;
		private JBrailleList cells;
		private JDisplayChar() {
			this.setPreferredSize(panelSize);
			instruction.setText("Display Character: Enter the character you would like to display as well as the cell it will appear on");
			stringToDisp = new JTextField(); 
			stringToDisp.setPreferredSize(new Dimension(panelSize.width/10,20));
			cells = new JBrailleList();
			this.add(stringToDisp);
			this.add(cells);
		}		
	}

	private class JDisplayPins extends JPanel implements ActionListener{	

		JBrailleList cells;
		JButton select;
		JBrailleCell edit;

		private JDisplayPins() {
			this.setPreferredSize(panelSize);
			instruction.setText("Display Pins on Cell: Select which Braille Cell you want to edit and select its pins");
			cells = new JBrailleList();			
			select = new JButton("Select");	
			select.addActionListener(this);
			this.add(cells);
			this.add(select);
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(select)) {
				select.setEnabled(false);
				cells.jBraille.setEnabled(false);
				edit = (JBrailleCell)cells.jBraille.getSelectedItem();
				this.add(edit.getJBrailleCell());
				this.validate();
				this.repaint();
			}
		}		
	}

	private class JTextToSpeech extends JPanel{		
		private JTextField stringToDisp;
		private JTextToSpeech() {
			this.setPreferredSize(panelSize);
			instruction.setText("Text To Speech: Enter the text you would like to say");
			stringToDisp = new JTextField(); 
			stringToDisp.setPreferredSize(new Dimension(panelSize.width/2,20));
			this.add(stringToDisp);
		}		
	}

	private class JUserInput extends JPanel{		
		private JUserInput() {
			this.setPreferredSize(panelSize);
			instruction.setText("User Input: The program will wait for a user input");
		}		
	}

	private class GoToEvent extends JPanel implements ActionListener{
		private JLabel goToEvent;
		private JEventList events;
		private JComboBox eventList;

		private GoToEvent(){
			this.setPreferredSize(panelSize);
			instruction.setText("Go To Event: Select which event you would like to travel to. The event must occur after this one");	
			goToEvent = new JLabel("Go To Event: "); 
			events = new JEventList();
			events.jEvents.addActionListener(this);
			this.add(goToEvent);
			this.add(events);
		}


		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(events.jEvents)) {
				if (events.jEvents.getSelectedIndex() <= ScenarioCreatorGUI.eventsList.indexOf(ScenarioCreatorGUI.activeEvent))
				{
					JOptionPane.showMessageDialog(MainFrame.getMainPanel(), "You are not allowed to traverse to this event!", "Invalid Input", JOptionPane.INFORMATION_MESSAGE);
					events.jEvents.setSelectedIndex(0);
				}
			}
		}	


	}

	private class JResetButtons extends JPanel{		
		private JResetButtons() {
			this.setPreferredSize(panelSize);
			instruction.setText("Reset Buttons: Resets the commands implemented with each button. Recommended to use at the beggining of each event");
		}		
	}

	private class JLowerPins extends JPanel implements ActionListener{	

		JBrailleList cells;
		JButton select;
		JBrailleCell edit;
		JComboBox<Integer> pins;

		Integer[] pin = {
				1,2,3,4,5,6,7,8		
		};

		private JLowerPins() {
			this.setPreferredSize(panelSize);
			instruction.setText("Lower Pin on Cell: Select which Braille Cell you want to edit then select the pin to lower");
			cells = new JBrailleList();			
			select = new JButton("Select");	
			select.addActionListener(this);
			pins = new JComboBox<Integer>(pin);
			this.add(cells);
			this.add(select);
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(select)) {
				select.setEnabled(false);
				cells.jBraille.setEnabled(false);
				edit = new JBrailleCell(JBrailleCell.DISPLAY_INDEX);
				this.add(edit.getJBrailleCell());
				this.add(pins);
				this.validate();
				this.repaint();
			}			


		}		
	}

	private class JRaisePins extends JPanel implements ActionListener{	

		JBrailleList cells;
		JButton select;
		JBrailleCell edit;
		JComboBox<Integer> pins;

		Integer[] pin = {
				1,2,3,4,5,6,7,8		
		};

		private JRaisePins() {
			this.setPreferredSize(panelSize);
			instruction.setText("Raise Pin on Cell: Select which Braille Cell you want to edit then select the pin to raise");
			cells = new JBrailleList();			
			select = new JButton("Select");	
			select.addActionListener(this);
			pins = new JComboBox<Integer>(pin);
			this.add(cells);
			this.add(select);
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(select)) {
				select.setEnabled(false);
				cells.jBraille.setEnabled(false);
				edit = new JBrailleCell(JBrailleCell.DISPLAY_INDEX);
				this.add(edit.getJBrailleCell());
				this.add(pins);
				this.validate();
				this.repaint();
			}			


		}		
	}


	private class PlayAudio extends JPanel implements ActionListener{
		private JLabel playAudio;
		private JButton browse;
		private JLabel audioName;

		private PlayAudio(){
			this.setPreferredSize(panelSize);
			instruction.setText("Play Audio: Select an audio file to play. The audio must a .WAV format!");	
			playAudio = new JLabel("Browse Audio");
			browse = new JButton("Browse");
			audioName = new JLabel("Selected Audio: ");

			browse.addActionListener(this);

			this.add(playAudio);
			this.add(browse);
			this.add(audioName);

		}

		public void actionPerformed(ActionEvent e) {

			if (e.getSource().equals(browse)) {

				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new java.io.File("."));
				fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.wav", "wav"));

				if (fileChooser.showOpenDialog(browse) == JFileChooser.APPROVE_OPTION) {
					if (fileChooser.getSelectedFile().getName().matches(".*.wav$")) {
						audioName.setText("Selected Audio: " + fileChooser.getSelectedFile().getName());
					}else {
						JOptionPane.showMessageDialog(MainFrame.getMainPanel(), "Invalid Audio File!", "Invalid Input", JOptionPane.INFORMATION_MESSAGE);
					}				
				}

			}	


		}
	}

	//SETTING CLASSES	
	private class JResponseButtonBox extends JPanel{		
		private JComboBox responseButtons;
		String[] buttons;

		private JResponseButtonBox() {
			buttons = new String[ScenarioCreatorManager.getNumButtons()];			
			for (int i = 0; i< buttons.length; i++) {
				buttons[i] = "Button: " + (i+1);
			}			
			responseButtons = new JComboBox<String>(buttons);			
			this.add(responseButtons);
		}			
	}

	private class JEventList extends JPanel{		
		public JComboBox jEvents;
		String[] events;

		private JEventList() {
			events = new String[ScenarioCreatorGUI.eventsList.size()];			
			for (int i = 0; i< events.length; i++) {
				events[i] = ScenarioCreatorGUI.eventsList.get(i).toString();
			}			
			jEvents = new JComboBox<String>(events);			
			this.add(jEvents);
		}			
	}

	private class JBrailleList extends JPanel{		
		public JComboBox<JBrailleCell> jBraille;

		private JBrailleList() {
			jBraille = new JComboBox<JBrailleCell>();			
			for (int i=0 ; i<ScenarioCreatorGUI.activeEvent.cells.size() ; i++) {
				jBraille.addItem(ScenarioCreatorGUI.activeEvent.cells.get(i));
			}			
			this.add(jBraille);
		}			
	}



}
