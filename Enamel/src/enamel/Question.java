package enamel;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private List<Command> commands;

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
            result += i.toString();
        }
        return result;
    }

    /*public void setQuestion(String question) {
		this.question = question;
	}
	
	public String getQuestion() {return question;}	
	
	public int getCellOrientation(int cellIndex) {
		if (cellIndex >= cells.size()) {
			throw new IllegalArgumentException("Cell Index Out of Bounds");
		}else {
		 return cells.get(cellIndex); 
		}
	}

	public void addBrailleCell(int cellOrientation) {
			cells.add(cellOrientation);		
	}
	
	public void setIncorrectAnswerResponse(String incorrectAnswerResponse) {
		this.incorrectAnswerResponse = incorrectAnswerResponse;
	}

	public String getIncorrectAnswerResponse() {return incorrectAnswerResponse;}

	public void setCorrectAnswerResponse(String correctAnswerResponse) {
		this.correctAnswerResponse = correctAnswerResponse;
	}
	
	public String getCorrectAnswerResponse() {return correctAnswerResponse;}*/


}

