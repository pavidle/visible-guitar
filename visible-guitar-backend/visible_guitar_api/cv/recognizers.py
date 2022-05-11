import typing as t
import cv2
import numpy as np
from abc import ABC, abstractmethod

from cv.point import Point
from cv.services import EncoderDecoderService
from cv.transformers import BaseTransformer


class BaseRecognizer(ABC):

    def __init__(self, encoder_decoder_service: EncoderDecoderService):
        self.__encoder_decoder_service = encoder_decoder_service

    def get(self, image, neck_coordinate: t.Tuple[int, int]) -> Point:
        decoded_image = self.__encoder_decoder_service.encode(image)
        return self._recognize(decoded_image, neck_coordinate)

    @abstractmethod
    def _recognize(self, image, neck_coordinate: t.Tuple[int, int]) -> Point:
        pass


class BaseLinesRecognizer(ABC):

    def __init__(self, transformer: BaseTransformer):
        self._transformer = transformer

    @abstractmethod
    def recognize(self, image) -> list:
        pass


class HoughLinesRecognizer(BaseLinesRecognizer):

    def recognize(self, image) -> list:
        canny = self._transformer.transform(image)
        return cv2.HoughLinesP(canny, 1, np.pi / 180, 170, None, 50, 80)


class DefaultNeckRecognizer(BaseRecognizer):

    def _recognize(self, image, neck_coordinate: t.Tuple[int, int]) -> Point:
        pass
