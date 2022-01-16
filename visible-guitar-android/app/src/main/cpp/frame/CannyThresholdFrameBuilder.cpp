#include "../include/frame/CannyThresholdFrameBuilder.h"
#include "../include/converters/MaskFrameConverter.h"
#include "../include/converters/EdgeFrameConverter.h"
#include "../include/converters/RotateFrameConverter.h"


CannyThresholdFrameBuilder::CannyThresholdFrameBuilder() {
    frame = new Frame();
}

CannyThresholdFrameBuilder::~CannyThresholdFrameBuilder() {
    delete frame;
}

void CannyThresholdFrameBuilder::add(FrameConverter* frameConverter) {
    frameConverter->convert();
}

void CannyThresholdFrameBuilder::addInputMatrix(cv::Mat& input) const {
    frame->setMatrix(input);
}

void CannyThresholdFrameBuilder::addMask(BoundingBoxPointsRepository* pointsRepository) const {
    add(new MaskFrameConverter(frame, pointsRepository));
}

void CannyThresholdFrameBuilder::addEdgeDetection() const {
    add(new EdgeFrameConverter(frame));
}

void CannyThresholdFrameBuilder::addRotation() const {
    add(new RotateFrameConverter(frame));
}

Frame* CannyThresholdFrameBuilder::build() const {
    return frame;
}
