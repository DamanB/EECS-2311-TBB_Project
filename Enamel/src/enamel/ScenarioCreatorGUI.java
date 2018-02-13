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
	private static JPanel questionListPanel;
	private static JPanel questionEditor;
	private static JPanel controls;
	
	private static List<Question> questionList = new ArrayList<Question>();
	private static List<QuestionGUI> jQuestionList = new ArrayList<>();
	
	private static ScenarioCreatorGUI instance;
	
	
	private ScenarioCreatorGUI() {
				
		BorderLayout layout = new BorderLayout();
		mainPanel = new JPanel(layout);
		
		FlowLayout flow = new FlowLayout(FlowLayout.LEFT);
		flow.setVgap(40);
		flow.setHgap(40);
		questionListPanel = new JPanel(flow);
		questionEditor = new JPanel();
		controls = new JPanel();
		leftBorder = new JPanel();
		
		mainPanel.setBackground(MainFrame.primColor);
		questionListPanel.setBackground(MainFrame.primColor);
		questionEditor.setBackground(MainFrame.primColor);
		controls.setBackground(MainFrame.primColor);
		leftBorder.setBackground(MainFrame.primColor);

		questionListPanel.setSize((int)(MainFrame.getSizeX()*0.9),(int)(MainFrame.getSizeY() * 0.2));
		questionEditor.setSize((int)(MainFrame.getSizeX()*0.9), (int)(MainFrame.getSizeY() * 0.80));
		controls.setSize((int)(MainFrame.getSizeX() * 0.12),(int)(MainFrame.getSizeY() * 0.80));
		leftBorder.setSize((int)(MainFrame.getSizeX() * 0.02),(int)(MainFrame.getSizeY() * 0.80));
		
		questionListPanel.setPreferredSize(questionListPanel.getSize());
		questionEditor.setPreferredSize(questionEditor.getSize());
		controls.setPreferredSize(controls.getSize());
		leftBorder.setPreferredSize(new Dimension(leftBorder.getSize()));
		
		questionEditor.setLayout(new BoxLayout(questionEditor,BoxLayout.Y_AXIS));
		
		mainPanel.setSize(MainFrame.dimension);
		mainPanel.add(questionListPanel,layout.SOUTH);
		mainPanel.add(controls,layout.EAST);
		mainPanel.add(questionEditor,layout.CENTER);
		mainPanel.add(leftBorder, layout.WEST);
		
		QuestionGUI question1 = new QuestionGUI();
		QuestionListGUI qList = new QuestionListGUI();

		
		
		
	}
	
	public static JPanel getScreen() {
		instance = new ScenarioCreatorGUI();
		return instance.mainPanel;
	}
	
	private static class QuestionListGUI implements ActionListener{
		
		private static JButton addQuestion;
		private static JComboBox questionComboBox;
		
		private QuestionListGUI() {
			addQuestion = new JButton("New Question");
			addQuestion.addActionListener(this);
			addQuestion.setSize((int)(questionListPanel.getSize().height * 0.8), (int)(questionListPanel.getSize().height * 0.8));
			addQuestion.setPreferredSize(addQuestion.getPreferredSize());		
			MainFrame.editJButton(addQuestion);
			
			questionComboBox = new JComboBox<QuestionGUI>();
			questionComboBox.addActionListener(this);
			questionComboBox.setBackground(Color.WHITE);
			
			questionListPanel.add(addQuestion);
			questionListPanel.add(questionComboBox);
		}
		
		
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource().equals(addQuestion)) {
				Question newQuestion = new Question();
				questionList.add(newQuestion);
				QuestionGUI newGUI = instance.new QuestionGUI();
				jQuestionList.add(newGUI);
				newGUI.setQuestionIndex();
				questionComboBox.addItem(newGUI);
			}
			else if (e.getSource().equals(questionComboBox)) {
				QuestionGUI selected = (QuestionGUI)questionComboBox.getSelectedItem();
				selected.addToFrame();				
			}
			
		}
	
	}
	
	private class QuestionGUI implements ActionListener{
		
		private int sizeX;
		private int sizeY;
		
		private int questionIndex;
		
		//QUESTION TITLE
		private JPanel questionTitle;
		private JLabel questionName;
		
		//QUESTION
		private JPanel questionPanel;
		private JLabel questionLabel;
		private JTextField questionText;
		
		//CELL CONFIGURATIONS
		private JPanel cellConfig;
		private JLabel cellLabel;
		private JComboBox cellComboBox;
		private String[] cellList;		
		private List<JBrailleCell> jCells;
		private JPanel jCellDisplayed;
		
		//CORRECT ANSWER
		private JPanel responseButtonConfig;
		private JLabel responseButtonLabel;
		private JComboBox responseButtonComboBox;
		private String[] responseButtonList;		
		private List<JCheckBox> jResponseButtons;
		private Dimension responseButtonSize = new Dimension(300,25);
		private boolean[] answer;
		private JPanel jResponseButtonDisplayed;
		
		private JScrollPane vScroller;
		
		private QuestionGUI() {
						
			sizeX = (int)(MainFrame.getSizeX()*0.9);
			sizeY = (int)(MainFrame.getSizeY() * 0.80);
			questionIndex = 0;
			
			/*
			vScroller = new JScrollPane(questionEditor);
			vScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			vScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			vScroller.setBounds((int)(MainFrame.getSizeX()*0.85), 0, (int)(MainFrame.getSizeX()*0.15), (int)(MainFrame.getSizeY() * 0.80));
			controls.add(vScroller);
			*/
			
			FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
			layout.setHgap((int)(sizeY*0.03));
			
			//QUESTION HEADER
			questionTitle = new JPanel(layout);
			questionName = new JLabel("Question 1");
			questionName.setFont(new Font("calibri",Font.BOLD,25));
			questionTitle.add(questionName);
			
			//QUESTION TEXT
			questionPanel = new JPanel(layout);
		    questionLabel = new JLabel("QUESTION: ");
		    questionText = new JTextField(sizeY/10);
		    questionPanel.add(questionLabel);
		    questionPanel.add(questionText);	
			
			//CELL CONFIGURARTION
			cellConfig = new JPanel(layout);
			cellLabel = new JLabel("Edit Cell: ");
			cellList = new String[ScenarioCreatorManager.getNumCells()];			
			jCells = new ArrayList<JBrailleCell>();
			jCellDisplayed = new JPanel(null);
			jCellDisplayed.setSize(JBrailleCell.getSizeX(), JBrailleCell.getSizeY());
			jCellDisplayed.setPreferredSize(jCellDisplayed.getSize());			
			for (int i = 0; i<cellList.length; i++) {
				cellList[i] = "Cell: " + String.valueOf(i+1);
				jCells.add(new JBrailleCell());
			}			
			cellComboBox = new JComboBox<String>(cellList); 
			cellComboBox.addActionListener(this);
			cellConfig.add(cellLabel);
			cellConfig.add(cellComboBox);
			cellConfig.add(jCellDisplayed);
			
			//ANSWER CONFIGURATION
			responseButtonConfig = new JPanel(layout);
			responseButtonLabel = new JLabel("Select Correct Answer(s): ");
			responseButtonList = new String[ScenarioCreatorManager.getNumButtons()];			
			jResponseButtons = new ArrayList<JCheckBox>();
			jResponseButtonDisplayed = new JPanel(null);
			jResponseButtonDisplayed.setSize(responseButtonSize);
			jResponseButtonDisplayed.setPreferredSize(responseButtonSize);			
			for (int i = 0; i<responseButtonList.length; i++) {
				responseButtonList[i] = "Button: " + String.valueOf(i+1);
				JCheckBox _button = new JCheckBox("Button: " + (i+1) + " is an answer?");
				_button.setSize(responseButtonSize);
				_button.setPreferredSize(responseButtonSize);
				jResponseButtons.add(_button);
			}			
			responseButtonComboBox = new JComboBox<String>(responseButtonList); 
			responseButtonComboBox.addActionListener(this);
			responseButtonConfig.add(responseButtonLabel);
			responseButtonConfig.add(responseButtonComboBox);
			responseButtonConfig.add(jResponseButtonDisplayed);
			
		}
		
		private void repaintEditor() { questionEditor.repaint();}
		
		private void addToFrame() {
			questionEditor.removeAll();
			questionEditor.add(questionTitle);
			questionEditor.add(questionPanel);
			questionEditor.add(cellConfig);	
			questionEditor.add(responseButtonConfig);
			questionEditor.validate();
			repaintEditor();	
			
		}

		public void actionPerformed(ActionEvent e) {
						
			if (e.getSource() == cellComboBox) {				
				int index = (cellComboBox.getSelectedIndex());				
				jCellDisplayed.removeAll();
				jCellDisplayed.add(jCells.get(index).getJBrailleCell());
				jCellDisplayed.validate();
				jCellDisplayed.repaint();
				}
			else if (e.getSource() == responseButtonComboBox) {
				int index = (responseButtonComboBox.getSelectedIndex());	
				jResponseButtonDisplayed.removeAll();
				jResponseButtonDisplayed.add(jResponseButtons.get(index));
				jResponseButtonDisplayed.validate();
				jResponseButtonDisplayed.repaint();
			}
				
				
			}
	
		public String toString() {
			return "Question "+ questionIndex;
		}
		
		public void setQuestionIndex() {
			questionIndex = (jQuestionList.indexOf(this)+1);
			questionName.setText("Question " + questionIndex);
		}
		
		}
		
}

