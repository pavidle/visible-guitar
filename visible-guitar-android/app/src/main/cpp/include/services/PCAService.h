#ifndef VISIBLE_GUITAR_PCASERVICE_H
#define VISIBLE_GUITAR_PCASERVICE_H

#include "opencv2/imgproc.hpp"


class PCAService {
private:
    std::vector<cv::Point> contour_;
    cv::PCA obj_;
    cv::Mat getPoints() const;
    cv::PCA create();
public:
    PCAService();
    PCAService(const std::vector<cv::Point>& contour);
    cv::PCA get() const;
};

#endif //VISIBLE_GUITAR_PCASERVICE_H
