package com.example.data.remote.factory

import com.example.data.BuildConfig
import com.example.data.remote.authenticator.JsonWebTokenAuthenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class HttpClientFactory(
    private val jsonWebTokenAuthenticator: JsonWebTokenAuthenticator
) {

    fun create(): OkHttpClient {
        val logger = HttpLoggingInterceptor()
            .setLevel(
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC
                else HttpLoggingInterceptor.Level.NONE
            )
        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .authenticator(jsonWebTokenAuthenticator)
            .build()
    }
}