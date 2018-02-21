package enamel;

import java.util.List;

/**
 * @author Sanjay Paraboo, Damanveer Bharaj, Penguin Guo
 * <p>
 * <p>
 * This class is used in ScenarioCreatorManager in the list of Question's. Each Question has a list of commands
 * which correlates to one line in the Scenario file. This class is used to keep track of the editing of Scenario
 * Files and checks if each command has a valid input. If not it will throw an exception.
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

        return (!input.equals("")) ? (this.command + " " + this.input) : (this.command);
    }
}

class Text extends Command {

    public Text(String input) {
        super("");
        this.regexPattern = ".*?";
        setInput(input);
    }

    @Override
    public String toString() {
        return this.input;
    }
}

class Pause extends Command {

    public Pause(String command, String input) {
        super(command);
        this.regexPattern = "^[1-9][0-9]*$";
        setInput(input);
    }
}

class Sound extends Command {
    public Sound(String command, String input) {
        super(command);
        this.regexPattern = "^[a-zA-Z1-9]+.wav$";
        setInput(input);
    }
}

class SkipButton extends Command {
    public SkipButton(String command, String input, Integer buttonSize) {
        super(command);
        this.regexPattern = "^[0-9]*\\s[A-Z]+$";
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

class Skip extends Command {
    public Skip(String command, String input) {
        super(command);
        this.regexPattern = "^[A-Z]+$";
        setInput(input);
    }
}

class Repeat extends Command {
    public Repeat(String command, String input) {
        super(command);
        setInput(input); // should be blank
    }
}

class EndRepeat extends Command {
    public EndRepeat(String command, String input) {
        super(command);
        setInput(input); // should be blank
    }
}

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

class ResetButtons extends Command {
    public ResetButtons(String command, String input) {
        super(command);
        setInput(input); // should be blank
    }
}

class DispClearAll extends Command {
    public DispClearAll(String command, String input) {
        super(command);
        setInput(input); // should be blank
    }
}

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

class DispString extends Command {
    public DispString(String command, String input) {
        super(command);
        this.regexPattern = "^[a-zA-Z]+$";
        setInput(input);
    }
}

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