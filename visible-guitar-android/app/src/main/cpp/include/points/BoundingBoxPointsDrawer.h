#ifndef VISIBLE_GUITAR_BOUNDINGBOXPOINTSDRAWER_H
#define VISIBLE_GUITAR_BOUNDINGBOXPOINTSDRAWER_H

#include "BoundingBoxPointsRepository.h"


class BoundingBoxPointsDrawer {
private:
    BoundingBoxPointsRepository* pointsRepository;
public:
    BoundingBoxPointsDrawer(BoundingBoxPointsRepository* pointsRepository);
    void draw(cv::Mat& input);
};

#endif
