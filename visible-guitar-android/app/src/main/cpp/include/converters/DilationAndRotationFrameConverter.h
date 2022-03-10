#ifndef VISIBLE_GUITAR_DILATIONANDROTATIONFRAMECONVERTER_H
#define VISIBLE_GUITAR_DILATIONANDROTATIONFRAMECONVERTER_H

#include "FrameConverter.h"


class DilationAndRotationFrameConverter : public FrameConverter {
private:
    double angle_;
public:
    DilationAndRotationFrameConverter(double angle);
    Frame apply(Frame frame) const override;
};


#endif //VISIBLE_GUITAR_DILATIONANDROTATIONFRAMECONVERTER_H
