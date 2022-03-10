

#ifndef VISIBLE_GUITAR_FRAMECONVERTER_H
#define VISIBLE_GUITAR_FRAMECONVERTER_H

#include "../Frame.h"


class FrameConverter {
public:
    virtual ~FrameConverter();
    virtual Frame apply(Frame frame) const = 0;
};


#endif //VISIBLE_GUITAR_FRAMECONVERTER_H
