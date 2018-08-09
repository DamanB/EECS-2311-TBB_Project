package enamel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.nio.file.Paths;

public class JSoundRecorder extends JFrame implements ActionListener {

	private JLabel title;
	private JTextField fileName;
	private JButton record;

	private SoundRecorder rec;

	private boolean isRecording;

	private GridBagConstraints c = new GridBagConstraints();

	public JSoundRecorder() {

		isRecording = false;
		this.setTitle("SDP-16 Sound Recorder");
		this.setSize(500, 300);
		this.setMinimumSize(new Dimension(200, 200));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		this.setBackground(MainFrame.primColor);

		c.gridx = 0;
		c.gridy = 0;
		title = new JLabel("Welcome To SDP-16's Sound Recorder");
		title.setFont(new Font("calibri", Font.BOLD, 20));
		this.add(title, c);

		c.gridy = 1;
		this.add(new JLabel("Enter File Name"), c);

		fileName = new JTextField();
		fileName.setSize(300, 20);
		fileName.setPreferredSize(fileName.getSize());
		c.gridy = 2;
		this.add(fileName, c);

		c.gridy = 3;
		this.add(new JLabel("Click to create your .WAV audio file"), c);

		record = new JButton("Record");
		record.addActionListener(this);
		c.gridy = 4;
		this.add(record, c);

		this.repaint();
		this.setVisible(true);

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(record)) {
			if (!isRecording) {
				Path path = Paths.get("./" + fileName.getText());
				if ((Files.exists(path))) {
					int user = JOptionPane.showConfirmDialog(this, "This sound file already exists. Overwrite?",
							"File Exists", JOptionPane.OK_CANCEL_OPTION);
					if (user == JOptionPane.OK_OPTION) {
						record();
					}
				} else {
					record();
				}

			}

			if (isRecording) {
				isRecording = false;
				rec.finish();
				record.setText("Start Recording");
			}
		}
	}

	private void record() {
		isRecording = true;
		record.setText("Stop Recording");
		File file = new File(fileName.getText() + ".wav");
		rec = new SoundRecorder(file);
		rec.start();
	}

	public Component getRecord() {
		return this.record;
	}
}
