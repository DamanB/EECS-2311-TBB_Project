package enamel;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame {
	private JFrame frame;

	private JPanel panel;
	private JButton open;
	private JButton create;

	public MainFrame() {
		frame = new JFrame();
		frame.setTitle("Main");
		frame.setBounds(100, 100, 627, 459);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());

		panel = new JPanel();
		panel.setSize(200, 50);

		open = new JButton("Open");
		open.addMouseListener(new Click());
		panel.add(this.open);

		create = new JButton("Create");
		create.addMouseListener(new Click());
		this.panel.add(this.create);

		frame.add(panel);
		frame.repaint();
		frame.setVisible(true);
	}

	public JButton getCreate() {
		return create;
	}

	public JButton getOpen() {
		return open;
	}

	class Click implements MouseListener {
		private void open() {
			ToyAuthoring.open();
		}

		private void create() {
			ScenarioCreator c = new ScenarioCreator();
			frame.setVisible(false);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				if (e.getSource().equals(open)) {
					open();
				} else if (e.getSource().equals(create)) {
					create();
				}
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
