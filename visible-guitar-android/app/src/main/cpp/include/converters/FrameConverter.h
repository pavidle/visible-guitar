#ifndef VISIBLE_GUITAR_FRAMECONVERTER_H
#define VISIBLE_GUITAR_FRAMECONVERTER_H

#include "../frame/Frame.h"


class FrameConverter {
public:
    FrameConverter(Frame* frame);
    virtual ~FrameConverter();
    void convert() const;
protected:
    Frame* frame;
    virtual cv::Mat convert_(cv::Mat& input) const = 0;
    virtual cv::Mat get() const;
    virtual void set(cv::Mat& input) const;
};

#endif
