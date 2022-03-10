

#ifndef VISIBLE_GUITAR_CANNYCONVERTER_H
#define VISIBLE_GUITAR_CANNYCONVERTER_H

#include "FrameConverter.h"


class CannyConverter : public FrameConverter {
public:
    Frame apply(Frame frame) const override;
};

#endif //VISIBLE_GUITAR_CANNYCONVERTER_H
