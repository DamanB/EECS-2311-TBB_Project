package enamel;

import javax.sound.sampled.*;
import java.io.*;
import java.util.Scanner;

/**
 * A sample program is to demonstrate how to record sound in Java
 * author: www.codejava.net
 * http://www.codejava.net/coding/capture-and-record-sound-into-wav-file-with-java-sound-api
 */
public class SoundRecorder {
    // record duration, in milliseconds
    private static final long RECORD_TIME = 60000;  // 1 minute

    // path of the wav file
    private File wavFile = new File("RecordAudio9.wav");

    // format of audio file
    private AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

    // the line from which audio data is captured
    private TargetDataLine line;

    public SoundRecorder(File wavFile) {
        this.wavFile = wavFile;
    }

    /**
     * Defines an audio format
     */
    AudioFormat getAudioFormat() {
        float sampleRate = 16000;
        int sampleSizeInBits = 8;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }

    /**
     * Captures the sound and record into a WAV file
     */
    public void start() {
        try {
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            // checks if system supports the data line
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
                System.exit(0);
            }
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();   // start capturing

            System.out.println("Start capturing...");

            AudioInputStream ais = new AudioInputStream(line);

            System.out.println("Start recording...");

            // start recording
            AudioSystem.write(ais, fileType, wavFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the target data line to finish capturing and recording
     */
    public void finish() {
        line.stop();
        line.close();
        System.out.println("Finished");
    }

    /**
     * Entry to run the program
     */
    public static void main(String[] args) {
        final SoundRecorder recorder = new SoundRecorder(new File("Testing2.wav"));

        // creates a new thread that waits for a specified
        // of time before stopping
        /*Thread stopper = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(RECORD_TIME);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                recorder.finish();
            }
        });

        stopper.start();*/


        /*Scanner scanner = new Scanner(System.in);

        if (scanner.nextLine().equals("s")) ;
        {
            System.out.println("Started");

            // start recording
            recorder.start();
        }

        System.out.println("Finished");
        recorder.finish();*/
    }
}