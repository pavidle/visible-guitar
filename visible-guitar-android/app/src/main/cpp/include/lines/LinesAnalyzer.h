#ifndef VISIBLE_GUITAR_LINESANALYZER_H
#define VISIBLE_GUITAR_LINESANALYZER_H

#include "../models/Line.h"
#include "../Frame.h"


class LinesAnalyzer {
private:
    std::vector<Line> lines_;
    Line tryGetTopmostLine(const Frame& frame) const;
    Line tryGetLowestLine(const Frame& frame) const;
public:
    LinesAnalyzer();
    LinesAnalyzer(const std::vector<Line>& lines);
    std::vector<cv::Point> getPoints(const Frame& frame) const;
    double calculateAverageSlopeAngle();
};


#endif
