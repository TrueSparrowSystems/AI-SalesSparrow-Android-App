package com.example.salessparrow.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.salessparrow.api.ApiService
import com.example.salessparrow.models.SalesForceConnectRequest
import com.example.salessparrow.models.RedirectUrl
import com.example.salessparrow.models.CurrentUserResponse
import com.example.salessparrow.services.NavigationService
import com.example.salessparrow.util.CookieManager
import com.example.salessparrow.util.Screens

import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    private val apiService: ApiService,
    private val cookieManager: CookieManager
) {


    private val _currentUserLiveData = MutableLiveData<CurrentUserResponse?>()
    val currentUserLiveData: LiveData<CurrentUserResponse?> = _currentUserLiveData

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
    ) {
        try {
            val request = SalesForceConnectRequest(code = code, redirect_uri = redirectUri)
            val response = apiService.salesForceConnect(request);
            val responseCookie = response.headers()["Set-Cookie"]
            Log.i("MyApp", "Response: $responseCookie")
            if (responseCookie != null) {
                cookieManager.saveCookie(responseCookie)
            }
            val currentUser = response.body()
            _currentUserLiveData.value = currentUser
            Log.i("MyApp", "Response: $currentUser")
            if (response.isSuccessful) {
                currentUser
                NavigationService.navigateWithPopUp(
                    Screens.HomeScreen.route,
                    Screens.LoginScreen.route
                )
            } else {
                null
            }
        } catch (e: Exception) {
            Log.i("MyApp", "Error salesForceConnect: $e")
            null
        }
    }

    suspend fun getCurrentUser() {
        try {
            Log.i("Authentication inside", "getCurrentUser: ")

            val response = apiService.getCurrentUser()
            Log.i("Authentication after response", "getCurrentUser: ${response.body()}")
            val currentUser = response.body()

            _currentUserLiveData.value = currentUser// Update the LiveData

            Log.i(
                "Authentication response body",
                "getCurrentUser: ${response} ${response.body()} ${_currentUserLiveData.value!!.current_user}"
            )

            // No need to return anything here
        } catch (e: Exception) {
            Log.i("Authentication exception", "getCurrentUser: $e")
            _currentUserLiveData.postValue(null) // Update LiveData with null in case of error
        }
    }


    suspend fun logout(): Boolean {
        return try {
            val response = apiService.logout();
            cookieManager.clearCookie();
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