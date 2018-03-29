package enamel;

public class ToyAuthoring {

	// this this the origin version

	public static void main(String[] args) {
		UsageLogger.initialise();
		UsageLogger.usageIncrement(UsageLogger.UsageElements.Launch);
		new MainFrame();
	}
}