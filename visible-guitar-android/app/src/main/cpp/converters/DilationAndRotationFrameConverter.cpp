#include "../include/converters/DilationAndRotationFrameConverter.h"


DilationAndRotationFrameConverter::DilationAndRotationFrameConverter(double angle) {
    this->angle_ = angle;
}

Frame DilationAndRotationFrameConverter::apply(Frame frame) const {
    frame.rotate(angle_);
    frame.erodeAndDilate();
    frame.rotate(-angle_);
    return frame;
}

