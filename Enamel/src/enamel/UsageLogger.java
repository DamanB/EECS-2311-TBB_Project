package enamel;

import org.junit.internal.ExactComparisonCriteria;
import org.omg.CORBA.DynAnyPackage.Invalid;

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

    private static String[] usageList = {
            "Player",
            "Editor"
    };

    public UsageLogger() {
        // Initialise text file
        // Update Map
        // Add test cases
        // Put all test cases in one folder
        // Add file verifier

        Scanner scanner = new Scanner(logFile);
        initialiseMap();
        if (logFile.ex)
        printWriter = new PrintWriter(logFile);

        while (scanner.hasNextLine()) {
            String[] temp = scanner.nextLine().split(",");
            // get key and value from string
            addElement(temp[0], Integer.parseInt(temp[1]));
        }
    }

    private void initialiseMap() {
        usageMap = new HashMap<>();

        usageMap.clear();

        for (String i : usageList) {
            usageMap.put(i, 0);
        }
    }

    private void addElement(String usageItem, int value) {
        if (usageMap.containsKey(usageItem)) {
            usageIncrement(usageItem, value);
        }
        else
        {
            usageMap.put(usageItem, value);
        }


    }

    public boolean usageIncrement(String usageItem) {
        usageIncrement(usageItem, 1);
    }

    public boolean usageIncrement(String usageItem, Integer value) {
        if (!usageMap.containsKey(usageItem)) {
            return false;
        }

        usageMap.put(usageItem, usageMap.get(usageItem) + value);
        return true;
    }

    private String mapToString()
    {
        String result = "";

        for (String i:usageList) {
            result+= i + "," + usageMap.get(i);
        }

        return result;
    }
}

