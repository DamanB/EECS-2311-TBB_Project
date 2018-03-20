package enamel;

public abstract class Action {

	public abstract boolean build(ScenarioCreatorManager sm);
	public String getName() {
		return this.getClass().getName();
	}
	
}
