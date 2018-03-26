package enamel;

import com.sun.prism.Texture;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ToyAuthoring {

    // this this the origin version

    public static void main(String[] args) {
        UsageLogger.initialise();
        UsageLogger.usageIncrement(UsageLogger.UsageElements.Launch);
        MainFrame main = new MainFrame();
    }
}