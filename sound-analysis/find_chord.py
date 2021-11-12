import librosa as lr
import librosa.display, librosa.feature
import numpy as np
import scipy as sc
import matplotlib.pyplot as plt
import winsound


file = "chords/c-major.wav"
# winsound.PlaySound(file, winsound.SND_FILENAME)
x, sample_rate = lr.load(file)


hop_length = 512
number_fft = 2048

# fourier = lr.stft(x, n_fft=number_fft, hop_length=hop_length)

# spectrogram = lr.amplitude_to_db(abs(fourier))


# plt.figure(figsize=(15, 5))
# lr.display.specshow(fourier, sr=sample_rate, hop_length=hop_length, x_axis='time', y_axis='linear')
# plt.colorbar(format='%+2.0f dB')

# hop_length_mel = 256
# mel_spectrogram = lr.feature.melspectrogram(x, sr=sample_rate, n_fft=4096, hop_length=hop_length_mel)
# log_mel_spectrogram = lr.power_to_db(abs(mel_spectrogram))
#
# plt.figure(figsize=(15, 5))
# librosa.display.specshow(log_mel_spectrogram, sr=sample_rate, hop_length=hop_length, x_axis='time', y_axis='mel')
# plt.colorbar(format='%+2.0f dB')
# plt.show()
# f_min = lr.midi_to_hz(36)
#
# C = librosa.cqt(x, sr=sample_rate, fmin=f_min, n_bins=72)
# log_c = librosa.amplitude_to_db(abs(C))
# plt.figure(figsize=(15, 5))
# librosa.display.specshow(log_c, sr=sample_rate, x_axis='time', y_axis='cqt_note', fmin=f_min, cmap='coolwarm')
# plt.colorbar(format='%+2.0f dB')
# plt.show()

chroma = librosa.feature.chroma_cens(x, sr=sample_rate, hop_length=hop_length)
plt.figure(figsize=(15, 5))
a = librosa.display.specshow(chroma, x_axis='time', y_axis='chroma', hop_length=hop_length, cmap='coolwarm')

plt.show()

pitches = ['C', 'C#', 'D', 'D#', 'E', 'F', 'F#', 'G', 'G#', 'A', 'A#', 'B']

pitches_values = []
for line in chroma:
    pitches_values.append(sum(line))

d = dict(zip(pitches, pitches_values))
print(d)
for x, y in sorted(d):
    print(x, y)
# select the most dominate pitch
# pitch_id = chroma.index(max(chroma))
# pitch = pitches[pitch_id]
#
# min_third_id = (pitch_id+3)%12
# maj_third_id = (pitch_id+4)%12
#
# #check if the musical 3rd is major or minor
# if chroma[min_third_id] < chroma[maj_third_id]:
#     third = 'major'
#     print(str.format('\nThis song is likely in {} {}',pitch, third))
# elif chroma[min_third_id] > chroma[maj_third_id]:
#     third = 'minor'
#     print(str.format('\nThis song is likely in {} {}',pitch, third))
# else:
#     print(str.format('\nThis song might be in {} something???',pitch))
