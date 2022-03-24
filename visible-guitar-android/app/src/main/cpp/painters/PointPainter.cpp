#include "../include/painters/PointPainter.h"


PointPainter::PointPainter(const std::vector<cv::Point>& points) {
    this->points_ = points;
}

void PointPainter::draw(const Frame &frame) {
    for (const cv::Point& point : points_) {
        cv::circle(frame.getMatrix(), point, 5, cv::Scalar(255, 0, 0), 3);
        cv::circle(frame.getMatrix(), point, 3, cv::Scalar(0, 0, 255), 3);

    }
}
