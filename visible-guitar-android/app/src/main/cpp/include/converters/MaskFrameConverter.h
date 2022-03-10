

#ifndef VISIBLE_GUITAR_MASKFRAMECONVERTER_H
#define VISIBLE_GUITAR_MASKFRAMECONVERTER_H

#include "FrameConverter.h"
#include "../lines/LinesAnalyzer.h"


class MaskFrameConverter : public FrameConverter {
private:
    LinesAnalyzer linesAnalyzer_;
public:
    MaskFrameConverter(const LinesAnalyzer& linesAnalyzer);
    Frame apply(Frame frame) const override;

};

#endif //VISIBLE_GUITAR_MASKFRAMECONVERTER_H
