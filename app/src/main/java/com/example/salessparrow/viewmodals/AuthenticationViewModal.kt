package com.example.salessparrow.viewmodals

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.salessparrow.entity.AuthorizationEntity
import com.example.salessparrow.repository.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModal @Inject constructor(private val authenticationRepository: AuthenticationRepository) :
    ViewModel() {


    fun isUserLoggedIn() = authenticationRepository.isLoggedIn()

    fun connectWithSalesForce(context: Context) {
        val url = authenticationRepository.connectWithSalesForce()
        Log.i("SalesForceConnectURl", url)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(intent);
    }

    private val authorizationLiveData: LiveData<AuthorizationEntity> =
        authenticationRepository.getAuthorization()

    fun getAuthorizationLiveData(): LiveData<AuthorizationEntity> {
        return authorizationLiveData
    }

    fun saveAuthorization(authCode: String) {
        val authorizationEntity = AuthorizationEntity(authCode)
        authenticationRepository.insertAuthorization(authorizationEntity)
    }
}