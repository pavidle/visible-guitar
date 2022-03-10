#ifndef VISIBLE_GUITAR_POINTPAINTER_H
#define VISIBLE_GUITAR_POINTPAINTER_H

#include "Painter.h"


class PointPainter : Painter {
private:
    std::vector<cv::Point> points_;
public:
    PointPainter(const std::vector<cv::Point>& points);
    void draw(const Frame& frame) override;
};

#endif //VISIBLE_GUITAR_POINTPAINTER_H
