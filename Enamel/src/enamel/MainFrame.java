package enamel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class MainFrame {
	private JFrame frame;
	LinkedList<JButton> buttonList = new LinkedList<JButton>();
	JPanel panel;
	JButton open;
	JButton create;

	public MainFrame() {
		frame = new JFrame();
		frame.setTitle("Main");
		frame.setBounds(100, 100, 627, 459);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());

		panel = new JPanel();
		panel.setSize(200, 50);

		open = new JButton("Open");
		open.addActionListener(ActionListener());
		panel.add(this.open);

		create = new JButton("Create");
		this.panel.add(this.create);

		frame.add(panel);
		frame.repaint();
		frame.setVisible(true);
	}
}
