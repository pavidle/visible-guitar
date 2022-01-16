#include "../include/converters/FrameConverter.h"


FrameConverter::FrameConverter(Frame* frame) {
    this->frame = frame;
}

FrameConverter::~FrameConverter() {
    delete frame;
}

void FrameConverter::convert() const {
    cv::Mat matrix = this->get();
    cv::Mat output = this->convert_(matrix);
    this->set(output);
}

void FrameConverter::set(cv::Mat& input) const {
    frame->setMatrix(input);
}

cv::Mat FrameConverter::get() const {
    return frame->getCurrentMatrix();
}
