package com.example.salessparrow.viewmodals

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salessparrow.entity.AuthorizationEntity
import com.example.salessparrow.repository.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.http.Url
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModal @Inject constructor(private val authenticationRepository: AuthenticationRepository) :
    ViewModel() {


    fun isUserLoggedIn() = authenticationRepository.isLoggedIn()

    fun connectWithSalesForce(context: Context, url: Uri) {
        Log.i("SalesForceConnectURl", url.toString())
        if (url != null) {
            val intent = Intent(Intent.ACTION_VIEW, url);
            context.startActivity(intent);
        }
    }

    fun getConnectWithSalesForceUrl(): String {
        val loginUrl = mutableStateOf("")
        viewModelScope.launch {
            loginUrl.value = authenticationRepository.getConnectWithSalesForceUrl()
        }
        return loginUrl.value;
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