#include "include/BoundingBoxDrawer.h"
#include <opencv2/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>

std::vector<cv::Point> BoundingBoxDrawer::get_points() {
    return points;
}

void BoundingBoxDrawer::add_point(const cv::Point& point) {
    points.push_back(point);
}

void BoundingBoxDrawer::draw(cv::Mat& input) {
    for (const cv::Point& point : points)
        cv::circle(input, point, 7, cv::Scalar(0, 255, 0));
//    cv::polylines(input, points, false, cv::Scalar(255, 0, 0));
}


