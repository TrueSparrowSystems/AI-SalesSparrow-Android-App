package com.truesparrow.sales.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.truesparrow.sales.api.ApiService
import com.truesparrow.sales.common_components.CustomToast
import com.truesparrow.sales.models.SalesForceConnectRequest
import com.truesparrow.sales.models.RedirectUrl
import com.truesparrow.sales.models.CurrentUserResponse
import com.truesparrow.sales.services.NavigationService
import com.truesparrow.sales.util.CookieManager
import com.truesparrow.sales.util.NetworkResponse
import com.truesparrow.sales.util.Screens
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

    private val _salesForceDisconnect = MutableLiveData<NetworkResponse<Unit>>()
    val salesForceDisconnect: LiveData<NetworkResponse<Unit>>
        get() = _salesForceDisconnect


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

    suspend fun disconnectSalesForce() {
         try {

            val response = apiService.disconnectSalesForce();

             if (response.isSuccessful) {
                 NavigationService.navigateWithPopUpClearingAllStack(Screens.LoginScreen.route)
                 cookieManager.clearCookie();

             } else if (response.errorBody() != null) {
                 val errorObj = JSONObject(response.errorBody()!!.charStream().readText())

             } else {

             }
            cookieManager.clearCookie();
            response.isSuccessful
        } catch (e: Exception) {

        }
    }


}