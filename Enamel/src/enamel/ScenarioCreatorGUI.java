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
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class ScenarioCreatorGUI {

	private static JPanel mainPanel;
	private static JPanel leftBorder;
	private static JPanel eventListPanel;
	private static JPanel eventEditor;
	private static JPanel controls;

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

	public static JPanel getScreen() {
		instance = new ScenarioCreatorGUI();
		return instance.mainPanel;
	}

	private static class controlGUI implements ActionListener{

		private static JLabel optionsTitle;
		private static JButton backButton;
		private static JButton buildButton;

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

			controls.add(optionsTitle);
			controls.add(backButton);
			controls.add(buildButton);

		}

		public void actionPerformed(ActionEvent e) {

			if (e.getSource().equals(backButton)) {				
				MainFrame.changeScreen(ScenarioEditorMenu.getScreen());
			}else if (e.getSource().equals(buildButton)) {
				build();
			}

		}

		private void build() {} //TODO: IMPLEMENT

	}

	private static class EventsListGUI implements ActionListener{

		private static JButton addEvent;
		private static JLabel currentlyEditing;
		private static JComboBox<EventGUI> eventComboBox;
		private static JLabel eventNameLabel;
		private static JTextField eventNameField;

		private EventsListGUI() {
			addEvent = new JButton("New Event");
			addEvent.addActionListener(this);
			addEvent.setSize((int)(eventListPanel.getSize().height * 0.8), (int)(eventListPanel.getSize().height * 0.8));
			addEvent.setPreferredSize(addEvent.getPreferredSize());		
			MainFrame.editJButton(addEvent);

			eventNameLabel = new JLabel("Event Name: ");
			eventNameField = new JTextField();
			eventNameField.setSize((int)(eventListPanel.getSize().width*0.2), (int)(eventListPanel.getSize().height*0.25));
			eventNameField.setPreferredSize(eventNameField.getSize());

			currentlyEditing = new JLabel("Currently Editing: ");

			eventComboBox = new JComboBox<EventGUI>();
			eventComboBox.addActionListener(this);
			eventComboBox.setBackground(Color.WHITE);

			eventListPanel.add(addEvent);
			eventListPanel.add(eventNameLabel);
			eventListPanel.add(eventNameField);
			eventListPanel.add(currentlyEditing);
			eventListPanel.add(eventComboBox);
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == addEvent) {				
				String name = this.eventNameField.getText();
				boolean create = true;
				for (EventGUI el : eventsList) {
					if (el.getEventName().equals(name)) {
						JOptionPane.showMessageDialog(MainFrame.getMainPanel(), "Please Enter a Non Duplicate Name", "Error Duplicate Name", JOptionPane.INFORMATION_MESSAGE);
						create = false;
						break;
					}					
				}
				if (create) {
					EventGUI newEvent =	ScenarioCreatorGUI.createNewEvent(this.eventNameField.getText());
					eventsList.add(newEvent);
					eventComboBox.addItem(newEvent);
					eventComboBox.setSelectedItem(newEvent);
					newEvent.addToFrame();
				}
			}else if (e.getSource().equals(eventComboBox)) {

				activeEvent = (EventGUI) eventComboBox.getSelectedItem();
				activeEvent.addToFrame();
			}
		}
	}

	public class EventGUI implements ActionListener{

		FlowLayout flow = new FlowLayout(FlowLayout.LEFT);
		LinkedList actionList = new LinkedList();

		private int sizeX;
		private int sizeY;
		private String eventName;

		private JPanel listedActions;
		private JLabel jEventName;
		
		public List<JBrailleCell> cells;

		private EventGUI(String eventName) {

			cells = new ArrayList<JBrailleCell>();
			
			for (int i =0; i<ScenarioCreatorManager.getNumCells(); i++) {
				cells.add(new JBrailleCell((i+1)));
			}
			
			//------------LISTED ACTIONS-------------------\\
			this.eventName = eventName;
			sizeX = eventEditor.getWidth();
			sizeY = eventEditor.getHeight();

			flow.setHgap(10);
			listedActions = new JPanel(flow);	
			listedActions.setSize(sizeX,(int)(sizeY * 0.75));
			listedActions.setPreferredSize(listedActions.getSize());

			jEventName = new JLabel("Event Name: " + eventName);
			jEventName.setPreferredSize(new Dimension(listedActions.getSize().width,20));

			actionOptions.setSize(sizeX,(int)(sizeY * 0.25));
			actionOptions.setPreferredSize(actionOptions.getSize());

			JActionNode newNode = new JActionNode();
			actionList.add(newNode);
			listedActions.add(jEventName);
			listedActions.add(newNode);

		}

		private String getEventName() {
			return eventName;
		}

		private LinkedList getActionList() {
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
			actionList.remove(nodeToRemove.getIndex());		
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
		}

		private void repaintEditor() {eventEditor.repaint();}

		private void addToFrame() {
			eventEditor.removeAll();
			eventEditor.add(listedActions, BorderLayout.NORTH);
			eventEditor.add(actionOptions, BorderLayout.CENTER);
			eventEditor.validate();
			activeEvent = this;
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

			public JPanel actionConfigure;

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

	
	
}

