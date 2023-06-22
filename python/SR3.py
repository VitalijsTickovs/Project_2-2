import sys
import numpy as np
import torch
import librosa
from transformers import Wav2Vec2ForCTC, Wav2Vec2Processor
import subprocess

def install(package):
    subprocess.check_call([sys.executable, "-m", "pip", "install", package])

dependencies = [
    'wave',
    'pickle',
    'pyaudio',
    'numpy',
    'matplotlib',
    'sklearn',
    'scipy',
    'python_speech_features',
    'torch',
    'librosa',
    'transformers',
    'gtts',
    'pydub'
]

for dependency in dependencies:
    install(dependency)

class SpeechRecognizer:
    def __init__(self, model_id):
        print('loading model')
        self.processor = Wav2Vec2Processor.from_pretrained(model_id)
        self.model = Wav2Vec2ForCTC.from_pretrained(model_id)

    def transcribe(self, audio_file):
        # Load the audio file
        speech_array, _ = librosa.load(audio_file, sr=16_000)
        inputs = self.processor(speech_array, sampling_rate=16_000, return_tensors="pt", padding=True)

        # Get the predicted logits from the model
        with torch.no_grad():
            logits = self.model(inputs.input_values, attention_mask=inputs.attention_mask).logits

        # Get the predicted IDs
        predicted_ids = torch.argmax(logits, dim=-1)

        # Decode the IDs to get the predicted sentence
        return self.processor.decode(predicted_ids[0])

def main():
    # Define the speech recognizer
    recognizer = SpeechRecognizer("jonatasgrosman/wav2vec2-large-xlsr-53-english")

    # Enter a loop of transcribing files
    for line in sys.stdin:
        audio_file_path = line.strip()  # Strip whitespace

        # Transcribe the audio file
        print(f"Transcribing the audio file {audio_file_path}...")
        predicted_sentence = recognizer.transcribe(audio_file_path)

        # Print the transcribed sentence
        print("Transcription:", predicted_sentence)
        sys.stdout.flush()  # Ensure the output is sent immediately

if __name__ == "__main__":
    main()
