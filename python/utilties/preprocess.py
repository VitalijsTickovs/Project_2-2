from pydub import AudioSegment
from pydub.effects import normalize

def convert_to_mono_normalize(file_path, output_path):
    # Load audio file
    audio = AudioSegment.from_file(file_path)

    # Convert to mono
    audio_mono = audio.set_channels(1)

    # Normalize loudness
    audio_normalized = normalize(audio_mono)

    # Export the normalized audio as WAV file
    audio_normalized.export(output_path, format='wav')

# Example usage
input_file = 'path_to_input_audio_file.wav'
output_file = 'path_to_output_audio_file.wav'

convert_to_mono_normalize(input_file, output_file)