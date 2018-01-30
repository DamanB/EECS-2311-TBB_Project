package enamel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class ScenarioCreator {

	private JFrame parent;
	private JFrame frame;
	private JPanel panel;

	private JButton addCell;
	private JButton removeCell;
	private JButton addButton;
	private JButton removeButton;
	private JButton next;

	private int buttonNumber;
	private int cellNumber;

	JPanel southPanel = new JPanel();
	JPanel centerPanel = new JPanel();
	LinkedList<JPanel> panelList = new LinkedList<JPanel>();
	LinkedList<JButton> buttonList = new LinkedList<JButton>();
	private GridLayout cellGrid = new GridLayout(4, 2);
	JRadioButton[] pins = new JRadioButton[8];
	int[] pinIndex = { 0, 2, 4, 1, 3, 5, 6, 7 };

	ScenarioCreator(JFrame parent) {
		this.parent = parent;
		frame = new JFrame();
		frame.setTitle("Scenario Creator");
		frame.setBounds(100, 100, 627, 459);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());

		panel = new JPanel();
		panel.setSize(200, 50);

		addCell = new JButton("Add Cell");
		addCell.addMouseListener(new AddComponent());
		panel.add(this.addCell);

		removeCell = new JButton("Remove Cell");
		removeCell.addMouseListener(new AddComponent());
		panel.add(this.removeCell);

		addButton = new JButton("Add Button");
		addButton.addMouseListener(new AddComponent());
		this.panel.add(this.addButton);

		removeButton = new JButton("Remove Button");
		removeButton.addMouseListener(new AddComponent());
		panel.add(this.removeButton);

		next = new JButton("Next");
		next.addMouseListener(new AddComponent());
		this.panel.add(this.next);

		frame.getContentPane().add(panel, BorderLayout.NORTH);
		frame.repaint();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new Close());
		frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
		frame.getContentPane().add(southPanel, BorderLayout.SOUTH);
		buttonNumber = 0;
		cellNumber = 0;
	}

	class AddComponent implements MouseListener {

		void addCell() {
			JPanel panel = new JPanel(cellGrid);
			for (int j = 0; j < 8; j++) {
				JRadioButton radioButton = new JRadioButton();
				radioButton.setEnabled(false);
				radioButton.setSize(25, 25);
				radioButton.getAccessibleContext().setAccessibleName("Cell " + (j + 1));
				pins[j] = radioButton;
				panel.add(radioButton);
				panel.repaint();
			}
			panel.setVisible(true);
			panelList.add(panel);
			panel.setSize(50, 50);
			panel.setBorder(BorderFactory.createLineBorder(Color.black));
			centerPanel.add(panel);
			frame.repaint();
			frame.setVisible(true);
			cellNumber++;
		}

		void removeCell() {
			if (cellNumber > 0) {
				centerPanel.remove(--cellNumber);
				panelList.removeLast();
				frame.repaint();
				frame.setVisible(true);
			}
		}

		void addButton() {
			JButton button = new JButton(Integer.toString((buttonNumber + 1)));
			buttonList.add(button);
			southPanel.add(button);
			frame.repaint();
			frame.setVisible(true);
			buttonNumber++;
		}

		void removeButton() {
			if (buttonNumber > 0) {
				southPanel.remove(--buttonNumber);
				buttonList.removeLast();
				frame.repaint();
				frame.setVisible(true);
			}
		}

		void next() {
			// please complex it
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				if (e.getSource().equals(addCell))
					addCell();
				if (e.getSource().equals(removeCell))
					removeCell();
				if (e.getSource().equals(addButton))
					addButton();
				if (e.getSource().equals(removeButton))
					removeButton();
				if (e.getSource().equals(next))
					next();
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}

	class Close implements WindowListener {
		@Override
		public void windowOpened(WindowEvent e) {
		}

		@Override
		public void windowClosing(WindowEvent e) {
		}

		@Override
		public void windowClosed(WindowEvent e) {
			parent.setVisible(true);
		}

		@Override
		public void windowIconified(WindowEvent e) {
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
		}

		@Override
		public void windowActivated(WindowEvent e) {
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
		}
	}
}
