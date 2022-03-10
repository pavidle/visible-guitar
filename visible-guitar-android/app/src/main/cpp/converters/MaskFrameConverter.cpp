#include "../include/converters/MaskFrameConverter.h"


MaskFrameConverter::MaskFrameConverter(const LinesAnalyzer& linesAnalyzer) {
    this->linesAnalyzer_ = linesAnalyzer;
}


Frame MaskFrameConverter::apply(Frame frame) const {
    cv::Mat mat = frame.getMatrix();
    std::vector<cv::Point> points = linesAnalyzer_.getPoints(frame);
    cv::Mat canvas = cv::Mat::zeros(mat.rows, mat.cols, CV_8U);
    cv::fillPoly(canvas, points, cv::Scalar(255, 0, 0));
    cv::Mat mask;
    cv::bitwise_and(mat, mat, mask, canvas);
    return Frame(mask);
}
