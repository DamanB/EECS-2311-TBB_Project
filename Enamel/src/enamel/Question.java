package enamel;

import java.util.ArrayList;
import java.util.List;

public class Question {
	private String question;
	private String correctAnswerResponse;
	private String incorrectAnswerResponse;
	private List<Integer> cells = new ArrayList<Integer>();
	private List<Command> commands;
	
	public void setQuestion(String question) {
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
	
	public String getCorrectAnswerResponse() {return correctAnswerResponse;}


}

