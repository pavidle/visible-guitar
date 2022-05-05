package com.example.data.common

import android.app.Application
import com.example.data.BuildConfig
import com.example.data.remote.ScarletWebSocketApi
import com.squareup.moshi.Moshi
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class ScarletFactory (
    private val application: Application,
    private val moshi: Moshi
)  {

    private val scarlet: Scarlet by lazy {
        Scarlet.Builder()
            .webSocketFactory(getClient().newWebSocketFactory(Constants.WEBSOCKET_URL))
            .addMessageAdapterFactory(MoshiMessageAdapter.Factory(moshi = moshi))
            .addStreamAdapterFactory(FlowStreamAdapter.Factory())
            .lifecycle(AndroidLifecycle.ofApplicationForeground(application = application))
            .build()
    }

    private fun getClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor()
            .setLevel(
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC
                else HttpLoggingInterceptor.Level.NONE
            )
        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
    }

    fun create(): ScarletWebSocketApi = scarlet.create()
}