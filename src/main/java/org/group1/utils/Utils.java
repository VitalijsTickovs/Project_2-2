package org.group1.utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Utils {

    // Method to play a .wav file
    public static void playWav(String wavFilePath) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(wavFilePath));
            Clip audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);
            Object syncObject = new Object();
            audioClip.addLineListener(event ->
            {
                if (event.getType() == LineEvent.Type.STOP)
                {
                    synchronized (syncObject)
                    {
                        syncObject.notifyAll();
                    }
                }
            });
            audioClip.start();
            synchronized (syncObject)
            {
                syncObject.wait();
            }
        }
        // Handle possible exceptions
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
