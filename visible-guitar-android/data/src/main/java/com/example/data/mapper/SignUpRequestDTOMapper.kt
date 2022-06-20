package com.example.data.mapper

import com.example.data.model.auth.SignUpRequestDTO
import com.example.domain.mapper.Mapper
import com.example.domain.model.auth.SignUpRequestEntity

class SignUpRequestDTOMapper : Mapper<SignUpRequestEntity, SignUpRequestDTO> {
    override fun convert(type: SignUpRequestEntity): SignUpRequestDTO =
        SignUpRequestDTO(
            type.username,
            type.email,
            type.password
        )

}