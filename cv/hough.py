import matplotlib.pyplot as plt
import numpy as np
import cv2


def houghLine(image):
    n_y = image.shape[0]
    n_x = image.shape[1]

    Maxdist = int(np.round(np.sqrt(n_x ** 2 + n_y ** 2)))
    thetas = np.deg2rad(np.arange(-90, 90))
    rs = np.linspace(-Maxdist, Maxdist, 2 * Maxdist)
    accumulator = np.zeros((2 * Maxdist, len(thetas)))
    for y in range(n_y):
        for x in range(n_x):
            if image[y, x] > 0:
                for k in range(len(thetas)):
                    r = x * np.cos(thetas[k]) + y * np.sin(thetas[k])
                    accumulator[int(r) + Maxdist, k] += 1
    return accumulator, thetas, rs


image = cv2.imread("test.png")
image = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
accumulator, thetas, rhos = houghLine(image)
plt.figure('Original Image')
plt.imshow(image)
plt.set_cmap('gray_r')
plt.figure('Hough Space')
plt.imshow(accumulator)
plt.set_cmap('gray_r')
plt.show()
