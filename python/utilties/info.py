import librosa
import librosa.display
import os
import matplotlib.pyplot as plt

class InfoAudio:
    @staticmethod
    def display_file_info(file_path):
        # Load audio file
        audio, sr = librosa.load(file_path)

        # Get file information
        duration = librosa.get_duration(audio, sr=sr)
        num_samples = audio.shape[0]
        sample_rate = sr

        # Get file type
        file_type = os.path.splitext(file_path)[1]

        # Determine if audio is stereo or mono
        num_channels = 1 if len(audio.shape) == 1 else audio.shape[1]
        audio_type = "Mono" if num_channels == 1 else "Stereo"

        # Display file information
        print('File Information:')
        print('File Type:', file_type)
        print('Duration: {:.2f} seconds'.format(duration))
        print('Number of samples:', num_samples)
        print('Sample rate:', sample_rate)
        print('Audio Type:', audio_type)

