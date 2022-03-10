#include "../include/models/Chord.h"


Chord::Chord(const std::vector<Note>& notes) {
    this->notes_ = notes;
}

std::vector<Note> Chord::getNotes() const {
    return notes_;
}
