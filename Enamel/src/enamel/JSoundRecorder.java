package enamel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class JSoundRecorder extends JFrame implements ActionListener{

	private JLabel title;
	private JTextField fileName;
	private JButton record;

	private boolean isRecording;
	
	private File soundFile;
	JActionConfigure.PlayAudio node;

	private GridBagConstraints c = new GridBagConstraints();	

	public JSoundRecorder(JActionConfigure.PlayAudio node) {
		
		MainFrame.frame.setEnabled(false);
		isRecording = false;
		this.node = node;
		this.setTitle("SDP-16 Sound Recorder");		
		this.setSize(500,300);
		this.setMinimumSize(new Dimension(200,200));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		this.setBackground(MainFrame.primColor);

		c.gridx=0;
		c.gridy=0;
		title = new JLabel("Welcome To SDP-16's Sound Recorder");
		title.setFont(new Font("calibri", Font.BOLD, 20));
		this.add(title,c);

		c.gridy = 1;
		this.add(new JLabel("Enter File Name"), c);

		fileName = new JTextField();
		fileName.setSize(200,30);
		fileName.setPreferredSize(fileName.getSize());
		c.gridy=2;
		this.add(fileName, c);

		c.gridy=3;
		this.add(new JLabel("Click to create your .WAV file"), c);
		
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
				record.setText("Stop Recording");
				isRecording = true;
			}else {
				record.setText("Start Recording");
				node.audioName.setText(fileName.getText() + ".wav");
				MainFrame.frame.setEnabled(true);
				this.setVisible(false);
				this.dispose();
			}
			
		}
	}
	
	public File getSoundFile() {
		return soundFile;
	}



}
