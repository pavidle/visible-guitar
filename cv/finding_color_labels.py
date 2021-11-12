import cv2
import numpy as np


def nothing(x):
    pass


cap = cv2.VideoCapture(0, cv2.CAP_DSHOW)
cv2.namedWindow("frame")
cv2.createTrackbar("H-min", "frame", 0, 180, nothing)
cv2.createTrackbar("S-min", "frame", 0, 255, nothing)
cv2.createTrackbar("V-min", "frame", 0, 255, nothing)
cv2.createTrackbar("H-max", "frame", 180, 180, nothing)
cv2.createTrackbar("S-max", "frame", 255, 255, nothing)
cv2.createTrackbar("V-max", "frame", 255, 255, nothing)
cv2.createTrackbar("KERNEL1", "frame", 255, 255, nothing)
cv2.createTrackbar("KERNEL2", "frame", 255, 255, nothing)


while True:

    success, frame = cap.read()

    h_min = cv2.getTrackbarPos("H-min", "frame")
    s_min = cv2.getTrackbarPos("S-min", "frame")
    v_min = cv2.getTrackbarPos("V-min", "frame")
    h_max = cv2.getTrackbarPos("H-max", "frame")
    s_max = cv2.getTrackbarPos("S-max", "frame")
    v_max = cv2.getTrackbarPos("V-max", "frame")
    kernel_first = cv2.getTrackbarPos("KERNEL1", "frame")
    kernel_second = cv2.getTrackbarPos("KERNEL2", "frame")

    lower = np.array([h_min, s_min, v_min])
    upper = np.array([h_max, s_max, v_max])
    hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)
    mask = cv2.inRange(hsv, lower, upper)
    kernel = np.ones((kernel_first, kernel_second), np.uint8)
    mask = cv2.erode(mask, kernel)

    cv2.imshow("frame", mask)
    if cv2.waitKey(50) == 27:
        break


cv2.destroyAllWindows()
