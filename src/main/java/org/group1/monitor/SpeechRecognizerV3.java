package org.group1.monitor;
import java.io.*;

public class SpeechRecognizerV3 {

    private BufferedWriter writer;
    private BufferedReader reader;
    private Process process;

    //TODO : Change these paths ( I couldn't use the relative paths)
    String pythonExecutablePath = "/usr/bin/python3";
    String pythonPathSR3 = "python/SR3.py";
    String pythonPathSV = "python/speakerVerification.py";
    String pythonPathTTS = "python/TTS.py";

    public SpeechRecognizerV3() {
        try {
            // Create the Python script command with the absolute path
            // We directly call the SR3 script from the constructor to call/load the model only once
            String[] cmd = {pythonExecutablePath, pythonPathSR3};
            // Create a new process builder
            ProcessBuilder processBuilder = new ProcessBuilder(cmd);
            // Redirect error stream to avoid deadlocks and consume the output stream from the subprocess
            processBuilder.redirectErrorStream(true);
            process = processBuilder.start();
            // Create writer to send input to the script
            writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
            // Create reader to read output from the script
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String transcribe(String audioFilePath) {
        String transcribedText = "";
        try {
            writer.write(audioFilePath);
            writer.newLine();
            writer.flush();
            String line;
            while (!(line = reader.readLine()).startsWith("Transcription:")) {
                // wait until we receive the transcription result
            }
            // Get the transcription result
            transcribedText = line.substring("Transcription:".length()).trim();
            //System.out.println("Transcription: " + transcribedText);
        } catch (Exception e) {
            return "";
        }
        return transcribedText;
    }

    public String getSpeakerID(String audioFilePath) {
        String identifiedSpeaker = "";

        try {

            String[] cmd = {pythonExecutablePath, pythonPathSV, audioFilePath};

            ProcessBuilder processBuilder = new ProcessBuilder(cmd);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                identifiedSpeaker = line.trim();
            }

            process.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return identifiedSpeaker;
    }



    public void textToSpeech(String text, String outputPath) {
        try {
            String[] cmd = {pythonExecutablePath, pythonPathTTS, text, outputPath};


            ProcessBuilder processBuilder = new ProcessBuilder(cmd);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            process.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void close() {
        try {
            // Send EOF to indicate we're done
            writer.close();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
