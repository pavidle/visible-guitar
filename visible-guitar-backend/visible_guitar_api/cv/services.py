from abc import ABC, abstractmethod
import typing as t


class EncoderDecoderService:
    @abstractmethod
    def encode(self, message: t.Any) -> t.Any:
        pass

    @abstractmethod
    def decode(self, message: t.Any) -> t.Any:
        pass
