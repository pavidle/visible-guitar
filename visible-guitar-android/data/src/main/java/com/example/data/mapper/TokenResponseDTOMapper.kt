package com.example.data.mapper

import com.example.data.model.auth.TokenResponseDTO
import com.example.domain.mapper.Mapper
import com.example.domain.model.auth.TokenResponseEntity

class TokenResponseDTOMapper : Mapper<TokenResponseDTO, TokenResponseEntity> {
    override fun convert(type: TokenResponseDTO): TokenResponseEntity =
        TokenResponseEntity(
            type.accessToken,
            type.refreshToken
        )
}