package enamel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class JActionConfigure extends JPanel{

	private static JLabel instruction;
	private static Dimension panelSize;
	private static JScrollPane configScroll;
	private JPanel main = this;
	public Action action;

	public JActionConfigure(int index) {		
		JActionConfigure.panelSize = new Dimension((int)(ScenarioCreatorGUI.configuration.getSize().width * 0.9), (int)(ScenarioCreatorGUI.configuration.getSize().height * 0.9));
		configScroll = new JScrollPane(main, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.setPreferredSize(panelSize);
		this.setBackground(Color.WHITE);
		this.setBorder(new LineBorder(Color.GRAY, 5));
		instruction = new JLabel("Instruction: ");
		instruction.setHorizontalAlignment(JLabel.CENTER);
		instruction.setSize(panelSize.width,30);
		instruction.setPreferredSize(instruction.getSize());
		JLabel config = new JLabel("- Configuration -");
		config.setFont(new Font("calibri", Font.BOLD, 17));
		config.setHorizontalAlignment(JLabel.CENTER);
		config.setSize(panelSize.width,30);
		config.setPreferredSize(config.getSize());
		this.add(config);
		this.add(instruction);		
		getOptions(index);
	}

	private void getOptions(int index) {

		if (index == 0) {
			action = new Checkpoint();
		}else if (index == 1) {
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
		}else if (index == 16) {
			action = new Title();
		}else {
			throw new IllegalArgumentException("Action Selected DNE");
		}
	}

	//ACTIONS	
	private class Checkpoint extends Action{

		private Checkpoint() {
			super.name = "Checkpoint: ";
			instruction.setText("Checkpoint: Give this checkpoint a name (One word consiting of only letters and no duplicates)");
		}

		public boolean build(ScenarioCreatorManager sm) {
			return false;
		}

	}

	private class JPauseButton extends Action implements ChangeListener{		
		private JSpinner pauseTime;
		private JPauseButton() {
			super.name = "Pause";
			instruction.setText("Pause: Select the amount of time (in seconds) you wish to pause");
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
				setAboveMin(pauseTime);				
			}			
		}
	}

	private class JDisplayWordInBraille extends Action{	

		private JTextField stringToDisp;
		private JDisplayWordInBraille() {
			super.name = "Braille: Display Word";
			instruction.setText("Display Word: Please enter the word you would like to display in braille. Ensure adequate Braille cells are created beforehand");
			stringToDisp = new JTextField(); 
			stringToDisp.setPreferredSize(new Dimension(panelSize.width/5,20));
			main.add(stringToDisp);
		}
		public boolean build(ScenarioCreatorManager sm) {
			
			if (stringToDisp.getText().length() > ScenarioCreatorGUI.numCells) {
				return false;
			}
			return sm.addDispString(stringToDisp.getText());			
		}		
	}

	private class JRepeat extends Action{		
		private JTextField stringToDisp;
		private JLabel selectButton;
		private ResponseButtonSpinner buttons;

		private JRepeat() {
			super.name = "Button: Repeat Text";
			instruction.setText("Repeat Text: Set the text to repeat, as well as the button required to initalize the repeat");	
			stringToDisp = new JTextField(); 
			stringToDisp.setPreferredSize(new Dimension(panelSize.width/2,20));
			main.add(stringToDisp);		
			selectButton = new JLabel("Select Repeat Button: ");
			buttons = new ResponseButtonSpinner();
			main.add(selectButton);
			main.add(buttons);
		}	

		public boolean build(ScenarioCreatorManager sm) {

			if (buttons.value() >= ScenarioCreatorGUI.numButtons) {
				return false;
			}		
			
			sm.addRepeat();
			List<String> string = new ArrayList<String>();
			string.add(stringToDisp.getText());
			if (!(sm.addText(string))) {
				return false;
			}
			if (!(sm.addRepeatButton(Integer.toString(buttons.value())))) {
				return false;
			}
			return sm.addEndRepeat();			

		}
	}

	private class GoToCheckpointButtonClick extends Action{
		private JLabel goToEvent;
		private JLabel when;
		private ResponseButtonSpinner buttons;
		private ActionSpinner nodeIndex;

		private GoToCheckpointButtonClick(){
			super.name = "Button: Go To Checkpoint";
			instruction.setText("Go To Checkpoint: Select the index of the checkpoint you would like to travel to when a specfifc button is clicked. The checkpoint must occur after this one");	
			goToEvent = new JLabel("Go To Checkpoint Indexed: "); 
			when = new JLabel("When This Button is Clicked: ");
			nodeIndex = new ActionSpinner();
			buttons = new ResponseButtonSpinner();
			main.add(goToEvent);
			main.add(nodeIndex);
			main.add(when);
			main.add(buttons);

		}

		public boolean build(ScenarioCreatorManager sm) {	
			if (buttons.value() >= ScenarioCreatorGUI.numButtons) {
				return false;
			}else if (nodeIndex.value() >= ScenarioCreatorGUI.nodes.size()) {
				return false;
			}		
			return sm.addSkipButton(Integer.toString(buttons.value()), ScenarioCreatorGUI.nodes.get(nodeIndex.value()).getCheckpointName().toUpperCase()); 		
		}


	}

	private class JClearCells extends Action{
		private JClearCells() {
			instruction.setText("Clear Cells: This command will clear all of the Braille Cells");
			super.name = "Braille: Clear All Braille Cells";
		}		

		public boolean build(ScenarioCreatorManager sm) {

			return sm.addDispClearAll();

		}
	}

	private class JClearSpecificCell extends Action{	

		private BrailleCellSpinner cells;

		private JClearSpecificCell() {
			super.name = "Braille: Clear A Specific Braille Cell";
			instruction.setText("Clear Specfifc Cell: Select which Braille Cell you would like to clear");
			cells = new BrailleCellSpinner();			
			main.add(cells);
		}	

		public boolean build(ScenarioCreatorManager sm) {
			if (cells.value() >= ScenarioCreatorGUI.numCells) {
				return false;
			}	
			return sm.addDispCellClear(Integer.toString(cells.value()));
		}
	}

	private class JDisplayChar extends Action{		
		private JTextField stringToDisp;
		private BrailleCellSpinner cells;
		private JDisplayChar() {
			super.name = "Braille: Display a Character";
			instruction.setText("Display Character: Enter the character you would like to display as well as the index of cell it will appear on");
			stringToDisp = new JTextField(); 
			stringToDisp.setPreferredSize(new Dimension(80,20));
			main.add(stringToDisp);
			main.add(new JLabel("Braille Cell: "));
			cells = new BrailleCellSpinner();
			main.add(cells);
		}

		public boolean build(ScenarioCreatorManager sm) {
			if (cells.value() >= ScenarioCreatorGUI.numCells) {
				return false;
			}			
			if (!(stringToDisp.getText().matches("^[a-zA-Z]$"))){
				return false;
			}else {
				return sm.addDispCellChar(Integer.toString(cells.value()), stringToDisp.getText());
			}		
		}
	}

	private class JDisplayPins extends Action{	

		BrailleCellSpinner cells;
		JBrailleCell edit;

		private JDisplayPins() {
			super.name = "Braille: Display Specific Pins";
			instruction.setText("Display Pins on Cell: Select which Braille Cell you want to edit and select its pins");
			cells = new BrailleCellSpinner();	
			main.add(new JLabel("Braille Cell: "));
			main.add(cells);
			edit = new JBrailleCell();
			main.add(edit);
		}

		public boolean build(ScenarioCreatorManager sm) {
			if (cells.value() >= ScenarioCreatorGUI.numCells) {
				return false;
			}
			return sm.addDispCellPins(Integer.toString(cells.value()), edit.cellConfig());
		}

	}

	private class JTextToSpeech extends Action{		
		private JTextField stringToDisp;
		private JTextToSpeech() {
			super.name = "Text To Speech";
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
			super.name = "Wait For User Input";
			instruction.setText("User Input: The program will wait for a user input");
		}

		public boolean build(ScenarioCreatorManager sm) {
			return sm.addUserInput();
		}
	}

	private class GoToEvent extends Action{
		private ActionSpinner events;
		private GoToEvent(){
			super.name = "Traverse to a Checkpoint";
			instruction.setText("Go To Event: Select which event you would like to travel to. The event must occur after this one");	
			main.add(new JLabel("Go To Event: ")); 
			events = new ActionSpinner();
			main.add(events);
		}

		public boolean build(ScenarioCreatorManager sm) {
			if (events.value() >= ScenarioCreatorGUI.nodes.size()) {
				return false;
			}
			return sm.addSkip(ScenarioCreatorGUI.nodes.get(events.value()).getCheckpointName().toUpperCase());
		}

	}

	private class JResetButtons extends Action{		
		private JResetButtons() {
			super.name = "Reset Button Configurations";
			instruction.setText("Reset Buttons: Resets the commands implemented with each button. Recommended to use at the beggining of each event");
		}	

		public boolean build(ScenarioCreatorManager sm) {
			return sm.addResetButtons();
		}
	}

	private class JLowerPins extends Action{	

		BrailleCellSpinner cells;
		JBrailleCell edit;
		JComboBox<Integer> pins;
		Integer[] pin = {
				1,2,3,4,5,6,7,8		
		};

		private JLowerPins() {
			super.name = "Braille: Lower Specific Pin";
			instruction.setText("Lower Pin on Cell: Select which Braille Cell you want to edit then select the pin to lower");
			main.add(new JLabel("Braille Cell: "));
			cells = new BrailleCellSpinner();
			main.add(cells);
			pins = new JComboBox<Integer>(pin);
			main.add(new JLabel("Lower Pin: "));
			main.add(pins);
		}

		public boolean build(ScenarioCreatorManager sm) {
			if (cells.value() >= ScenarioCreatorGUI.numCells) {
				return false;
			}
			return sm.addDispCellRaise(Integer.toString(cells.value()), Integer.toString((int)pins.getSelectedItem()));
		}		
	}

	private class JRaisePins extends Action{	

		BrailleCellSpinner cells;
		JBrailleCell edit;
		JComboBox<Integer> pins;
		Integer[] pin = {
				1,2,3,4,5,6,7,8		
		};

		private JRaisePins() {
			super.name = "Braille: Raise Specific Pin";
			instruction.setText("Lower Pin on Cell: Select which Braille Cell you want to edit then select the pin to raise");
			main.add(new JLabel("Braille Cell: "));
			cells = new BrailleCellSpinner();
			main.add(cells);
			pins = new JComboBox<Integer>(pin);
			main.add(new JLabel("Raise Pin: "));
			main.add(pins);
		}

		public boolean build(ScenarioCreatorManager sm) {
			if (cells.value() >= ScenarioCreatorGUI.numCells) {
				return false;
			}
			return sm.addDispCellRaise(Integer.toString(cells.value()), Integer.toString((int)pins.getSelectedItem()));
		}
	}

	private class PlayAudio extends Action implements ActionListener{
		private JLabel playAudio;
		private JButton browse;
		private JLabel audioName;

		private String fileName;

		private PlayAudio(){
			super.name = "Audio: Browse or Record Audio";
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

	private class Title extends Action{
		private Title() {
			super.name = "Title: ";
			instruction.setText("Title: Give this scenario a title");
		}

		public boolean build(ScenarioCreatorManager sm) {
			return true;
		}
		
		
	}
	
	//SETTING CLASSES	
	private class ResponseButtonSpinner extends JSpinner implements ChangeListener{
		private Dimension size = new Dimension(80,20);
		private ResponseButtonSpinner() {
			SpinnerModel number = new SpinnerNumberModel();
			this.setModel(number);
			this.setPreferredSize(size);
			addChangeListener(this);	
			this.setValue(1);
		}

		public void stateChanged(ChangeEvent e) {
			this.removeChangeListener(this);
			setAboveMin(this);
			if ((int)getValue() > ScenarioCreatorGUI.numButtons) {
				this.setValue((int)ScenarioCreatorGUI.numButtons);
			}
			this.addChangeListener(this);
		}	

		public int value() {
			return (int)this.getValue()-1;
		}
	}
	private class BrailleCellSpinner extends JSpinner implements ChangeListener{
		private Dimension size = new Dimension(80,20);
		private BrailleCellSpinner() {
			SpinnerModel number = new SpinnerNumberModel();
			this.setModel(number);
			this.setPreferredSize(size);
			this.setValue(1);
			addChangeListener(this);			
		}

		public void stateChanged(ChangeEvent e) {
			this.removeChangeListener(this);
			setAboveMin(this);
			if ((int)getValue() > ScenarioCreatorGUI.numCells) {
				this.setValue((int)ScenarioCreatorGUI.numCells);
			}
			this.addChangeListener(this);
		}		

		public int value() {
			return (int)this.getValue()-1;
		}
	}
	private class ActionSpinner extends JSpinner implements ChangeListener{
		private Dimension size = new Dimension(80,20);
		private ActionSpinner() {
			SpinnerModel number = new SpinnerNumberModel();
			this.setModel(number);
			this.setPreferredSize(size);
			this.setValue(1);
			addChangeListener(this);			
		}

		public void stateChanged(ChangeEvent e) {
			this.removeChangeListener(this);
			if ((int)getValue() < 0) {
				this.setValue(0);
			}else if ((int)getValue() > ScenarioCreatorGUI.nodes.size()-1) {
				this.setValue((int)(ScenarioCreatorGUI.nodes.size()-1));
			}
			this.addChangeListener(this);
		}		

		public int value() {
			return (int)this.getValue();
		}
	}

	//SETTING METHODS
	private void setAboveMin(JSpinner spinner) {
		if ((int)spinner.getValue() < 1) {
			spinner.setValue(1);
		}
	}


}
