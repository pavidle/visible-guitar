#ifndef VISIBLE_GUITAR_NOTE_H
#define VISIBLE_GUITAR_NOTE_H

#include "opencv2/imgproc.hpp"


class Note {
private:
    int fret_;
    int string_;
public:
    Note(int fret, int string);
    cv::Point getPoint() const;
};

#endif //VISIBLE_GUITAR_NOTE_H
