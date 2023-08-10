package com.example.salessparrow.viewmodals

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salessparrow.models.CurrentUser
import com.example.salessparrow.models.RedirectUrl
import com.example.salessparrow.repository.AuthenticationRepository
import com.example.salessparrow.services.NavigationService
import com.example.salessparrow.util.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModal @Inject constructor(private val authenticationRepository: AuthenticationRepository) :
    ViewModel() {
    open val currentUser = mutableStateOf(CurrentUser(email = "", id = null, name = ""))

    fun checkUserLoggedIn() {
        viewModelScope.launch {
            val isUserLoggedIn = authenticationRepository.isLoggedIn()
            if (isUserLoggedIn) {
                NavigationService.navigateWithPopUp(
                    Screens.HomeScreen.route, Screens.SplashScreen.route
                );
            } else {
                NavigationService.navigateWithPopUp(
                    Screens.LoginScreen.route, Screens.SplashScreen.route
                );

            }
        }
    }

    fun salesForceConnect(code: String, redirectUri: String): CurrentUser {
        viewModelScope.launch {
            currentUser.value = authenticationRepository.salesForceConnect(code, redirectUri)!!
            println("currentUser: ${currentUser.value}")
            if (currentUser.value.id.toString().isNotEmpty()) {
                NavigationService.navigateWithPopUp(
                    Screens.HomeScreen.route, Screens.LoginScreen.route
                );
            }
        }
        return currentUser.value;
    }

    fun getCurrentUser(): CurrentUser {
        return currentUser.value;
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
            NavigationService.navigateTo(Screens.LoginScreen.route);
        }
    }

    fun disconnectSalesForce() {
        viewModelScope.launch {
            authenticationRepository.disconnectSalesForce()
            NavigationService.navigateTo(Screens.LoginScreen.route);
        }
    }
}