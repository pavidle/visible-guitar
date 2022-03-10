#ifndef VISIBLE_GUITAR_UKULELESTRINGFINDER_H
#define VISIBLE_GUITAR_UKULELESTRINGFINDER_H

#include "StringFinder.h"
#include "../factories/EigenFactory.h"


class UkuleleStringFinder : public StringFinder {
private:
    cv::Point fretCenter_;
    PCAService pcaService_;
protected:
    std::vector<cv::Point> findAllStringPoints() const override;
public:
    UkuleleStringFinder(const cv::Point& fretCenter, const PCAService& pcaService);
};

#endif
