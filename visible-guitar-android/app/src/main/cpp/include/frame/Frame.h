#ifndef VISIBLE_GUITAR_FRAME_H
#define VISIBLE_GUITAR_FRAME_H

#include <opencv2/imgproc.hpp>


class Frame {
private:
    cv::Mat currentMatrix;
    cv::Mat currentMask;
public:
    cv::Mat getCurrentMatrix();
    cv::Mat getCurrentMask();
    void setMatrix(cv::Mat& matrix);
    void setMask(cv::Mat& mask);
//    void set_edge_detection();
};

#endif
