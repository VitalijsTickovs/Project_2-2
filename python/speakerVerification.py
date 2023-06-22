import os
import sys
import wave
import time
import pickle
import pyaudio
import warnings
import numpy as np
import matplotlib.pyplot as plt
from sklearn import preprocessing
from scipy.io.wavfile import read
import python_speech_features as mfcc
from sklearn.mixture import GaussianMixture

warnings.filterwarnings("ignore")


def calculate_delta(array):
   
    rows,cols = array.shape
    print(rows)
    print(cols)
    deltas = np.zeros((rows,20))
    N = 2
    for i in range(rows):
        index = []
        j = 1
        while j <= N:
            if i-j < 0:
              first =0
            else:
              first = i-j
            if i+j > rows-1:
                second = rows-1
            else:
                second = i+j 
            index.append((second,first))
            j+=1
        deltas[i] = ( array[index[0][0]]-array[index[0][1]] + (2 * (array[index[1][0]]-array[index[1][1]])) ) / 10
    return deltas

def extract_features(audio,rate):
       
    mfcc_feature = mfcc.mfcc(audio,rate, 0.025, 0.01,20,nfft = 1200, appendEnergy = True)    
    mfcc_feature = preprocessing.scale(mfcc_feature)
    print(mfcc_feature)
    delta = calculate_delta(mfcc_feature)
    combined = np.hstack((mfcc_feature,delta)) 
    return combined

def identify_speaker(file_path):
    modelpath = "src/main/resources/trained_models"

    gmm_files = [os.path.join(modelpath, fname) for fname in os.listdir(modelpath) if fname.endswith('.gmm')]

    # Load the Gaussian speaker models
    models = []
    for fname in gmm_files:
        with open(fname, 'rb') as f:
          model = pickle.load(f)
          models.append(model)

    speakers = [fname.split("/")[-1].split(".gmm")[0] for fname in gmm_files]

    sr, audio = read(file_path)
    vector = extract_features(audio, sr)

    log_likelihood = np.zeros(len(models))

    for i in range(len(models)):
        gmm = models[i]
        scores = np.array(gmm.score(vector))
        log_likelihood[i] = scores.sum()

    winner = np.argmax(log_likelihood)
    identified_speaker = speakers[winner]
    identified_speaker_label = os.path.basename(identified_speaker)  # Extract label from file name
    return identified_speaker_label

if __name__ == "__main__":
    file_path = sys.argv[1]  # Get the audio file path from the command line arguments
    id = identify_speaker(file_path)
    print(id)  # Print the identified speaker
