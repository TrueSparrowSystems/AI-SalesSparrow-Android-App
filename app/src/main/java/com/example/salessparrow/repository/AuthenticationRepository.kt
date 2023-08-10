package com.example.salessparrow.repository

import android.util.Log
import com.example.salessparrow.api.ApiService
import com.example.salessparrow.models.CurrentUser
import com.example.salessparrow.models.RedirectUrl

import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun isLoggedIn(): Boolean {
        val currentUser = getCurrentUser()
        Log.i("Authentication getCurrentUser", "isUserLoggedIn: ${currentUser?.id !== null}")
        if (currentUser != null) {
            return currentUser.id !== null
        }
        return false
    }


    suspend fun getConnectWithSalesForceUrl(
        redirectUri: String
    ): RedirectUrl? {
        return try {
            val response = apiService.getSalesForceRedirectUrl(redirectUri)
            val redirectUrl = response.body()
            if (response.isSuccessful) {
                redirectUrl
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun salesForceConnect(
        code: String,
        redirectUri: String
    ): CurrentUser? {
        return try {
            val response = apiService.salesForceConnect(code, redirectUri);
            val currentUser = response.body()
            Log.i("MyApp", "Response: $currentUser")
            if (response.isSuccessful) {
                currentUser
            } else {
                null
            }
        } catch (e: Exception) {
            Log.i("MyApp", "Error salesForceConnect: $e")
            null
        }
    }

    suspend fun getCurrentUser(): CurrentUser? {
        return try {
            Log.i("Authentication inside", "getCurrentUser: ")
            val response = apiService.getCurrentUser();
            Log.i("Authentication after response", "getCurrentUser: ${response}")
            val currentUser = response.body()

            Log.i("Authentication resoponse body", "getCurrentUser: $currentUser")

            if (response.isSuccessful) {
                currentUser!!
            } else {
                null
            }
        } catch (e: Exception) {
            Log.i("Authentication exception", "getCurrentUser: $e")
            null
        }
    }

    suspend fun logout(): Boolean {
        return try {
            val response = apiService.logout();
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    suspend fun disconnectSalesForce(): Boolean {
        return try {
            val response = apiService.disconnectSalesForce();
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }


}