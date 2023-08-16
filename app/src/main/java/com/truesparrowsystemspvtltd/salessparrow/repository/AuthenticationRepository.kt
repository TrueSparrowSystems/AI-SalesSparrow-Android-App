package com.truesparrowsystemspvtltd.salessparrow.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.truesparrowsystemspvtltd.salessparrow.api.ApiService
import com.truesparrowsystemspvtltd.salessparrow.models.SalesForceConnectRequest
import com.truesparrowsystemspvtltd.salessparrow.models.RedirectUrl
import com.truesparrowsystemspvtltd.salessparrow.models.CurrentUserResponse
import com.truesparrowsystemspvtltd.salessparrow.services.NavigationService
import com.truesparrowsystemspvtltd.salessparrow.util.CookieManager
import com.truesparrowsystemspvtltd.salessparrow.util.NetworkResponse
import com.truesparrowsystemspvtltd.salessparrow.util.Screens
import org.json.JSONObject

import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    private val apiService: ApiService,
    private val cookieManager: CookieManager
) {


    private val _currentUserLiveData = MutableLiveData<NetworkResponse<CurrentUserResponse>>()
    val currentUserLiveData: LiveData<NetworkResponse<CurrentUserResponse>>
        get() = _currentUserLiveData

    private val _getSalesForcceConnectUrl = MutableLiveData<NetworkResponse<RedirectUrl>>()
    val getSalesForcceConnectUrl: LiveData<NetworkResponse<RedirectUrl>>
        get() = _getSalesForcceConnectUrl

    suspend fun getConnectWithSalesForceUrl(
        redirectUri: String
    ) {
        try {
            _getSalesForcceConnectUrl.postValue(NetworkResponse.Loading())
            val response = apiService.getSalesForceRedirectUrl(redirectUri)
            if (response.isSuccessful && response.body() != null) {
                _getSalesForcceConnectUrl.postValue(NetworkResponse.Success(response.body()!!))
            } else if (response.errorBody() != null) {
                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                _getSalesForcceConnectUrl.postValue(NetworkResponse.Error(errorObj.getString("message")))
            } else {
                _getSalesForcceConnectUrl.postValue(NetworkResponse.Error("Error went wrong"))
            }
        } catch (e: Exception) {
            _getSalesForcceConnectUrl.postValue(NetworkResponse.Error("Something went wrong"))
        }
    }

    suspend fun salesForceConnect(
        code: String,
        redirectUri: String
    ) {

        try {
            val request = SalesForceConnectRequest(code = code, redirect_uri = redirectUri)
            val response = apiService.salesForceConnect(request);
            if (response.isSuccessful && response.body() != null) {
                val responseCookie = response.headers()["Set-Cookie"]
                if (responseCookie != null) {
                    cookieManager.saveCookie(responseCookie)
                }
                _currentUserLiveData.postValue(NetworkResponse.Success(response.body()!!))
                NavigationService.navigateWithPopUp(
                    Screens.HomeScreen.route,
                    Screens.LoginScreen.route
                )
            } else if (response.errorBody() != null) {
                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                _currentUserLiveData.postValue(NetworkResponse.Error(errorObj.getString("message")))
            } else {
                _currentUserLiveData.postValue(NetworkResponse.Error("Error went wrong"))
            }

        } catch (e: Exception) {
            _currentUserLiveData.postValue(NetworkResponse.Error("Something went wrong"))
        }
    }

    suspend fun getCurrentUser() {
        try {
            Log.i("Authentication inside", "getCurrentUser: ")
            _currentUserLiveData.postValue(NetworkResponse.Loading())
            val response = apiService.getCurrentUser()

            if (response.isSuccessful) {
                _currentUserLiveData.postValue(NetworkResponse.Success(response.body()!!))
                Log.i("Authentication response", "getCurrentUser: ${response.body()}")
            } else if (response.errorBody() != null) {
                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                _currentUserLiveData.postValue(NetworkResponse.Error(errorObj.getString("message")))
                Log.i("Authentication response", "getCurrentUser: ${response.errorBody()}")
            } else {
                _currentUserLiveData.postValue(NetworkResponse.Error("Error went wrong"))
                Log.i("Authentication response", "getCurrentUser: ${response.errorBody()}")
            }
        } catch (e: Exception) {
            Log.i("Authentication exception", "getCurrentUser: $e")
            _currentUserLiveData.postValue(NetworkResponse.Error("Something went wrong"))
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