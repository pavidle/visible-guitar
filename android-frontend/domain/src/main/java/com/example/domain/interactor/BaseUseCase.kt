package com.example.domain.interactor

interface BaseUseCase<in Parameter, out Result> {
    suspend operator fun invoke(param: Parameter) : Result
}
