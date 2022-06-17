package com.example.visible_guitar.mapper

import com.example.domain.mapper.Mapper
import com.example.domain.model.auth.AuthRequestEntity
import com.example.visible_guitar.model.auth.AuthRequest

class AuthRequestMapper : Mapper<AuthRequest, AuthRequestEntity> {
    override fun convert(type: AuthRequest): AuthRequestEntity =
        AuthRequestEntity(
            type.email,
            type.password
        )
}