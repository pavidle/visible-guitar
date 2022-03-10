
#ifndef VISIBLE_GUITAR_FRETCENTERFINDER_H
#define VISIBLE_GUITAR_FRETCENTERFINDER_H

#include "opencv2/imgproc.hpp"
#include "../services/PCAService.h"


class FretCenterFinder {
private:
    PCAService pcaService_;
public:
    FretCenterFinder(const PCAService& pcaService);
    cv::Point find();
};

#endif //VISIBLE_GUITAR_FRETCENTERFINDER_H
