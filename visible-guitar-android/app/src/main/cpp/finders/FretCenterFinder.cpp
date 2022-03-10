#include "../include/finders/FretCenterFinder.h"
#include "android/log.h"


FretCenterFinder::FretCenterFinder(const PCAService& pcaService) {
    this->pcaService_ = pcaService;
}

cv::Point FretCenterFinder::find() {
    return {
            static_cast<int>(pcaService_.get().mean.at<double>(0, 0)),
            static_cast<int>(pcaService_.get().mean.at<double>(0, 1))
    };
}
