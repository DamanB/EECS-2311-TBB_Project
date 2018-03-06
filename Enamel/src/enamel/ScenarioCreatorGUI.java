package enamel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class ScenarioCreatorGUI {

	private static JPanel mainPanel;
	private static JPanel leftBorder;
	private static JPanel eventListPanel;
	private static JPanel eventEditor;
	private static JPanel controls;
	
	public static String ScenarioTitle;
	public static int numCells = 0;
	public static int numButtons = 0;

	public static JPanel actionOptions;

	public static List<EventGUI> eventsList;
	public static EventGUI activeEvent;

	private static ScenarioCreatorGUI instance;

	public static String[] userCommandList = {
			"Text to Speech", //DONE
			"Play Audio", //DONE
			"Pause", //DONE
			"Display Pins on Braille Cell", //DONE
			"Display Word with Braille Cells", //DONE
			"Display Character on Braille Cell", //DONE
			"Clear All Braille Cells", //DONE       
			"Clear Specific Braille Cell", //DONE
			"Lower Pins on Cell", //DONE
			"Raise Pins on Cell", //DONE
			"Repeat Text w/ Button Clicked", //DONE
			"Go to Event", //DONE
			"Go to Event w/ Button Clicked", //DONE
			"Reset Button Configurations", //DONE
			"User Input" //DONE  
	};


	private ScenarioCreatorGUI() {

		eventsList = new ArrayList<>();

		BorderLayout layout = new BorderLayout();
		mainPanel = new JPanel(layout);	

		FlowLayout flow = new FlowLayout(FlowLayout.LEFT);
		flow.setHgap(15);
		eventListPanel = new JPanel(flow);

		eventEditor = new JPanel(new BorderLayout());

		controls = new JPanel();

		leftBorder = new JPanel();


		mainPanel.setBackground(MainFrame.primColor);
		eventListPanel.setBackground(MainFrame.primColor);
		eventEditor.setBackground(MainFrame.primColor);
		controls.setBackground(MainFrame.primColor);
		leftBorder.setBackground(MainFrame.primColor);

		eventListPanel.setSize((int)(MainFrame.getSizeX()*0.9),(int)(MainFrame.getSizeY() * 0.12));
		eventEditor.setSize((int)(MainFrame.getSizeX()*0.9), (int)(MainFrame.getSizeY() * 0.80));
		controls.setSize((int)(MainFrame.getSizeX() * 0.12),(int)(MainFrame.getSizeY() * 0.80));
		leftBorder.setSize((int)(MainFrame.getSizeX() * 0.02),(int)(MainFrame.getSizeY() * 0.80));

		eventListPanel.setPreferredSize(eventListPanel.getSize());
		eventEditor.setPreferredSize(eventEditor.getSize());
		controls.setPreferredSize(controls.getSize());
		leftBorder.setPreferredSize(new Dimension(leftBorder.getSize()));

		actionOptions = new JPanel();
		actionOptions.setBorder(new LineBorder(MainFrame.primColor,1));

		mainPanel.setSize(MainFrame.dimension);
		mainPanel.add(eventListPanel,BorderLayout.SOUTH);
		mainPanel.add(controls,BorderLayout.EAST);
		mainPanel.add(eventEditor,BorderLayout.CENTER);
		mainPanel.add(leftBorder, BorderLayout.WEST);

		EventsListGUI eList = new EventsListGUI();
		controlGUI control = new controlGUI();

	}

	private ScenarioCreatorGUI(File file) {
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	public static JPanel getScreen() {
		instance = new ScenarioCreatorGUI();
		return instance.mainPanel;
	}

	public static JPanel getPreBuiltScreen(File file) {
		instance = new ScenarioCreatorGUI(file);
		return instance.mainPanel;
	}
	
	private static class controlGUI implements ActionListener{

		private static JLabel optionsTitle;
		private static JButton backButton;
		private static JButton buildButton;
		public static JSpinner scenarioIndex;
		private static JLabel indexText;

		private controlGUI(){

			optionsTitle = new JLabel("Options",JLabel.CENTER);
			optionsTitle.setFont(new Font("calibri", Font.ITALIC, 25));

			backButton = new JButton("Back to Menu");
			backButton.setSize(controls.getSize().width-30, controls.getSize().height/10);
			backButton.setPreferredSize(backButton.getSize());

			buildButton = new JButton("Build Project!");
			buildButton.setSize(controls.getSize().width-30, controls.getSize().height/10);
			buildButton.setPreferredSize(backButton.getSize());

			Font buttonFont = new Font("cailibri", Font.ITALIC, 15);
			backButton.setFont(buttonFont);
			buildButton.setFont(buttonFont);
			backButton.addActionListener(this);
			buildButton.addActionListener(this);

			MainFrame.editJButton(backButton);
			MainFrame.editJButton(buildButton);

			indexText = new JLabel("Scenario_");

			SpinnerModel number = new SpinnerNumberModel();
			scenarioIndex = new JSpinner(number);	
			scenarioIndex.setPreferredSize(new Dimension(controls.getSize().width/2,20));

			controls.add(optionsTitle);
			controls.add(backButton);
			controls.add(indexText);
			controls.add(scenarioIndex);
			controls.add(buildButton);

		}

		public void actionPerformed(ActionEvent e) {

			if (e.getSource().equals(backButton)) {		

				int k = JOptionPane.showConfirmDialog(MainFrame.getMainPanel(), "Exiting will cause you to loose any changes made to this Scenario. Are you sure you wish to exit?", "Exit", JOptionPane.OK_CANCEL_OPTION);

				if (k == JOptionPane.OK_OPTION) {
					MainFrame.changeScreen(ScenarioEditorMenu.getScreen());
				}
			}else if (e.getSource().equals(buildButton)) {
				build();
			}

		}

		private void build() {

			File file;				

			while (true) {
				//CREATE FILE
				if ((int)controlGUI.scenarioIndex.getValue() <= 0) {
					JOptionPane.showMessageDialog(MainFrame.getMainPanel(), "Please select an index greater than 0 for the Scenario name", "Invalid Index", JOptionPane.INFORMATION_MESSAGE);
					break;
				}
				String fileName = "Scenario_" + (int)scenarioIndex.getValue() + ".txt";	
				Path path = Paths.get("./" + fileName); 
				if ((Files.exists(path))) {
					JOptionPane.showMessageDialog(MainFrame.getMainPanel(), fileName + " Already Exists. Please Choose a New Index", "File Name Exists", JOptionPane.INFORMATION_MESSAGE);
					JOptionPane.showMessageDialog(MainFrame.getMainPanel(), "Overwriting a file will become available during the final launch", "Overwriting", JOptionPane.INFORMATION_MESSAGE);
					break;
				}else {
					file = new File(fileName);
					try {
						file.createNewFile();
					}catch (IOException e){
						e.printStackTrace();
					}
				}

				ScenarioCreatorManager sm = new ScenarioCreatorManager(file);				
				//ADDING ACTIONS	
				sm.setTitle(ScenarioTitle);		
				sm.setButtonNum(numButtons);
				sm.setCellNum(numCells);

				boolean valid = true;
				int count = 0;

				for (EventGUI event : eventsList) {

					if (count != 0) {
						sm.addSkipPos(event.getEventName().toUpperCase());
					}					
					count++;
					for (ScenarioCreatorGUI.EventGUI.JActionNode action : event.actionList) {
						if (action.actionConfigure == null) {
							JOptionPane.showMessageDialog(MainFrame.getMainPanel(), "There is an action in event: " + event.eventName + " that has not been initalized", "Compilation Error", JOptionPane.INFORMATION_MESSAGE);
							valid = false;
							break;
						}
						if (!(action.actionConfigure.action.build(sm))) {						
							JOptionPane.showMessageDialog(MainFrame.getMainPanel(), "You have error in event: " + event.getEventName() + " in Action indexed: " + (action.getIndex()+1), "Compilation Error", JOptionPane.INFORMATION_MESSAGE);
							JOptionPane.showMessageDialog(MainFrame.getMainPanel(), "ERROR LISTED AS: " + sm.getErrorMessage(), "Compilation Error", JOptionPane.INFORMATION_MESSAGE);
							valid = false;
							break;
						}
					}					

					if (valid == false) {
						JOptionPane.showMessageDialog(MainFrame.getMainPanel(), "BUILD FAILED! " + fileName + " will be deleted" , "Error", JOptionPane.INFORMATION_MESSAGE);
						file.delete();
						break;
					}else {
						sm.nextQuestion();
					}

				}	

				if (valid) {
					sm.saveToFile();
				}
				break;
			}

		}
	}

	private static class EventsListGUI implements ActionListener{

		private static JButton addEvent;
		private static JLabel currentlyEditing;
		private static JComboBox<EventGUI> eventComboBox;
		private static JLabel eventNameLabel;
		private static JTextField eventNameField;
		private static JButton deleteEvent;

		private EventsListGUI() {
			addEvent = new JButton("Add Event");
			addEvent.addActionListener(this);
			addEvent.setSize(80,25);
			addEvent.setPreferredSize(addEvent.getSize());		
			MainFrame.editJButton(addEvent);

			eventNameLabel = new JLabel("Event Name: ");
			eventNameField = new JTextField();
			eventNameField.setSize((int)(eventListPanel.getSize().width*0.2), (int)(eventListPanel.getSize().height*0.25));
			eventNameField.setPreferredSize(eventNameField.getSize());

			currentlyEditing = new JLabel("Currently Editing: ");

			eventComboBox = new JComboBox<EventGUI>();
			eventComboBox.addActionListener(this);
			eventComboBox.setBackground(Color.WHITE);
			
			deleteEvent = new JButton("Delete Event");
			MainFrame.editJButton(deleteEvent);
			deleteEvent.addActionListener(this);
			deleteEvent.setSize(80,25);
			deleteEvent.setPreferredSize(deleteEvent.getSize());	

			eventListPanel.add(eventNameLabel);
			eventListPanel.add(eventNameField);
			eventListPanel.add(addEvent);
			eventListPanel.add(currentlyEditing);
			eventListPanel.add(eventComboBox);
			eventListPanel.add(deleteEvent);
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == addEvent) {				
				String name = this.eventNameField.getText().toLowerCase();
				boolean create = true;

				if (name.isEmpty() || !name.matches("^[A-Za-z]*$")) {
					JOptionPane.showMessageDialog(MainFrame.getMainPanel(), "Please Enter a Name Consisting of Only Letters and no Spaces", "Error Invalid Name", JOptionPane.INFORMATION_MESSAGE);
					create = false;
				}
				if (create) {
					for (EventGUI el : eventsList) {
						if (el.getEventName().toLowerCase().equals(name)) {
							JOptionPane.showMessageDialog(MainFrame.getMainPanel(), "Please Enter a Non Duplicate Name (Event names are not case sensitive)", "Error Duplicate Name", JOptionPane.INFORMATION_MESSAGE);
							create = false;
							break;
						}					
					}
				}
				if (create) {
					EventGUI newEvent =	ScenarioCreatorGUI.createNewEvent(this.eventNameField.getText());
					eventsList.add(newEvent);
					eventComboBox.addItem(newEvent);
					eventComboBox.setSelectedItem(newEvent);
					newEvent.addToFrame();
					removeActionOption(); 
				}
			}else if (e.getSource().equals(eventComboBox)) {

				activeEvent = (EventGUI) eventComboBox.getSelectedItem();
				activeEvent.addToFrame();
				removeActionOption();
			}else if (e.getSource().equals(deleteEvent)){
				
				int k = JOptionPane.showConfirmDialog(MainFrame.getMainPanel(), "Deleting this event will cause any other references to this event to break and build to fail. Confirm Delete?", "Delete?", JOptionPane.OK_CANCEL_OPTION);
				if (k==JOptionPane.OK_OPTION) {					
					activeEvent.removeFromFrame();
					removeActionOption();
					eventsList.remove(activeEvent);
					eventComboBox.removeActionListener(this);
					eventComboBox.removeItem(activeEvent);
					eventComboBox.setSelectedIndex(-1);						
					eventComboBox.addActionListener(this);	
					activeEvent = null;
				}
				
				
			}
		}
	}

	public class EventGUI implements ActionListener{

		//FlowLayout flow = new FlowLayout(FlowLayout.LEFT);
		
		LinkedList<JActionNode> actionList = new LinkedList<JActionNode>();

		private int sizeX;
		private int sizeY;
		private String eventName;

		private JPanel listedActions;
		private JLabel jEventName;
		
		private JScrollPane scroll;
		
		public List<JBrailleCell> cells;

		private EventGUI(String eventName) {

			cells = new ArrayList<JBrailleCell>();
			
			for (int i =0; i<numCells; i++) {
				cells.add(new JBrailleCell((i+1)));
			}

			//------------LISTED ACTIONS-------------------\\
			this.eventName = eventName;
			sizeX = eventEditor.getWidth();
			sizeY = eventEditor.getHeight();

			//flow.setHgap(10);
			listedActions = new JPanel();	
			listedActions.setLayout(new BoxLayout(listedActions, BoxLayout.Y_AXIS));
			//listedActions.setSize(sizeX,(int)(sizeY * 0.75));
			//listedActions.setPreferredSize(listedActions.getSize());
			//listedActions.setMaximumSize(new Dimension(sizeX,(int)(sizeY * 1000)));
			
			jEventName = new JLabel("Event Name: " + eventName);
			jEventName.setPreferredSize(new Dimension(listedActions.getSize().width,20));

			actionOptions.setSize(sizeX,(int)(sizeY * 0.25));
			actionOptions.setPreferredSize(actionOptions.getSize());

			scroll = new JScrollPane(listedActions, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scroll.setSize(20,(int)(sizeY * 0.75));
			scroll.setPreferredSize(scroll.getSize());

			
			JActionNode newNode = new JActionNode();
			actionList.add(newNode);
			listedActions.add(jEventName);
			listedActions.add(newNode);
			//leftBorder.add(scroll);

		}

		public String getEventName() {
			return eventName;
		}

		private LinkedList<JActionNode> getActionList() {
			return actionList;
		}

		private void addAction(int indexToAdd) {

			JActionNode newNode = new JActionNode();
			newNode.setIndex(indexToAdd);			
			if (newNode.getIndex() == actionList.size()) {
				actionList.add(newNode);
			}else if(newNode.getIndex() < actionList.size()){
				actionList.add(indexToAdd, newNode);
			}else {
				throw new IllegalArgumentException("INVALID INDEX ON NEW ACTION NODE");
			}

			repaintGUI();

		}

		private void removeAction(JActionNode nodeToRemove) {			
			if (actionList.remove(nodeToRemove.getIndex()) == null) {		
			}
			if (actionList.isEmpty()) {
				JActionNode newNode = new JActionNode();
				actionList.add(newNode);
				listedActions.add(newNode);
			}			
			repaintGUI();			
		}


		private void repaintGUI() {
			listedActions.removeAll();			
			Iterator<JActionNode> it = actionList.iterator();
			int index = 0;
			listedActions.add(jEventName);
			while (it.hasNext()) {
				JActionNode node = it.next();
				listedActions.add(node);
				node.setIndex(index);
				index = index+1;
			}			
			listedActions.validate();
			listedActions.repaint();
			scroll.repaint();

		}

		private void repaintEditor() {eventEditor.repaint();}

		private void addToFrame() {
			eventEditor.removeAll();
			eventEditor.add(scroll, BorderLayout.NORTH);
			eventEditor.add(actionOptions, BorderLayout.CENTER);
			eventEditor.validate();
			activeEvent = this;
			repaintEditor();	
		}
		
		private void removeFromFrame() {
			eventEditor.removeAll();
			repaintEditor();
		}

		public void actionPerformed(ActionEvent e) {





		}	

		public String toString() {
			return this.eventName;
		}


		private class JActionNode extends JPanel implements ActionListener{

			private int index = 0;

			private JPanel buttons;
			private JComboBox actionList;
			private JButton addAction;
			private JButton removeAction;
			private JLabel arrow;
			private JButton edit;

			private int sizeX;
			private int sizeY;


			public JActionConfigure actionConfigure;

			private JActionNode(){

				sizeX = MainFrame.getSizeX()/7;
				sizeY = MainFrame.getSizeY()/7;

				buttons = new JPanel();
				actionList = new JComboBox<String>(userCommandList);
				addAction = new JButton("+");
				removeAction = new JButton("-");
				edit = new JButton("Select");
				arrow = new JLabel((this.index+1) + ". ");

				this.add(arrow);
				this.add(actionList);
				this.add(buttons);
				buttons.add(edit);
				buttons.add(removeAction);	
				buttons.add(addAction);

				addAction.addActionListener(this);
				removeAction.addActionListener(this);
				edit.addActionListener(this);

				this.setSize(sizeX, sizeY);				
			}

			public int getSelectedItem() {
				return actionList.getSelectedIndex();
			}

			public JButton getRemoveButton() {
				return this.removeAction;
			}

			public JButton getAddButton() {
				return this.addAction;
			}

			public int getIndex() {
				return index;
			}

			public void setIndex(int i) {
				index = i;
				arrow.setText((this.index+1) + ". ");
			}

			public void actionPerformed(ActionEvent e) {

				if (e.getSource().equals(addAction)){
					activeEvent.addAction(this.index+1);
					removeActionOption();
				}else if (e.getSource().equals(removeAction)) {
					activeEvent.removeAction(this);
					removeActionOption();
				}else if (e.getSource().equals(edit)) {
					if (edit.getText().equals("Select")) {					
						this.actionList.setEnabled(false);
						actionConfigure = new JActionConfigure(actionList.getSelectedIndex());
						edit.setText("Configure");
					}else if (edit.getText().equals("Configure")){
						ScenarioCreatorGUI.changeActionOptions(this.actionConfigure);
					}

				}
			}


		}

	}

	private static EventGUI createNewEvent(String eventName) {		
		return instance.new EventGUI(eventName);		
	}

	private static void changeActionOptions(JPanel newPanel) {	
		actionOptions.removeAll();
		actionOptions.add(newPanel);
		actionOptions.validate();
		actionOptions.repaint();		
	}

	private static void removeActionOption() {
		actionOptions.removeAll();
		actionOptions.validate();
		actionOptions.repaint();
	}

	public static JPanel getMainPanel() {
		return mainPanel;
	}

}

