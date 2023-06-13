import torch
import librosa
from transformers import Wav2Vec2ForCTC, Wav2Vec2Processor

MODEL_ID = "jonatasgrosman/wav2vec2-large-xlsr-53-english"

processor = Wav2Vec2Processor.from_pretrained(MODEL_ID)
model = Wav2Vec2ForCTC.from_pretrained(MODEL_ID)

def speech_file_to_array_fn(file_path):
    speech_array, sampling_rate = librosa.load(file_path, sr=16_000)
    return speech_array

# The audio file has to be in WAV format and 16.000 Hz
audio_file = "/Users/lorispodevyn/Documents/JavaBook/vitaly16hz.wav"
speech_array = speech_file_to_array_fn(audio_file)

inputs = processor(speech_array, sampling_rate=16_000, return_tensors="pt", padding=True)

with torch.no_grad():
    logits = model(inputs.input_values, attention_mask=inputs.attention_mask).logits

predicted_ids = torch.argmax(logits, dim=-1)
predicted_sentence = processor.decode(predicted_ids[0])

print("Prediction:", predicted_sentence)
