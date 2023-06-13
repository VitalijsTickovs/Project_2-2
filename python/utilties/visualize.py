import librosa
import librosa.display
import os
import matplotlib.pyplot as plt

class VisualizeAudio:
    @staticmethod
    def visualize_waveform(file_path,title='Waveform'):
        # Load audio file
        audio, sr = librosa.load(file_path)

        # Plot waveform
        plt.figure()
        librosa.display.waveplot(audio, sr=sr)
        plt.title(title)
        plt.xlabel('Time')
        plt.ylabel('Amplitude')
        plt.show()
        
    @staticmethod
    def visualize_spectrogram(file_path,title='Spectogram'):
        # Load audio file
        audio, sr = librosa.load(file_path)

        # Compute spectrogram
        spectrogram = librosa.amplitude_to_db(librosa.stft(audio), ref=np.max)

        # Plot spectrogram
        plt.figure()
        librosa.display.specshow(spectrogram, sr=sr, x_axis='time', y_axis='log')
        plt.colorbar(format='%+2.0f dB')
        plt.title(title)
        plt.show()

   