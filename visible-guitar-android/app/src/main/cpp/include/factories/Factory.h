#ifndef VISIBLE_GUITAR_FACTORY_H
#define VISIBLE_GUITAR_FACTORY_H

template<typename T>
class Factory {
public:
    virtual T create() const = 0;
};

#endif //VISIBLE_GUITAR_FACTORY_H
