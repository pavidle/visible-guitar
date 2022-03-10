#ifndef VISIBLE_GUITAR_EIGENFACTORY_H
#define VISIBLE_GUITAR_EIGENFACTORY_H

#include "../services/PCAService.h"
#include "../models/Eigen.h"
#include "Factory.h"


class EigenFactory : Factory<Eigen> {
private:
    PCAService pcaService_;
public:
    EigenFactory();
    EigenFactory(const PCAService& pcaService);
    Eigen create() const;
};
#endif //VISIBLE_GUITAR_EIGENFACTORY_H
