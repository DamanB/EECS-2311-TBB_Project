package enamel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

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
		
		QuestionEditor editor = new QuestionEditor();
		
	}
	
	public static JPanel getScreen() {
		instance = new ScenarioCreatorGUI();
		return instance.mainPanel;
	}
	
	private static class QuestionEditor {
		
		private static int sizeX;
		private static int sizeY;
		
		private static JPanel questionTitle;
		private static JLabel questionIndex;
		
		private static JPanel questionPanel;
		private static JLabel questionLabel;
		private static JTextField questionText;
		
		private static JPanel cellConfig;
		private static JLabel cellLabel;
		private static JComboBox cellComboBox;
		private static String[] cellList;		
		
		private static JScrollPane vScroller;
		
		private QuestionEditor() {
			
			sizeX = (int)(MainFrame.getSizeX()*0.9);
			sizeY = (int)(MainFrame.getSizeY() * 0.80);
			
			/*
			vScroller = new JScrollPane(questionEditor);
			vScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			vScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			vScroller.setBounds((int)(MainFrame.getSizeX()*0.85), 0, (int)(MainFrame.getSizeX()*0.15), (int)(MainFrame.getSizeY() * 0.80));
			controls.add(vScroller);
			*/
			
			questionTitle = new JPanel(new FlowLayout(FlowLayout.LEFT));
			questionTitle.setBackground(MainFrame.primColor);
			questionIndex = new JLabel("Question 1");
			questionIndex.setFont(new Font("calibri",Font.BOLD,25));
			questionTitle.add(questionIndex);
			questionEditor.add(questionTitle);
			
			questionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		    questionLabel = new JLabel("QUESTION: ");
		    questionText = new JTextField(sizeY/10);
		    questionPanel.add(questionLabel);
		    questionPanel.add(questionText);	
			questionEditor.add(questionPanel);
			
			cellConfig = new JPanel(new FlowLayout(FlowLayout.LEFT));
			cellLabel = new JLabel("Edit Cell ");
			cellList = new String[ScenarioCreatorManager.getNumCells()];			
			for (int i = 0; i<cellList.length; i++) {
				cellList[i] = "Cell: " + String.valueOf(i+1);
			}			
			cellComboBox = new JComboBox<String>(cellList); 			
			cellConfig.add(cellLabel);
			cellConfig.add(cellComboBox);
			questionEditor.add(cellConfig);
			
			
			
			
		}
		
		private static void repaintEditor() { questionEditor.repaint();	}
		
		
	}
	
	private class JBrailleCell extends JComponent{
		
		private JPanel cell;
		
		public JBrailleCell() {
			
			cell = new JPanel(new GridLayout(4,2));
			
		}
		
		
		
	}

	
	
	
}
