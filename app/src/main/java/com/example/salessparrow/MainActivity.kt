package com.example.salessparrow

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.salessparrow.repository.AuthenticationRepository
import com.example.salessparrow.screens.LogInScreen
import com.example.salessparrow.services.NavigationService
import com.example.salessparrow.services.handleDeepLink
import com.example.salessparrow.ui.theme.SalesSparrowTheme
import com.example.salessparrow.viewmodals.AuthenticationViewModal
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val intentState = mutableStateOf<Intent?>(null)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationService(intentState.value)

        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        intentState.value = intent
    }
}

