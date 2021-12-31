#ifndef VISIBLE_GUITAR_BOUNDINGBOXDRAWER_H
#define VISIBLE_GUITAR_BOUNDINGBOXDRAWER_H

#include <opencv2/core.hpp>


class BoundingBoxDrawer {
private:
    std::vector<cv::Point> points;
public:
    std::vector<cv::Point> getPoints();
    void addPoint(const cv::Point&);
    void draw(cv::Mat&);
};

#endif
