package enamel;

//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sanjay Paraboo, Damanveer Bharaj, Penguin Guo
 */

public class ScenarioCreatorManager {

    // Used to record the cell number, button number, a list of questions and the file to read/write to
    private int cellNum;
    private int buttonNum;
    private static int numCells = 3;
    private static int numButtons = 4;
    private List<Question> questions;
    private File scenarioFile;
    private String errorMessage;

    // Shows list of all commands. Stores as <Tag-Name>|<Command-Name>
    public static String[] commandList = {
            "End Repeat|/~endrepeat",
            "Sound|/~sound",
            "Skip|/~skip",
            "Pause|/~pause",
            "Repeat Button|/~repeat-button",
            "Repeat/~repeat",
            "Reset Buttons|/~reset-buttons",
            "Skip Button|/~skip-button",
            "Display Clear All|/~disp-clearAll",
            "Display Cell Pins|/~disp-cell-pins:",
            "Display String|/~disp-string:",
            "Display Cell Character|/~disp-cell-char:",
            "Display Cell Clear|/~disp-cell-clear:",
            "Display Cell Lower Pins|/~disp-cell-lowerPins:",
            "User Input|/~user-input"
    };


    public ScenarioCreatorManager(File scenarioFile) {

        // If the file already exists it parses the file and
        if (scenarioFile.exists()) {
            // parseFile(scenarioFile);
            this.questions = new ArrayList<>();
        } else {
            if (scenarioFile.getName().matches("^Scenario_[0-9][1-9]*$")) {
                try {
                    scenarioFile.createNewFile();
                    this.questions = new ArrayList<>();
                } catch (IOException e) {
                    System.out.println(e.toString());
                }
            } else {
                throw new IllegalArgumentException("Invalid Scenario File Name");
            }
        }
    }

    // Saves the list of questions, cell number and button numbers to the file.
    public void saveToFile() {

    }

    // Will parse an existing scenario file and create a list of questions from it and the cell/button numbers
    // TODO if Illegal Argument Exception is thrown return that the file has errors
    // TODO At the end check for skip buttons and repeat connecting to end repeat
    // TODO Check to see if only command and input is on the line if not throw error. Check input length?
    // TODO Check to see is user input is done after skip button and repeat button
    public void parseFile() {
        List<String> lines = new ArrayList<>();
        this.errorMessage = "";

        try (BufferedReader br = new BufferedReader(new FileReader(this.scenarioFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        } catch (IOException e) {
            System.out.println(e.toString());
        }


        if (lines.get(0).matches("^Cell [1-9][0-9]*$") && lines.get(1).matches("^Button [1-9][0-9]*$")) {
            this.cellNum = Integer.parseInt(lines.get(0).substring(5));
            this.buttonNum = Integer.parseInt(lines.get(1).substring(7));
        } else {
            System.out.println("Invalid Cell or Button commands");
        }

        int questionIndex = 0;
        boolean repeat = false, textReached = false;
        List<String> repeatedText = new ArrayList<>();

        for (int i = 2; i < lines.size(); i++) {
            if (!textReached) {
                textReached = (!(lines.get(i).equals("")));
            }
            if (!lines.get(i).equals("")) {
                if (repeat) {
                    // Stops assuming that the text is being repeated with the
                    // /~endrepeat key phrase
                    if (lines.get(i).length() >= 11 && lines.get(i).substring(0, 11).equals("/~endrepeat")) {
                        repeat = false;
                    } else {
                        repeatedText.add(lines.get(i));
                    }
                } else {
                    // The key phrase to indicate to play a sound file.
                    if (lines.get(i).length() >= 8 && lines.get(i).substring(0, 8).equals("/~sound:")) {

                        Command temp;

                        try {
                            temp = new Sound(lines.get(i).substring(0, 8), lines.get(i).substring(8));
                        } catch (IllegalArgumentException e) {
                            this.errorMessage = e.toString();
                            return;
                        }

                        this.questions.get(questionIndex).getCommands().add(temp);
                    }
                    // The key phrase to indicate to skip to another part of the
                    // scenario.
                    else if (lines.get(i).length() >= 7 && lines.get(i).substring(0, 7).equals("/~skip:")) {
                        Command temp;

                        try {
                            temp = new Skip(lines.get(i).substring(0, 7), lines.get(i).substring(7));
                        } catch (IllegalArgumentException e) {
                            this.errorMessage = e.toString();
                            return;
                        }

                        this.questions.get(questionIndex).getCommands().add(temp);
                        //skip(lines.get(i).substring(7));
                    }
                    // The key phrase to indicate to pause for a specified number of
                    // seconds.
                    else if (lines.get(i).length() >= 8 && lines.get(i).substring(0, 8).equals("/~pause:")) {

                        Command temp;

                        try {
                            temp = new Pause(lines.get(i).substring(0, 8), lines.get(i).substring(8));
                        } catch (IllegalArgumentException e) {
                            this.errorMessage = e.toString();
                            return;
                        }

                        this.questions.get(questionIndex).getCommands().add(temp);

                        // Checks if there is a positive integer after the pause command if not logs it then exits
                        /*if (lines.get(i).substring(8).matches("^[1-9][0-9]*$")) {
                            pause(lines.get(i).substring(8));
                        } else {
                            errorLog(IllegalArgumentException.class.toString(), "Number of seconds for pause is not a positive integer.");
                            System.exit(0);
                        }*/
                    }
                    // The key phrase to assign a button to repeat text.
                    else if (lines.get(i).length() >= 16 && lines.get(i).substring(0, 16).equals("/~repeat-button:")) {
                        Command temp;

                        try {
                            // TODO figure out how to pass through button number from GUI
                            temp = new RepeatButton(lines.get(i).substring(0, 8), lines.get(i).substring(8), -1);
                        } catch (IllegalArgumentException e) {
                            this.errorMessage = e.toString();
                            return;
                        }

                        this.questions.get(questionIndex).getCommands().add(temp);
                        //repeatButton(lines.get(i).substring(16));
                    }
                    // The key phrase to signal that everything after that key phrase
                    // will be repeated.
                    else if (lines.get(i).length() >= 8 && lines.get(i).substring(0, 8).equals("/~repeat")) {
                        this.questions.get(questionIndex).getCommands().add(new Repeat(lines.get(i).substring(0, 8), lines.get(i).substring(8)));

                        repeatedText.clear();
                        repeat = true;
                    }
                    // The key phrase to reset the action listeners of all of the
                    // JButtons.
                    else if (lines.get(i).length() >= 15 && lines.get(i).substring(0, 15).equals("/~reset-buttons")) {
                        this.questions.get(questionIndex).getCommands().add(new ResetButtons(lines.get(i).substring(0, 15), lines.get(i).substring(15)));
                        //resetButtons();
                    }
                    // The key phrase to assign a button to skip to another part of the
                    // scenario.
                    else if (lines.get(i).length() >= 14 && lines.get(i).substring(0, 14).equals("/~skip-button:")) {
                        Command temp;

                        try {
                            temp = new SkipButton(lines.get(i).substring(0, 14), lines.get(i).substring(14));
                        } catch (IllegalArgumentException e) {
                            this.errorMessage = e.toString();
                            return;
                        }

                        this.questions.get(questionIndex).getCommands().add(temp);
                        //skipButton(lines.get(i).substring(14));
                    }
                    // The key phrase to clear the display of all of the braille cells.
                    else if (lines.get(i).length() >= 15 && lines.get(i).substring(0, 15).equals("/~disp-clearAll")) {
                        this.questions.get(questionIndex).getCommands().add(new DispClearAll(lines.get(i).substring(0, 15), lines.get(i).substring(15)));

                        //player.clearAllCells();
                    }
                    // The key phrase to set a Braille cell to a string.
                    else if (lines.get(i).length() >= 17 && lines.get(i).substring(0, 17).equals("/~disp-cell-pins:")) {
                        Command temp;

                        try {
                            temp = new DispCellPins(lines.get(i).substring(0, 17), lines.get(i).substring(17));
                        } catch (IllegalArgumentException e) {
                            this.errorMessage = e.toString();
                            return;
                        }

                        this.questions.get(questionIndex).getCommands().add(temp);

                        //dispCellPins(lines.get(i).substring(17));
                    }
                    // The key phrase to represent a string in Braille.
                    else if (lines.get(i).length() >= 14 && lines.get(i).substring(0, 14).equals("/~disp-string:")) {
                        Command temp;

                        try {
                            temp = new DispString(lines.get(i).substring(0, 14), lines.get(i).substring(14));
                        } catch (IllegalArgumentException e) {
                            this.errorMessage = e.toString();
                            return;
                        }

                        this.questions.get(questionIndex).getCommands().add(temp);

                        // Checks if the string is composed of letters then displays it. If not logs it and exits
                        /*if (lines.get(i).substring(14).matches("^[a-zA-Z]+$")) {
                            player.displayString(lines.get(i).substring(14));
                        } else {
                            errorLog(IllegalArgumentException.class.toString(), "Invalid String for disp-string.");
                            System.exit(0);
                        }*/
                    }
                    // The key phrase to change the cell to represent a character in
                    // Braille.
                    else if (lines.get(i).length() >= 17 && lines.get(i).substring(0, 17).equals("/~disp-cell-char:")) {
                        Command temp;

                        try {
                            temp = new DispCellChar(lines.get(i).substring(0, 17), lines.get(i).substring(17));
                        } catch (IllegalArgumentException e) {
                            this.errorMessage = e.toString();
                            return;
                        }

                        this.questions.get(questionIndex).getCommands().add(temp);
                        //dispCellChar(lines.get(i).substring(17));
                    }
                    // The key phrase to raise a pin of the specified Braille cell.
                    else if (lines.get(i).length() >= 18 && lines.get(i).substring(0, 18).equals("/~disp-cell-raise:")) {
                        //dispCellRaise(lines.get(i).substring(18));
                    }
                    // The key phrase to lower a pin of the specified Braille cell.
                    else if (lines.get(i).length() >= 18 && lines.get(i).substring(0, 18).equals("/~disp-cell-lower:")) {
                        dispCellLower(lines.get(i).substring(18));
                    }
                    // The key phrase to clear a Braille cell.
                    else if (lines.get(i).length() >= 18 && lines.get(i).substring(0, 18).equals("/~disp-cell-clear:")) {
                        dispCellClear(lines.get(i).substring(18));
                    } else if (lines.get(i).length() >= 21 && lines.get(i).substring(0, 21).equals("/~disp-cell-lowerPins")) {
                        dispCellRaise("0");
                    }
                    // The key phrase to wait for the program to receive a user's input.
                    else if (lines.get(i).length() >= 12 && lines.get(i).substring(0, 12).equals("/~user-input")) {
                        userInput = true;
                    }
                    // Anything other than the specified commands above, is to be
                    // interpreted as text that
                    // will be spoken for the user to hear.
                    else {
                        this.questions.get(questionIndex).getCommands().add(new Text(lines.get(i)));
                        //repeatedText(lines.get(i));
                    }
                }
            } else {
                questionIndex++;
            }
        }
    }


    // Getters and Setters for private variables

    public int getCellNum() {
        return cellNum;
    }

    public void setCellNum(int cellNum) {
        this.cellNum = cellNum;
    }

    public int getButtonNum() {
        return buttonNum;
    }

    public void setButtonNum(int buttonNum) {
        this.buttonNum = buttonNum;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

	public static int getNumButtons() {
		return numButtons;
	}

	public static void setNumButtons(int numButtons) {
		ScenarioCreatorManager.numButtons = numButtons;
	}

	public static int getNumCells() {
		return numCells;
	}

	public static void setNumCells(int numCells) {
		ScenarioCreatorManager.numCells = numCells;
	}
}
