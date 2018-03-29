package enamel;

public class GUIBuilder {

	public GUIBuilder() {}
		
	public static void setNumButtons(int numButtons) {
		ScenarioCreatorGUI.numButtons = numButtons;
		ScenarioCreatorGUI.EditorGUI.numberOfButtons.setValue(numButtons);
	}
	
	public static void setNumCells (int numCells) {
		ScenarioCreatorGUI.numCells = numCells;
		ScenarioCreatorGUI.EditorGUI.numberOfBraille.setValue(numCells);
	}
	
	public static void setTitle(String title) {
		JActionConfigure config = new JActionConfigure();
		JActionConfigure.Title action = config.new Title();		
		config.action = action;
		ScenarioCreatorGUI.EditorGUI.addCheckpointNode(config, title, true);
	}
		
	public static void createPause(int pauseTime) {		
		JActionConfigure config = new JActionConfigure();
		JActionConfigure.JPauseButton action = config.new JPauseButton(pauseTime);		
		config.action = action;
		ScenarioCreatorGUI.EditorGUI.addActionNode(config);		
	}

	public static void createDisplayWordInBraille(String word) {		
		JActionConfigure config = new JActionConfigure();
		JActionConfigure.JDisplayWordInBraille action = config.new JDisplayWordInBraille(word);		
		config.action = action;
		ScenarioCreatorGUI.EditorGUI.addActionNode(config);		
	}
	
	public static void createRepeat (String text, int buttonIndex) {		
		JActionConfigure config = new JActionConfigure();
		JActionConfigure.JRepeat action = config.new JRepeat(text, buttonIndex);		
		config.action = action;
		ScenarioCreatorGUI.EditorGUI.addActionNode(config);		
	}
	
	public static void createCheckpointTravelButtonClick(int checkpointIndex, int buttonIndex) {		
		JActionConfigure config = new JActionConfigure();
		JActionConfigure.GoToCheckpointButtonClick action = config.new GoToCheckpointButtonClick(checkpointIndex, buttonIndex);		
		config.action = action;
		ScenarioCreatorGUI.EditorGUI.addActionNode(config);		
	}
	
	public static void createClearCells() {		
		JActionConfigure config = new JActionConfigure();
		JActionConfigure.JClearCells action = config.new JClearCells();		
		config.action = action;
		ScenarioCreatorGUI.EditorGUI.addActionNode(config);		
	}
	
	public static void createClearSpecificCell(int cellIndex) {		
		JActionConfigure config = new JActionConfigure();
		JActionConfigure.JClearSpecificCell action = config.new JClearSpecificCell(cellIndex);		
		config.action = action;
		ScenarioCreatorGUI.EditorGUI.addActionNode(config);		
	}
	
	public static void createDisplayCharacter(char character, int cellIndex) {		
		JActionConfigure config = new JActionConfigure();
		JActionConfigure.JDisplayChar action = config.new JDisplayChar(character, cellIndex);		
		config.action = action;
		ScenarioCreatorGUI.EditorGUI.addActionNode(config);		
	}
	
	public static void createDisplayCellPins(String configuration, int cellIndex) {
		JActionConfigure config = new JActionConfigure();
		JActionConfigure.JDisplayPins action = config.new JDisplayPins(configuration, cellIndex);		
		config.action = action;
		ScenarioCreatorGUI.EditorGUI.addActionNode(config);		
	}
	
	public static void createTextToSpeech(String text) {		
		JActionConfigure config = new JActionConfigure();
		JActionConfigure.JTextToSpeech action = config.new JTextToSpeech(text);		
		config.action = action;
		ScenarioCreatorGUI.EditorGUI.addActionNode(config);		
	}
	
	public static void createUserInput() {		
		JActionConfigure config = new JActionConfigure();
		JActionConfigure.JUserInput action = config.new JUserInput();		
		config.action = action;
		ScenarioCreatorGUI.EditorGUI.addActionNode(config);		
	}
	
	public static void createGoToCheckpoint(int eventIndex) {		
		JActionConfigure config = new JActionConfigure();
		JActionConfigure.GoToEvent action = config.new GoToEvent(eventIndex);		
		config.action = action;
		ScenarioCreatorGUI.EditorGUI.addActionNode(config);		
	}
	
	public static void createResetButtons() {		
		JActionConfigure config = new JActionConfigure();
		JActionConfigure.JResetButtons action = config.new JResetButtons();		
		config.action = action;
		ScenarioCreatorGUI.EditorGUI.addActionNode(config);		
	}
	
	public static void createRaisePin(int pinToRaise, int cellIndex) {		
		JActionConfigure config = new JActionConfigure();
		JActionConfigure.JRaisePins action = config.new JRaisePins(cellIndex,pinToRaise);		
		config.action = action;
		ScenarioCreatorGUI.EditorGUI.addActionNode(config);		
	}
	
	public static void createLowerPin(int pinToLower, int cellIndex) {		
		JActionConfigure config = new JActionConfigure();
		JActionConfigure.JLowerPins action = config.new JLowerPins(cellIndex,pinToLower);		
		config.action = action;
		ScenarioCreatorGUI.EditorGUI.addActionNode(config);		
	}
	
	public static void createPlayAudio(String filename) {
		JActionConfigure config = new JActionConfigure();
		JActionConfigure.PlayAudio action = config.new PlayAudio(filename);		
		config.action = action;
		ScenarioCreatorGUI.EditorGUI.addActionNode(config);		
	}
	
	public static void createCheckpoint(String title) {
		JActionConfigure config = new JActionConfigure();
		JActionConfigure.Checkpoint action = config.new Checkpoint();		
		config.action = action;
		ScenarioCreatorGUI.EditorGUI.addCheckpointNode(config, title, false);
	}
	
}
