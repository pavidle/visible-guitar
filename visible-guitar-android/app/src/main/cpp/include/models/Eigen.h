#ifndef VISIBLE_GUITAR_EIGEN_H
#define VISIBLE_GUITAR_EIGEN_H

#include <vector>
#include "opencv2/imgproc.hpp"


class Eigen {
private:
    std::vector<cv::Point2d> vectors_;
    std::vector<double> values_;
public:
    Eigen(const std::vector<cv::Point2d>& vectors, const std::vector<double>& values);
    std::vector<cv::Point2d> getVectors();
    std::vector<double> getValues();
};
#endif //VISIBLE_GUITAR_EIGEN_H
