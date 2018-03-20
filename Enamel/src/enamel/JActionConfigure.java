package enamel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class JActionConfigure extends JPanel{

	private static JLabel instruction;
	private static Dimension panelSize;
	private JPanel main = this;
	public Action action;

	public JActionConfigure(int index) {		
		this.panelSize = ScenarioCreatorGUI.configuration.getSize();
		this.setPreferredSize(panelSize);		
		instruction = new JLabel("Instruction: ");
		instruction.setHorizontalAlignment(JLabel.CENTER);
		instruction.setSize(panelSize.width,30);
		this.add(instruction);		
		getOptions(index);
	}

	private void getOptions(int index) {

		if (index == 0) {
			action = new Checkpoint();
		}
		else if (index == 1) {
			action = new JTextToSpeech();
		}else if (index == 2) {
			action = new PlayAudio();
		}else if (index == 3) {
			action = new JPauseButton();
		}else if (index == 4) {	
			action = new JDisplayPins();
		}else if (index == 5) {
			action = new JDisplayWordInBraille();
		}else if (index == 6) {
			action = new JDisplayChar();
		}else if (index == 7) {
			action = new JClearCells();
		}else if (index == 8) {
			action = new JClearSpecificCell();
		}else if (index == 9) {
			action = new JLowerPins();
		}else if (index == 10) {
			action = new JRaisePins();
		}else if (index == 11) {
			action = new JRepeat();
		}else if (index == 12) {
			action = new GoToEvent();
		}else if (index == 13) {
			action = new GoToCheckpointButtonClick();
		}else if (index == 14) {
			action = new JResetButtons();
		}else if (index == 15) {
			action = new JUserInput();
		}else {
			throw new IllegalArgumentException("Action Selected DNE");
		}
	}

	private class Checkpoint extends Action{

		private Checkpoint() {
			instruction.setText("Checkpoint: Give this checkpoint a name (One word consiting of only letters and no duplicates)");
		}

		public boolean build(ScenarioCreatorManager sm) {
			return false;
		}	

	}

	private class JPauseButton extends Action implements ChangeListener{		
		private JSpinner pauseTime;
		private JPauseButton() {
			instruction.setText("Pause: Select the amount of time in seconds (a value greater than 0) you wish to pause");
			SpinnerModel number = new SpinnerNumberModel();
			pauseTime = new JSpinner(number);			
			pauseTime.setPreferredSize(new Dimension(100,20));
			pauseTime.setValue((int)1);
			pauseTime.addChangeListener(this);
			main.add(pauseTime);
		}	

		public boolean build(ScenarioCreatorManager sm) {
			return sm.addPause(Integer.toString((int)pauseTime.getValue()));
		}

		public void stateChanged(ChangeEvent e) {
			if (e.getSource().equals(pauseTime)) {

				if ((int)pauseTime.getValue() < 1) {
					pauseTime.setValue((int)1);
				}				
			}			
		}
	}

	private class JDisplayWordInBraille extends Action{		
		private JTextField stringToDisp;
		private JDisplayWordInBraille() {
			instruction.setText("Display Word: Please enter the word you would like to display in braille. Ensure adequate Braille cells are created beforehand");
			stringToDisp = new JTextField(); 
			stringToDisp.setPreferredSize(new Dimension(panelSize.width/5,20));
			main.add(stringToDisp);
		}
		public boolean build(ScenarioCreatorManager sm) {
			return sm.addDispString(stringToDisp.getText());			
		}		
	}

	private class JRepeat extends Action{		
		private JTextField stringToDisp;
		private JLabel selectButton;
		private JResponseButtonBox buttons;

		private JRepeat() {
			instruction.setText("Repeat Text: Set the text to what you would like to repeat, as well as button to initalize the repeat");	
			stringToDisp = new JTextField(); 
			stringToDisp.setPreferredSize(new Dimension(panelSize.width/2,20));
			main.add(stringToDisp);		
			selectButton = new JLabel("Select Repeat Button: ");
			buttons = new JResponseButtonBox();
			main.add(selectButton);
			main.add(buttons);

		}	

		public boolean build(ScenarioCreatorManager sm) {

			boolean toReturn = true;
			sm.addRepeat();
			List string = new ArrayList<String>();
			string.add(stringToDisp.getText());
			if (!(sm.addText(string))) {
				return false;
			}
			if (!(sm.addRepeatButton(Integer.toString(buttons.responseButtons.getSelectedIndex())))) {
				return false;
			}
			return sm.addEndRepeat();			

		}
	}

	private class GoToCheckpointButtonClick extends Action implements ActionListener{
		private JLabel goToEvent;
		private JLabel when;
		private JResponseButtonBox buttons;
		private JEventList events;
		private JComboBox eventList;

		private GoToCheckpointButtonClick(){
			instruction.setText("Go To Checkpoint: Select which checkpoint you would like to travel to when a specfifc button is clicked. The event must occur after this one");	
			goToEvent = new JLabel("Go To Checkpoint: "); 
			when = new JLabel("When This Button is Clicked: ");
			events = new JEventList();
			buttons = new JResponseButtonBox();
			events.jEvents.setSelectedIndex(-1);
			events.jEvents.addActionListener(this);
			main.add(goToEvent);
			main.add(events);
			main.add(when);
			main.add(buttons);

		}

		public boolean build(ScenarioCreatorManager sm) {
			if (events.jEvents.getSelectedItem() == null) {
				return false;
			}
			EventGUI selectedItem = (ScenarioCreatorGUI.EventGUI)events.jEvents.getSelectedItem();			
			return sm.addSkipButton(Integer.toString(buttons.responseButtons.getSelectedIndex()),selectedItem.getEventName().toUpperCase());		
		}


		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(events.jEvents)) {
				if (events.jEvents.getSelectedIndex() <= ScenarioCreatorGUI.eventsList.indexOf(ScenarioCreatorGUI.activeEvent))
				{
					JOptionPane.showMessageDialog(MainFrame.getMainPanel(), "You are not allowed to traverse to this event!", "Invalid Input", JOptionPane.INFORMATION_MESSAGE);
					events.jEvents.setSelectedIndex(-1);
				}
			}
		}	


	}

	private class JClearCells extends Action{		
		private JClearCells() {
			instruction.setText("Clear Cells: This command will clear all of the Braille Cells");
		}		

		public boolean build(ScenarioCreatorManager sm) {

			return sm.addDispClearAll();

		}
	}

	private class JClearSpecificCell extends Action{	

		JBrailleList cells;

		private JClearSpecificCell() {
			instruction.setText("Clear Specfifc Cell: Select which Braille Cell you would like to clear");
			cells = new JBrailleList();			
			main.add(cells);
		}	

		public boolean build(ScenarioCreatorManager sm) {
			return sm.addDispCellClear(Integer.toString(cells.jBraille.getSelectedIndex()));
		}
	}

	private class JDisplayChar extends Action{		
		private JTextField stringToDisp;
		private JBrailleList cells;
		private JDisplayChar() {
			instruction.setText("Display Character: Enter the character you would like to display as well as the cell it will appear on");
			stringToDisp = new JTextField(); 
			stringToDisp.setPreferredSize(new Dimension(panelSize.width/10,20));
			cells = new JBrailleList();
			main.add(stringToDisp);
			main.add(cells);
		}

		public boolean build(ScenarioCreatorManager sm) {

			if (!(stringToDisp.getText().matches("^[a-zA-Z]$"))){
				return false;
			}else {
				return sm.addDispCellChar(Integer.toString(cells.jBraille.getSelectedIndex()), stringToDisp.getText());
			}		
		}
	}

	private class JDisplayPins extends Action implements ActionListener{	

		JBrailleList cells;
		JButton select;
		JBrailleCell edit;

		private JDisplayPins() {
			instruction.setText("Display Pins on Cell: Select which Braille Cell you want to edit and select its pins");
			cells = new JBrailleList();			
			select = new JButton("Select");	
			select.addActionListener(this);
			main.add(cells);
			main.add(select);
		}

		public boolean build(ScenarioCreatorManager sm) {

			return sm.addDispCellPins(Integer.toString(cells.jBraille.getSelectedIndex()), edit.cellConfig());


		}

		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(select)) {
				select.setEnabled(false);
				cells.jBraille.setEnabled(false);
				edit = (JBrailleCell)cells.jBraille.getSelectedItem();
				main.add(edit.getJBrailleCell());
				main.validate();
				main.repaint();
			}
		}		
	}

	private class JTextToSpeech extends Action{		
		private JTextField stringToDisp;
		private JTextToSpeech() {
			instruction.setText("Text To Speech: Enter the text you would like to say");
			stringToDisp = new JTextField(); 
			stringToDisp.setPreferredSize(new Dimension(panelSize.width/2,20));
			main.add(stringToDisp);
		}	

		public boolean build(ScenarioCreatorManager sm) {
			List string = new ArrayList<String>();
			string.add(stringToDisp.getText());
			return sm.addText(string);
		}
	}

	private class JUserInput extends Action{		
		private JUserInput() {
			instruction.setText("User Input: The program will wait for a user input");
		}

		public boolean build(ScenarioCreatorManager sm) {
			return sm.addUserInput();
		}
	}

	private class GoToEvent extends Action implements ActionListener{
		private JLabel goToEvent;
		private JEventList events;
		private JComboBox eventList;

		private GoToEvent(){
			instruction.setText("Go To Event: Select which event you would like to travel to. The event must occur after this one");	
			goToEvent = new JLabel("Go To Event: "); 
			events = new JEventList();
			events.jEvents.setSelectedIndex(-1);
			events.jEvents.addActionListener(this);
			main.add(goToEvent);
			main.add(events);
		}

		public boolean build(ScenarioCreatorManager sm) {
			if (events.jEvents.getSelectedItem() == null) {
				return false;
			}

			EventGUI selectedItem = (EventGUI)events.jEvents.getSelectedItem();			
			return sm.addSkip(selectedItem.getEventName().toUpperCase());
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(events.jEvents)) {
				if (events.jEvents.getSelectedIndex() <= ScenarioCreatorGUI.eventsList.indexOf(ScenarioCreatorGUI.activeEvent))
				{
					JOptionPane.showMessageDialog(MainFrame.getMainPanel(), "You are not allowed to traverse to this event!", "Invalid Input", JOptionPane.INFORMATION_MESSAGE);
					events.jEvents.setSelectedIndex(-1);
				}
			}
		}	


	}

	private class JResetButtons extends Action{		
		private JResetButtons() {
			instruction.setText("Reset Buttons: Resets the commands implemented with each button. Recommended to use at the beggining of each event");
		}	

		public boolean build(ScenarioCreatorManager sm) {
			return sm.addResetButtons();
		}
	}

	private class JLowerPins extends Action implements ActionListener{	

		JBrailleList cells;
		JButton select;
		JBrailleCell edit;
		JComboBox<Integer> pins;

		Integer[] pin = {
				1,2,3,4,5,6,7,8		
		};

		private JLowerPins() {
			instruction.setText("Lower Pin on Cell: Select which Braille Cell you want to edit then select the pin to lower");
			cells = new JBrailleList();			
			select = new JButton("Select");	
			select.addActionListener(this);
			pins = new JComboBox<Integer>(pin);
			main.add(cells);
			main.add(select);
		}

		public boolean build(ScenarioCreatorManager sm) {
			return sm.addDispCellRaise(Integer.toString(cells.jBraille.getSelectedIndex()), Integer.toString((int)pins.getSelectedItem()));
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(select)) {
				select.setEnabled(false);
				cells.jBraille.setEnabled(false);
				edit = new JBrailleCell(JBrailleCell.DISPLAY_INDEX);
				main.add(edit.getJBrailleCell());
				main.add(pins);
				main.validate();
				main.repaint();
			}			


		}		
	}

	private class JRaisePins extends Action implements ActionListener{	

		JBrailleList cells;
		JButton select;
		JBrailleCell edit;
		JComboBox<Integer> pins;

		Integer[] pin = {
				1,2,3,4,5,6,7,8		
		};

		private JRaisePins() {
			instruction.setText("Raise Pin on Cell: Select which Braille Cell you want to edit then select the pin to raise");
			cells = new JBrailleList();			
			select = new JButton("Select");	
			select.addActionListener(this);
			pins = new JComboBox<Integer>(pin);
			main.add(cells);
			main.add(select);
		}

		public boolean build(ScenarioCreatorManager sm) {
			return sm.addDispCellRaise(Integer.toString(cells.jBraille.getSelectedIndex()), Integer.toString((int)pins.getSelectedItem()));
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(select)) {
				select.setEnabled(false);
				cells.jBraille.setEnabled(false);
				edit = new JBrailleCell(JBrailleCell.DISPLAY_INDEX);
				main.add(edit.getJBrailleCell());
				main.add(pins);
				main.validate();
				main.repaint();
			}			


		}		
	}


	private class PlayAudio extends Action implements ActionListener{
		private JLabel playAudio;
		private JButton browse;
		private JLabel audioName;

		private String fileName;

		private PlayAudio(){
			fileName = "";
			instruction.setText("Play Audio: Select an audio file to play. The audio must a .WAV format!");	
			playAudio = new JLabel("Browse Audio");
			browse = new JButton("Browse");
			audioName = new JLabel("Selected Audio: ");

			browse.addActionListener(this);

			main.add(playAudio);
			main.add(browse);
			main.add(audioName);
		}

		public boolean build(ScenarioCreatorManager sm) {
			return sm.addSound(fileName);
		}

		public void actionPerformed(ActionEvent e) {

			if (e.getSource().equals(browse)) {

				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new java.io.File("."));
				fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.wav", "wav"));

				if (fileChooser.showOpenDialog(browse) == JFileChooser.APPROVE_OPTION) {
					if (fileChooser.getSelectedFile().getName().matches(".*.wav$")) {
						fileName = fileChooser.getSelectedFile().getName();
						audioName.setText("Selected Audio: " + fileName);
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
			buttons = new String[ScenarioCreatorGUI.numButtons];			
			for (int i = 0; i< buttons.length; i++) {
				buttons[i] = "Button: " + (i+1);
			}			
			responseButtons = new JComboBox<String>(buttons);			
			this.add(responseButtons);
		}			
	}

	private class JEventList extends JPanel{		
		public JComboBox<EventGUI> jEvents;

		private JEventList() {	
			jEvents = new JComboBox<EventGUI>();
			jEvents.setModel(new DefaultComboBoxModel(ScenarioCreatorGUI.eventsList.toArray()));

			this.add(jEvents);			
		}			
	}

	private class JBrailleList extends JPanel{		
		public JComboBox<JBrailleCell> jBraille;

		private JBrailleList() {
			jBraille = new JComboBox<JBrailleCell>();			
			for (int i=0 ; i<ScenarioCreatorGUI.numCells; i++) {
				jBraille.addItem(ScenarioCreatorGUI.activeEvent.cells.get(i));
			}			
			this.add(jBraille);
		}			
	}


	public boolean build() {

		return true;

	}


}
