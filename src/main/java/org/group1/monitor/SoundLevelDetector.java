package org.group1.monitor;

import org.group1.effects.DynamicRangeGate;
import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import org.group1.utils.Converter;
import org.group1.utils.SpellingCorrector;
import org.group1.utils.Utils;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import static org.group1.utils.Language.AMERICAN;

public class SoundLevelDetector {

    // MicMonitoring
    boolean micMonitor;

    // AutoMicThread
    public Thread autoMicThread;
    public boolean micOn = true;

    // TextToSpeech
    boolean ttsEngaged = false;

    // Dispatcher
    private AudioDispatcher dispatcher;

    // Recorded buffers
    private ArrayList<float[]> recordedBuffers = new ArrayList<>();

    // History of transcribed text
    Stack<String> conversation = new Stack<>();

    // WAV
    private static final int BLOCK_SIZE = 1024;

    // LoPie Audio Suite
    DynamicRangeGate gate = new DynamicRangeGate(-50, 1000, 16000);
    Converter cnv = new Converter();

    // SpeechRecognition Model
    SpeechRecognizerV3 sr;
    // Spelling corrector object
    SpellingCorrector spellingCorrector = new SpellingCorrector(AMERICAN);

    // Threshold passed for first time
    boolean firstRec = false;

    // Silence counter (extra gate control)
    long silenceCounter = 10;

    // Hopefully
    public void monitorMicAudio() {

        while(!micOn)  {

            // stop

        }


            // Load a SpeechRecognizer model
        sr = new SpeechRecognizerV3();

        try {
            // Recorded material to analyze using SR
            ArrayList<float[]> recordedBuffers = new ArrayList<>();

            // Define the mic as input stream of audio
            AudioFormat format = new AudioFormat(44100, 16, 1, true, false);
            TargetDataLine line = AudioSystem.getTargetDataLine(format);
            line.open(format);
            line.start();

            // Use AudioDispatcher for block size 1024
            int overlap = 0;
            AudioDispatcher dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(BLOCK_SIZE, overlap);

            // Measure the loudness using a processor and display in real-time
            dispatcher.addAudioProcessor(new AudioProcessor() {


                float[] processedBuffer = new float[BLOCK_SIZE];

                // Count silence duration additional to gate release
                long silentIter = 0;

                @Override
                public boolean process(AudioEvent audioEvent) {
                    // Current buffer
                    float[] audioBuffer = audioEvent.getFloatBuffer();

                    // Apply the gate
                    processedBuffer = gate.process(audioBuffer);

                    // Append the recorded list
                    recordedBuffers.add(Arrays.copyOf(processedBuffer, processedBuffer.length));

                    // Activate
                    if(processedBuffer[0]!=0){
                        firstRec=true;
                        System.out.println("activated");
                    }

                    // Accumulate for stop recording later
                    if(processedBuffer[0]==0 && firstRec){
                        silentIter++;
                    }

                    if(silentIter>silenceCounter){
                        try {

                            // TODO:
                            //      - Identify Speaker

                            // Make .WAV from recorded buffers
                            cnv.makeWAV(recordedBuffers);
                            System.out.println("First rec done..");

                            // Reset bookkeeping
                            silentIter=0;
                            firstRec=false;

                            // Transcribe
                            String transcribedText = sr.transcribe("out16.wav");

                            // Conversation
                            conversation.push(transcribedText);
                            recordedBuffers.clear();

                            // TTS
                            String chatBotResponse = "";
                            String outputPath = "/Users/lorispodevyn/Desktop/pie_is_cool/VersionControl/silentbot.wav";
                            boolean silentBot = false;

                            // Check silentBot
                            if(chatBotResponse.equals("")) {
                                outputPath = "/Users/lorispodevyn/Desktop/pie_is_cool/VersionControl/silentbot.wav";
                                silentBot=true;
                            } else {
                                outputPath = "/Users/lorispodevyn/Desktop/pie_is_cool/VersionControl/tts_Loris.wav";
                            }

                            // SilentBot
                            if(silentBot) {
                                ttsEngaged = true;
                                while(ttsEngaged) {
                                    Utils.playWav("silent.wav");
                                    System.out.println("answer given");
                                    ttsEngaged = false;
                                }
                                silentBot=false;

                            } else  {

                                sr.textToSpeech(chatBotResponse,outputPath);
                                ttsEngaged = true;
                                while(ttsEngaged) {
                                    Utils.playWav(outputPath);
                                    System.out.println("answer given");
                                    ttsEngaged = false;
                                }
                            }


                            System.out.println("Program continues");



                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    // Check Thread Situation
                    if (autoMicThread.isInterrupted()) {
                        // stop processing and cleanup if needed
                        return false;
                    } else {
                        return true;
                    }


                }

                @Override
                public void processingFinished() {
                }
            });

            // Thread (
            //new Thread(dispatcher::run).start();
            autoMicThread = new Thread(dispatcher::run);
            autoMicThread.start();

        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Constructor
    public SoundLevelDetector() {

    }


    // Stop the autoMicThread
    public void stopAutoMic() {

        System.out.println("Stopping autoMic");
        if (autoMicThread != null) {
            autoMicThread.interrupt();
        }
    }



    // ================================

    // TextToSpeech
    public void botTextToSpeech(String botAnswer, String outputPath) {
        sr.textToSpeech(botAnswer,outputPath);
        Utils.playWav(outputPath);
    }





    // NOTE: THIS GAVE ISSUE WITH JAVAFX THREADS
    public String record(boolean recording) throws LineUnavailableException, IOException {

        micMonitor = recording;

        // Make dispatcher
        AudioDispatcher dispatcher = makeDispatcher();

        // Recorded material to analyze using SR
        ArrayList<float[]> recordedBuffers = new ArrayList<>();

        // Load a SpeechRecognizer model
        sr = new SpeechRecognizerV3();

        while(micMonitor){

                //
                dispatcher.addAudioProcessor(new AudioProcessor() {

                    float[] processedBuffer = new float[BLOCK_SIZE];

                    @Override
                    public boolean process(AudioEvent audioEvent) {

                        // Current buffer
                        float[] audioBuffer = audioEvent.getFloatBuffer();

                        // Apply the gate
                        processedBuffer = gate.process(audioBuffer);

                        // Append the recorded list
                        recordedBuffers.add(Arrays.copyOf(processedBuffer, processedBuffer.length));

                        return true;
                    }

                    @Override
                    public void processingFinished() {

                    }
                });

                new Thread(dispatcher::run).start();

            }

        // Transcribe

        // Make .WAV
        System.out.println("Building .wav");

        cnv.makeWAV(recordedBuffers);

        // Transcribe
        String transcription  = sr.transcribe("out16.wav");

        return transcription;
    }

    // =============================
    // @NOTE:
    //      THIS IS BOT PART

    // Create dispatcher for mic
    public AudioDispatcher makeDispatcher() throws LineUnavailableException {
        // Define the mic as input stream of audio
        AudioFormat format = new AudioFormat(44100, 16, 1, true, false);
        TargetDataLine line = AudioSystem.getTargetDataLine(format);
        line.open(format);
        line.start();

        // Use AudioDispatcher for block size 1024
        int overlap = 0;
        AudioDispatcher dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(BLOCK_SIZE, overlap);

        System.out.println("dispatcher made");
        return dispatcher;
    }

    // Start Recording
    public void recordStart() throws LineUnavailableException, IOException {
        // Reset the recorded buffers
        recordedBuffers.clear();

        // Make dispatcher
        dispatcher = makeDispatcher();

        // Load a SpeechRecognizer model
        sr = new SpeechRecognizerV3();

        dispatcher.addAudioProcessor(new AudioProcessor() {
            float[] processedBuffer = new float[BLOCK_SIZE];

            @Override
            public boolean process(AudioEvent audioEvent) {
                // Current buffer
                float[] audioBuffer = audioEvent.getFloatBuffer();

                // Apply the gate
                processedBuffer = gate.process(audioBuffer);

                // Append the recorded list
                recordedBuffers.add(Arrays.copyOf(processedBuffer, processedBuffer.length));

                return true;
            }

            @Override
            public void processingFinished() {

            }
        });

        new Thread(dispatcher::run).start();
    }


    // Stop Recording - Return Transcribed Text

    public String recordStop() throws IOException {
        // Dispatcher Stop
        dispatcher.stop();
        // Make .WAV
        System.out.println("Building .wav");
        cnv.makeWAV(recordedBuffers);
        // Transcribe
        String transcription = sr.transcribe("out16.wav");
        System.out.println("Original trans ->"+transcription);
        System.out.print("Corrected trans ->" );
        return spellingCorrector.correctSentence(transcription);
    }

    public String botGetSpeakerID() {
        return sr.getSpeakerID("out16.wav");
    }

    public void close() {
        sr.close();
    }

    public static void main(String[] args) {
        SoundLevelDetector detector = new SoundLevelDetector();
        detector.monitorMicAudio();
        // make sure to close the speech recognizer at the end
        Runtime.getRuntime().addShutdownHook(new Thread(detector::close));
    }
}
