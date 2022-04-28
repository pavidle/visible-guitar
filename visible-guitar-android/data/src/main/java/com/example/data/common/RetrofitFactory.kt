package com.example.data.common

import com.example.data.remote.ChordApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory : ApiFactory<ChordApi> {
    override fun create(): ChordApi =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ChordApi::class.java)
}
