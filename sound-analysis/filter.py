import numpy as np
import matplotlib.pyplot as plt
from scipy.io.wavfile import write
from scipy.fft import rfft, rfftfreq, irfft


def generate_sin_wave(frequency, duration, sampling_frequency):
    x = np.linspace(0, duration, sampling_frequency * duration, endpoint=False)
    # Частота дискретизации - 44100 Гц (стандарт для компакт-дисков).
    frequencies = x * frequency
    y = np.sin(2 * np.pi * frequencies)
    return x, y


DURATION = 5  # Длительность волны, в секундах
SAMPLING_FREQUENCY = 44100  # Частота дискретизации, в Гц,
# частота дискретизации - 44100 Гц (стандарт для компакт-дисков)


def make_fft(n, data):  # xn * exp(2 * pi * i * N * k / n), i - sqrt(-1)
    y_fft = rfft(data)
    x_fft = rfftfreq(n, 1 / SAMPLING_FREQUENCY)
    return x_fft, y_fft


if __name__ == "__main__":
    _, y = generate_sin_wave(400, DURATION, SAMPLING_FREQUENCY)
    normalized = np.int16((y / y.max()) * 32767)
    plt.plot(normalized[:1000])
    plt.show()
    _, noise = generate_sin_wave(4000, DURATION, SAMPLING_FREQUENCY)
    noise = noise * 0.3
    mixed_tone = y + noise
    normalized_noise = np.int16((mixed_tone / mixed_tone.max()) * 32767)
    plt.plot(normalized_noise[:1000])
    plt.show()
    write("my_wave.wav", SAMPLING_FREQUENCY, normalized_noise)
    xf, yf = make_fft(SAMPLING_FREQUENCY * DURATION, normalized_noise)

    points_per_freq = len(xf) / (SAMPLING_FREQUENCY / 2)
    target = int(points_per_freq * 4000)

    plt.plot(xf, np.abs(yf))
    plt.show()
    yf[target-2:target+2] = 0

    plt.plot(xf, np.abs(yf))
    plt.show()
    signal = irfft(yf)
    normalized_signal = np.int16(signal * (32767 / signal.max()))
    plt.plot(normalized_signal[:1000])
    plt.show()
    write("clean_wave.wav", SAMPLING_FREQUENCY, normalized_signal)
