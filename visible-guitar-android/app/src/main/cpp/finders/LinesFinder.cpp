#include "../include/finders/LinesFinder.h"


LinesFinder::LinesFinder(FrameConverter* converter) {
    this->converter_ = converter;
}

LinesFinder::~LinesFinder() {
    delete converter_;
}

std::vector<Line> LinesFinder::getFoundedLines(const Frame& frame) const {
    std::vector<Line> lines;
    std::vector<cv::Vec4i> linesCoordinates;
    Frame canny = converter_->apply(frame);
    cv::Mat matrix = canny.getMatrix();
    cv::HoughLinesP(matrix, linesCoordinates, 1, CV_PI / 180, 170, 50, 80);
    for (auto &coordinate : linesCoordinates)
    {
        cv::Point firstPoint = cv::Point(coordinate[0], coordinate[1]);
        cv::Point secondPoint = cv::Point(coordinate[2], coordinate[3]);
        lines.emplace_back(firstPoint, secondPoint);
    }

    return lines;
}
