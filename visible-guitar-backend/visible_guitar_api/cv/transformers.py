import cv2
from abc import ABC, abstractmethod


class BaseTransformer(ABC):
    @abstractmethod
    def transform(self, image):
        pass


class CannyTransformer(BaseTransformer):
    def transform(self, image):
        return cv2.Canny(image, 100, 300)
