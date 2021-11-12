import cv2
import numpy as np


cap = cv2.VideoCapture(0, cv2.CAP_DSHOW)
# cap.set(3, 640)
# cap.set(4, 480)


def find_color(hsv_image, hsv_min, hsv_max):
    mask = cv2.inRange(hsv_image, hsv_min, hsv_max)
    moments = cv2.moments(mask, 1)
    area = moments["m00"]
    if area > 50:
        x = int(moments["m10"] / area)
        y = int(moments["m01"] / area)
        return x, y
    return None, None


def show(x, y):
    cv2.circle(frame, (x, y), 10, (0, 0, 255), -1)
    print(x, y)


def nothing(param):
    pass


while True:
    _, frame = cap.read()

    hsv_min_orange = np.array((10, 100, 20), np.uint8)
    hsv_max_orange = np.array((25, 255, 255), np.uint8)

    hsv_min_green = np.array((36, 55, 147), np.uint8)
    hsv_max_green = np.array((83, 255, 255), np.uint8)

    hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)
    x_orange, y_orange = find_color(hsv, hsv_min_orange, hsv_max_orange)
    x_green, y_green = find_color(hsv, hsv_min_green, hsv_max_green)
    if x_orange and y_orange:
        show(x_orange, y_orange)

    if x_green and y_green and x_orange and y_orange:
        show(x_green, y_green)
        blank = np.zeros(frame.shape[:2], dtype=np.uint8)
        mask = cv2.rectangle(blank, (x_green, y_green), (x_orange, y_orange), (255, 0, 0), -1)
        masked = cv2.bitwise_and(frame, frame, mask=mask)
        cv2.imshow("crop", masked)

    # cv2.imshow("result", frame)

    # # try:
    # cropped = frame[y_green:y_orange, x_green:x_orange]
    # # except:
    # #     cropped = frame[x_green:x_orange, y_green:y_orange]
    k = cv2.waitKey(5)
    if k == 27:
        break

cap.release()
cv2.destroyAllWindows()
