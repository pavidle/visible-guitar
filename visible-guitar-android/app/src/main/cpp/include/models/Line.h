#ifndef VISIBLE_GUITAR_LINE_H
#define VISIBLE_GUITAR_LINE_H

#include "opencv2/imgproc.hpp"

class Line {
private:
    cv::Point firstPoint_;
    cv::Point secondPoint_;
public:
    Line(const cv::Point& firstPoint, const cv::Point& secondPoint);
    double getSlopeCoefficient() const;
    double getFreeCoefficient() const;
    cv::Point getFirstPoint() const;
    cv::Point getSecondPoint() const;
    double getLength() const;
    std::vector<cv::Point_<double>> getExtremePoints(const cv::Mat& mat) const;
};

#endif
