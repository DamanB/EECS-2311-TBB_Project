package enamel;

import java.util.List;

/**
 * @author Sanjay Paraboo, Damanveer Bharaj, Penguin Guo
 *         <p>
 *         <p>
 *         This class is used in ScenarioCreatorManager in the list of Question's. Each Question has a list of commands
 *         which correlates to one line in the Scenario file. This class is used to keep track of the editing of Scenario
 *         Files and checks if each command has a valid input. If not it will throw an exception.
 */
//TODO Regex may error for overridden setInputs

public abstract class Command {
    protected String command;
    protected String input;
    protected String regexPattern = "^$"; // Default Pattern is a blank input

    protected Command(String command) {
        this.command = command;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        if (input.matches(regexPattern)) {
            this.input = input;
        } else {
            throw new IllegalArgumentException("Invalid format on a " + this.command + " command.");
        }
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        if (command.substring(0, 2).equals("/~")) {
            this.command = command;
        } else {
            throw new IllegalArgumentException(command + " is an Invalid Command");
        }
    }

    @Override
    public String toString() {
        return (!input.equals("")) ? (this.command + this.input) : (this.command);
    }
}
// Used for adding Text into the Scenario file for TTS
class Text extends Command {

    public Text(String input) {
        super("");
        this.regexPattern = "^[0-9A-Za-z]+$"; // Regex matches anything
        setInput(input);
    }

    @Override
    public String toString() {
        return this.input;
    }
}

// Used for writing Pause function to the file
class Pause extends Command {

    public Pause(String command, String input) {
        super(command);
        this.regexPattern = "^[1-9][0-9]*$"; // Matches any positive integer
        setInput(input);
    }
}

// Used to play a sound in the scenario file
class Sound extends Command {
    public Sound(String command, String input) {
        super(command);
        this.regexPattern = "^[a-zA-Z1-9]+.wav$"; // Matches alphanumeric value with extension *.wav
        setInput(input);
    }
}

// Used to add a SkipButton in the Scenario File
class SkipButton extends Command {
    public SkipButton(String command, String input, Integer buttonSize) {
        super(command);
        this.regexPattern = "^[0-9]*\\s[a-zA-Z]+$"; // Matches a valid button value and then a skip position
        setInput(input, buttonSize);
    }

    public void setInput(String input, Integer buttonSize) {
        String[] inputs = input.split("\\s");
        if (input.matches(regexPattern) && Integer.parseInt(inputs[0]) < buttonSize) {
            this.input = input;
        } else {
            throw new IllegalArgumentException("Invalid format on a " + this.command + " command.");
        }
    }
}

// Adds a skip position to the scenario file
class Skip extends Command {
    public Skip(String command, String input) {
        super(command);
        this.regexPattern = "^[a-zA-Z]+$"; // Matches any text in all caps
        setInput(input);
    }
}

// Adds a Repeat function to the scenario file
class Repeat extends Command {
    public Repeat(String command, String input) {
        super(command);
        setInput(input); // should be blank because no input
    }
}

// Ends the repeat call and wraps repeat text
class EndRepeat extends Command {
    public EndRepeat(String command, String input) {
        super(command);
        setInput(input); // should be blank because no input
    }
}

// Refers to a button that is used to repeat the text
class RepeatButton extends Command {
    public RepeatButton(String command, String input, Integer buttonSize) {
        super(command);
        this.regexPattern = "^[0-9]*$";
        setInput(input, buttonSize);

    }

    public void setInput(String input, Integer buttonSize) {
        if (input.matches(regexPattern) && Integer.parseInt(input) < buttonSize) {
            this.input = input;
        } else {
            throw new IllegalArgumentException("Invalid format on a " + this.command + " command.");
        }
    }
}

// Refers to a reset button that TODO
class ResetButtons extends Command {
    public ResetButtons(String command, String input) {
        super(command);
        setInput(input); // should be blank
    }
}

// Clears all the pins for every cell
class DispClearAll extends Command {
    public DispClearAll(String command, String input) {
        super(command);
        setInput(input); // should be blank
    }
}

// Displays a pattern on the specified braille cell
class DispCellPins extends Command {
    public DispCellPins(String command, String input, Integer brailleCellSize) {
        super(command);
        this.regexPattern = "^[0-9]*\\s[0-1]{8}$";
        setInput(input, brailleCellSize);
    }

    public void setInput(String input, Integer brailleCellSize) {
        String[] inputs = input.split("\\s");

        if (input.matches(regexPattern) && Integer.parseInt(inputs[0]) < brailleCellSize) {
            this.input = input;
        } else {
            throw new IllegalArgumentException("Invalid format on a " + this.command + " command.");
        }
    }
}

// Displays a string using the braille cells
class DispString extends Command {
    public DispString(String command, String input) {
        super(command);
        this.regexPattern = "^[a-zA-Z]+$";
        setInput(input);
    }
}

// Displays a character at the specified braille cell
class DispCellChar extends Command {
    public DispCellChar(String command, String input, Integer brailleCellSize) {
        super(command);
        this.regexPattern = "^[0-9]*\\s[a-zA-Z]$";
        setInput(input, brailleCellSize);
    }

    public void setInput(String input, Integer brailleCellSize) {
        String[] inputs = input.split("\\s");
        if (input.matches(regexPattern) && Integer.parseInt(inputs[0]) < brailleCellSize) {
            this.input = input;
        } else {
            throw new IllegalArgumentException("Invalid format on a " + this.command + " command.");
        }
    }
}

// TODO figure out the validity of the pin indices for cell raise and lower
// Specifies a cell to raise on a braille cell
class DispCellRaise extends Command {
    public DispCellRaise(String command, String input, Integer brailleCellSize) {
        super(command);
        this.regexPattern = "^[0-9]*\\s[1-8]$";
        setInput(input, brailleCellSize);
    }

    public void setInput(String input, Integer brailleCellSize) {
        String[] inputs = input.split("\\s");
        if (input.matches(regexPattern) && Integer.parseInt(inputs[0]) < brailleCellSize) {
            this.input = input;
        } else {
            throw new IllegalArgumentException("Invalid format on a " + this.command + " command.");
        }
    }
}

class DispCellLower extends Command {
    public DispCellLower(String command, String input, Integer brailleCellSize) {
        super(command);
        this.regexPattern = "^[0-9]*\\s[1-8]$";
        setInput(input, brailleCellSize);
    }

    public void setInput(String input, Integer brailleCellSize) {
        String[] inputs = input.split("\\s");
        if (input.matches(regexPattern) && Integer.parseInt(inputs[0]) < brailleCellSize) {
            this.input = input;
        } else {
            throw new IllegalArgumentException("Invalid format on a " + this.command + " command.");
        }
    }
}

class DispCellLowerPins extends Command {
    public DispCellLowerPins(String command, String input) {
        super(command);
        setInput(input);
    }
}

class DispCellClear extends Command {
    public DispCellClear(String command, String input, Integer brailleCellSize) {
        super(command);
        this.regexPattern = "^[0-9]*$";
        setInput(input, brailleCellSize);
    }

    public void setInput(String input, Integer brailleCellSize) {
        if (input.matches(regexPattern) && Integer.parseInt(input) < brailleCellSize) {
            this.input = input;
        } else {
            throw new IllegalArgumentException("Invalid format on a " + this.command + " command.");
        }
    }
}

class SkipPos extends Command {
    public SkipPos(String input) {
        super("/~");
        this.regexPattern = "^[a-zA-Z]+$";
        setInput(input);
    }

    public String toString() {
        return this.command + this.input;
    }

    public void setInput(String input) {
        if (input.matches(regexPattern)) {
            this.input = input;
        } else {
            throw new IllegalArgumentException("Invalid format on a SkipPos command.");
        }
    }
}

class UserInput extends Command {
    public UserInput(String command, String input) {
        super(command);
        setInput(input); // should be blank
    }
}

class Space extends Command {
    public Space() {
        super("");
        setInput("");
    }
}