from gtts import gTTS
import os

# Specify the text to be converted to speech
text = "Saman, you are very hot and sexy. "

# Set the language
language = 'en'

# Create a speech object using gTTS
speech = gTTS(text=text, lang=language, slow=False)

# Save the speech as an mp3 file
speech.save("natalia.mp3")

# Play the mp3 file
os.system("mpg321 hello.mp3")
