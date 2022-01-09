#ifndef VISIBLE_GUITAR_FRAMECONVERTER_H
#define VISIBLE_GUITAR_FRAMECONVERTER_H

#include <opencv2/imgproc.hpp>

class FrameConverter {
private:
    std::vector<cv::Point> points;
    static cv::Mat convert_to_grayscale_canny_with_dilate(cv::Mat& input);
    void rotate_to_horizontal_strings(cv::Mat& input);
public:
    FrameConverter(const std::vector<cv::Point>& points);
    void convert(cv::Mat& input, cv::Mat& output);
    cv::Mat create_mask(cv::Mat& input);

};

#endif
