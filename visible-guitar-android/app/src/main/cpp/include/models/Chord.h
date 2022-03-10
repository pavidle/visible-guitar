#ifndef VISIBLE_GUITAR_CHORD_H
#define VISIBLE_GUITAR_CHORD_H

#include "Note.h"


class Chord {
private:
    std::vector<Note> notes_;
public:
    Chord(const std::vector<Note>& notes);
    std::vector<Note> getNotes() const;
};

#endif //VISIBLE_GUITAR_CHORD_H
