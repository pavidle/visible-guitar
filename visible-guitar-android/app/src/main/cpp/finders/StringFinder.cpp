#include "../include/finders/StringFinder.h"


cv::Point StringFinder::tryFind(int number) const {
    std::vector<cv::Point> stringPoints = findAllStringPoints();
    cv::Point stringPoint;
    try {
        stringPoint = stringPoints.at(number);
    } catch (const std::out_of_range& oor) {
        //        cv::putText(input,
//                    "Don't find fret or string",
//                    cv::Point(10, 10),
//                    cv::FONT_HERSHEY_DUPLEX,
//                    1.0,
//                    CV_RGB(118, 185, 0),
//                    2);

        return {};
    }
    return stringPoint;
}
