#include "../include/points/BoundingBoxPointsRepository.h"


std::vector<cv::Point> BoundingBoxPointsRepository::getPoints() {
    return points;
}

void BoundingBoxPointsRepository::addPoint(const cv::Point& point) {
    points.push_back(point);
}
