#ifndef VISIBLE_GUITAR_BOUNDINGBOXPOINTSREPOSITORY_H
#define VISIBLE_GUITAR_BOUNDINGBOXPOINTSREPOSITORY_H

#include <opencv2/imgproc.hpp>


class BoundingBoxPointsRepository {
private:
    std::vector<cv::Point> points;
public:
    std::vector<cv::Point> getPoints();
    void addPoint(const cv::Point& point);
};

#endif
