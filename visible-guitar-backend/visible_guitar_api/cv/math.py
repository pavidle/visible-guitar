import typing as t
import numpy as np
from cv.repositories import Line


def get_average_angle_slope(lines: t.List[Line]) -> float:
    sum_of_angles = 0
    count_of_lines = len(lines)
    if count_of_lines > 0:
        for line in lines:
            angle = np.arctan(line.slope) * (180 / np.pi)
            sum_of_angles += angle
        average_angle = sum_of_angles / count_of_lines
    else:
        average_angle = 0
    return average_angle
