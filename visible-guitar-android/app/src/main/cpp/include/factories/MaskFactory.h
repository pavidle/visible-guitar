#ifndef VISIBLE_GUITAR_MASKFACTORY_H
#define VISIBLE_GUITAR_MASKFACTORY_H

#include "Factory.h"
#include "../Frame.h"


class MaskFactory : Factory<Frame> {
private:
    Frame frame_;
public:
    MaskFactory(const Frame& frame);
    Frame create() const override;
};

#endif //VISIBLE_GUITAR_MASKFACTORY_H
