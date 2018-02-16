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

	private static List<EventGUI> eventsList;
	private static EventGUI activeEvent;
	private static ScenarioCreatorGUI instance;
	

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
		private static JLabel activeEvent;
		private static JComboBox<EventGUI> eventComboBox;

		private EventsListGUI() {
			addEvent = new JButton("New Event");
			addEvent.addActionListener(this);
			addEvent.setSize((int)(eventListPanel.getSize().height * 0.8), (int)(eventListPanel.getSize().height * 0.8));
			addEvent.setPreferredSize(addEvent.getPreferredSize());		
			MainFrame.editJButton(addEvent);

			activeEvent = new JLabel("CURRENTLY EDITING: ");

			eventComboBox = new JComboBox<EventGUI>();
			eventComboBox.addActionListener(this);
			eventComboBox.setBackground(Color.WHITE);

			eventListPanel.add(addEvent);
			eventListPanel.add(activeEvent);
			eventListPanel.add(eventComboBox);
		}

		
		

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == addEvent) {				
				EventGUI newEvent =	ScenarioCreatorGUI.createNewEvent();
				eventsList.add(newEvent);
				eventComboBox.addItem(newEvent);
				newEvent.addToFrame();
			}


		}



	}

	private class EventGUI implements ActionListener{

		FlowLayout flow = new FlowLayout(FlowLayout.LEFT);
		
		LinkedList actionList = new LinkedList();

		private int sizeX;
		private int sizeY;

		private JPanel listedActions;
		private JPanel actionOptions;
		
		private JButton addAction;
		



		private EventGUI() {
			
			sizeX = eventEditor.getWidth();
			sizeY = eventEditor.getHeight();
			
			flow.setHgap(10);
			listedActions = new JPanel(flow);	
			listedActions.setSize(sizeX,sizeY/3);
			listedActions.setPreferredSize(listedActions.getSize());
			
			actionOptions = new JPanel();
			actionOptions.setLayout(flow);
			actionOptions.setSize(sizeX,sizeY/2);
			actionOptions.setPreferredSize(actionOptions.getSize());
			
			/*
			addAction = new JButton("New Event +");
			addAction.addActionListener(this);
			addAction.setSize((int)(listedActions.WIDTH * 0.2), (int)(listedActions.HEIGHT * 0.2));
			addAction.setPreferredSize(addAction.getPreferredSize());		
			
			MainFrame.editJButton(addAction);
			*/
			
			JActionNode newNode = new JActionNode();
			actionList.add(newNode);
			listedActions.add(newNode);
		}
		
		private LinkedList getActionList() {
			return actionList;
		}
		
		private void addAction(int indexToAdd) {
			System.out.println(actionList.size() + ", " + indexToAdd);
			
			JActionNode newNode = new JActionNode();
			newNode.setIndex(indexToAdd);			
			if (newNode.getIndex() == actionList.size()) {
				actionList.add(newNode);
			}else if(newNode.getIndex() < actionList.size()){
				actionList.add(indexToAdd, newNode);
			}else {
				throw new IllegalArgumentException("INVALID INDEX ON NEW ACTION NODE");
			}
			
			listedActions.removeAll();
			
			Iterator<JActionNode> it = actionList.iterator();
			while (it.hasNext()) {
				listedActions.add(it.next());
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
			return "event";
		}
		
		
		
		private class JActionNode extends JPanel implements ActionListener{
			
			private int index = 0;
			
			private JPanel buttons;
			private JComboBox actionList;
			private JButton addAction;
			private JButton removeAction;
			
			private int sizeX;
			private int sizeY;
			
			private JActionNode(){
				
				sizeX = MainFrame.getSizeX()/7;
				sizeY = MainFrame.getSizeY()/7;
				
				buttons = new JPanel();
				actionList = new JComboBox(ScenarioCreatorManager.userCommandList);
				addAction = new JButton("+");
				removeAction = new JButton("-");
				
				this.add(actionList);
				this.add(buttons);
				buttons.add(addAction);
				buttons.add(removeAction);	
				
				addAction.addActionListener(this);
				removeAction.addActionListener(this);
				
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
			}
			
			public void actionPerformed(ActionEvent e) {

				if (e.getSource().equals(addAction)){
					activeEvent.addAction(this.index+1);
				}
				
			}
						
			
		}
		
	}

	private static EventGUI createNewEvent() {		
		return instance.new EventGUI();		
	}

}

