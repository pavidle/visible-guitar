package com.example.data.remote.factory

import com.example.data.common.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitFactory(
    private val okHttpClient: OkHttpClient
) {

    val client get() = okHttpClient

    inline fun <reified T> create() =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create<T>()
}
