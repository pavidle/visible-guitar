#ifndef VISIBLE_GUITAR_STRINGFINDER_H
#define VISIBLE_GUITAR_STRINGFINDER_H

#include "opencv2/imgproc.hpp"
#include "../services/PCAService.h"


class StringFinder {
protected:
    virtual std::vector<cv::Point> findAllStringPoints() const = 0;
public:
    cv::Point tryFind(int number) const;
};

#endif //VISIBLE_GUITAR_STRINGFINDER_H
