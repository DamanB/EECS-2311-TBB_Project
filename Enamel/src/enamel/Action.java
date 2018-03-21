package enamel;

public abstract class Action {

	public String name;
	
	public abstract boolean build(ScenarioCreatorManager sm);
	public String toString() {
		return this.name;
	}
	
}
