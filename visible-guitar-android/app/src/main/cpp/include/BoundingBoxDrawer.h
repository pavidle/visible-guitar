#ifndef VISIBLE_GUITAR_BOUNDINGBOXDRAWER_H
#define VISIBLE_GUITAR_BOUNDINGBOXDRAWER_H

#include <opencv2/core.hpp>


class BoundingBoxDrawer {
private:
    std::vector<cv::Point> points;
public:
    std::vector<cv::Point> get_points();
    void add_point(const cv::Point& point);
    void draw(cv::Mat& input);
};

#endif
