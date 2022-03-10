#include "../include/converters/CannyConverter.h"


Frame CannyConverter::apply(Frame frame) const {
    cv::Mat canny;
    cv::Canny(frame.getMatrix(), canny, 100, 300);
    return Frame(canny);
}
