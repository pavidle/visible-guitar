import numpy as np
import cv2


cap = cv2.VideoCapture("guitar.mp4")

CANVAS_SIZE = (600, 800)
FINAL_LINE_COLOR = (255, 255, 255)
WORKING_LINE_COLOR = (127, 127, 127)


def rotate(image, degree):
    h, w = image.shape[:2]
    rotate_matrix = cv2.getRotationMatrix2D((w / 2.0, h / 2.0), degree, 1)
    rotated = cv2.warpAffine(image, rotate_matrix, (w, h), borderValue=(0, 0, 0))
    return rotated


def rotate_point(point, degree, center):
    x, y = point
    cx, cy = center
    degree = np.rad2deg(degree)
    x2 = (x - cx) * np.cos(degree) + (y - cy) * np.sin(degree) + cx
    y2 = (x - cx) * np.sin(degree) + (y - cy) * np.cos(degree) + cy
    return x2, y2


def get_horizontal_angle(image, original_image):
    hough_lines = cv2.HoughLines(image, 1, np.pi / 180, 210)
    summ = 0
    if hough_lines is not None:
        for i in range(len(hough_lines)):
            for rho, theta in hough_lines[i]:
                a = np.cos(theta)
                b = np.sin(theta)
                x0 = a * rho
                y0 = b * rho
                x1 = int(round(x0 + 1000 * (-b)))
                y1 = int(round(y0 + 1000 * a))
                x2 = int(round(x0 - 1000 * (-b)))
                y2 = int(round(y0 - 1000 * a))
                summ += theta
                # cv2.line(original_image, (x1, y1), (x2, y2), (0, 255, 0), 1, cv2.LINE_AA)
            # cv2.imshow("Imagelines", original_image)
        average = summ / len(hough_lines)
        angle = average / np.pi * 180 - 90
        return angle


def find_homography(first_right_frame, edge_frame, frame_for_transformation, detector=cv2.ORB_create()):
    kp1, des1 = detector.detectAndCompute(first_right_frame, None)
    kp2, des2 = detector.detectAndCompute(edge_frame, None)

    bf = cv2.BFMatcher(cv2.NORM_HAMMING)
    matches = list(bf.match(des2, des1))
    matches.sort(key=lambda x: x.distance)
    good_points = matches[:int(len(matches) * 0.8)]

    current_frame_points = np.float32([kp2[m.queryIdx].pt for m in good_points]).reshape(-1, 1, 2)
    first_right_points = np.float32([kp1[m.trainIdx].pt for m in good_points]).reshape(-1, 1, 2)

    homography, _ = cv2.findHomography(current_frame_points, first_right_points, cv2.RANSAC, 2.0)
    h, w = first_right_frame.shape
    result = cv2.warpPerspective(frame_for_transformation, homography, (w, h))
    image_matches = cv2.drawMatches(first_right_frame, kp1, result, kp2, good_points, None)
    cv2.imshow('result', image_matches)


def unwarp(img, src, dst):
    h, w = img.shape[:2]
    m = cv2.getPerspectiveTransform(src, dst)
    warped = cv2.warpPerspective(img, m, (w, h), flags=cv2.INTER_LINEAR)
    return warped


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

        filter_size = (3, 3)
        kernel = cv2.getStructuringElement(cv2.MORPH_RECT, filter_size)
        first_rotated_frame = None
        feature_params = dict(maxCorners=100,
                              qualityLevel=0.3,
                              minDistance=7,
                              blockSize=7)

        lk_params = dict(winSize=(15, 15),
                         maxLevel=2,
                         criteria=(cv2.TERM_CRITERIA_EPS | cv2.TERM_CRITERIA_COUNT,
                                   10, 0.03))

        color = np.random.randint(0, 255, (100, 3))
        ret, old_frame = cap.read()
        old_gray = cv2.cvtColor(old_frame,
                                cv2.COLOR_BGR2GRAY)
        p0 = cv2.goodFeaturesToTrack(old_gray, mask=None,
                                     **feature_params)
        mask = np.zeros_like(old_frame)

        while True:
            success, frame = cap.read()
            if not success:
                break

            canvas = np.zeros(frame.shape[:2], dtype=np.uint8)
            mask = cv2.fillPoly(canvas, np.array([self.points]), FINAL_LINE_COLOR)
            gray_scale = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
            canny = cv2.Canny(gray_scale, 250, 300)
            masked = cv2.bitwise_and(frame, frame, mask=mask)

            gray_masked = cv2.cvtColor(masked, cv2.COLOR_BGR2GRAY)
            mask_zeros = np.zeros(frame.shape, frame.dtype)
            mask_zeros[:, :] = (255, 0, 0)



            blue_mask = cv2.bitwise_and(mask_zeros, mask_zeros, mask=mask)
            cv2.addWeighted(blue_mask, 1, frame, 1, 0, frame)
            dilated = cv2.dilate(masked, kernel, iterations=3)
            binary_and = cv2.bitwise_and(dilated, dilated, mask=canny)
            gray_scale_canny = cv2.cvtColor(binary_and, cv2.COLOR_BGR2GRAY)

            # p1, st, err = cv2.calcOpticalFlowPyrLK(old_gray,
            #                                        gray_masked,
            #                                        p0, None,
            #                                        **lk_params)
            # good_new = p1[st == 1]
            # good_old = p0[st == 1]
            #
            # # draw the tracks
            # for i, (new, old) in enumerate(zip(good_new,
            #                                    good_old)):
            #     a, b = new.ravel()
            #     c, d = old.ravel()
            #     mask = cv2.line(mask, (int(a), int(b)), (int(c), int(d)),
            #                     color[i].tolist(), 2)
            #     cv2.imshow('mask', mask)
            #     frame = cv2.circle(frame, (int(a), int(b)), 5,
            #                        color[i].tolist(), -1)
            #
            # # img = cv2.add(frame, mask)
            #
            # cv2.imshow('frame', frame)
            #
            # old_gray = gray_masked.copy()
            # p0 = good_new.reshape(-1, 1, 2)

            # cv2.imshow("gray_scale_canny", gray_scale_canny)
            h = get_horizontal_angle(gray_scale_canny, frame)
            if h:
                rotated = rotate(frame, h)
                rotated_mask = rotate(gray_masked, h)
                rotated_canny = rotate(gray_scale_canny, h)
                x, y, w, h = cv2.boundingRect(rotated_mask)
                # cv2.rectangle(rotated, (x, y), (x + w, y + h), (0, 0, 255), 2, 1)

                # cv2.imshow("rotated", rotated)
                # cv2.imshow("new", new_image)

                lines = cv2.HoughLinesP(
                    image=rotated_canny, rho=1, theta=np.pi / 180, lines=np.array([]), threshold=220,
                    maxLineGap=110
                )
                if lines is not None:
                    for line in lines:
                        x1, y1, x2, y2 = line[0]
                        cv2.line(rotated, (x1, y1), (x2, y2), (0, 255, 0), 1)
                cv2.imshow("rotated", rotated)
                # cv2.imshow("rotated", rotated_canny)
                # if first_rotated_frame is None:
                #     first_rotated_frame = rotated_canny
                # find_homography(first_rotated_frame, rotated_canny, rotated)
            else:
                cv2.imshow("rotated", frame)
            k = cv2.waitKey(50)
            if k == 27:
                break


if __name__ == "__main__":
    pd = PolygonDrawer("Polygon")
    pd.run()
    cv2.destroyAllWindows()




