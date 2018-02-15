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
    protected String regexPattern = "^$";

    protected Command(String command, String input) {
        this.command = command;
        setInput(input);
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
        return this.command + " " + this.input;
    }
}

class Text extends Command {

    public Text(String input) {
        super("", input);
        this.regexPattern = ".*?";
    }

    @Override
    public String toString() {
        return this.input;
    }
}

class Pause extends Command {

    public Pause(String command, String input) {
        super(command, input);
        this.regexPattern = "^[1-9][0-9]*$";
    }
}

class Sound extends Command {
    public Sound(String command, String input) {
        super(command, input);
        this.regexPattern = "^[a-zA-Z1-9]+.wav$";
    }
}

class SkipButton extends Command {
    public SkipButton(String command, String input) {
        super(command, input);
        this.regexPattern = "^[1-9][0-9]*\\s[A-Z]+$";
    }
}

class Skip extends Command {
    public Skip(String command, String input) {
        super(command, input);
        this.regexPattern = "^[A-Z]+$";
    }
}

class Repeat extends Command {
    public Repeat(String command, String input) {
        super(command, input);
    }
}

class EndRepeat extends Command {
    public EndRepeat(String command, String input) {
        super(command, input);
    }
}

class RepeatButton extends Command {

    public RepeatButton(String command, String input, Integer buttonSize) {
        super(command, input);
        this.regexPattern = "^[1-9][0-9]*$";
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
        super(command, input);
    }
}

class DispClearAll extends Command {
    public DispClearAll(String command, String input) {
        super(command, input);
    }
}

class DispCellPins extends Command {
    public DispCellPins(String command, String input) {
        super(command, input);
        this.regexPattern = "^[1-9][0-9]*\\s[0-1]{8}$";
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
        super(command, input);
        this.regexPattern = "^[a-zA-Z]+$";
    }
}

class DispCellChar extends Command {
    public DispCellChar(String command, String input) {
        super(command, input);
        this.regexPattern = "^[1-9][0-9]*\\s[a-zA-Z]$";
        // TODO figure out how to pass through braille cell size
    }

    public void setInput(String command, Integer brailleCellSize) {
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
    public DispCellRaise(String command, String input) {
        super(command, input);
        this.regexPattern = "^[1-9][0-9]*\\s[1-8]$";
    }
}

class DispCellLower extends Command {
    public DispCellLower(String command, String input) {
        super(command, input);
        this.regexPattern = "^[1-9][0-9]*\\s[1-8]$";
    }
}

class DispCellLowerPins extends Command {
    public DispCellLowerPins(String command, String input) {
        super(command, input);
    }
}

class DispCellClear extends Command {
    public DispCellClear(String command, String input) {
        super(command, input);
        this.regexPattern = "^[1-9][0-9]*$";
    }

    public void setInput(String command, Integer brailleCellSize) {
        if (input.matches(regexPattern) && Integer.parseInt(input) < brailleCellSize) {
            this.input = input;
        } else {
            throw new IllegalArgumentException("Invalid format on a " + this.command + " command.");
        }
    }
}

class UserInput extends Command {
    public UserInput(String command, String input) {
        super(command, input);
    }
}