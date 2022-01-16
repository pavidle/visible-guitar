#include "include/GuitarRecognizer.h"


GuitarRecognizer::GuitarRecognizer(FrameBuilder* frameBuilder, BoundingBoxPointsRepository* pointsRepository) {
    this->frameBuilder = frameBuilder;
    this->pointsRepository = pointsRepository;
}

void GuitarRecognizer::findEdges(cv::Mat& input) {
    frameBuilder->addInputMatrix(input);
    frameBuilder->addMask(pointsRepository);
    frameBuilder->addEdgeDetection();
    frameBuilder->addRotation();
}

void GuitarRecognizer::show(cv::Mat &output) {
    Frame* frame = frameBuilder->build();
    output = frame->getCurrentMatrix();
}
