import librosa as lr
import librosa.display
import librosa.feature
import matplotlib.pyplot as plt
import winsound

file = "chords/distortion_chord.wav"
# winsound.PlaySound(file, winsound.SND_FILENAME)
x, sample_rate = lr.load(file)


hop_length = 1024

chroma = librosa.feature.chroma_cqt(x, sr=sample_rate, hop_length=hop_length)
plt.figure(figsize=(15, 5))
librosa.display.specshow(chroma, x_axis='time', y_axis='chroma', hop_length=hop_length)

plt.show()

pitches = ['C', 'C#', 'D', 'D#', 'E', 'F', 'F#', 'G', 'G#', 'A', 'A#', 'B']

pitches_values = []
maximum_pitches_values = []
for line in chroma:
    maximum_pitches_values.append(max(line))
    pitches_values.append(sum(line))

d = dict(zip(pitches, pitches_values))
sorted_pitches = sorted(d, key=d.get, reverse=True)
notes_in_chord = sorted_pitches[:3]

print("Доминирующие ноты: " + ", ".join(notes_in_chord))

pitch_id = maximum_pitches_values.index(max(maximum_pitches_values))
pitch = pitches[pitch_id]

min_third_id = (pitch_id + 3) % 12
maj_third_id = (pitch_id + 4) % 12

if maximum_pitches_values[min_third_id] < maximum_pitches_values[maj_third_id]:
    third = 'major'
    print(str.format('\n{} {}', pitch, third))
elif maximum_pitches_values[min_third_id] > maximum_pitches_values[maj_third_id]:
    third = 'minor'
    print(str.format('\n{} {}', pitch, third))
else:
    print(str.format('\n{} ???', pitch))
