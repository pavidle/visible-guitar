#ifndef VISIBLE_GUITAR_MASKFRAMECONVERTER_H
#define VISIBLE_GUITAR_MASKFRAMECONVERTER_H

#include "FrameConverter.h"
#include "../points/BoundingBoxPointsRepository.h"


class MaskFrameConverter : public FrameConverter {
private:
    BoundingBoxPointsRepository* pointsRepository;
public:
    MaskFrameConverter(Frame *frame, BoundingBoxPointsRepository* pointsRepository);
protected:
    cv::Mat convert_(cv::Mat& input) const override;
    void set(cv::Mat& input) const override;
};

#endif