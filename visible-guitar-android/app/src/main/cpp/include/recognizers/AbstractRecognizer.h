#ifndef VISIBLE_GUITAR_ABSTRACTRECOGNIZER_H
#define VISIBLE_GUITAR_ABSTRACTRECOGNIZER_H


#include "../Frame.h"
#include "../models/Chord.h"

class AbstractRecognizer {
protected:
    Frame frame_;
public:
    AbstractRecognizer(Frame frame);
    virtual std::vector<cv::Point> get(const Chord& chord) const = 0;
};

#endif //VISIBLE_GUITAR_ABSTRACTRECOGNIZER_H
