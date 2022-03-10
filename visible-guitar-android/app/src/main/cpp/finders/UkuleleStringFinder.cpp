#include "../include/finders/UkuleleStringFinder.h"
#include "../include/factories/EigenFactory.h"
#include "android/log.h"


bool sortByYCallback(const cv::Point& first, const cv::Point& second) {
    return first.y < second.y;
}

UkuleleStringFinder::UkuleleStringFinder(const cv::Point& fretCenter, const PCAService& pcaService) {
    this->fretCenter_ = fretCenter;
    this->pcaService_ = pcaService;
}

std::vector<cv::Point> UkuleleStringFinder::findAllStringPoints() const {
    std::vector<cv::Point> stringPoints;
    EigenFactory factory = EigenFactory(pcaService_);

    Eigen eigen = factory.create();

    std::vector<cv::Point2d> eigenvectors = eigen.getVectors();
    std::vector<double> eigenvalues = eigen.getValues();
    for (int i = 0; i < 3; i++) {
        cv::Point point = fretCenter_ + 0.05 * i * cv::Point(static_cast<int>(eigenvectors[0].x * eigenvalues[0]),
                                                        static_cast<int>(eigenvectors[0].y * eigenvalues[0])
        );
        stringPoints.push_back(point);
    }

    for (int i = 0; i < 2; i++) {
        cv::Point point = fretCenter_ - 0.05 * i * cv::Point(static_cast<int>(eigenvectors[0].x * eigenvalues[0]),
                                                        static_cast<int>(eigenvectors[0].y * eigenvalues[0])
        );
        stringPoints.push_back(point);
    }
    std::sort(stringPoints.begin(), stringPoints.end(), sortByYCallback);
    return stringPoints;
}
