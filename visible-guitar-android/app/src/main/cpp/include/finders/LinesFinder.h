

#ifndef VISIBLE_GUITAR_LINESFINDER_H
#define VISIBLE_GUITAR_LINESFINDER_H

#include "opencv2/imgproc.hpp"
#include "../Frame.h"
#include "../models/Line.h"
#include "../converters/CannyConverter.h"


class LinesFinder {
private:
    FrameConverter* converter_;
public:
    virtual ~LinesFinder();
    LinesFinder(FrameConverter* converter);
    std::vector<Line> getFoundedLines(const Frame& frame) const;
};


#endif