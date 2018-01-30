package enamel;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class PinCells {

	JPanel panel;
	JRadioButton[] pins = new JRadioButton[8];
	int[] pinIndex = { 0, 2, 4, 1, 3, 5, 6, 7 };
	public static final GridLayout DEFAULT_CELL_GRID = new GridLayout(4, 2);

	public PinCells() {
		this(DEFAULT_CELL_GRID);
	}

	public PinCells(GridLayout cellGrid) {
		panel = new JPanel(cellGrid);
		for (int j = 0; j < 8; j++) {
			JRadioButton radioButton = new JRadioButton();
			radioButton.setEnabled(false);
			radioButton.setSize(25, 25);
			radioButton.getAccessibleContext().setAccessibleName("Cell " + (j + 1));
			pins[j] = radioButton;
			panel.add(radioButton);
			panel.repaint();
		}
	}
}
