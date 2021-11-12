import numpy as np
from scipy.fft import rfft, rfftfreq, fftshift
from scipy.io.wavfile import read
from scipy.signal import spectrogram
import matplotlib.pyplot as plt


if __name__ == "__main__":
    sample_rate, data = read("chords/c-major.wav")
    f = abs(rfft(data))
    freq = rfftfreq(len(f), 1 / sample_rate)
    f_x = freq[range(len(f) // 2)]
    f_y = f[range(len(f) // 2)]
    plt.plot(f_x, f_y)
    plt.show()
    f, t, Sxx = spectrogram(f_y, 10e3, return_onesided=False)
    plt.pcolormesh(t, fftshift(f), fftshift(Sxx, axes=0), shading='gouraud')
    plt.ylabel('Frequency [Hz]')
    plt.xlabel('Time [sec]')
    plt.show()

