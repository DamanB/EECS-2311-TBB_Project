package enamel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
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
	private static JPanel questions;
	private static JPanel questionEditor;
	private static JPanel controls;
	
	private static ScenarioCreatorGUI instance;
	
	private ScenarioCreatorGUI() {
		
		BorderLayout layout = new BorderLayout();
		mainPanel = new JPanel(layout);
		questions = new JPanel();
		questionEditor = new JPanel();
		controls = new JPanel();
		leftBorder = new JPanel();
		
		mainPanel.setBackground(MainFrame.primColor);
		questions.setBackground(MainFrame.primColor);
		questionEditor.setBackground(MainFrame.primColor);
		controls.setBackground(MainFrame.primColor);
		leftBorder.setBackground(MainFrame.primColor);

		questions.setSize((int)(MainFrame.getSizeX()*0.9),(int)(MainFrame.getSizeY() * 0.2));
		questionEditor.setSize((int)(MainFrame.getSizeX()*0.9), (int)(MainFrame.getSizeY() * 0.80));
		controls.setSize((int)(MainFrame.getSizeX() * 0.12),(int)(MainFrame.getSizeY() * 0.80));
		leftBorder.setSize((int)(MainFrame.getSizeX() * 0.02),(int)(MainFrame.getSizeY() * 0.80));
		
		questions.setPreferredSize(new Dimension(questions.getSize()));
		questionEditor.setPreferredSize(new Dimension(questionEditor.getSize()));
		controls.setPreferredSize(new Dimension(controls.getSize()));
		leftBorder.setPreferredSize(new Dimension(leftBorder.getSize()));
		
		questionEditor.setLayout(new BoxLayout(questionEditor,BoxLayout.Y_AXIS));
		
		mainPanel.setSize(MainFrame.dimension);
		mainPanel.add(questions,layout.SOUTH);
		mainPanel.add(controls,layout.EAST);
		mainPanel.add(questionEditor,layout.CENTER);
		mainPanel.add(leftBorder, layout.WEST);
		
		QuestionGUI editor = new QuestionGUI();
		
	}
	
	public static JPanel getScreen() {
		instance = new ScenarioCreatorGUI();
		return instance.mainPanel;
	}
	
	private class QuestionGUI implements ActionListener{
		
		private int sizeX;
		private int sizeY;
		
		private JPanel questionTitle;
		private JLabel questionIndex;
		
		private JPanel questionPanel;
		private JLabel questionLabel;
		private JTextField questionText;
		
		private JPanel cellConfig;
		private JLabel cellLabel;
		private JComboBox cellComboBox;
		private String[] cellList;		
		private List<JBrailleCell> jCells;
		private JPanel jCellDisplayed;
		
		private JScrollPane vScroller;
		
		private QuestionGUI() {
			
			sizeX = (int)(MainFrame.getSizeX()*0.9);
			sizeY = (int)(MainFrame.getSizeY() * 0.80);
			
			/*
			vScroller = new JScrollPane(questionEditor);
			vScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			vScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			vScroller.setBounds((int)(MainFrame.getSizeX()*0.85), 0, (int)(MainFrame.getSizeX()*0.15), (int)(MainFrame.getSizeY() * 0.80));
			controls.add(vScroller);
			*/
			
			FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
			layout.setHgap((int)(sizeY*0.03));
			
			questionTitle = new JPanel(layout);
			questionTitle.setBackground(MainFrame.primColor);
			questionIndex = new JLabel("Question 1");
			questionIndex.setFont(new Font("calibri",Font.BOLD,25));
			questionTitle.add(questionIndex);
			questionEditor.add(questionTitle);
			
			questionPanel = new JPanel(layout);
		    questionLabel = new JLabel("QUESTION: ");
		    questionText = new JTextField(sizeY/10);
		    questionPanel.add(questionLabel);
		    questionPanel.add(questionText);	
			questionEditor.add(questionPanel);
			
			cellConfig = new JPanel(layout);
			cellLabel = new JLabel("Edit Cell # ");
			cellList = new String[ScenarioCreatorManager.getNumCells()];			
			jCells = new ArrayList<JBrailleCell>();
			jCellDisplayed = new JPanel(null);
			jCellDisplayed.setSize(50,100);
			jCellDisplayed.setPreferredSize(jCellDisplayed.getSize());			
			for (int i = 0; i<cellList.length; i++) {
				cellList[i] = String.valueOf(i+1);
				jCells.add(new JBrailleCell());
			}			
			cellComboBox = new JComboBox<String>(cellList); 
			cellComboBox.addActionListener(this);
			cellConfig.add(cellLabel);
			cellConfig.add(cellComboBox);
			cellConfig.add(jCellDisplayed);
			questionEditor.add(cellConfig);	
			
			
		}
		
		private void repaintEditor() { questionEditor.repaint();}

		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == cellComboBox) {				
				int index = (cellComboBox.getSelectedIndex());				
				jCellDisplayed.removeAll();
				jCellDisplayed.add(jCells.get(index).getJBrailleCell());
				jCellDisplayed.validate();
				jCellDisplayed.repaint();
				}
				
				
			}
			
		}
	
	
}
