#include "../include/models/Line.h"


Line::Line(const cv::Point& firstPoint, const cv::Point& secondPoint) {
    this->firstPoint_ = firstPoint;
    this->secondPoint_ = secondPoint;
}

double Line::getSlopeCoefficient() const {
    return (double)(firstPoint_.y - secondPoint_.y) / (firstPoint_.x - secondPoint_.x);
}

double Line::getFreeCoefficient() const {
    return secondPoint_.y - getSlopeCoefficient() * secondPoint_.x;
}

std::vector<cv::Point_<double>> Line::getExtremePoints(const cv::Mat& mat) const {
    cv::Size size = mat.size();
    double x1 = size.width;
    double y0 = getFreeCoefficient();
    double y1 = getSlopeCoefficient() * x1 + y0;
    return std::vector<cv::Point_<double>> {cv::Point(0, y0), cv::Point(x1, y1)};
}

double Line::getLength() const {
    double dx = secondPoint_.x - firstPoint_.x;
    double dy = secondPoint_.y - firstPoint_.y;
    return sqrt(dx * dx + dy * dy);
}

cv::Point Line::getFirstPoint() const {
    return firstPoint_;
}

cv::Point Line::getSecondPoint() const {
    return secondPoint_;
}
