import torch
import librosa
from transformers import Wav2Vec2ForCTC, Wav2Vec2Processor

class SpeechRecognizer:
    def __init__(self, model_id):
        self.processor = Wav2Vec2Processor.from_pretrained(model_id)
        self.model = Wav2Vec2ForCTC.from_pretrained(model_id)

    def transcribe(self, audio_file):
        speech_array, _ = librosa.load(audio_file, sr=16_000)
        inputs = self.processor(speech_array, sampling_rate=16_000, return_tensors="pt", padding=True)

        with torch.no_grad():
            logits = self.model(inputs.input_values, attention_mask=inputs.attention_mask).logits

        predicted_ids = torch.argmax(logits, dim=-1)
        return self.processor.decode(predicted_ids[0])
