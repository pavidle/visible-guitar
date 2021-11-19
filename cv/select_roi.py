import numpy as np
import cv2


CANVAS_SIZE = (600, 800)

FINAL_LINE_COLOR = (255, 255, 255)
WORKING_LINE_COLOR = (127, 127, 127)

cap = cv2.VideoCapture("guitar.mp4")


def to_radians(degree):
    return degree / np.pi * 180


def rotate(image, degree):
    h, w = image.shape[:2]
    rotate_matrix = cv2.getRotationMatrix2D((w / 2.0, h / 2.0), degree, 1)
    rotated = cv2.warpAffine(image, rotate_matrix, (w, h), borderValue=(0, 0, 0))
    return rotated


def calculate_angle(image, original_image):
    lines = cv2.HoughLines(image, 1, np.pi / 180, 700)
    sum = 0
    if lines is not None:
        for i in range(len(lines)):
            for rho, theta in lines[i]:
                a = np.cos(theta)
                b = np.sin(theta)
                x0 = a * rho
                y0 = b * rho
                x1 = int(round(x0 + 1000 * (-b)))
                y1 = int(round(y0 + 1000 * a))
                x2 = int(round(x0 - 1000 * (-b)))
                y2 = int(round(y0 - 1000 * a))
                sum += theta
                # cv2.line(original_image, (x1, y1), (x2, y2), (0, 255, 0), 1, cv2.LINE_AA)
                # cv2.imshow("Imagelines", original_image)

        average = sum / len(lines)
        angle = to_radians(average) - 90
        return angle


class PolygonDrawer(object):
    def __init__(self, window_name):
        self.window_name = window_name

        self.done = False
        self.current = (0, 0)
        self.points = []

    def on_mouse(self, event, x, y, buttons, user_param):
        if self.done:
            return

        if event == cv2.EVENT_MOUSEMOVE:

            self.current = (x, y)
        elif event == cv2.EVENT_LBUTTONDOWN:
            print("Adding point #%d with position(%d,%d)" % (len(self.points), x, y))
            self.points.append((x, y))
        elif event == cv2.EVENT_RBUTTONDOWN:
            print("Completing polygon with %d points." % len(self.points))
            self.done = True

    def run(self):
        cv2.namedWindow(self.window_name)
        _, frame = cap.read()
        cv2.imshow(self.window_name, frame)
        cv2.waitKey(1)
        cv2.setMouseCallback(self.window_name, self.on_mouse)

        while not self.done:
            canvas = np.zeros(CANVAS_SIZE, np.uint8)
            if len(self.points) > 0:
                cv2.polylines(frame, np.array([self.points]), False, FINAL_LINE_COLOR, 1)
                cv2.line(canvas, self.points[-1], self.current, WORKING_LINE_COLOR)
            cv2.imshow(self.window_name, frame)
            if cv2.waitKey(50) == 27:
                self.done = True

        filter_size = (8, 8)
        kernel = cv2.getStructuringElement(cv2.MORPH_RECT, filter_size)
        kernel_dilate = cv2.getStructuringElement(cv2.MORPH_RECT, (2, 2))

        while True:
            _, frame = cap.read()
            canvas = np.zeros(frame.shape[:2], dtype=np.uint8)
            mask = cv2.fillPoly(canvas, np.array([self.points]), FINAL_LINE_COLOR)
            masked = cv2.bitwise_and(frame, frame, mask=mask)
            gray = cv2.cvtColor(masked, cv2.COLOR_BGR2GRAY)
            morph = cv2.morphologyEx(gray, cv2.MORPH_TOPHAT, kernel)
            # t, thresh = cv2.threshold(morph, 39, 255, cv2.THRESH_BINARY)
            # canny = cv2.Canny(morph, 50, 400)
            # sobel_x = cv2.Sobel(morph, cv2.CV_8UC1, 1, 0, ksize=3)

            # dilate = cv2.dilate(thresh, kernel_dilate, iterations=2)
            #
            alpha = 3
            beta = 0
            # adjusted = cv2.convertScaleAbs(laplacian, alpha=alpha, beta=beta)

            degree = calculate_angle(morph, frame)
            rotated_image = rotate(morph, degree)

            sobel_x = cv2.Sobel(rotated_image, cv2.CV_8UC1, 1, 0, ksize=3)
            # canny = cv2.Canny(sobel_x, 50, 250)
            lines = cv2.HoughLinesP(image=sobel_x, rho=1, theta=np.pi / 180, threshold=550, lines=np.array([]), minLineLength=500, maxLineGap=80)
            # cv2.imshow("gray", sobel_x)
            a, b, c = lines.shape
            for i in range(a):
                cv2.line(frame, (lines[i][0][0], lines[i][0][1]), (lines[i][0][2], lines[i][0][3]), (0, 0, 255), 1,
                         cv2.LINE_AA)
                cv2.imshow("lines", frame)
            cv2.imshow("thresh", sobel_x)
            k = cv2.waitKey(50)
            if k == 27:
                break


if __name__ == "__main__":
    pd = PolygonDrawer("Polygon")
    pd.run()
    cv2.destroyAllWindows()
