package enamel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Sanjay on 3/21/2018.
 */
public class UsageLogger {
    private static String logFile = "USAGE_LOG.txt";
    private static Map<String, Integer> usageMap;
    private static PrintWriter printWriter;
    private static File fileName;

    // TODO Add more options for each UI element
    public static enum UsageElements {
    	Checkpoint, 
		TexttoSpeech, // DONE
		PlayAudio, // DONE
		Pause, // DONE
		DisplayPinsonBrailleCell, // DONE
		DisplayWordwithBrailleCells, // DONE
		DisplayCharacteronBrailleCell, // DONE
		ClearAllBrailleCells, // DONE
		ClearSpecificBrailleCell, // DONE
		LowerPinonCell, // DONE
		RaisePinonCell, // DONE
		RepeatTextwithButtonClick, // DONE
		GotoCheckpoint, // DONE
		GotoCheckpointwithButtonClick, // DONE
		ResetButtonConfigurations, // DONE
		UserInput // DONE
    }

    public static void initialise() {
        // Initialise text file
        // Update Map
        // Add test cases
        // Put all test cases in one folder
        // Add file verifier

        fileName = new File(logFile);
        Scanner scanner = new Scanner(logFile);

        initialiseMap();

        if (!fileName.exists()) {
            createFile();
        } else {

            String line = "";
            String[] temp;

            try (BufferedReader br = new BufferedReader(new FileReader(logFile))) {
                while ((line = br.readLine()) != null) {
                    if (line.contains(",")) {
                        temp = line.split(",");

                        addElement(UsageElements.valueOf(temp[0]), Integer.parseInt(temp[1]));

                    }
                }
            } catch (Exception e) {
                System.out.println("File Error: " + e.toString());
            }

            saveMap();
        }
    }


    private static void initialiseMap() {
        usageMap = new HashMap<>();

        usageMap.clear();

        for (UsageElements i : UsageElements.values()) {
            usageMap.put(i.toString(), 0);
        }
    }

    private static void addElement(UsageElements element, int value) {
        if (usageMap.containsKey(element.toString())) {
            usageIncrement(element, value);
        } else {
            usageMap.put(element.toString(), value);
        }
        if (!fileName.exists()) {
            createFile();
        }
        saveMap();
    }

    public static boolean usageIncrement(UsageElements element) {
        usageIncrement(element, 1);
        return true;
    }

    private static boolean usageIncrement(UsageElements element, Integer value) {
        if (!usageMap.containsKey(element.toString())) {
            return false;
        }

        usageMap.put(element.toString(), usageMap.get(element.toString()) + value);
        saveMap();
        return true;
    }

    private static void saveMap() {
        try {
            printWriter = new PrintWriter(fileName);
            printWriter.println(mapToString());
            printWriter.close();
        } catch (Exception e) {
            System.out.print("File writing error");
        }
    }

    private static void createFile() {
        try {
            printWriter = new PrintWriter(fileName);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private static String mapToString() {
        String result = "";

        for (UsageElements i : UsageElements.values()) {
            result += i.toString() + "," + usageMap.get(i.toString()) + "\n";
        }

        return result;
    }
}

