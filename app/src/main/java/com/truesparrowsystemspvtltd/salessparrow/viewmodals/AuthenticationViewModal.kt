package com.truesparrowsystemspvtltd.salessparrow.viewmodals

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truesparrowsystemspvtltd.salessparrow.BuildConfig
import com.truesparrowsystemspvtltd.salessparrow.models.CurrentUserResponse
import com.truesparrowsystemspvtltd.salessparrow.models.RedirectUrl
import com.truesparrowsystemspvtltd.salessparrow.repository.AuthenticationRepository
import com.truesparrowsystemspvtltd.salessparrow.services.NavigationService
import com.truesparrowsystemspvtltd.salessparrow.util.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModal @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) :
    ViewModel() {

    val currentUserLiveData: LiveData<CurrentUserResponse?> =
        authenticationRepository.currentUserLiveData


    init {
        viewModelScope.launch {
            authenticationRepository.getCurrentUser() // Call the function to get the latest user data
        }
    }

    fun salesForceConnect(code: String, redirectUri: String) {
        viewModelScope.launch {
            val response = authenticationRepository.salesForceConnect(code, redirectUri)
            Log.i("SalesSparow", "salesForceConnect: $response")
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

            }
        }

    }

    fun getConnectWithSalesForceUrl(redirectUri: String, context: Context): RedirectUrl {
        val redirectUrl = mutableStateOf(RedirectUrl(url = ""))
        Log.i("AuthenticationViewModal", "getConnectWithSalesForceUrl: $redirectUri")
        viewModelScope.launch {
            redirectUrl.value = authenticationRepository.getConnectWithSalesForceUrl(redirectUri)!!
            Log.i(
                "AuthenticationViewModal",
                "getConnectWithSalesForceUrl: ${redirectUrl.value.url}"
            )
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(redirectUrl.value.url));
            context.startActivity(intent);
        }
        Log.i("AuthenticationViewModal", "getConnectWithSalesForceUrl: ${redirectUrl.value}")
        return redirectUrl.value;
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