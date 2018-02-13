package enamel;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;

public class JBrailleCell{
	
	private JPanel cell;
	private List<JRadioButton> pins;
	
	private int sizeX = 50;
	private int sizeY = 100;
	
	public JBrailleCell() {			
		cell = new JPanel(new GridLayout(4,2));
		cell.setBorder(new LineBorder(Color.BLACK));
		cell.setSize(sizeX,sizeY);
		cell.setPreferredSize(cell.getSize());
		pins = new ArrayList<JRadioButton>();
		pins.add(new JRadioButton());
		pins.add(new JRadioButton());
		pins.add(new JRadioButton());
		pins.add(new JRadioButton());
		pins.add(new JRadioButton());
		pins.add(new JRadioButton());
		pins.add(new JRadioButton());
		pins.add(new JRadioButton());

		cell.add(pins.get(0));
		cell.add(pins.get(2));
		cell.add(pins.get(4));
		cell.add(pins.get(6));
		cell.add(pins.get(1));
		cell.add(pins.get(3));
		cell.add(pins.get(5));
		cell.add(pins.get(7));			
		
	}
	
	public JPanel getJBrailleCell() {
		return this.cell;
	}		
	public int getSizeX() {
		return sizeX;
	}
	public int getSizeY() {
		return sizeY;
	}
	
	
}
