#ifndef VISIBLE_GUITAR_ROTATEFRAMECONVERTER_H
#define VISIBLE_GUITAR_ROTATEFRAMECONVERTER_H

#include "FrameConverter.h"


class RotateFrameConverter : public FrameConverter {
public:
    RotateFrameConverter(Frame* frame);
protected:
    cv::Mat convert_(cv::Mat& input) const override;
    cv::Mat get() const override;
};

#endif
