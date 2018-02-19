package enamel;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;

public class JBrailleCell{

	public final static String DISPLAY_INDEX = "Display Indexes";

	private JPanel cell;
	private List<JRadioButton> pins;
	private int index;

	private static int sizeX = 50;
	private static int sizeY = 100;

	public JBrailleCell(int index) {

		this.index = index;

		cell = new JPanel(new GridLayout(4,2));
		cell.setBorder(new LineBorder(Color.BLACK));
		cell.setSize(sizeX,sizeY);
		cell.setPreferredSize(cell.getSize());
		pins = new ArrayList<JRadioButton>();

		for(int i = 0; i<8; i++) {
			pins.add(new JRadioButton());
		}	

		cell.add(pins.get(0));
		cell.add(pins.get(4));
		cell.add(pins.get(1));
		cell.add(pins.get(5));
		cell.add(pins.get(2));
		cell.add(pins.get(6));
		cell.add(pins.get(3));
		cell.add(pins.get(7));			

	}

	public JBrailleCell(String indexes) {

		GridLayout layout = new GridLayout(4,2);
		layout.setHgap(13);
		cell = new JPanel(layout);
		cell.setBorder(new LineBorder(Color.BLACK));
		cell.setSize(sizeX,sizeY);
		cell.setPreferredSize(cell.getSize());
		List<JLabel>indexList = new ArrayList<JLabel>();

		for(int i = 0; i<8; i++) {
			indexList.add(new JLabel("["+(i+1)+"]"));
		}	

		cell.add(indexList.get(0));
		cell.add(indexList.get(4));
		cell.add(indexList.get(1));
		cell.add(indexList.get(5));
		cell.add(indexList.get(2));
		cell.add(indexList.get(6));
		cell.add(indexList.get(3));
		cell.add(indexList.get(7));		


	}

	public JPanel getJBrailleCell() {
		return this.cell;
	}		
	public static int getSizeX() {
		return sizeX;
	}
	public static int getSizeY() {
		return sizeY;
	}

	public String toString() {
		return "Braill Cell: " + index;
	}


}
