package com.example.domain.interactor

interface UseCase<in P, out R> {
    operator fun invoke(parameter: P): R
}