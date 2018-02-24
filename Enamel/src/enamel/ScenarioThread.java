package enamel;

public class ScenarioThread extends Thread{
    String fileName;

    public ScenarioThread(String fileName)
    {
        this.fileName = fileName;
    }

    @Override
    public void run()
    {
        ScenarioParser s = new ScenarioParser(true);
        s.setScenarioFile(this.fileName);
    }
}
