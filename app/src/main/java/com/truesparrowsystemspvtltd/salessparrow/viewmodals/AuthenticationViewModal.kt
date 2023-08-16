package com.truesparrowsystemspvtltd.salessparrow.viewmodals

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truesparrowsystemspvtltd.salessparrow.BuildConfig
import com.truesparrowsystemspvtltd.salessparrow.models.CurrentUserResponse
import com.truesparrowsystemspvtltd.salessparrow.models.RedirectUrl
import com.truesparrowsystemspvtltd.salessparrow.repository.AuthenticationRepository
import com.truesparrowsystemspvtltd.salessparrow.services.NavigationService
import com.truesparrowsystemspvtltd.salessparrow.util.NetworkResponse
import com.truesparrowsystemspvtltd.salessparrow.util.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthenticationViewModal @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) :
    ViewModel() {

    val currentUserLiveData: LiveData<NetworkResponse<CurrentUserResponse>>
        get() = authenticationRepository.currentUserLiveData

    val getSalesForcceConnectUrl: LiveData<NetworkResponse<RedirectUrl>>
        get() = authenticationRepository.getSalesForcceConnectUrl


    init {
        viewModelScope.launch {
            authenticationRepository.getCurrentUser()
        }
    }

    fun salesForceConnect(code: String, redirectUri: String) {
        viewModelScope.launch {
           authenticationRepository.salesForceConnect(code, redirectUri)
        }
    }

    fun handleDeepLink(intent: Intent) {
        Log.i("SalesSparow", "handleDeepLink authCode: $intent")
        val uri = intent.data
        if (uri != null) {
            val code = uri.getQueryParameter("code")
            Log.i("SalesSparow", "handleDeepLink authCode: $code")
            val redirectUri = BuildConfig.REDIRECT_URI
            if (code != null && redirectUri != null) {
                salesForceConnect(code, redirectUri)
                intent.action = ""
                intent.data = null
                intent.flags = 0

            }
        }

    }

    fun getConnectWithSalesForceUrl(redirectUri: String) {
        viewModelScope.launch {
            authenticationRepository.getConnectWithSalesForceUrl(redirectUri)
        }

    }

    fun logout() {
        viewModelScope.launch {
            authenticationRepository.logout()
            NavigationService.navigateWithPopUpClearingAllStack(Screens.LoginScreen.route)
        }
    }

    fun disconnectSalesForce() {
        viewModelScope.launch {
            authenticationRepository.disconnectSalesForce()
            NavigationService.navigateTo(Screens.LoginScreen.route);
        }
    }
}