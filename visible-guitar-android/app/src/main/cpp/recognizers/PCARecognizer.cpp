#include "../include/recognizers/PCARecognizer.h"
#include "../include/services/PCAService.h"
#include "../include/finders/FretCenterFinder.h"
#include "../include/finders/UkuleleStringFinder.h"


PCARecognizer::PCARecognizer(const Frame& frame) : AbstractRecognizer(frame) {}


std::vector<cv::Point> PCARecognizer::get(const Chord &chord) const {
    std::vector<cv::Point> points;
    for (Note note : chord.getNotes()) {
        cv::Point notePoint = note.getPoint();
        int fret = notePoint.x;
        int string = notePoint.y;
        std::vector<std::vector<cv::Point>> contours;
        cv::Mat matrix = frame_.getMatrix();
        cv::findContours(matrix, contours, cv::RETR_LIST, cv::CHAIN_APPROX_NONE);

        if (contours.empty()) return std::vector<cv::Point>();
        std::vector<cv::Point> contour;
        try {
            contour = contours.at(contours.size() - fret);
        } catch (const std::out_of_range& oor) {
            return std::vector<cv::Point>();
        }

        PCAService pcaService(contour);
        FretCenterFinder fretCenterFinder(pcaService);
        cv::Point center = fretCenterFinder.find();
        UkuleleStringFinder stringFinder(center, pcaService);
        cv::Point point = stringFinder.tryFind(string);
        points.push_back(point);
    }
    return points;
}




