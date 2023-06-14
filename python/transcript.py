import speech_recognition as sr

# DESCRIPTION:
#   this script generates string transcripts of audio files

filename = "/Users/piedeboer/Documents/GitHub/Project_2-2/datasets/unsorted/aaron_001.wav"

# initialize the recognizer
r = sr.Recognizer()

# open the file
with sr.AudioFile(filename) as source:
    # listen for the data (load audio to memory)
    audio_data = r.record(source)
    # recognize (convert from speech to text)
    text = r.recognize_google(audio_data)
    print(text)

