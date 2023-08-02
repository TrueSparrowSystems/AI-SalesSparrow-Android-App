package com.example.salessparrow.repository

import android.content.Context
import android.util.Log
import com.example.salessparrow.BuildConfig
import com.example.salessparrow.api.AuthenticationApi
import com.example.salessparrow.services.ApiService

class AuthenticationRepository() {

    fun isLoggedIn(): Boolean {
        return false
    }

    fun connectWithSalesForce(): String {
//        apiService.createServiceClass(AuthenticationApi::class.java).getSalesForceConnectUrl();
        val salesForceConnectURl = BuildConfig.SALESFORCE_LOGIN_URL;
        Log.i("SalesForceConnectURl", salesForceConnectURl)
        return salesForceConnectURl;
    }

}