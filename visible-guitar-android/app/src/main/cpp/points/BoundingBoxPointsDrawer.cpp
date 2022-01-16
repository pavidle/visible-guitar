#include "../include/points/BoundingBoxPointsDrawer.h"


BoundingBoxPointsDrawer::BoundingBoxPointsDrawer(BoundingBoxPointsRepository* pointsRepository) {
    this->pointsRepository = pointsRepository;
}

void BoundingBoxPointsDrawer::draw(cv::Mat& input) {
    std::vector<cv::Point> points = pointsRepository->getPoints();
    for (const cv::Point& point : points)
        cv::circle(input, point, 7, cv::Scalar(0, 255, 0));
}
