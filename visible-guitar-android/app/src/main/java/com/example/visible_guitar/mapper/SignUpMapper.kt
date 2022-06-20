package com.example.visible_guitar.mapper

import com.example.domain.mapper.Mapper
import com.example.domain.model.auth.SignUpRequestEntity
import com.example.visible_guitar.model.auth.SignUpRequest

class SignUpMapper : Mapper<SignUpRequest, SignUpRequestEntity> {
    override fun convert(type: SignUpRequest): SignUpRequestEntity =
        SignUpRequestEntity(
            type.username,
            type.email,
            type.password
        )
}