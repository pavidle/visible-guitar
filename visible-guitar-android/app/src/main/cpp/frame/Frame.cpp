#include "../include/frame/Frame.h"


cv::Mat Frame::getCurrentMatrix() {
    return currentMatrix;
}

cv::Mat Frame::getCurrentMask() {
    return currentMask;
}

void Frame::setMatrix(cv::Mat& matrix) {
    this->currentMatrix = matrix;
}

void Frame::setMask(cv::Mat &mask) {
    this->currentMask = mask;
}
