package com.example.salessparrow.viewmodals

import androidx.lifecycle.ViewModel
import com.example.salessparrow.repository.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModal @Inject constructor(private val authenticationRepository: AuthenticationRepository) :
    ViewModel() {
    fun isUserLoggedIn() = authenticationRepository.isLoggedIn()
}