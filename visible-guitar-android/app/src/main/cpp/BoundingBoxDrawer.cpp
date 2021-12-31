#include "include/BoundingBoxDrawer.h"
#include <opencv2/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>

std::vector<cv::Point> BoundingBoxDrawer::getPoints() {
    return points;
}

void BoundingBoxDrawer::addPoint(const cv::Point& point) {
    points.push_back(point);
}

void BoundingBoxDrawer::draw(cv::Mat& mat) {
    for (const cv::Point& point : points)
        cv::circle(mat, point, 7, cv::Scalar(0, 255, 0));
    cv::polylines(mat, points, false, cv::Scalar(255, 0, 0));
}


