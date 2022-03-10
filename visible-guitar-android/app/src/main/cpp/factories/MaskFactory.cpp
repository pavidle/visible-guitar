#include "../include/factories/MaskFactory.h"
#include "../include/finders/LinesFinder.h"
#include "../include/converters/CannyConverter.h"
#include "../include/lines/LinesAnalyzer.h"
#include "../include/converters/MaskFrameConverter.h"
#include "../include/converters/DilationAndRotationFrameConverter.h"
#include "../include/services/FrameConverterService.h"


MaskFactory::MaskFactory(const Frame &frame) {
    this->frame_ = frame;
}

Frame MaskFactory::create() const {
    LinesFinder hough = LinesFinder(new CannyConverter());
    std::vector<Line> lines = hough.getFoundedLines(frame_);
    LinesAnalyzer analyzer(lines);
    double angle = analyzer.calculateAverageSlopeAngle();

    std::vector<FrameConverter*> converters {
            new MaskFrameConverter(analyzer),
            new DilationAndRotationFrameConverter(angle)
    };

    FrameConverterService frameService(converters);
    return frameService.convert(frame_);
}
