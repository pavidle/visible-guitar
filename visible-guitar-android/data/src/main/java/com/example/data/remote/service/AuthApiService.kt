package com.example.data.remote.service

import com.example.data.model.auth.AuthRequestDTO
import com.example.data.model.auth.TokenResponseDTO
import com.example.data.model.auth.SignUpRequestDTO
import com.example.data.model.auth.UserDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApiService {

    @POST("auth/jwt/create/")
    suspend fun getTokensPair(
        @Body authRequestDTO: AuthRequestDTO
    ): TokenResponseDTO

    @POST("auth/users/")
    suspend fun createUser(
        @Body signUpRequestDTO: SignUpRequestDTO
    )

    @GET("auth/users/me")
    suspend fun getCurrentUser(): UserDTO

    @POST("auth/jwt/refresh/")
    suspend fun refreshAccessToken(
        refreshToken: String
    ): String
}