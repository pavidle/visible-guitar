#include "../include/services/PCAService.h"
#include "android/log.h"


PCAService::PCAService() = default;

PCAService::PCAService(const std::vector<cv::Point>& contour) {
    this->contour_ = contour;
    this->obj_ = create();
}

cv::Mat PCAService::getPoints() const {
    int countOfPoints = contour_.size();
    cv::Mat points = cv::Mat(countOfPoints, 2, CV_64F);

    for (int i = 0; i < points.rows; i++)
    {
        points.at<double>(i, 0) = contour_[i].x;
        points.at<double>(i, 1) = contour_[i].y;
    }
    return points;
}

cv::PCA PCAService::create() {
    cv::PCA pca(getPoints(), cv::Mat(), cv::PCA::DATA_AS_ROW);
    return pca;
}

cv::PCA PCAService::get() const {
    return obj_;
}

