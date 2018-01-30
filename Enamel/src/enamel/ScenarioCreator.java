package enamel;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ScenarioCreator {

	private JFrame frame;
	private JPanel panel;
	private JButton addCell;
	private JButton addButton;
	private JButton next;

	ScenarioCreator() {
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

		addButton = new JButton("Add Button");
		addButton.addMouseListener(new AddComponent());
		this.panel.add(this.addButton);

		next = new JButton("Next");
		next.addMouseListener(new AddComponent());
		this.panel.add(this.next);

		frame.add(panel);
		frame.repaint();
		frame.setVisible(true);
	}

	class AddComponent implements MouseListener {

		void addCell() {
			// please complex it
		}

		void addButton() {
			// please complex it
		}

		void next() {
			// please complex it
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				if (e.getSource().equals(addCell))
					addCell();
				if (e.getSource().equals(addButton))
					addButton();
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
}
