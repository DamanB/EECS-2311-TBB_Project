package enamel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ScenarioCreatorGUI {
	//Contains hotkeys
	private static JPanel mainPanel;
	private static JPanel leftBorder;
	private static JPanel northBorder;
	private static JPanel editor;
	private static JPanel controls;
	public static JPanel configuration;

	public static String ScenarioTitle;
	public static int numCells = 1;
	public static int numButtons = 1;

	public static boolean isActive = false;

	public static LinkedList<EditorGUI.JNode> nodes = new LinkedList<EditorGUI.JNode>();

	private static controlGUI controlClass;
	public static EditorGUI editorClass;

	private static ScenarioCreatorGUI instance;

	public static String[] userCommandList = { "Checkpoint", "Text to Speech", // DONE
			"Play Audio", // DONE
			"Pause", // DONE
			"Display Pins on Braille Cell", // DONE
			"Display Word with Braille Cells", // DONE
			"Display Character on Braille Cell", // DONE
			"Clear All Braille Cells", // DONE
			"Clear Specific Braille Cell", // DONE
			"Lower Pin on Cell", // DONE
			"Raise Pin on Cell", // DONE
			"Repeat Text with Button Click", // DONE
			"Go to Checkpoint", // DONE
			"Go to Checkpoint with Button Click", // DONE
			"Reset Button Configurations", // DONE
			"User Input" // DONE
	};

	private ScenarioCreatorGUI() {

		BorderLayout layout = new BorderLayout();
		FlowLayout flow = new FlowLayout(FlowLayout.LEFT);
		flow.setHgap(15);

		mainPanel = new JPanel(layout);
		MainFrame.getMainPanel().setFocusable(true);
		MainFrame.getMainPanel().addKeyListener(new Hotkeys());
		editor = new JPanel();
		controls = new JPanel();
		leftBorder = new JPanel();
		northBorder = new JPanel();
		configuration = new JPanel();

		editor.setLayout(new BoxLayout(editor, BoxLayout.PAGE_AXIS));

		mainPanel.setBackground(MainFrame.primColor);
		editor.setBackground(Color.white);
		controls.setBackground(MainFrame.primColor);
		leftBorder.setBackground(MainFrame.primColor);
		northBorder.setBackground(MainFrame.primColor);
		configuration.setBackground(MainFrame.primColor);

		editor.setSize((int) (MainFrame.getSizeX() * 0.78), (int) (MainFrame.getSizeY() * 0.70));
		controls.setSize((int) (MainFrame.getSizeX() * 0.2), (int) (MainFrame.getSizeY()));
		leftBorder.setSize((int) (MainFrame.getSizeX() * 0.02), (int) (MainFrame.getSizeY() * 0.80));
		northBorder.setSize(MainFrame.getSizeX(), (int) (MainFrame.getSizeY() * 0.04));
		configuration.setSize((int) (MainFrame.getSizeX() * 0.78), (int) (MainFrame.getSizeY() * 0.30));

		controls.setPreferredSize(controls.getSize());
		leftBorder.setPreferredSize(leftBorder.getSize());
		northBorder.setPreferredSize(northBorder.getSize());
		configuration.setPreferredSize(configuration.getSize());

		mainPanel.setSize(MainFrame.dimension);
		mainPanel.add(configuration, BorderLayout.SOUTH);
		mainPanel.add(controls, BorderLayout.EAST);
		mainPanel.add(editor, BorderLayout.CENTER);
		mainPanel.add(leftBorder, BorderLayout.WEST);
		mainPanel.add(northBorder, BorderLayout.NORTH);
		controlClass = new controlGUI();
		editorClass = new EditorGUI();	
		editor.setFocusable(true);
		editor.addKeyListener(new Hotkeys());


	}

	private static class controlGUI implements ActionListener, ChangeListener, ListSelectionListener {

		private static JList<String> listOfCommands;
		private static JTextField selected;

		private static JLabel optionsTitle;
		private static JButton backButton;
		private static JButton buildButton;
		private static JButton openPlayer;
		private static JButton openSoundRecorder;
		public static JSpinner scenarioIndex;
		private static JLabel indexText;

		private controlGUI() {

			JPanel commands = new JPanel();
			commands.setSize(controls.getSize().width, 200);
			commands.setPreferredSize(commands.getSize());
			commands.setBackground(MainFrame.primColor);
			listOfCommands = new JList<String>(userCommandList);
			listOfCommands.setBorder(new LineBorder(MainFrame.primColor.darker(), 2));
			listOfCommands.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			listOfCommands.setSelectedIndex(0);
			listOfCommands.addListSelectionListener(this);
			listOfCommands.setFocusable(true);
			listOfCommands.addKeyListener(new Hotkeys());
			JScrollPane scroll = new JScrollPane(listOfCommands);

			createSelectedGUI();

			commands.add(selected);
			commands.add(scroll);
			controls.add(commands);

			optionsTitle = new JLabel("Build", JLabel.CENTER);
			optionsTitle.setFont(new Font("calibri", Font.BOLD, 20));
			optionsTitle.setForeground(Color.black);
			backButton = new JButton("Back to Menu");
			backButton.setSize(150, 40);
			backButton.setPreferredSize(backButton.getSize());
			buildButton = new JButton("Save Scenario");
			buildButton.setSize(200, 40);
			buildButton.setPreferredSize(buildButton.getSize());
			openSoundRecorder = new JButton("Sound Recorder");
			openSoundRecorder.setSize(180, 40);
			openSoundRecorder.setPreferredSize(openSoundRecorder.getSize());
			Font buttonFont = new Font("cailibri", Font.PLAIN, 14);
			backButton.setFont(buttonFont);
			buildButton.setFont(buttonFont);
			openSoundRecorder.setFont(buttonFont);
			backButton.addActionListener(this);
			buildButton.addActionListener(this);
			openSoundRecorder.addActionListener(this);

			openPlayer = new JButton("Open Player");
			openPlayer.setSize(165, 40);
			openPlayer.setPreferredSize(openPlayer.getSize());
			openPlayer.addActionListener(this);
			openPlayer.setFont(buttonFont);

			MainFrame.editJButton(backButton);
			MainFrame.editJButton(buildButton);
			MainFrame.editJButton(openPlayer);
			MainFrame.editJButton(openSoundRecorder);

			JPanel indexer = new JPanel();
			indexer.setBackground(MainFrame.primColor);
			indexer.setSize(controls.getSize().width, 30);
			indexer.setPreferredSize(indexer.getSize());
			indexText = new JLabel("File Name: Scenario_");
			SpinnerModel number = new SpinnerNumberModel();
			scenarioIndex = new JSpinner(number);
			scenarioIndex.setPreferredSize(new Dimension(60, 20));
			scenarioIndex.setValue(1);
			scenarioIndex.addChangeListener(this);
			indexer.add(indexText);
			indexer.add(scenarioIndex);

			controls.add(indexer);
			controls.add(buildButton);
			controls.add(openSoundRecorder);
			controls.add(openPlayer);
			controls.add(backButton);

			northBorder.add(new JLabel("Hotkeys: Type Number to set Selected Action: "));
			northBorder.add(new JLabel("1 - Checkpoint"));
			northBorder.add(new JLabel("2 - Text To Speech"));
			northBorder.add(new JLabel("3 - Play Audio"));
			northBorder.add(new JLabel("4 - Display Pins on Braille Cell"));

			setButtonHotkeys(backButton);
			setButtonHotkeys(buildButton);
			setButtonHotkeys(openPlayer);
			setButtonHotkeys(openSoundRecorder);



		}

		private void createSelectedGUI() {
			selected = new JTextField("Selected: Checkpoint");
			selected.setHorizontalAlignment(JLabel.CENTER);
			selected.setBorder(new LineBorder(Color.BLACK, 1));
			selected.setBackground(Color.white);
			selected.setSize((int) (controls.getSize().width * 0.98), 30);
			selected.setPreferredSize(selected.getSize());
			selected.setEditable(false);
		}

		public void actionPerformed(ActionEvent e) {

			if (e.getSource().equals(backButton)) {

				int k = JOptionPane.showConfirmDialog(MainFrame.getMainPanel(),
						"Exiting will cause you to loose any changes made to this Scenario. Are you sure you wish to exit?",
						"Exit", JOptionPane.OK_CANCEL_OPTION);

				if (k == JOptionPane.OK_OPTION) {
					nodes.clear();
					MainFrame.changeScreen(ScenarioEditorMenu.getScreen());
				}
			} else if (e.getSource().equals(buildButton)) {
				build();
			} else if (e.getSource().equals(openPlayer)) {
				MainMenu.player();
			} else if (e.getSource().equals(openSoundRecorder)) {
				new JSoundRecorder();
			}

		}

		public void stateChanged(ChangeEvent e) {
			if (e.getSource().equals(scenarioIndex)) {
				if ((int) scenarioIndex.getValue() < 1) {
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
			boolean allGood = true;
			int index = 0;

			while (true) {
				// CREATE FILE
				String fileName = "Scenario_" + (int) scenarioIndex.getValue() + ".txt";
				Path path = Paths.get("./" + fileName);
				if ((Files.exists(path))) {
					int overwrite = JOptionPane.showConfirmDialog(MainFrame.getMainPanel(),
							fileName + " Already Exists. Do you Wish to Overwrite it?", "File Name Exists",
							JOptionPane.OK_CANCEL_OPTION);
					if (overwrite == JOptionPane.OK_OPTION) {
						original = path.toFile();
						file = new File(fileName);
						try {
							file.createNewFile();
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						break;
					}
				} else {
					file = new File(fileName);
					try {
						file.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				ScenarioCreatorManager sm = new ScenarioCreatorManager(file);
				// ADDING ACTIONS
				sm.setButtonNum(numButtons);
				sm.setCellNum(numCells);

				for (EditorGUI.JNode node : nodes) {
					if (index == 0) {
						sm.setTitle(node.getCheckpointName());
					} else if (node.getClass() == nodes.get(0).getClass()) {
						if ((!(node.getCheckpointName().matches("^[A-Za-z]+$")))) {
							allGood = false;
							break;
						}
						sm.nextQuestion();
						if (!(sm.addSkipPos(node.getCheckpointName().toUpperCase()))) {
							allGood = false;
							break;
						}
					} else {
						if (!(node.action.action.build(sm))) {
							allGood = false;
							break;
						}
					}
					index++;
				}
				if (!allGood) {
					JOptionPane.showMessageDialog(MainFrame.frame,
							"BUILD FAILED: You have an error at index - " + index);
					if (original == null) {
						file.delete();
					}
				} else {
					if (original != null) {
						original.delete();
					}

					if (sm.saveToFile()) {
						JOptionPane.showMessageDialog(MainFrame.getMainPanel(), "BUILD SUCCESSFUL", "Builder",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(MainFrame.getMainPanel(), "!!!BUILD UNSUCCESSFUL!!!", "Builder",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
				break;
			}

		}

		public static int getSelectedAction() {
			return listOfCommands.getSelectedIndex();
		}

		public static void setSelectedAction(int index) {
			listOfCommands.setSelectedIndex(index);
		}

	}

	public static class EditorGUI implements ChangeListener {

		private static JNodeConfig config;
		private static JScrollPane scroll;

		private static JPanel button;
		private static JPanel braille;
		public static JSpinner numberOfBraille;
		public static JSpinner numberOfButtons;

		private EditorGUI() {

			scroll = new JScrollPane(editor, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			mainPanel.add(scroll);
			config = new JNodeConfig();
			createBrailleGUI();
			createButtonGUI();

			JCheckpointNode n = new JCheckpointNode(0, true);
			config.connected = n;
			editor.add(n);
			nodes.add(n);
			scroll.setFocusable(true);
			scroll.addKeyListener(new Hotkeys());
			refreshEditor();

		}

		private void createBrailleGUI() {
			braille = new JPanel(new FlowLayout(FlowLayout.LEFT));
			braille.setSize(editor.getSize().width, 60);
			braille.setPreferredSize(braille.getSize());
			braille.add(new JLabel("Select The Amount of Braille Cells: "));
			braille.setBackground(Color.white);
			SpinnerModel number = new SpinnerNumberModel();
			numberOfBraille = new JSpinner(number);
			numberOfBraille.setSize(60, 20);
			numberOfBraille.setPreferredSize(numberOfBraille.getSize());
			numberOfBraille.setValue(1);
			numberOfBraille.addChangeListener(this);
			braille.add(numberOfBraille);
			editor.add(braille);
		}

		private void createButtonGUI() {
			button = new JPanel(new FlowLayout(FlowLayout.LEFT));
			button.setSize(editor.getSize().width, 60);
			button.setPreferredSize(button.getSize());
			button.add(new JLabel("Select The Amount of Buttons: "));
			button.setBackground(Color.white);
			SpinnerModel number = new SpinnerNumberModel();
			numberOfButtons = new JSpinner(number);
			numberOfButtons.setPreferredSize(new Dimension(60, 20));
			numberOfButtons.setValue(1);
			numberOfButtons.addChangeListener(this);
			button.add(numberOfButtons);
			editor.add(button);
		}

		private static void addCheckpointNode(int index) {
			JCheckpointNode n = editorClass.new JCheckpointNode(index, false);
			config.setConnected(n);

			if (index == nodes.size()) {
				nodes.addLast(n);
			} else {
				nodes.add(index, n);
			}

		}

		private static void addActionNode(int index) {
			JActionNode n = editorClass.new JActionNode(index);
			config.setConnected(n);

			if (index == nodes.size()) {
				nodes.addLast(n);
			} else {
				nodes.add(index, n);
			}
		}

		public static void addCheckpointNode(JActionConfigure config, String name, boolean isTitle) {
			JCheckpointNode n = editorClass.new JCheckpointNode(config, name, isTitle);

			if (isTitle) {
				nodes.removeFirst();
				nodes.addFirst(n);
			} else {
				nodes.addLast(n);
			}
			refreshEditor();
		}

		public static void addActionNode(JActionConfigure config) {
			JActionNode n = editorClass.new JActionNode(config);
			nodes.addLast(n);
			refreshEditor();
		}

		private static void createNode(int index) {
			if (controlGUI.getSelectedAction() <= 0) {
				addCheckpointNode(index);
			} else {
				addActionNode(index);
			}
			refreshEditor();
		}

		private static void refreshEditor() {
			editor.removeAll();
			editor.add(braille);
			editor.add(button);

			int count = 0;
			for (JNode n : nodes) {
				n.setIndex(count);
				editor.add(n);
				count++;
			}

			if (nodes.size() < 10) {
				int amountToFill = 10 - nodes.size();
				for (int i = 0; i < amountToFill; i++) {
					JPanel p = new JPanel();
					p.setBackground(null);
					editor.add(p);
				}
			}

			editor.validate();
			editor.setVisible(true);
			editor.repaint();
		}

		public void stateChanged(ChangeEvent e) {
			if (e.getSource().equals(numberOfBraille)) {
				if ((int) numberOfBraille.getValue() < 1) {
					numberOfBraille.setValue(1);
				}
				numCells = (int) numberOfBraille.getValue();
			} else if (e.getSource().equals(numberOfButtons)) {
				if ((int) numberOfButtons.getValue() < 1) {
					numberOfButtons.setValue(1);
				}
				numButtons = (int) numberOfButtons.getValue();
			}
		}	

		// NODES
		private class JNodeConfig extends JPanel implements ActionListener {

			private JNode connected;
			private JButton moveUp, moveDown, delete;

			private JNodeConfig() {
				this.setBackground(MainFrame.primColor);
				this.setBorder(new LineBorder(Color.BLACK, 1));
				moveUp = new JButton("Shift Up");
				moveDown = new JButton("Shift Down");
				delete = new JButton("Delete");

				moveUp.addActionListener(this);
				moveDown.addActionListener(this);
				delete.addActionListener(this);

				this.add(moveUp);
				this.add(moveDown);
				this.add(delete);
				this.addKeyListener(new Hotkeys());

			}

			public void setConnected(JNode node) {
				this.setVisible(false);
				connected.remove(this);
				connected.setBackground(Color.white);
				connected = node;
				connected.setBackground(new Color(200, 197, 217));
				connected.add(this);
				connected.validate();
				if (connected.index == 0) {
					delete.setEnabled(false);
					moveUp.setEnabled(false);
					moveDown.setEnabled(false);
				} else {
					delete.setEnabled(true);
					moveUp.setEnabled(true);
					moveDown.setEnabled(true);
				}
				this.setVisible(true);
				this.repaint();
				connected.repaint();
				editor.repaint();
			}

			public JNode getConnected() {
				return connected;
			}

			public void actionPerformed(ActionEvent e) {
				if (e.getSource().equals(moveUp)) {
					if (connected.getIndex() - 1 > 0) {
						connected.swapWith(nodes.get(connected.getIndex() - 1));
					}
				} else if (e.getSource().equals(moveDown)) {
					if (connected.getIndex() < nodes.size() - 1)
						connected.swapWith(nodes.get(connected.getIndex() + 1));
				} else if (e.getSource().equals(delete)) {
					nodes.remove(connected);
					refreshEditor();
				}

			}

		}

		public class JNode extends JPanel implements ActionListener {

			private JLabel header;
			private JButton add, configure;
			public int index;
			private JActionConfigure action;

			private JNode(int index) {
				this.index = index;
				createJNodeGUI();
				action = new JActionConfigure(controlGUI.getSelectedAction());
				this.setFocusable(true);
				this.addKeyListener(new Hotkeys());
				setButtonHotkeys(configure);

			}

			private JNode(JActionConfigure config) {
				this.index = nodes.size() - 1;
				createJNodeGUI();
				this.action = config;
				this.setFocusable(true);
				this.addKeyListener(new Hotkeys());
				setButtonHotkeys(configure);
			}

			private void createJNodeGUI() {
				this.setSize(editor.getSize().width, 60);
				this.setPreferredSize(this.getSize());
				this.setBackground(Color.WHITE);
				this.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

				header = new JLabel();
				header.setBackground(null);
				header.setHorizontalAlignment(JLabel.LEFT);
				this.add(header, BorderLayout.CENTER);
				configure = new JButton("Configure");
				configure.addActionListener(this);
				add = new JButton("Add Selected Below");
				add.addActionListener(this);
			}

			public void actionPerformed(ActionEvent e) {
				if (e.getSource().equals(configure)) {
					EditorGUI.config.setConnected(this);
					configuration.removeAll();
					configuration.add(EditorGUI.config.connected.action);
					configuration.validate();
					configuration.repaint();
				} else if (e.getSource().equals(add)) {
					EditorGUI.createNode(this.index + 1);
					UsageLogger.usageIncrement(UsageLogger.UsageElements.values()[controlGUI.getSelectedAction()]);
				}
			}

			public void setIndex(int index) {
			}

			public int getIndex() {
				return index;
			}

			private void swapWith(JNode otherNode) {
				editor.setVisible(false);
				editor.remove(this);
				editor.remove(otherNode);
				int oldIndex = this.index;
				int newIndex = otherNode.getIndex();
				this.setIndex(newIndex);
				otherNode.setIndex(oldIndex);

				nodes.set(newIndex, this);
				nodes.set(oldIndex, otherNode);

				refreshEditor();
				editor.setVisible(true);
			}

			public String getCheckpointName() {
				return "Action";
			}

			public Component getAdd() {
				return add;
			}

			public Component getConfigure() {
				return configure;
			}

		}

		private class JCheckpointNode extends JNode implements ActionListener, FocusListener {

			private String checkpointInst;
			private JTextField checkpointName;
			private boolean isTitle;

			private JCheckpointNode(int index, boolean isTitle) {
				super(index);
				createCheckpointNode(isTitle);
			}

			private void createCheckpointNode(boolean isTitle) {
				if (isTitle) {
					checkpointInst = "Enter this Scenarios Title:";
					super.action = new JActionConfigure(16);
				} else {
					checkpointInst = "Enter a Checkpoint Name";
				}
				this.isTitle = isTitle;
				super.header.setText(super.index + " - " + checkpointInst);
				checkpointName = new JTextField();
				checkpointName.setSize(250, 20);
				checkpointName.setPreferredSize(checkpointName.getSize());
				checkpointName.addFocusListener(this);
				super.add(checkpointName);
				super.add(super.configure);
				super.add(super.add);
			}

			private JCheckpointNode(JActionConfigure config, String name, boolean isTitle) {
				super(config);
				createCheckpointNode(isTitle);
				this.checkpointName.setText(name);
			}

			@Override
			public void setIndex(int index) {
				super.index = index;
				super.header.setText(super.index + " - " + checkpointInst);
			}

			@Override
			public String getCheckpointName() {
				return this.checkpointName.getText();
			}

			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (e.getSource().equals(checkpointName)) {
					if (!isTitle) {
						if (!(checkpointName.getText().matches("^[A-Za-z]+$"))) {
							JOptionPane.showMessageDialog(MainFrame.frame,
									"Checkpoint Name can not have any numbers or spaces", "Invalid Name",
									JOptionPane.OK_OPTION);
							checkpointName.setText("");
						}
					}
				}
			}
		}

		private class JActionNode extends JNode implements ActionListener {

			private String actionName;

			private JActionNode(int index) {
				super(index);
				createJActionGUI();
			}

			private JActionNode(JActionConfigure config) {
				super(config);
				createJActionGUI();
			}

			private void createJActionGUI() {
				actionName = super.action.action.toString();
				super.header.setText("\t" + super.index + " - " + actionName);
				super.header.setSize(260, 60);
				super.header.setPreferredSize(super.header.getSize());
				super.add(super.configure);
				super.add(super.add);
			}

			@Override
			public void setIndex(int index) {
				super.index = index;
				super.header.setText("\t             " + super.index + " - " + actionName);
			}

			@Override
			public String getCheckpointName() {
				return "Action";
			}
		}

	}

	public static class Hotkeys implements KeyListener{

		@Override
		public void keyPressed(KeyEvent k) {
			if (k.getKeyCode() == KeyEvent.VK_1) {	
				System.out.println("Hotkey 1 Clicked");
				controlGUI.setSelectedAction(0);
			}else if (k.getKeyCode() == KeyEvent.VK_2) {
				System.out.println("Hotkey 2 Clicked");
				controlGUI.setSelectedAction(1);
			}else if (k.getKeyCode() == KeyEvent.VK_3) {
				System.out.println("Hotkey 3 Clicked");
				controlGUI.setSelectedAction(2);
			}else if (k.getKeyCode() == KeyEvent.VK_4) {
				System.out.println("Hotkey 4 Clicked");
				controlGUI.setSelectedAction(4);
			}
		}

		@Override
		public void keyReleased(KeyEvent k) {
		}

		@Override
		public void keyTyped(KeyEvent k) {

		}


	}
	/////////////////////////////////// METHODS///////////////////////////////////////////

	public static JPanel getMainPanel() {
		return mainPanel;
	}

	public static JPanel getScreen() {
		instance = new ScenarioCreatorGUI();
		return ScenarioCreatorGUI.mainPanel;
	}

	public static Component getBuild() {
		return controls.getComponent(2);
	}

	public static Component getRecord() {
		return controls.getComponent(3);
	}

	public static Component getPlayer() {
		return controls.getComponent(4);
	}

	public static Component getBack() {
		return controls.getComponent(5);
	}

	public static Component getTextBox() {
		return EditorGUI.numberOfBraille;
	}

	private static void setButtonHotkeys(JButton button) {
		button.setFocusable(true);
		button.addKeyListener(new Hotkeys());
	}
}
