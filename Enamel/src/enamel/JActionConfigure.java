package enamel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JActionConfigure extends JPanel{

	private static JLabel instruction;

	private static Dimension panelSize;

	public JActionConfigure(int index) {		
		this.panelSize = ScenarioCreatorGUI.actionOptions.getSize();
		this.setPreferredSize(panelSize);		
		instruction = new JLabel("Instruction: ");
		instruction.setHorizontalAlignment(JLabel.CENTER);
		this.add(instruction);		
		getOptions(index);
	}

	private void getOptions(int index) {
		this.removeAll();
		this.add(instruction);		
		
		if (index == 3) {
			this.add(new JPauseButton());
		}else if (index == 5) {
			this.add(new JRepeat());
		}else if (index == 10) {
			this.add(new JDisplayString());
		}	
	}

	private class JPauseButton extends JPanel{		
		private JTextField pauseTime;
		private JPauseButton() {
			this.setPreferredSize(panelSize);
			instruction.setText("Pause: Please select the amount of time in seconds (a value greater than 0) you wish to pause");
			pauseTime = new JTextField(); 
			pauseTime.setPreferredSize(new Dimension(100,20));
			this.add(pauseTime);
		}		
	}

	private class JDisplayString extends JPanel{		
		private JTextField stringToDisp;
		private JDisplayString() {
			this.setPreferredSize(panelSize);
			instruction.setText("Display Word: Please enter the word you would like to display in braille. Ensure adequate Braille cells are created beforehand");
			stringToDisp = new JTextField(); 
			stringToDisp.setPreferredSize(new Dimension(panelSize.width/2,20));
			this.add(stringToDisp);
		}		
	}
	
	private class JRepeat extends JPanel{		
		private JTextField stringToDisp;
		
		private JRepeat() {
			this.setPreferredSize(panelSize);
			instruction.setText("Repeat Text: Set the text to what you would like to have repeated when the repeat button is clicked");	
			stringToDisp = new JTextField(); 
			stringToDisp.setPreferredSize(new Dimension(panelSize.width/2,20));
			this.add(stringToDisp);

		}			
	}


}
