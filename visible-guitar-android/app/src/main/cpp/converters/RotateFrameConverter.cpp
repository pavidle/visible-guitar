#include "../include/math/Math.h"
#include "../include/converters/RotateFrameConverter.h"


RotateFrameConverter::RotateFrameConverter(Frame *frame) : FrameConverter(frame) {}

cv::Mat RotateFrameConverter::convert_(cv::Mat& input) const {
    cv::Mat edge = frame->getCurrentMatrix();
    double angle = Math::get_angle_of_slope(edge);
    cv::cvtColor(input, input, cv::COLOR_BGR2GRAY);
    cv::Mat rotated;
    Math::rotate(input, rotated, angle);
    return rotated;
}

cv::Mat RotateFrameConverter::get() const {
    return frame->getCurrentMask();
}
