package com.example.data.mapper

import com.example.data.model.auth.UserDTO
import com.example.domain.mapper.Mapper
import com.example.domain.model.auth.UserEntity

class UserDTOMapper : Mapper<UserEntity, UserDTO> {
    override fun convert(type: UserEntity): UserDTO =
        UserDTO(
            type.username,
            type.email,
            type.password
        )

}