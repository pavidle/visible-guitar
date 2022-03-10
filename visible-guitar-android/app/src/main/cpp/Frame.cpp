#include "include/Frame.h"


Frame::Frame() = default;

Frame::Frame(const cv::Mat& matrix) {
    this->matrix_ = matrix;
}

void Frame::rotate(double angle) {
    cv::Mat rotateMatrix = cv::getRotationMatrix2D(cv::Point(matrix_.rows / 2, matrix_.cols / 2), angle, 1);
    cv::warpAffine(matrix_, matrix_, rotateMatrix, matrix_.size());
}

cv::Mat Frame::getMatrix() const {
    return matrix_;
}

void Frame::erodeAndDilate() {
    cv::cvtColor(matrix_, matrix_, cv::COLOR_BGR2GRAY);
    cv::adaptiveThreshold(matrix_, matrix_, 255, cv::ADAPTIVE_THRESH_MEAN_C, cv::THRESH_BINARY, 15, -2);
    cv::Mat vertical = matrix_.clone();
    int cols = vertical.cols;
    int size = cols / 30;
    cv::Mat structure = cv::getStructuringElement(cv::MORPH_RECT, cv::Size(1, size));
    cv::erode(matrix_, matrix_, structure);
    cv::dilate(matrix_, matrix_, structure);
}
