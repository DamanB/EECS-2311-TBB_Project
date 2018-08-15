package enamel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ScenarioEditorMenu implements MouseListener {

	public static ScenarioEditorMenu instance;

	public static JPanel pane;
	private static JLabel title, instruction;
	public static JButton create, modify, back;

	private static int sizeX;
	private static int sizeY;

	private static GridBagConstraints c = new GridBagConstraints();

	private ScenarioEditorMenu() {

		sizeX = MainFrame.dimension.width;
		sizeY = MainFrame.dimension.height;

		GridBagLayout layout = new GridBagLayout();
		c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 10, 10);

		pane = new JPanel();
		pane.setBackground(MainFrame.primColor);
		pane.setLayout(layout);
		createLabels();
		createButtons();

	}

	public static JPanel getScreen() {
		instance = new ScenarioEditorMenu();
		return pane;
	}

	private void createLabels() {
		title = new JLabel("Scenario Editor Menu");
		title.setFont(new Font("calibri", Font.PLAIN, 35));
		title.setForeground(Color.BLACK);
		title.setHorizontalAlignment(JLabel.CENTER);

		instruction = new JLabel("Would You Like to Open an Already Exisitng Scenario or Create a New One?");
		instruction.setFont(new Font("calibri", Font.PLAIN, 20));
		instruction.setForeground(Color.BLACK);
		instruction.setHorizontalAlignment(JLabel.CENTER);

		c.gridx = 0;
		c.gridy = 0;
		pane.add(title, c);
		c.gridx = 0;
		c.gridy = 1;
		pane.add(instruction, c);
	}

	private void createButtons() {
		create = new JButton("Create");
		modify = new JButton("Modify");
		back = new JButton("Back");
		modifyButtonSettings(create);
		modifyButtonSettings(modify);
		modifyButtonSettings(back);

		c.gridx = 0;
		c.gridy = 2;
		pane.add(create, c);
		c.gridx = 0;
		c.gridy = 3;
		pane.add(modify, c);
		c.gridx = 0;
		c.gridy = 4;
		pane.add(back, c);

	}

	private void modifyButtonSettings(JButton button) {

		Dimension size = new Dimension(190, 55);

		button.setPreferredSize(size);
		button.setMinimumSize(size);
		button.addMouseListener(this);
		button.setFont(new Font("Calibri", Font.PLAIN, 20));
		MainFrame.editJButton(button);
		button.setVerticalAlignment(JButton.CENTER);
	}

	public static void main(String[] args) {
		ScenarioCreatorManager scm = new ScenarioCreatorManager(new File("Enamel/FactoryScenarios/Scenario_1.txt"));

		scm.parseFile();
		/*
		 * System.out.println("REE"); for (int i = 0; i < scm.getQuestions().size();
		 * i++) { for (int j = 0; j < scm.getQuestions().get(i).getCommands().size();
		 * j++) {
		 * System.out.println(scm.getQuestions().get(i).getCommands().get(j).getClass().
		 * getName() + "\t\t\t\t" +
		 * scm.getQuestions().get(i).getCommands().get(j).toString()); } }
		 */

		System.out.println(findNextOccurrence(0, 0, scm.getQuestions(), 0, "ONEE"));
	}

	private static int findNextOccurrence(int iIndex, int jIndex, List<Question> questionList, int index,
			String skipName) {
		Command temp;
		boolean repeat = false;

		for (int i = iIndex; i < questionList.size(); i++) {
			for (int j = jIndex; j < questionList.get(i).getCommands().size(); j++) {

				iIndex = 0;
				jIndex = 0;

				temp = questionList.get(i).getCommands().get(j);

				if (temp.getClass().getName().equals("enamel.Repeat")) {
					if (repeat) {
						return -1;
					}
					repeat = true;
				} else if (temp.getClass().getName().equals("enamel.EndRepeat")) {
					if (!repeat) {
						return -1;
					}

					repeat = false;
				} else if (!repeat && !temp.getClass().getName().equals("enamel.Space")) {
					index++;
				}

				if (temp.getClass().getName().equals("enamel.SkipPos") && (temp.getInput().equals(skipName))) {
					return index;
				}
			}
		}
		return -1;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if (e.getSource().equals(create)) {
			MainFrame.changeScreen(ScenarioCreatorGUI.getScreen());
		} else if (e.getSource().equals(modify)) {

			File modify = getFile();
			if (modify != null) {
				MainFrame.changeScreen(ScenarioCreatorGUI.getScreen()); // ENSURE THIS IS THE FIRST CALL
				ScenarioCreatorManager scm = new ScenarioCreatorManager(new File(modify.getAbsolutePath()));

				if (scm.parseFile()) {
					GUIBuilder.setNumButtons(scm.getButtonNum());
					GUIBuilder.setNumCells(scm.getCellNum());
					GUIBuilder.setTitle(scm.getTitle());

					String[] split;
					String repeatText;
					boolean repeat = false;
					int index = 0, tempIndex = 0;

					for (int i = 0; i < scm.getQuestions().size(); i++) {

						repeatText = "";

						for (int j = 0; j < scm.getQuestions().get(i).getCommands().size(); j++) {
							Command tempCommand = scm.getQuestions().get(i).getCommands().get(j);

							switch (tempCommand.getClass().getName().substring(7)) {
							case "DispCellChar":
								split = tempCommand.getInput().split("\\s");

								GUIBuilder.createDisplayCharacter(split[1].charAt(0), Integer.parseInt(split[0]));
								index++;
								break;

							case "DispCellClear":
								GUIBuilder.createClearSpecificCell(Integer.parseInt(tempCommand.getInput()));
								index++;
								break;

							case "DispCellLower":
								split = tempCommand.getInput().split("\\s");
								GUIBuilder.createLowerPin(Integer.parseInt(split[1]), Integer.parseInt(split[0]));
								index++;
								break;

							case "DispCellLowerPins":
								// TODO Figure out what lower pins does
								// GUIBuilder.createLowerPins
								break;

							case "DispCellPins":
								split = tempCommand.getInput().split("\\s");
								GUIBuilder.createDisplayCellPins(split[1], Integer.parseInt(split[0]));
								index++;
								break;

							case "DispCellRaise":
								split = tempCommand.getInput().split("\\s");
								GUIBuilder.createRaisePin(Integer.parseInt(split[1]), Integer.parseInt(split[0]));
								index++;
								break;

							case "DispClearAll":
								GUIBuilder.createClearCells();
								index++;
								break;

							case "DispString":
								GUIBuilder.createDisplayWordInBraille(tempCommand.getInput());
								index++;
								break;

							case "EndRepeat":
								if (!repeat) {
									throw new IllegalArgumentException(
											"Error on the following command: " + tempCommand.toString());
								}

								repeat = false;
								break;

							case "Pause":
								GUIBuilder.createPause(Integer.parseInt(tempCommand.getInput()));
								index++;
								break;

							case "Repeat":
								if (repeat) {
									throw new IllegalArgumentException(
											"Error on the following command: " + tempCommand.toString());
								}

								repeat = true;
								break;

							case "RepeatButton":
								if (!repeat) {
									GUIBuilder.createRepeat(repeatText, Integer.parseInt(tempCommand.getInput()));
									index++;
								} else {
									throw new IllegalArgumentException(
											"Error on the following command: " + tempCommand.toString());
								}
								index++;
								break;

							case "ResetButtons":
								GUIBuilder.createResetButtons();
								index++;
								break;

							case "Skip":
								tempIndex = findNextOccurrence(i, j, scm.getQuestions(), index, tempCommand.getInput());

								if (tempIndex == -1) {
									throw new IllegalArgumentException(
											"Error on the following command: " + tempCommand.toString());

								}

								GUIBuilder.createGoToCheckpoint(tempIndex);
								index++;
								break;

							case "SkipButton":
								split = tempCommand.getInput().split("\\s");
								tempIndex = findNextOccurrence(i, j, scm.getQuestions(), index, split[1]);

								if (tempIndex == -1) {
									throw new IllegalArgumentException(
											"Error on the following command: " + tempCommand.toString());
								}

								GUIBuilder.createCheckpointTravelButtonClick(tempIndex, Integer.parseInt(split[0]));
								index++;
								break;

							case "SkipPos":
								GUIBuilder.createCheckpoint(tempCommand.getInput());
								index++;
								break;

							case "Sound":
								GUIBuilder.createPlayAudio(tempCommand.getInput());
								index++;
								break;

							case "Space":
								// TODO figure out how to start a new block
								// GUIBuilder.
								break;

							case "Text":
								if (repeat) {
									repeatText += tempCommand.getInput();
								} else {
									GUIBuilder.createTextToSpeech(tempCommand.getInput());
									index++;
								}
								break;

							case "UserInput":
								GUIBuilder.createUserInput();
								index++;
								break;
							}
						}
					}

					if (repeat) {
						// TODO Throw error with file
						throw new IllegalArgumentException();
					}

				} else {
					// Error when parsing file
					System.out.println(scm.getErrorMessage());
				}

				// DELETE FROM HERE
				/*
				 * GUIBuilder.setNumCells(3); GUIBuilder.setNumButtons(2);
				 * GUIBuilder.setTitle("This is a test"); GUIBuilder.
				 * createTextToSpeech("What does the braille cell say? Click one for Dog Two for Cat"
				 * ); GUIBuilder.createDisplayWordInBraille("Dog");
				 * GUIBuilder.createCheckpointTravelButtonClick(6, 0);
				 * GUIBuilder.createCheckpointTravelButtonClick(8, 1);
				 * GUIBuilder.createUserInput(); GUIBuilder.createCheckpoint("AnswerCorrect");
				 * GUIBuilder.createTextToSpeech("YOU ARE CORRECT!");
				 * GUIBuilder.createCheckpoint("AnswerWrong");
				 * GUIBuilder.createTextToSpeech("YOU ARE WRONG!");
				 */

				// TEST: DELETE ABOVE
			}

		} else if (e.getSource().equals(back)) {
			MainFrame.changeScreen(MainMenu.getScreen());
		}

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	private static File getFile() {

		JFileChooser fileChooser = new JFileChooser();
		// Starts JFileChooser at project directory
		fileChooser.setCurrentDirectory(new java.io.File("."));
		// Shows only text files in the file chooser
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		boolean f = true;
		while (f) {
			if (fileChooser.showOpenDialog(MainFrame.frame) == JFileChooser.APPROVE_OPTION) {
				if (fileChooser.getSelectedFile().isDirectory()) {
					fileChooser.setCurrentDirectory(fileChooser.getSelectedFile());
					continue;
				} else {
					f = false;
				}
				if (fileChooser.getSelectedFile().getName().matches("^Scenario_[1-9][0-9]*.txt$")) {
					try {
						// Reads the file and records the first 2 lines
						BufferedReader br = new BufferedReader(
								new FileReader(fileChooser.getSelectedFile().getAbsolutePath()));
						String[] lines = new String[2];
						lines[0] = br.readLine();
						lines[1] = br.readLine();
						// Checks if first two lines if they follow the format. Cell + (positive
						// integer) and Button + (positive integer)
						if (lines[0] != null && lines[1] != null && lines[0].matches("^Cell [1-9][0-9]*$")
								&& lines[1].matches("^Button [1-9][0-9]*$")) {
							br.close();
							return fileChooser.getSelectedFile();
						} else {
							// exit
							System.out.println("Error: Invalid file format. Cell and Button numbers not found");
						}
					} catch (IOException e) {
						JOptionPane.showMessageDialog(MainFrame.frame,
								"The file you selected does not meet the Scenario requirements", "Invalid File",
								JOptionPane.OK_OPTION);
						return null;
					}
				} else {
					JOptionPane.showMessageDialog(MainFrame.frame,
							"The file you selected does not meet the Scenario_# name format", "Invalid File",
							JOptionPane.OK_OPTION);
					return null;
				}
			} else { // user clicked exit
				break;
			}
		}
		return null;
	}

}
