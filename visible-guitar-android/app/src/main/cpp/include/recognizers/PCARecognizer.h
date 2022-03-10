#ifndef VISIBLE_GUITAR_PCARECOGNIZER_H
#define VISIBLE_GUITAR_PCARECOGNIZER_H

#include "AbstractRecognizer.h"


class PCARecognizer : public AbstractRecognizer {
public:
    PCARecognizer(const Frame& frame);
    virtual std::vector<cv::Point> get(const Chord& chord) const override;
};

#endif //VISIBLE_GUITAR_PCARECOGNIZER_H
