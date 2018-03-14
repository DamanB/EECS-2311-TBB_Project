package enamel;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private List<Command> commands;

    public Question()
    {
        commands = new ArrayList<>();
    }

    public Question(List<Command> commands) {
        this.commands = commands;
    }

    public List<Command> getCommands() {
        return commands;
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public String toString() {
        String result = "";

        for (Command i : this.commands) {
            result += i.toString() + "\n";
        }
        return result;
    }


}

