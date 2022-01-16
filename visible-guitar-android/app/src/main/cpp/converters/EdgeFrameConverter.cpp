#include "../include/converters/EdgeFrameConverter.h"


EdgeFrameConverter::EdgeFrameConverter(Frame *frame) : FrameConverter(frame) {}

cv::Mat EdgeFrameConverter::convert_(cv::Mat& input) const {
    cv::Mat kernel = cv::getStructuringElement(cv::MORPH_RECT, cv::Size(3, 3));
    cv::Mat gray_scale;
    cv::cvtColor(input, gray_scale, cv::COLOR_BGR2GRAY);
    cv::Mat canny;
    cv::Canny(gray_scale, canny, 250, 300);
    cv::Mat dilated;
    cv::dilate(input, dilated, kernel);
    cv::Mat binary_and;
    cv::bitwise_and(dilated, dilated, binary_and, canny);
    cv::Mat gray_scale_canny;
    cv::cvtColor(binary_and, gray_scale_canny, cv::COLOR_BGR2GRAY);
    return gray_scale_canny;
}


