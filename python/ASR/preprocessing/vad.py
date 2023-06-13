import pyaudio
import os

import numpy as np
import webrtcvad
import io
import wave
from threading import Thread
from gtts import gTTS

#
# @LINKS: 
#   https://pypi.org/project/webrtcvad-wheels/#:~:text=py%2Dwebrtcvad%2Dwheels&text=A%20VAD%20classifies%20a%20piece,being%20fast%2C%20modern%20and%20free.
#   https://thegradient.pub/one-voice-detector-to-rule-them-all/
#
# @TITLE: 
#   VOICE ACTIVITY DETECTION


# ================================= #
#   GOOGLE TEXT TO SPEECH
text = "Welcome cool person!"
language = 'en'
speech = gTTS(text=text, lang=language, slow=False)
# ================================= #


# setup vad
vad = webrtcvad.Vad()
vad.set_mode(1)         # filter aggressiveness

# setup pyaudio
chunk_size = 1024
audio_format = pyaudio.paInt16
sample_rate = 16000
audio_channels = 1

p = pyaudio.PyAudio()


# microphone stream
stream = p.open(format=audio_format,
                channels=audio_channels,
                rate=sample_rate,
                input=True,
                frames_per_buffer=chunk_size)

# phrase for activation
activation_phrase = "Edison"

# function for playing audio
def play_audio(audio):
    os.system("mpg123 -q -")  # Assuming you have mpg123 installed. You can use any audio player.

# function for detecting activation phrase
def detect_activation():
    while True:
        # read audio in chunks
        audio_data = stream.read(chunk_size)

        # convert into np array
        audio_np = np.frombuffer(audio_data, dtype=np.int16)

        # determine whether it contains speech activity
        is_speech = vad.is_speech(audio_np.tobytes(), sample_rate)

        # audio chunk to string
        audio_str = audio_data.decode('utf-8')

        # check match with activation phrase
        if is_speech and activation_phrase in audio_str:
            print("Activation phrase detected!")
            speech.save("output.mp3")  # Save the output as an audio file
            break

# main loop
activation_thread = Thread(target=detect_activation)
activation_thread.start()

# enable chatbot
print("Chatbot engaged!")

# Play the welcome message in real-time
play_audio(speech)

# Join the activation thread
activation_thread.join()