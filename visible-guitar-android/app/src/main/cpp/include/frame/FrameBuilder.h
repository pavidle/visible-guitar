#ifndef VISIBLE_GUITAR_FRAMEBUILDER_H
#define VISIBLE_GUITAR_FRAMEBUILDER_H

#include "../frame/Frame.h"
#include "../points/BoundingBoxPointsRepository.h"


class FrameBuilder {
public:
    virtual ~FrameBuilder() {}
    virtual void addInputMatrix(cv::Mat& input) const = 0;
    virtual void addMask(BoundingBoxPointsRepository* pointsRepository) const = 0;
    virtual void addEdgeDetection() const = 0;
    virtual void addRotation() const = 0;
    virtual Frame* build() const = 0;
//   virtual void add_edge_detection() const = 0;
//    virtual void add_strings_detection() const = 0;
//    virtual void add_frets_detection() const = 0;
};

#endif
