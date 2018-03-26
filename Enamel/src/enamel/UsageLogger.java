package enamel;

import org.junit.internal.ExactComparisonCriteria;
import org.omg.CORBA.DynAnyPackage.Invalid;

import java.io.File;
import java.io.PrintWriter;
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
    private static String[] usageList = {
            "Player",
            "Editor"
    };

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
            while (scanner.hasNextLine()) {
                String[] temp = scanner.nextLine().split(",");
                // get key and value from string
                addElement(temp[0], Integer.parseInt(temp[1]));
            }
        }
    }

    private static void createFile() {
        try {
            printWriter = new PrintWriter(fileName);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private static void initialiseMap() {
        usageMap = new HashMap<>();

        usageMap.clear();

        for (String i : usageList) {
            usageMap.put(i, 0);
        }
    }

    private static void addElement(String usageItem, int value) {
        if (usageMap.containsKey(usageItem)) {
            usageIncrement(usageItem, value);
        } else {
            usageMap.put(usageItem, value);
        }


    }

    public static boolean usageIncrement(String usageItem) {
        usageIncrement(usageItem, 1);
        return true;
    }

    private static boolean usageIncrement(String usageItem, Integer value) {
        if (!usageMap.containsKey(usageItem)) {
            return false;
        }

        usageMap.put(usageItem, usageMap.get(usageItem) + value);
        return true;
    }

    private static void saveMap() {
        printWriter.println(mapToString());
        printWriter.close();
    }

    private static String mapToString() {
        String result = "";

        for (String i : usageList) {
            result += i + "," + usageMap.get(i);
        }

        return result;
    }
}

