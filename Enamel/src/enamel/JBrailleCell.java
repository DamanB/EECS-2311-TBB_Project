package enamel;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;

public class JBrailleCell extends JPanel{

	private List<JRadioButton> pins;

	private static int sizeX = 50;
	private static int sizeY = 100;

	public JBrailleCell() {

		this.setLayout((new GridLayout(4,2)));
		this.setBorder(new LineBorder(Color.BLACK));
		this.setSize(sizeX,sizeY);
		this.setPreferredSize(this.getSize());
		pins = new ArrayList<JRadioButton>();

		for(int i = 0; i<8; i++) {
			pins.add(new JRadioButton());
		}	

		this.add(pins.get(0));
		this.add(pins.get(4));
		this.add(pins.get(1));
		this.add(pins.get(5));
		this.add(pins.get(2));
		this.add(pins.get(6));
		this.add(pins.get(3));
		this.add(pins.get(7));			

	}
	public JBrailleCell(boolean isSelectable) {

		GridLayout layout = new GridLayout(4,2);
		layout.setHgap(13);
		this.setLayout(layout);
		this.setBorder(new LineBorder(Color.BLACK));
		this.setSize(sizeX,sizeY);
		this.setPreferredSize(this.getSize());
		List<JLabel>indexList = new ArrayList<JLabel>();

		for(int i = 0; i<8; i++) {
			indexList.add(new JLabel("["+(i+1)+"]"));
		}	

		this.add(indexList.get(0));
		this.add(indexList.get(4));
		this.add(indexList.get(1));
		this.add(indexList.get(5));
		this.add(indexList.get(2));
		this.add(indexList.get(6));
		this.add(indexList.get(3));
		this.add(indexList.get(7));	
	}

	public String cellConfig() {
		String toReturn = "";		
		for (JRadioButton pin : pins) {
			if (pin.isSelected()) {
				toReturn += "1";
			}else {
				toReturn+= "0";
			}
		}		
		return toReturn;		
	}

	
}
