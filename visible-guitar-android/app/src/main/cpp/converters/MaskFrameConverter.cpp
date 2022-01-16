#include "../include/converters/MaskFrameConverter.h"


MaskFrameConverter::MaskFrameConverter(Frame* frame, BoundingBoxPointsRepository* pointsRepository)
        : FrameConverter(frame) {
    this->pointsRepository = pointsRepository;
}

cv::Mat MaskFrameConverter::convert_(cv::Mat& input) const {
    cv::Mat canvas = cv::Mat::zeros(input.rows, input.cols, CV_8U);
    cv::fillPoly(canvas, pointsRepository->getPoints(), cv::Scalar(255, 0, 0));
    cv::Mat output;
    cv::bitwise_and(input, input, output, canvas);
    return output;
}

void MaskFrameConverter::set(cv::Mat& input) const {
    frame->setMask(input);
}
