package com.example.data.remote.authenticator

import android.content.SharedPreferences
import android.util.Log
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class JsonWebTokenAuthenticator(
    private val localTokenProvider: LocalTokenProvider
//    private val authApiService: AuthApiService
) : Authenticator {

    private val Response.count: Int
        get() = generateSequence(this) { it.priorResponse }.count()

    override fun authenticate(route: Route?, response: Response): Request? {
        val request = response.request
        if (response.count >= 3) {
            return null
        }
        Log.e("HERE", "AUTHENTICATE!!")
        if (request.header("Authorization") != null) {
            return null
        }
        val token = localTokenProvider.getAccessToken()
        return token?.let {
            request.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        }
    }
}


//        val request = response.request
//        val lastSavedAccessToken = encryptedSharedPreferences.getString("access_token", null)
//        val authorizationHeader = request.header("Authorization")
//        val accessTokenOfRequest = getTokenByHeader(authorizationHeader)
//        if (accessTokenOfRequest != lastSavedAccessToken)
//
//            return request.newBuilder()
//                .header("Authorization", "Bearer $accessTokenOfRequest")
//                .build()


