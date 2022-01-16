#ifndef VISIBLE_GUITAR_MATH_H
#define VISIBLE_GUITAR_MATH_H

#include <opencv2/imgproc.hpp>


class Math {
public:
    static double get_angle_of_slope(cv::Mat& input);
    static void rotate(cv::Mat& input, cv::Mat& output, double angle);
};


#endif
