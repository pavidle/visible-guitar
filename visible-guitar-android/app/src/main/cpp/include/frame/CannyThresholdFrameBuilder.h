#ifndef VISIBLE_GUITAR_CANNYTHRESHOLDFRAMEBUILDER_H
#define VISIBLE_GUITAR_CANNYTHRESHOLDFRAMEBUILDER_H

#include "FrameBuilder.h"
#include "../converters/FrameConverter.h"


class CannyThresholdFrameBuilder : public FrameBuilder {
private:
    Frame* frame;
    static void add(FrameConverter* frameConverter);
public:
    CannyThresholdFrameBuilder();
    ~CannyThresholdFrameBuilder();
    void addMask(BoundingBoxPointsRepository* pointsRepository) const override;
    void addInputMatrix(cv::Mat& input) const override;
    void addEdgeDetection() const override;
    void addRotation() const override;
    Frame* build() const override;

//    void add_strings_detection();
//    void add_frets_detection();
};

#endif
