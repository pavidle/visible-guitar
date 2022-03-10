

#ifndef VISIBLE_GUITAR_FRAMECONVERTERSERVICE_H
#define VISIBLE_GUITAR_FRAMECONVERTERSERVICE_H

#include "../converters/FrameConverter.h"


class FrameConverterService {
private:
    std::vector<FrameConverter*> converters_;
public:
    FrameConverterService(const std::vector<FrameConverter*>& converters);
    Frame convert(const Frame& frame);
};

#endif //VISIBLE_GUITAR_FRAMECONVERTERSERVICE_H
