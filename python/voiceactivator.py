import audioop
import pyaudio
import wave
import pyaudio
import pygame
import os
import speech_recognition as sr
import sounddevice as sd
import noisereduce as nr
from SpeechRecog import SpeechRecognizer
from transformers import Wav2Vec2ForCTC, Wav2Vec2Processor
from gtts import gTTS
from io import BytesIO
from gtts import gTTS
from scipy.io import wavfile

def detect_voice():

    # advanced recognizer
    recognizer = SpeechRecognizer(model_id="jonatasgrosman/wav2vec2-large-xlsr-53-english")

    CHUNK = 16000             # Number of audio samples per chunk for 1 second
    FORMAT = pyaudio.paInt16  # Audio format (16-bit)
    CHANNELS = 1              # Mono audio
    RATE = 16000              # Sampling rate (16kHz)
    ENERGY_THRESHOLD = 200    # Minimum energy level for speech detection

    p = pyaudio.PyAudio()

    stream = p.open(
        format=FORMAT,
        channels=CHANNELS,
        rate=RATE,
        input=True,
        frames_per_buffer=CHUNK
    )

    activation_phrase = "hello"
    is_activated = False

    # recognizer = sr.Recognizer()

    print("Listening for activation phrase...")

    try:
        capturing_audio = False
        audio_buffer = []

        while True:
            # Read audio data from the stream
            data = stream.read(CHUNK, exception_on_overflow=False)

            # Calculate energy level of audio data
            energy = audioop.rms(data, 2)

            # Check if energy level surpasses the threshold
            if energy > ENERGY_THRESHOLD:
                # Print 'loud' to indicate exceeding energy threshold
                print('Loud')

                if not capturing_audio:
                    capturing_audio = True
                    audio_buffer = []

                audio_buffer.append(data)

                try:
                    print('Speech')

                    # Convert the audio data into a WAV file
                    with wave.open('audio.wav', 'wb') as wav_file:
                        wav_file.setnchannels(CHANNELS)
                        wav_file.setsampwidth(p.get_sample_size(FORMAT))
                        wav_file.setframerate(RATE)
                        for audio_data in audio_buffer:
                            wav_file.writeframes(audio_data)

                    rate, data = wavfile.read("audio.wav")                   
                    reduced_noise = nr.reduce_noise(y=data, sr=RATE)
                    wavfile.write("audio.wav", RATE, reduced_noise)

                    text = recognizer.transcribe("audio.wav")

                    # Use speech recognition to transcribe the WAV file
                    # with sr.AudioFile('audio.wav') as source:
                        # audio = recognizer.record(source)
                        # text = recognizer.recognize_google(audio)
                    print('Transcribed Text:', text)

                    # Check if the activation phrase is detected
                    if activation_phrase in text.lower() and not is_activated:
                        print("Hello user!")
                        is_activated = True
                        break

                    capturing_audio = False
                    audio_buffer = []

                except sr.UnknownValueError:
                    # Speech recognition could not understand audio
                    print('dont understand')
                    pass

    except KeyboardInterrupt:
        # User interrupted the program
        pass

    stream.stop_stream()
    stream.close()
    p.terminate()

detect_voice()

print('Welcome to chatbott')











































# ======================================
# USE GGTS IN REAL-TIME TO GREET THE USER

# Specify the text to be converted to speech
text = "Hi user!"

# Set the language
language = 'en'

# Create a speech object using gTTS
speech = gTTS(text=text, lang=language, slow=False)


def play_audio(audio_data):
    audio_stream = BytesIO(audio_data)
    pygame.mixer.init()
    pygame.mixer.music.load(audio_stream)
    pygame.mixer.music.play()
    while pygame.mixer.music.get_busy():
        continue

def greet_user():
    # Specify the text to be converted to speech
    text = "Hi user!"

    # Set the language
    language = 'en'

    # Create a speech object using gTTS
    speech = gTTS(text=text, lang=language, slow=False)

    # Save the speech as an mp3 file
    speech.save("output.mp3")

    # Initialize pygame mixer
    pygame.mixer.init()

    # Load the audio file
    pygame.mixer.music.load("output.mp3")

    # Play the audio file
    pygame.mixer.music.play()

    # Wait until the audio finishes playing
    while pygame.mixer.music.get_busy():
        continue

    # Clean up the mixer and delete the audio file
    pygame.mixer.quit()
    os.remove("output.mp3")
    
# greet user
greet_user()

# stop prgram

