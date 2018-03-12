package enamel;

//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

/**
 * @author Sanjay Paraboo, Damanveer Bharaj, Penguin Guo
 */

public class ScenarioCreatorManager {

    // Used to record the cell number, button number, a list of questions and the file to read/write to
    private int cellNum;                // Records Actual Value
    private int buttonNum;
    private String title = "";
    private static int numCells;
    private static int numButtons;
    private List<Question> questions;
    private File scenarioFile;
    private String errorMessage = "Nothing";
    private Integer questionIndex = 0;

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

    public static String[] userCommandList = {

            "End Repeat",
            "Sound",
            "Skip",
            "Pause", //DONE
            "Repeat Button",
            "Repeat",
            "Reset Buttons",
            "Skip Button",
            "Display Clear All",
            "Display Cell Pins",
            "Display Word on Braille", //DONE
            "Display Cell Character",
            "Display Cell Clear",
            "Display Cell Lower Pins",
            "User Input"


    };


    public ScenarioCreatorManager(File scenarioFile) {

        if (!(scenarioFile.getName().matches("^Scenario_[1-9][0-9]*.txt$"))) {
            throw new IllegalArgumentException("Invalid Scenario File Name Format");
        }

       /* if (!(scenarioFile.exists())) {
            try {
                scenarioFile.createNewFile();
            } catch (IOException e) {
                this.errorMessage = e.toString();
            }
        }
        */


        this.scenarioFile = scenarioFile;

        this.questions = new ArrayList<>();
        this.questions.add(new Question());
    }

    // Saves the list of questions, cell number and button numbers to the file.
    public void saveToFile() {
        String result = "";
        try {
            result = this.toString();

            // Erases the contents of the file
            PrintWriter printWriter = new PrintWriter(this.scenarioFile);
            /*
            printWriter.println("Cell " + this.cellNum);
            printWriter.println("Button " + this.buttonNum);
            if (!title.equals("")) {
                printWriter.println(this.title);
            }
            printWriter.println();*/
            printWriter.println(result);
            printWriter.close();

            JOptionPane.showMessageDialog(MainFrame.getMainPanel(), "BUILD SUCCESSFUL", "Builder", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            System.out.println(e.toString());
            this.errorMessage = e.toString();
        }
    }

    // Will parse an existing scenario file and create a list of questions from it and the cell/button numbers
    // TODO if Illegal Argument Exception is thrown return that the file has errors
    // TODO At the end check for skip buttons and repeat connecting to end repeat
    // TODO Check to see if only command and input is on the line if not throw error. Check input length?
    // TODO Check to see is user input is done after skip button and repeat button
    public boolean parseFile() {
        List<String> lines = new ArrayList<>();
        this.errorMessage = "";

        try (BufferedReader br = new BufferedReader(new FileReader(this.scenarioFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            br.close();
        } catch (FileNotFoundException e) {
            this.errorMessage = "File has not been found|" + e.toString();
            System.out.println(e.toString());
            return false;
        } catch (IOException e) {
            this.errorMessage = "Error when reading file|" + e.toString();
            System.out.println(e.toString());
            return false;
        }


        if (lines.get(0).matches("^Cell [1-9][0-9]*$") && lines.get(1).matches("^Button [1-9][0-9]*$")) {
            this.cellNum = Integer.parseInt(lines.get(0).substring(5));
            this.buttonNum = Integer.parseInt(lines.get(1).substring(7));
        } else {
            System.out.println("Invalid Cell or Button commands");
            this.errorMessage = "Invalid Cell or Button commands";
            return false;
        }

        int questionIndex = 0;
        boolean repeat = false, textReached = false;
        List<String> repeatedText = new ArrayList<>();

        for (int i = 2; i < lines.size(); i++) {
            if (!textReached) {
                if (!(lines.get(i).equals(""))) {
                    textReached = true;
                    this.setTitle(lines.get(i));
                    this.questions.add(new Question());
                }
            } else if (!lines.get(i).equals("")) {
                if (repeat) {
                    // Stops assuming that the text is being repeated with the /~endrepeat key phrase
                    if (lines.get(i).length() >= 11 && lines.get(i).substring(0, 11).equals("/~endrepeat")) {
                        repeat = false;
                        this.addText(repeatedText);
                        repeatedText = new ArrayList<>();
                    } else {
                        repeatedText.add(lines.get(i));
                    }
                } else {
                    // The key phrase to indicate to play a sound file.
                    // TODO check if sound file exists
                    if (lines.get(i).length() >= 8 && lines.get(i).substring(0, 8).equals("/~sound:")) {

                        Command temp;

                        try {
                            temp = new Sound(lines.get(i).substring(0, 8), lines.get(i).substring(8));
                        } catch (IllegalArgumentException e) {
                            this.errorMessage = e.toString();
                            return false;
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
                            return false;
                        }

                        this.questions.get(questionIndex).getCommands().add(temp);
                    }
                    // The key phrase to indicate to pause for a specified number of
                    // seconds.
                    else if (lines.get(i).length() >= 8 && lines.get(i).substring(0, 8).equals("/~pause:")) {

                        Command temp;

                        try {
                            temp = new Pause(lines.get(i).substring(0, 8), lines.get(i).substring(8));
                        } catch (IllegalArgumentException e) {
                            this.errorMessage = e.toString();
                            return false;
                        }

                        this.questions.get(questionIndex).getCommands().add(temp);
                    }
                    // The key phrase to assign a button to repeat text.
                    else if (lines.get(i).length() >= 16 && lines.get(i).substring(0, 16).equals("/~repeat-button:")) {
                        Command temp;

                        try {
                            // TODO figure out how to pass through button number from GUI
                            temp = new RepeatButton(lines.get(i).substring(0, 8), lines.get(i).substring(8), this.buttonNum);
                        } catch (IllegalArgumentException e) {
                            this.errorMessage = e.toString();
                            return false;
                        }

                        this.questions.get(questionIndex).getCommands().add(temp);
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
                    }
                    // The key phrase to assign a button to skip to another part of the
                    // scenario.
                    else if (lines.get(i).length() >= 14 && lines.get(i).substring(0, 14).equals("/~skip-button:")) {
                        Command temp;

                        try {
                            temp = new SkipButton(lines.get(i).substring(0, 14), lines.get(i).substring(14), this.buttonNum);
                        } catch (IllegalArgumentException e) {
                            this.errorMessage = e.toString();
                            return false;
                        }

                        this.questions.get(questionIndex).getCommands().add(temp);
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
                            temp = new DispCellPins(lines.get(i).substring(0, 17), lines.get(i).substring(17), this.cellNum);
                        } catch (IllegalArgumentException e) {
                            this.errorMessage = e.toString();
                            return false;
                        }

                        this.questions.get(questionIndex).getCommands().add(temp);
                    }
                    // The key phrase to represent a string in Braille.
                    else if (lines.get(i).length() >= 14 && lines.get(i).substring(0, 14).equals("/~disp-string:")) {
                        Command temp;

                        try {
                            temp = new DispString(lines.get(i).substring(0, 14), lines.get(i).substring(14));
                        } catch (IllegalArgumentException e) {
                            this.errorMessage = e.toString();
                            return false;
                        }

                        this.questions.get(questionIndex).getCommands().add(temp);
                    }
                    // The key phrase to change the cell to represent a character in
                    // Braille.
                    else if (lines.get(i).length() >= 17 && lines.get(i).substring(0, 17).equals("/~disp-cell-char:")) {
                        Command temp;

                        try {
                            temp = new DispCellChar(lines.get(i).substring(0, 17), lines.get(i).substring(17), this.cellNum);
                        } catch (IllegalArgumentException e) {
                            this.errorMessage = e.toString();
                            return false;
                        }

                        this.questions.get(questionIndex).getCommands().add(temp);
                    }
                    // The key phrase to raise a pin of the specified Braille cell.
                    else if (lines.get(i).length() >= 18 && lines.get(i).substring(0, 18).equals("/~disp-cell-raise:")) {

                        Command temp;

                        try {
                            temp = new DispCellRaise(lines.get(i).substring(0, 18), lines.get(i).substring(18), this.cellNum);
                        } catch (Exception e) {
                            this.errorMessage = e.toString();
                            return false;
                        }

                        this.questions.get(questionIndex).getCommands().add(temp);
                    }
                    // The key phrase to lower a pin of the specified Braille cell.
                    else if (lines.get(i).length() >= 18 && lines.get(i).substring(0, 18).equals("/~disp-cell-lower:")) {
                        Command temp;
                        try {
                            temp = new DispCellLower(lines.get(i).substring(0, 18), lines.get(i).substring(18), this.cellNum);
                        } catch (Exception e) {
                            this.errorMessage = e.toString();
                            return false;
                        }

                        this.questions.get(questionIndex).getCommands().add(temp);
                    }
                    // The key phrase to clear a Braille cell.
                    else if (lines.get(i).length() >= 18 && lines.get(i).substring(0, 18).equals("/~disp-cell-clear:")) {
                        Command temp;
                        try {
                            temp = new DispCellClear(lines.get(i).substring(0, 18), lines.get(i).substring(18), this.cellNum);
                        } catch (Exception e) {
                            this.errorMessage = e.toString();
                            return false;
                        }

                        this.questions.get(questionIndex).getCommands().add(temp);

                    } else if (lines.get(i).length() >= 21 && lines.get(i).substring(0, 21).equals("/~disp-cell-lowerPins")) {
                        Command temp;
                        try {
                            temp = new DispCellLowerPins(lines.get(i).substring(0, 21), lines.get(i).substring(21));
                        } catch (Exception e) {
                            this.errorMessage = e.toString();
                            return false;
                        }

                        this.questions.get(questionIndex).getCommands().add(temp);
                    }
                    // The key phrase to wait for the program to receive a user's input.
                    else if (lines.get(i).length() >= 12 && lines.get(i).substring(0, 12).equals("/~user-input")) {
                        Command temp;
                        try {
                            temp = new UserInput(lines.get(i).substring(0, 12), lines.get(i).substring(12));
                        } catch (Exception e) {
                            this.errorMessage = e.toString();
                            return false;
                        }

                        this.questions.get(questionIndex).getCommands().add(temp);
                    }
                    // Anything other than the specified commands above, is to be
                    // interpreted as text that
                    // will be spoken for the user to hear.
                    else {
                        this.questions.get(questionIndex).getCommands().add(new Text(lines.get(i)));
                    }
                }
            } else {
                questionIndex++;
                this.questions.add(new Question());
                this.questions.get(questionIndex).getCommands().add(new Space());
            }
        }
        return true;
    }


    // Getters and Setters for private variables

    public int getCellNum() {
        return cellNum;
    }

    public void setCellNum(int cellNum) {
        this.cellNum = cellNum;
        this.numCells = cellNum;
    }

    public int getButtonNum() {
        return buttonNum;
    }

    public void setButtonNum(int buttonNum) {
        this.buttonNum = buttonNum;
        this.numButtons = buttonNum;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public static int getNumCells() {
        return numCells;
    }

    public Integer getQuestionIndex() {
        return questionIndex;
    }

    public void setQuestionIndex(Integer questionIndex) {
        this.questionIndex = questionIndex;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    //
    public void nextQuestion() {
        this.questions.get(this.questionIndex).getCommands().add(new Space());
        this.questionIndex++;
        this.questions.add(new Question());
    }

    // Adds a command to the current question
    public boolean addText(List<String> textLines) {
        for (String i : textLines) {
            try {
                this.questions.get(this.questionIndex).getCommands().add(new Text(i));
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
        return true;
    }

    //
    public boolean addPause(String pauseTime) {
        try {
            this.questions.get(this.questionIndex).getCommands().add(new Pause("/~pause:", pauseTime));
        } catch (IllegalArgumentException e) {
            this.errorMessage = e.toString();
            return false;
        }
        return true;
    }

    //
    public boolean addSound(String fileName) {
        try {
            this.questions.get(this.questionIndex).getCommands().add(new Sound("/~sound:", fileName));
        } catch (IllegalArgumentException e) {
            this.errorMessage = e.toString();
            return false;
        }
        return true;
    }


    public boolean addSkipButton(String jButtonIndex, String goToBtn) {
        try {
            this.questions.get(this.questionIndex).getCommands().add(new SkipButton("/~skip-button:", jButtonIndex + " " + goToBtn, this.buttonNum));
        } catch (IllegalArgumentException e) {
            this.errorMessage = e.toString();
            return false;
        }
        return true;
    }

    public boolean addSkip(String goToBtn) {
        try {
            this.questions.get(this.questionIndex).getCommands().add(new Skip("/~skip:", goToBtn));
        } catch (IllegalArgumentException e) {
            this.errorMessage = e.toString();
            return false;
        }
        return true;
    }

    public boolean addRepeat() {
        try {
            this.questions.get(this.questionIndex).getCommands().add(new Repeat("/~repeat", ""));
        } catch (IllegalArgumentException e) {
            this.errorMessage = e.toString();
            return false;
        }
        return true;
    }

    public boolean addEndRepeat() {
        try {
            this.questions.get(this.questionIndex).getCommands().add(new EndRepeat("/~end-repeat", ""));
        } catch (IllegalArgumentException e) {
            this.errorMessage = e.toString();
            return false;
        }
        return true;
    }

    public boolean addRepeatButton(String jButtonIndex) {
        try {
            this.questions.get(this.questionIndex).getCommands().add(new RepeatButton("/~repeat-button:", jButtonIndex, this.buttonNum));
        } catch (IllegalArgumentException e) {
            this.errorMessage = e.toString();
            return false;
        }
        return true;
    }

    public boolean addResetButtons() {
        try {
            this.questions.get(this.questionIndex).getCommands().add(new ResetButtons("/~reset-buttons", ""));
        } catch (IllegalArgumentException e) {
            this.errorMessage = e.toString();
            return false;
        }
        return true;
    }

    public boolean addDispClearAll() {
        try {
            this.questions.get(this.questionIndex).getCommands().add(new DispClearAll("/~disp-clearAll", ""));
        } catch (IllegalArgumentException e) {
            this.errorMessage = e.toString();
            return false;
        }
        return true;
    }

    public boolean addDispCellPins(String cellPos, String charSeq) {
        try {
            this.questions.get(this.questionIndex).getCommands().add(new DispCellPins("/~disp-cell-pins:", cellPos + " " + charSeq, this.cellNum));
        } catch (IllegalArgumentException e) {
            this.errorMessage = e.toString();
            return false;
        }
        return true;
    }

    public boolean addDispString(String stringDisp) {
        try {
            this.questions.get(this.questionIndex).getCommands().add(new DispString("/~disp-string:", stringDisp));
        } catch (IllegalArgumentException e) {
            this.errorMessage = e.toString();
            return false;
        }
        return true;
    }

    public boolean addDispCellChar(String cellPos, String cellChar) {
        try {
            this.questions.get(this.questionIndex).getCommands().add(new DispCellChar("/~disp-cell-char:", cellPos + " " + cellChar, this.cellNum));
        } catch (IllegalArgumentException e) {
            this.errorMessage = e.toString();
            return false;
        }
        return true;
    }

    public boolean addDispCellRaise(String cellPos, String pinNum) {
        try {
            this.questions.get(this.questionIndex).getCommands().add(new DispCellRaise("/~disp-cell-raise:", cellPos + " " + pinNum, this.cellNum));
        } catch (IllegalArgumentException e) {
            this.errorMessage = e.toString();
            return false;
        }
        return true;
    }

    public boolean addDispCellLower(String cellPos, String pinNum) {
        try {
            this.questions.get(this.questionIndex).getCommands().add(new DispCellLower("/~disp-cell-lower:", cellPos + " " + pinNum, this.cellNum));
        } catch (IllegalArgumentException e) {
            this.errorMessage = e.toString();
            return false;
        }
        return true;
    }

    public boolean addDispCellClear(String cellPos) {
        try {
            this.questions.get(this.questionIndex).getCommands().add(new DispCellClear("/~disp-cell-clear:", cellPos, this.cellNum));
        } catch (IllegalArgumentException e) {
            this.errorMessage = e.toString();
            return false;
        }
        return true;
    }

    public boolean addUserInput() {
        try {
            this.questions.get(this.questionIndex).getCommands().add(new UserInput("/~user-input", ""));
        } catch (IllegalArgumentException e) {
            this.errorMessage = e.toString();
            return false;
        }
        return true;
    }

    public boolean addSkipPos(String input) {
        try {
            this.questions.get(this.questionIndex).getCommands().add(new SkipPos(input));
        } catch (IllegalArgumentException e) {
            this.errorMessage = e.toString();
            return false;
        }
        return true;
    }

    public boolean addSpace() {
        try {
            this.questions.get(this.questionIndex).getCommands().add(new Space());
        } catch (IllegalArgumentException e) {
            this.errorMessage = e.toString();
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String result = "Cell " + this.cellNum + "\n";
        result += "Button" + this.buttonNum + "\n";
        result += this.title + "\n";


        for (Question i : this.questions) {
            result += i.toString();
        }

        return result;
    }

    //////////////////////////////////////// TESTING /////////////////////////////////////
    // The following main method is used to test
    /*public static void main(String[] args) {
        ScenarioCreatorManager scenarioCreatorManager = new ScenarioCreatorManager(new File("Enamel/FactoryScenarios/Scenario_1.txt"));

        scenarioCreatorManager.parseFile();

        System.out.println(scenarioCreatorManager.toString());
    }*/
    // The following example recreated the Scenario_1.txt under the file name Scenario_10.txt
    // Tunning this main method will create a Scenario_10.txt which will be exactly the same as Scenario_1.txt
    public static void main(String[] args) {

        File temp = (new File("Enamel/FactoryScenarios/Scenario_10.txt"));

        // Initialise ScenarioCreatorManager and pass through file object as param
        ScenarioCreatorManager scenarioCreatorManager = new ScenarioCreatorManager(temp);

        // You MUST set cell and button num before adding any commands.
        // Title command is optional
        scenarioCreatorManager.setCellNum(1);
        scenarioCreatorManager.setButtonNum(4);
        scenarioCreatorManager.setTitle("Directional Orientation");

        // Every method for every command returns a boolean which tells you whether the input was valid or not
        //When it returns false it will update the errorMessage string which can be accessed with getErrorMessage();
        if (!(scenarioCreatorManager.addSpace())) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addDispCellPins("0", "11100000"))) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addText(Arrays.asList("These are pins 1, 2 and 3, the 3 pins on the left side. ", "Press button 1 to continue.")))) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addSkipButton("0", "ONEE"))) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addUserInput())) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        // Use next question to increment the question index and put a space in the ScenarioFile for the next question
        scenarioCreatorManager.nextQuestion();

        if (!(scenarioCreatorManager.addSkipPos("ONEE"))) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addDispCellClear("0"))) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addPause("1"))) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addResetButtons())) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addDispCellPins("0", "00011100"))) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addText(Arrays.asList("These are pins 4, 5 and 6, the 3 pins on the right side. ", "Press button 1 to continue.")))) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addSkipButton("0", "ONEE"))) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addUserInput())) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        scenarioCreatorManager.nextQuestion();

        if (!(scenarioCreatorManager.addSkipPos("ONEE"))) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addDispCellClear("0"))) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addPause("1"))) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addResetButtons())) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addDispCellPins("0", "11000000"))) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addText(Arrays.asList("These are pins 4, 5 and 6, the 3 pins on the right side.", "Press button 1 to continue.")))) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addSkipButton("0", "ONEE"))) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addUserInput())) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }


        scenarioCreatorManager.nextQuestion();

        if (!(scenarioCreatorManager.addSkipPos("ONEE"))) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addDispCellClear("0"))) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addPause("1"))) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addResetButtons())) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addDispCellPins("0", "00011000"))) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addText(Arrays.asList("These are pins 4 and 5, the top two pins on the right side. Press button 1 to continue.")))) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addSkipButton("0", "ONEE"))) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addUserInput())) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        scenarioCreatorManager.nextQuestion();

        if (!(scenarioCreatorManager.addSkipPos("ONEE"))) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addDispCellClear("0"))) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addPause("1"))) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addResetButtons())) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addDispCellPins("0", "10010000"))) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addText(Arrays.asList("These are pins 1 and 4, the two pins on the top. Press button 1 to continue.")))) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addSkipButton("0", "ONEE"))) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addUserInput())) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        scenarioCreatorManager.nextQuestion();

        if (!(scenarioCreatorManager.addSkipPos("ONEE"))) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addDispCellClear("0"))) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addPause("1"))) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addResetButtons())) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addDispCellPins("0", "10010000"))) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addText(Arrays.asList("These are pins 3 and 6, the two pins on the bottom. Press button 1 to continue.")))) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addSkipButton("0", "ONEE"))) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addUserInput())) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        scenarioCreatorManager.nextQuestion();

        if (!(scenarioCreatorManager.addSkipPos("ONEE"))) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addText(Arrays.asList("That's the end of directional orientation")))) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }

        if (!(scenarioCreatorManager.addDispCellClear("0"))) {
            System.out.println(scenarioCreatorManager.errorMessage);
        }


        // After all the commands have been added saveFile() 
        scenarioCreatorManager.saveToFile();
    }
    //////////////////////////////////////// TESTING /////////////////////////////////////

}
