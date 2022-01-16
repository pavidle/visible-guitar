#ifndef VISIBLE_GUITAR_GUITARRECOGNIZER_H
#define VISIBLE_GUITAR_GUITARRECOGNIZER_H

#include "frame/FrameBuilder.h"
#include "points/BoundingBoxPointsRepository.h"

class GuitarRecognizer {
private:
    FrameBuilder* frameBuilder;
    BoundingBoxPointsRepository* pointsRepository;
public:
    GuitarRecognizer(FrameBuilder* frameBuilder, BoundingBoxPointsRepository* pointsRepository);
    void findEdges(cv::Mat& input);
    void show(cv::Mat& output);
};

#endif
