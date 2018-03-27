package enamel;

import org.junit.internal.ExactComparisonCriteria;
import org.omg.CORBA.DynAnyPackage.Invalid;

import java.io.File;
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
    private static enum UsageElements {
        Player,
        Editor
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

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();

                if (line.contains(",")) {
                    temp = line.split(",");

                    for (UsageElements i : UsageElements.values()) {
                        if (i.toString().equals(temp[0])) {
                            addElement(i, Integer.parseInt(temp[1]));
                        }
                    }

                }
            }
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
            result += i + "," + usageMap.get(i);
        }

        return result;
    }
}

