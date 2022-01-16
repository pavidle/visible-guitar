#ifndef VISIBLE_GUITAR_EDGEFRAMECONVERTER_H
#define VISIBLE_GUITAR_EDGEFRAMECONVERTER_H

#include "FrameConverter.h"
#include <opencv2/imgproc.hpp>


class EdgeFrameConverter : public FrameConverter {
public:
    EdgeFrameConverter(Frame* frame);
protected:
    cv::Mat convert_(cv::Mat& input) const override;
};

#endif
