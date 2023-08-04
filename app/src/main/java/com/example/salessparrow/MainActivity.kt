package com.example.salessparrow

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.salessparrow.repository.AuthenticationRepository
import com.example.salessparrow.services.NavigationService
import com.example.salessparrow.services.handleDeepLink
import com.example.salessparrow.ui.theme.SalesSparrowTheme
import com.example.salessparrow.viewmodals.AuthenticationViewModal
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SalesSparrowTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationService()
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        setContent {
            NavigationService();
            handleDeepLink(intent = intent);
        }
    }
}
