#include "../include/models/Eigen.h"


Eigen::Eigen(const std::vector<cv::Point2d>& vectors, const std::vector<double>& values) {
    this->vectors_ = vectors;
    this->values_ = values;
}

std::vector<cv::Point2d> Eigen::getVectors() {
    return vectors_;
}

std::vector<double> Eigen::getValues() {
    return values_;
}
