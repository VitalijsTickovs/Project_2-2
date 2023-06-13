import numpy as np
import sounddevice as sd
import librosa
from sklearn.mixture import GaussianMixture

# Define the feature extraction parameters
n_mfcc = 13
n_fft = 2048
hop_length = 512

# Define the number of Gaussian components for the GMM
n_components = 1

# Define the paths to the training audio files for each speaker
# pie_file = 
# tom_file = '/Users/piedeboer/Desktop/project-2-2/speecrecognition/audio/training_data/train_tom_001.wav'

# Define a function to extract the pitch and MFCC features from an audio signal
def extract_features(audio, sr):
    # Extract the pitch features
    pitches, _ = librosa.piptrack(y=audio, sr=sr, n_fft=n_fft, hop_length=hop_length)
    pitch_features = np.mean(pitches, axis=1)

    # Extract the MFCC features
    mfccs = librosa.feature.mfcc(y=audio, sr=sr, n_mfcc=n_mfcc, n_fft=n_fft, hop_length=hop_length)
    mfcc_features = np.mean(mfccs.T, axis=0)

    # Concatenate the features
    features = np.concatenate((pitch_features, mfcc_features))

    return features

# Extract the features for Tom and Pie
tom_audio, sr = librosa.load(tom_file)
tom_features = extract_features(tom_audio, sr)
pie_audio, sr = librosa.load(pie_file)
pie_features = extract_features(pie_audio, sr)

# Create the training data
X = np.vstack((tom_features, pie_features))
y = np.array([0, 1])

# Train the Gaussian Mixture Model
gmm = GaussianMixture(n_components=n_components, covariance_type='diag')
gmm.fit(X, y)

# # Define a function to perform real-time speaker identification using the microphone
# def identify_speaker(sr):
#     # Start the microphone stream
#     with sd.InputStream(channels=1, blocksize=2048, samplerate=sr) as stream:
#         print('Listening...')
#         while True:
#             # Read the audio data from the stream
#             audio = stream.read(2048)

#             # Reshape the audio data
#             audio = audio.reshape(-1)

#             # Extract the features from the audio data
#             features = extract_features(audio, sr)

#             # Predict the speaker using the GMM
#             speaker = gmm.predict([features])[0]

#             # Print the name of the speaker
#             if speaker == 0:
#                 print('Tom')
#             else:
#                 print('Pie')

# # Call the identify_speaker function to start the real-time speaker identification
# identify_speaker(sr)
