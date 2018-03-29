package enamel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource().equals(create)) {
            MainFrame.changeScreen(ScenarioCreatorGUI.getScreen());
        } else if (e.getSource().equals(modify)) {

            //TODO Sanjay Add a call for retrieving the Filename and parsing here
            MainFrame.changeScreen(ScenarioCreatorGUI.getScreen()); //ENSURE THIS IS THE FIRST CALL

            // TODO Get file from JFilChooser and store in ctor below

            ScenarioCreatorManager scm = new ScenarioCreatorManager(new File("ADD_FILE"));

            if (scm.parseFile()) {
                GUIBuilder.setNumButtons(scm.getButtonNum());
                GUIBuilder.setNumCells(scm.getCellNum());
                GUIBuilder.setTitle(scm.getTitle());

                String[] split;
                String repeatText;
                boolean repeat = false;

                for (int i = 0; i < scm.getQuestions().size(); i++) {

                    repeatText = "";

                    for (int j = 0; j < scm.getQuestions().get(i).getCommands().size(); j++) {
                        Command tempCommand = scm.getQuestions().get(i).getCommands().get(j);


                        switch (tempCommand.getClass().getName()) {
                            case "DispCellChar":
                                split = tempCommand.getInput().split("\\s");

                                GUIBuilder.createDisplayCharacter(split[1].charAt(0), Integer.parseInt(split[0]));
                                break;

                            case "DispCellClear":
                                GUIBuilder.createClearSpecificCell(Integer.parseInt(tempCommand.getInput()));
                                break;

                            case "DispCellLower":
                                split = tempCommand.getInput().split("\\s");

                                GUIBuilder.createLowerPin(Integer.parseInt(split[1]), Integer.parseInt(split[0]));
                                break;

                            case "DispCellLowerPins":
                                // TODO Daman Create a Lower pins command like the one in ScenarioParser at line 158
                                //GUIBuilder.createLowerPins
                                break;

                            case "DispCellPins":
                                split = tempCommand.getInput().split("\\s");

                                GUIBuilder.createDisplayCellPins(split[1], Integer.parseInt(split[0]));
                                break;

                            case "DispCellRaise":
                                split = tempCommand.getInput().split("\\s");

                                GUIBuilder.createRaisePin(Integer.parseInt(split[1]), Integer.parseInt(split[0]));
                                break;
                            case "DispClearAll":
                                GUIBuilder.createClearCells();
                                break;
                            case "DispString":
                                GUIBuilder.createDisplayWordInBraille(tempCommand.getInput());
                                break;
                            case "EndRepeat":
                                repeat = false;
                                break;
                            case "Pause":
                                GUIBuilder.createPause(Integer.parseInt(tempCommand.getInput()));
                                break;
                            case "Repeat":
                                repeat = true;
                                break;
                            case "RepeatButton":
                                // TODO Daman create a repeat button method
                                break;
                            case "ResetButtons":
                                GUIBuilder.createResetButtons();
                                break;
                            case "Skip":
                                // TODO figure out how to do skip to a name tag
                                break;

                            case "SkipButton":
                                // TODO Daman add a skip button method
                                break;

                            case "SkipPos":
                                GUIBuilder.createCheckpoint(tempCommand.getInput());
                                break;

                            case "Sound":
                                GUIBuilder.createPlayAudio(tempCommand.getInput());
                                break;

                            case "Space":
                                // TODO figure out how to start a new block
                                break;

                            case "Text":
                                if (repeat) {
                                    repeatText += tempCommand.getInput();
                                } else {
                                    GUIBuilder.createTextToSpeech(tempCommand.getInput());
                                }
                                break;

                            case "UserInput":
                                GUIBuilder.createUserInput();
                                break;
                        }
                    }
                }
            } else {
                // Error when parsing file
                System.out.println(scm.getErrorMessage());
            }


            //DELETE FROM HERE
			/*GUIBuilder.setNumCells(3);
			GUIBuilder.setNumButtons(2);
			GUIBuilder.setTitle("This is a test");
			GUIBuilder.createTextToSpeech("What does the braille cell say? Click one for Dog Two for Cat");
			GUIBuilder.createDisplayWordInBraille("Dog");
			GUIBuilder.createCheckpointTravelButtonClick(6, 0);
			GUIBuilder.createCheckpointTravelButtonClick(8, 1);
			GUIBuilder.createUserInput();
			GUIBuilder.createCheckpoint("AnswerCorrect");
			GUIBuilder.createTextToSpeech("YOU ARE CORRECT!");
			GUIBuilder.createCheckpoint("AnswerWrong");
			GUIBuilder.createTextToSpeech("YOU ARE WRONG!");*/


            //TEST: DELETE ABOVE


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


}
