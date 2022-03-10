#include "../include/services/FrameConverterService.h"


FrameConverterService::FrameConverterService(const std::vector<FrameConverter*>& converters) {
    this->converters_ = converters;
}

Frame FrameConverterService::convert(const Frame &frame) {
    Frame f = frame;
    for (auto* converter : converters_) {
        f = converter->apply(f);
    }
    return f;
}
