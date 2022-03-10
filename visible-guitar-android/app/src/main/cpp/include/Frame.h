#ifndef VISIBLE_GUITAR_FRAME_H
#define VISIBLE_GUITAR_FRAME_H

#include "opencv2/imgproc.hpp"


class Frame {
private:
    cv::Mat matrix_;
public:
    Frame();
    Frame(const cv::Mat& matrix);
    void rotate(double angle);
    void erodeAndDilate();
    cv::Mat getMatrix() const;
};

#endif //VISIBLE_GUITAR_FRAME_H
