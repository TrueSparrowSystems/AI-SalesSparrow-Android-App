package com.example.salessparrow.viewmodals

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salessparrow.repository.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModal @Inject constructor(private val authenticationRepository: AuthenticationRepository) :
    ViewModel() {

    constructor() : this(AuthenticationRepository())

    fun isUserLoggedIn() = authenticationRepository.isLoggedIn()

    fun connectWithSalesForce(context: Context) {
        val url = authenticationRepository.connectWithSalesForce()
        Log.i("SalesForceConnectURl", url)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(intent);
    }
}