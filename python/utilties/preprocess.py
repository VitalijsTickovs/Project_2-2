import soundfile as sf
from pydub import AudioSegment
from pydub.effects import normalize

def convert_to_mono_normalize(file_path, output_path):
    # Load audio file
    audio, sample_rate = sf.read(file_path)

    # Convert to mono
    audio_mono = audio.mean(axis=1)

    # Normalize loudness
    audio_mono_normalized = normalize(audio_mono)

    # Resample to 16 kHz
    audio_resampled = sf.resample(audio_mono_normalized, sample_rate, 16000)

    # Convert to 16-bit
    audio_16bit = (audio_resampled * 32767).astype('int16')

    # Save updated audio sample
    updated_file_path = 'updated_audio.wav'
    sf.write(output_path, audio_16bit, 16000, 'PCM_16')


# Example usage
input_file = 'path_to_input_audio_file.wav'
output_file = 'path_to_output_audio_file.wav'

convert_to_mono_normalize(input_file, output_file)