#include "../include/painters/PointPainter.h"


PointPainter::PointPainter(const std::vector<cv::Point>& points) {
    this->points_ = points;
}

void PointPainter::draw(const Frame &frame) {
    for (const cv::Point& point : points_) {
        cv::circle(frame.getMatrix(), point, 4, cv::Scalar(255, 255, 255), 3);
    }
}
