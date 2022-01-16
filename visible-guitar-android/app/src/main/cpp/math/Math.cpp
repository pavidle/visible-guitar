#include <opencv2/imgproc.hpp>
#include "../include/math/Math.h"


double Math::get_angle_of_slope(cv::Mat& input) {
    std::vector<cv::Vec2f> lines;
    cv::HoughLines(input, lines, 1, CV_PI / 180, 210);
    float sum = 0;
    for (auto& line : lines) {
        float theta = line[1];
        sum += theta;
    }

    double average = sum / lines.size();
    return average / CV_PI * 180 - 90;
}

void Math::rotate(cv::Mat& input, cv::Mat& output, double angle) {
    cv::Mat rotate_matrix = cv::getRotationMatrix2D(cv::Point(input.rows / 2, input.cols / 2), angle, 1);
    cv::warpAffine(input, output, rotate_matrix, input.size());
}