package com.example.visible_guitar.mapper

import com.example.domain.mapper.Mapper
import com.example.domain.model.auth.UserEntity
import com.example.visible_guitar.model.auth.User

class UserMapper : Mapper<User, UserEntity> {
    override fun convert(type: User): UserEntity =
        UserEntity(
            type.username,
            type.email,
            type.password
        )
}