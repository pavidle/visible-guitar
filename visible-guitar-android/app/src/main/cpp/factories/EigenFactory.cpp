#include "../include/factories/EigenFactory.h"
#include "android/log.h"


EigenFactory::EigenFactory() = default;

EigenFactory::EigenFactory(const PCAService& pcaService) {
    this->pcaService_ = pcaService;
}

Eigen EigenFactory::create() const {
    std::vector<cv::Point2d> eigenvectors(2);
    std::vector<double> eigenvalues(2);
    cv::PCA pca = pcaService_.get();
    for (int i = 0; i < 2; i++)
    {
        eigenvectors[i] = cv::Point2d(pca.eigenvectors.at<double>(i, 0),
                                      pca.eigenvectors.at<double>(i, 1));
        eigenvalues[i] = pca.eigenvalues.at<double>(i);
    }

    return Eigen(eigenvectors, eigenvalues);
}
