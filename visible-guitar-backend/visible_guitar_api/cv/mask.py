import cv2
import numpy as np
from cv.repositories import LinesRepository


class ImageMaskGenerator:

    def __init__(self, lines_repository: LinesRepository):
        self.lines_repository = lines_repository

    def generate(self, image):
        top_edge_points = self.lines_repository.get_line_with_min_free().get_points(image)
        bottom_edge_points = self.lines_repository.get_line_with_max_free().get_points(image)
        points = [
            (top_edge_points[0].x, top_edge_points[0].y),
            (top_edge_points[1].x, top_edge_points[1].y),
            (bottom_edge_points[1].x, bottom_edge_points[1].y),
            (bottom_edge_points[0].x, bottom_edge_points[0].y)
        ]
        mask = np.zeros(image.shape[:2], dtype="uint8")
        mask = cv2.fillPoly(mask, np.array([points]), (255, 255, 255))
        masked = cv2.bitwise_and(image, image, mask=mask)
        return masked
