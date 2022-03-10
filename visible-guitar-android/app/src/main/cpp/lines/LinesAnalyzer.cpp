#include <cmath>
#include "../include/lines/LinesAnalyzer.h"


bool sortAscending(const Line& first, const Line& second) {
    return first.getFreeCoefficient() < second.getFreeCoefficient();
}

LinesAnalyzer::LinesAnalyzer() = default;

LinesAnalyzer::LinesAnalyzer(const std::vector<Line>& lines) {
    this->lines_ = lines;
    std::sort(this->lines_.begin(), this->lines_.end(), sortAscending);
}


Line LinesAnalyzer::tryGetTopmostLine(const Frame& frame) const {
    if (!lines_.empty())
        return lines_[0];
    return {cv::Point(0, 0), cv::Point(frame.getMatrix().cols, 0)};

}

Line LinesAnalyzer::tryGetLowestLine(const Frame& frame) const {
    if (!lines_.empty())
        return lines_.back();
    return {cv::Point(0, 0), cv::Point(frame.getMatrix().cols, 0)};
}

std::vector<cv::Point> LinesAnalyzer::getPoints(const Frame& frame) const {
    Line topLine = tryGetTopmostLine(frame);
    Line lowerLine = tryGetLowestLine(frame);
    cv::Mat mat = frame.getMatrix();
    std::vector<cv::Point_<double>> topExtremePoints = topLine.getExtremePoints(mat);
    std::vector<cv::Point_<double>> lowerExtremePoints = lowerLine.getExtremePoints(mat);

    return std::vector<cv::Point> {
        topExtremePoints[0],
        topExtremePoints[1],
        lowerExtremePoints[1],
        lowerExtremePoints[0]
    };

}

double LinesAnalyzer::calculateAverageSlopeAngle() {
    if (lines_.empty())
        return 0;
    double sum_of_angles = 0;
    for (const Line& line : lines_) {
        double angle = atan(line.getSlopeCoefficient()) * (180 / CV_PI);
        sum_of_angles += angle;

    }
    return sum_of_angles / lines_.size();
}
