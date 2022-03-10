#include "../include/models/Note.h"


Note::Note(int fret, int string) {
    this->fret_ = fret;
    this->string_ = string;
}

cv::Point Note::getPoint() const {
    return {fret_, string_};
}
