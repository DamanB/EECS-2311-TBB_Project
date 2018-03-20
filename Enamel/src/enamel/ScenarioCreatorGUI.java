package enamel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ScenarioCreatorGUI {

	private static JPanel mainPanel;
	private static JPanel leftBorder;
	private static JPanel northBorder;
	private static JPanel editor;
	private static JPanel controls;
	public static JPanel configuration;

	public static String ScenarioTitle;
	public static int numCells = 0;
	public static int numButtons = 0;

	public static LinkedList<EditorGUI.JNode> nodes = new LinkedList<EditorGUI.JNode>();
	
	private static controlGUI controlClass;
	private static EditorGUI editorClass;

	//public static List<> eventsList;

	private static ScenarioCreatorGUI instance;

	public static String[] userCommandList = { 
			"Checkpoint",
			"Text to Speech", // DONE
			"Play Audio", // DONE
			"Pause", // DONE
			"Display Pins on Braille Cell", // DONE
			"Display Word with Braille Cells", // DONE
			"Display Character on Braille Cell", // DONE
			"Clear All Braille Cells", // DONE
			"Clear Specific Braille Cell", // DONE
			"Lower Pins on Cell", // DONE
			"Raise Pins on Cell", // DONE
			"Repeat Text with Button Click", // DONE
			"Go to Checkpoint", // DONE
			"Go to Checkpoint with Button Click", // DONE
			"Reset Button Configurations", // DONE
			"User Input" // DONE
	};

	private ScenarioCreatorGUI() {

		//eventsList = new ArrayList<>();

		BorderLayout layout = new BorderLayout();
		FlowLayout flow = new FlowLayout(FlowLayout.LEFT);
		flow.setHgap(15);
		
		mainPanel = new JPanel(layout);
		editor = new JPanel();
		controls = new JPanel();
		leftBorder = new JPanel();
		northBorder = new JPanel();
		configuration = new JPanel();
		
		editor.setLayout(new BoxLayout(editor, BoxLayout.PAGE_AXIS));

		/*configuration.setBorder(new LineBorder(MainFrame.primColor.darker(), 2));
		controls.setBorder(new LineBorder(MainFrame.primColor.darker(), 2));
		leftBorder.setBorder(new LineBorder(MainFrame.primColor.darker(), 2));		
		northBorder.setBorder(new LineBorder(MainFrame.primColor.darker(), 2));		
		 */

		mainPanel.setBackground(MainFrame.primColor);
		editor.setBackground(Color.white);
		controls.setBackground(MainFrame.primColor);
		leftBorder.setBackground(MainFrame.primColor);
		northBorder.setBackground(MainFrame.primColor);
		configuration.setBackground(MainFrame.primColor);

		editor.setSize((int) (MainFrame.getSizeX() * 0.78), (int) (MainFrame.getSizeY() * 0.80));
		controls.setSize((int) (MainFrame.getSizeX() * 0.2), (int) (MainFrame.getSizeY()));
		leftBorder.setSize((int) (MainFrame.getSizeX() * 0.02), (int) (MainFrame.getSizeY() * 0.80));
		northBorder.setSize(MainFrame.getSizeX(), (int) (MainFrame.getSizeY() * 0.02));
		configuration.setSize((int) (MainFrame.getSizeX() * 0.78), (int) (MainFrame.getSizeY() * 0.20));

		//editor.setPreferredSize(editor.getSize());
		controls.setPreferredSize(controls.getSize());
		leftBorder.setPreferredSize(leftBorder.getSize());
		northBorder.setPreferredSize(northBorder.getSize());
		configuration.setPreferredSize(configuration.getSize());

		mainPanel.setSize(MainFrame.dimension);
		mainPanel.add(controls, BorderLayout.EAST);
		mainPanel.add(editor, BorderLayout.CENTER);
		mainPanel.add(leftBorder, BorderLayout.WEST);
		mainPanel.add(configuration, BorderLayout.SOUTH);
		mainPanel.add(northBorder, BorderLayout.NORTH);
		controlClass = new controlGUI();
		editorClass = new EditorGUI();
	}

	private ScenarioCreatorGUI(File file) {
		this();	
	}

	private static class controlGUI implements ActionListener, ChangeListener, ListSelectionListener {

		private static JLabel selectActionText;
		private static JList<String> listOfCommands;
		private static JTextField selected;

		private static JLabel optionsTitle;
		private static JButton backButton;
		private static JButton buildButton;
		public static JSpinner scenarioIndex;
		private static JLabel indexText;

		private controlGUI() {

			JPanel commands = new JPanel();
			commands.setSize(controls.getSize().width,200);
			commands.setPreferredSize(commands.getSize());
			commands.setBackground(MainFrame.primColor);
			selectActionText = new JLabel("Select to Add");
			selectActionText.setFont(new Font("Calibri", Font.BOLD, 20));
			selectActionText.setHorizontalAlignment(JLabel.CENTER);
			selectActionText.setForeground(Color.black);
			listOfCommands = new JList<String>(userCommandList);
			listOfCommands.setBorder(new LineBorder(MainFrame.primColor.darker(), 2));	
			listOfCommands.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			listOfCommands.setSelectedIndex(0);
			listOfCommands.addListSelectionListener(this);
			JScrollPane scroll = new JScrollPane(listOfCommands);

			createSelectedGUI();

			commands.add(selectActionText);
			commands.add(selected);
			commands.add(scroll);
			controls.add(selectActionText);
			controls.add(commands);		


			optionsTitle = new JLabel("Build", JLabel.CENTER);
			optionsTitle.setFont(new Font("calibri", Font.BOLD, 20));
			optionsTitle.setForeground(Color.black);
			backButton = new JButton("Back to Menu");
			backButton.setSize(150, 40);
			backButton.setPreferredSize(backButton.getSize());
			buildButton = new JButton("Build Project!");
			buildButton.setSize(200, 40);
			buildButton.setPreferredSize(buildButton.getSize());
			Font buttonFont = new Font("cailibri", Font.PLAIN, 14);
			backButton.setFont(buttonFont);
			buildButton.setFont(buttonFont);
			backButton.addActionListener(this);
			buildButton.addActionListener(this);
			MainFrame.editJButton(backButton);
			MainFrame.editJButton(buildButton);

			JPanel indexer = new JPanel();
			indexer.setBackground(MainFrame.primColor);
			indexer.setSize(controls.getSize().width, 30);
			indexer.setPreferredSize(indexer.getSize());
			indexText = new JLabel("File Name: Scenario_");
			SpinnerModel number = new SpinnerNumberModel();
			scenarioIndex = new JSpinner(number);
			scenarioIndex.setPreferredSize(new Dimension(60,20));
			scenarioIndex.setValue(1);
			scenarioIndex.addChangeListener(this);
			indexer.add(indexText);
			indexer.add(scenarioIndex);

			controls.add(optionsTitle);
			controls.add(indexer);
			controls.add(buildButton);
			controls.add(backButton);			
		}

		private void createSelectedGUI() {
			selected = new JTextField("Selected: Checkpoint");
			selected.setHorizontalAlignment(JLabel.CENTER);
			selected.setBorder(new LineBorder(Color.BLACK,1));
			selected.setBackground(Color.white);
			selected.setSize((int)(controls.getSize().width*0.98), 30);
			selected.setPreferredSize(selected.getSize());
			selected.setEditable(false);
		}

		public void actionPerformed(ActionEvent e) {

			if (e.getSource().equals(backButton)) {

				int k = JOptionPane.showConfirmDialog(MainFrame.getMainPanel(),
						"Exiting will cause you to loose any changes made to this Scenario. Are you sure you wish to exit?",
						"Exit", JOptionPane.OK_CANCEL_OPTION);

				if (k == JOptionPane.OK_OPTION) {
					MainFrame.changeScreen(ScenarioEditorMenu.getScreen());
				}
			} else if (e.getSource().equals(buildButton)) {
				build();
			}

		}

		public void stateChanged(ChangeEvent e) {
			if (e.getSource().equals(scenarioIndex)) {
				if ((int)scenarioIndex.getValue() < 1) {
					scenarioIndex.setValue(1);
				}
			}
		}

		public void valueChanged(ListSelectionEvent e) {
			if (e.getSource().equals(listOfCommands)) {
				selected.setText("Selected: " + listOfCommands.getSelectedValue());
			}
		}

		private void build() {

			File file;	
			File original = null;

			while (true) {
				// CREATE FILE
				if ((int) controlGUI.scenarioIndex.getValue() <= 0) {
					JOptionPane.showMessageDialog(MainFrame.getMainPanel(),
							"Please select an index greater than 0 for the Scenario name", "Invalid Index",
							JOptionPane.INFORMATION_MESSAGE);
					break;
				}
				String fileName = "Scenario_" + (int) scenarioIndex.getValue() + ".txt";
				Path path = Paths.get("./" + fileName);
				if ((Files.exists(path))) {
					int overwrite = JOptionPane.showConfirmDialog(MainFrame.getMainPanel(), fileName + " Already Exists. Do you Wish to Overwrite it?", "File Name Exists", JOptionPane.OK_CANCEL_OPTION);
					if (overwrite==JOptionPane.OK_OPTION) {
						original = path.toFile();
						file = new File(fileName);
						try {
							file.createNewFile();
						}catch (IOException e){
							e.printStackTrace();
						}
					}else {
						break;
					}
				}else {
					file = new File(fileName);
					try {
						file.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				ScenarioCreatorManager sm = new ScenarioCreatorManager(file);
				// ADDING ACTIONS
				sm.setTitle(ScenarioTitle);
				sm.setButtonNum(numButtons);
				sm.setCellNum(numCells);

				//TODO BUILD

				break;
			}

		}

		public static int getSelectedAction() {
			return listOfCommands.getSelectedIndex();
		}

	}

	private static class EditorGUI implements ChangeListener, ActionListener{

		private static JNodeConfig config;
		private static JScrollPane scroll;
		
		private static JSpinner numberOfBraille;
		private static JSpinner numberOfButtons;
		private static JTextField scenarioTitle;
		private static JButton add;
		
		private EditorGUI(){
			
			scroll = new JScrollPane(editor, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			mainPanel.add(scroll);			
			config = new JNodeConfig();
			createBrailleGUI();
			createButtonGUI();
			
			JCheckpointNode n = new JCheckpointNode(1, true);
			config.connected=n;
			editor.add(n);
		}
		
		private void createBrailleGUI() {
			JPanel braille = new JPanel(new FlowLayout(FlowLayout.LEFT));
			braille.setSize(editor.getSize().width,60);
			braille.setPreferredSize(braille.getSize());
			braille.add(new JLabel("	Select The Amount of Buttons: "));
			braille.setBackground(Color.white);
			SpinnerModel number = new SpinnerNumberModel();
			numberOfBraille = new JSpinner(number);
			numberOfBraille.setPreferredSize(new Dimension(60,20));
			numberOfBraille.setValue(1);
			numberOfBraille.addChangeListener(this);
			braille.add(numberOfBraille);
			editor.add(braille);
		}

		private void createButtonGUI() {
			JPanel button = new JPanel(new FlowLayout(FlowLayout.LEFT));
			button.setSize(editor.getSize().width,60);
			button.setPreferredSize(button.getSize());
			button.add(new JLabel("		Select The Amount of Buttons: "));
			button.setBackground(Color.white);
			SpinnerModel number = new SpinnerNumberModel();
			numberOfButtons = new JSpinner(number);
			numberOfButtons.setPreferredSize(new Dimension(60,20));
			numberOfButtons.setValue(1);
			numberOfButtons.addChangeListener(this);
			button.add(numberOfButtons);
			editor.add(button);
		}
		
		private static void addCheckpointNode(int index) {
			JCheckpointNode n = editorClass.new JCheckpointNode(index, false);
			config.setConnected(n);
			editor.add(n);
			n.repaint();
			editor.repaint();
			nodes.add(index, n);
		}
		
		private static void addActionNode(int cpIndex, int actionIndex) {
			JActionNode n = editorClass.new JActionNode(cpIndex,actionIndex);
			config.setConnected(n);
			editor.add(n);
			n.repaint();
			editor.repaint();
		}
		
		public void stateChanged(ChangeEvent e) {
			if (e.getSource().equals(numberOfBraille)) {
				if ((int)numberOfBraille.getValue()<1) {
					numberOfBraille.setValue(1);
				}
			}
			else if (e.getSource().equals(numberOfButtons)) {
				if ((int)numberOfButtons.getValue()<1) {
					numberOfButtons.setValue(1);
				}
			}			
		}
		
		
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(add)){	
				if (controlGUI.getSelectedAction() <= 0) {
					EditorGUI.addCheckpointNode(1);
				}else{
					EditorGUI.addActionNode(1, 1);						
				}
			}
		}
		
		//NODES
		private class JNodeConfig extends JPanel implements ActionListener{

			public JNode connected;
			private JButton moveUp, moveDown, add, delete;

			private JNodeConfig(){
				this.setBackground(MainFrame.primColor);
				this.setBorder(new LineBorder(Color.BLACK,1));
				moveUp = new JButton("Shift Up");
				moveDown = new JButton("Shift Down");
				add = new JButton("Add Selected Below");
				delete = new JButton("Delete");

				moveUp.addActionListener(this);
				moveDown.addActionListener(this);
				add.addActionListener(this);
				delete.addActionListener(this);

				this.add(moveUp);
				this.add(moveDown);
				this.add(add);
				this.add(delete);

			}

			public void actionPerformed(ActionEvent e) {
				if (e.getSource().equals(add)) {
					createNode();					
				}
				
			}

			private void createNode() {
				if (controlGUI.getSelectedAction() <= 0) {
					EditorGUI.addCheckpointNode(connected.checkpointIndex+1);
				}else{
					EditorGUI.addActionNode(connected.checkpointIndex, connected.actionIndex+1);						
				}
			}

			public void setConnected(JNode node) {
				this.setVisible(false);
				connected.remove(this);
				connected.validate();
				connected.repaint();
				connected = node;
				connected.add(this);
				connected.validate();
				this.setVisible(true);
				this.repaint();
				connected.repaint();
				editor.repaint();
			}			
			public JNode getConnected() {return connected;}

		}

		private class JNode extends JPanel implements ActionListener{

			private JButton header;
			public int checkpointIndex = 1;
			public int actionIndex = 0;
			private JActionConfigure action;

			private JNode() {	
				this.setSize(editor.getSize().width,60);
				this.setPreferredSize(this.getSize());
				this.setBackground(Color.WHITE);
				this.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));

				header = new JButton();
				header.setBackground(null);
				//header.setBorderPainted(false);
				header.setHorizontalAlignment(JLabel.LEFT);
				header.addActionListener(this);
				this.add(header, BorderLayout.CENTER);				
				action = new JActionConfigure(controlGUI.getSelectedAction());
				//TODO ADD TO NODES
								
			}

			public void actionPerformed(ActionEvent e) {
				if (e.getSource().equals(header)) {
					EditorGUI.config.setConnected(this);
				}
			}
			
		}

		private class JCheckpointNode extends JNode implements ActionListener{

			private String checkpointInst;
			private JTextField checkpointName;
			private LinkedList<JActionNode> attachedNodes;
			
			private JCheckpointNode(int index, boolean isTitle) {
				super();
				if (isTitle) {
					checkpointInst = "Enter a Title for this Scenario";
				}else {
					checkpointInst = "Enter a Checkpoint Name";
				}
				super.checkpointIndex = index;
				super.actionIndex = 0;
				super.header.setText(super.checkpointIndex + "." + super.actionIndex + " - " + checkpointInst);
				checkpointName = new JTextField();
				checkpointName.setSize(250,20);
				checkpointName.setPreferredSize(checkpointName.getSize());
				super.add(checkpointName);
				
				attachedNodes = new LinkedList<JActionNode>();
			}
			
			public String checkpointName() {
				return this.checkpointName.getText();
			}
			
			public void addActionNode(JActionNode node) {
				attachedNodes.add(node.actionIndex, node);
				int count = 1;
				for (JActionNode n : attachedNodes) {
					n.setActionIndex(count);
					count++;
				}			
			}

		}
				
		private class JActionNode extends JNode implements ActionListener{
			
			private String actionName;

			private JActionNode(int cpIndex, int actionIndex) {
				super();				
				actionName = super.action.action.getName();
				super.checkpointIndex = cpIndex;
				super.actionIndex = actionIndex;
				super.header.setText("\t             " + super.checkpointIndex + "." + super.actionIndex + " - " + actionName);
			}			
			public void setActionIndex(int index) {
				super.actionIndex = index;
				super.header.setText(("\t             " + super.checkpointIndex + "." + super.actionIndex + " - " + actionName));
			}
		}

	}


	///////////////////////////////////METHODS///////////////////////////////////////////


	public static JPanel getMainPanel() {
		return mainPanel;
	}

	public static JPanel getScreen() {
		instance = new ScenarioCreatorGUI();
		return instance.mainPanel;
	}

	public static JPanel getPreBuiltScreen(File file) {
		instance = new ScenarioCreatorGUI(file);
		return instance.mainPanel;
	}

}

