package com.example.data.remote.authenticator

import android.content.SharedPreferences
import javax.inject.Inject

class LocalTokenProvider @Inject constructor(
    private val encryptedSharedPreferences: SharedPreferences
) {

    companion object {
        const val ACCESS_TOKEN = "access"
        const val REFRESH_TOKEN = "refresh"
    }

    fun setAccessToken(token: String) =
        encryptedSharedPreferences.edit()
            .putString(ACCESS_TOKEN, token)
            .apply()

    fun deleteAccessToken() =
        encryptedSharedPreferences.edit()
            .remove(ACCESS_TOKEN)
            .apply()

    fun setRefreshToken(token: String) =
        encryptedSharedPreferences.edit()
            .putString(REFRESH_TOKEN, token)
            .apply()

    fun getAccessToken(): String? =
        encryptedSharedPreferences.getString(ACCESS_TOKEN, null)

    fun getRefreshToken(): String? =
        encryptedSharedPreferences.getString(REFRESH_TOKEN, null)

}