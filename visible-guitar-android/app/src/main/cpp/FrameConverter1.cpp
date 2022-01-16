//#include "include/FrameConverter1.h"
//#include <opencv2/imgproc.hpp>
//#include "include/math/Math.h"
//
//
//FrameConverter1::FrameConverter1(const std::vector<cv::Point>& points) {
//    this->points = points;
//}
//
//cv::Mat FrameConverter1::create_mask(cv::Mat &input) {
//    cv::Mat canvas = cv::Mat::zeros(input.rows, input.cols, CV_8U);
//    cv::fillPoly(canvas, points, cv::Scalar(255, 0, 0));
//    cv::Mat mask;
//    cv::bitwise_and(input, input, mask, canvas);
//    return mask;
//}
//
//cv::Mat FrameConverter1::convert_to_grayscale_canny_with_dilate(cv::Mat& input) {
//    cv::Mat c = input.clone();
//    cv::Mat kernel = cv::getStructuringElement(cv::MORPH_RECT, cv::Size(3, 3));
//    cv::Mat gray_scale;
//    cv::cvtColor(input, gray_scale, cv::COLOR_BGR2GRAY);
//    cv::Mat canny;
//    cv::Canny(gray_scale, canny, 250, 300);
//    cv::Mat dilated;
//    cv::dilate(input, dilated, kernel);
//    cv::Mat binary_and;
//    cv::bitwise_and(dilated, dilated, binary_and, canny);
//    cv::Mat gray_scale_canny;
//    cv::cvtColor(binary_and, gray_scale_canny, cv::COLOR_BGR2GRAY);
//    return gray_scale_canny;
//}
//
//
//void FrameConverter1::convert(cv::Mat& input, cv::Mat& output) {
//
//    cv::Mat mask = create_mask(input);
//    cv::Mat gray_scale_canny = convert_to_grayscale_canny_with_dilate(mask);
//
//    double angle = Math::get_angle_of_slope(gray_scale_canny);
//
//    cv::cvtColor(mask, mask, cv::COLOR_BGR2GRAY);
//
//    cv::Mat rotated;
//    Math::rotate(mask, rotated, angle);
//
//    cv::Mat bw;
//    cv::adaptiveThreshold(rotated, bw, 255, cv::ADAPTIVE_THRESH_MEAN_C, cv::THRESH_BINARY, 21, -2);
//    cv::Mat horizontal = bw.clone();
//    cv::Mat vertical = bw.clone();
//
//    int vertical_size = vertical.rows / 30;
//    cv::Mat verticalStructure = cv::getStructuringElement(cv::MORPH_RECT, cv::Size(1, vertical_size));
//    erode(vertical, vertical, verticalStructure);
//    dilate(vertical, vertical, verticalStructure);
//
//    int horizontal_size = horizontal.rows / 30;
//    cv::Mat horizontalStructure = cv::getStructuringElement(cv::MORPH_RECT, cv::Size(horizontal_size, 1));
//    erode(horizontal, horizontal, horizontalStructure);
//    dilate(horizontal, horizontal, horizontalStructure);
//
//    cv::Mat out;
//    Math::rotate(input, out, angle);
//    std::vector<cv::Vec4i> lines;
//    cv::HoughLinesP(vertical, lines, 22, CV_PI, 14, 0, 1110 );
//
//    for (auto& line : lines) {
//        cv::line(out, cv::Point(line[0], line[1]), cv::Point(line[2], line[3]), cv::Scalar(0, 255, 0), 1);
//    }
//
//    std::vector<cv::Vec4i> lines1;
//    cv::HoughLinesP(horizontal, lines1, 1, CV_PI / 180, 222, 0, 110 );
//
//    for (auto& line : lines1) {
//        cv::line(out, cv::Point(line[0], line[1]), cv::Point(line[2], line[3]), cv::Scalar(0, 255, 0), 1);
//    }
//
//
//    output = out;
//}
