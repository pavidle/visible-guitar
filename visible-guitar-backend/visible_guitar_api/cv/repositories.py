import typing as t
import operator
from cv.point import Point
from cv.recognizers import BaseLinesRecognizer


class Line:
    def __init__(self, slope: float, free: float):
        self.slope = slope
        self.free = free

    def get_points(self, image):
        x_second = image.shape[1]
        y_second = self.slope * x_second + self.free
        first_point = Point(0, int(self.free))
        second_point = Point(int(x_second), int(y_second))
        return first_point, second_point

    # def get_slope(self) -> float:
    #     return (self.first.y - self.second.y) / (self.first.x - self.second.x)
    #
    # def get_free(self):
    #     return self.second.y - self.get_slope() * self.second.x


class LinesRepository:

    def __init__(self, lines_recognizer: BaseLinesRecognizer):
        self.__lines: t.List[Line] = list()
        self.__lines_recognizer = lines_recognizer

    @property
    def lines(self) -> t.List[Line]:
        return self.__lines

    def add(self, line: Line) -> None:
        self.__lines.append(line)

    def recognize_and_fill(self, image) -> None:
        recognized_lines = self.__lines_recognizer.recognize(image)
        if recognized_lines is not None:
            for line in recognized_lines:
                current = line[0]
                x_first, y_first, x_second, y_second = current
                k = (y_first - y_second) / (x_first - x_second)
                b = y_second - k * x_second
                self.add(Line(k, b))

    def __get_coefficients(self, callback: t.Callable) -> tuple:
        lines = list()
        for line in self.__lines:
            lines.append((line.slope, line.free))

        if lines is not None:
            return callback(
                lines,
                key=operator.itemgetter(1)
            )
        return 0, 0

    def get_line_with_max_free(self) -> Line:
        try:
            k, b = self.__get_coefficients(max)
            return Line(k, b)
        except ValueError:
            return Line(0, 0)

    def get_line_with_min_free(self) -> Line:
        try:
            k, b = self.__get_coefficients(min)
            return Line(k, b)
        except ValueError:
            return Line(0, 0)
