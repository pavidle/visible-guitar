#ifndef VISIBLE_GUITAR_PAINTER_H
#define VISIBLE_GUITAR_PAINTER_H

#include "../Frame.h"


class Painter {
public:
    virtual void draw(const Frame& frame) = 0;
};

#endif //VISIBLE_GUITAR_PAINTER_H
