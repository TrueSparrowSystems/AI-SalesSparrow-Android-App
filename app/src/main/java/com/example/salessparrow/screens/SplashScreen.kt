package com.example.salessparrow.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.salessparrow.R
import com.example.salessparrow.services.NavigationService
import com.example.salessparrow.ui.theme.Pink80
import com.example.salessparrow.ui.theme.Purple80
import com.example.salessparrow.ui.theme.PurpleGrey80
import com.example.salessparrow.ui.theme.Typography
import com.example.salessparrow.ui.theme.black
import com.example.salessparrow.util.Screens
import com.example.salessparrow.viewmodals.AuthenticationViewModal
import kotlinx.coroutines.delay

@Composable
fun SplashScreen() {
    val isUserLoggedIn = true;
    val gradientBrush = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFF1E1E1E), Color(0xFF1E1E1E)
        )
    )
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = gradientBrush)
        ) {

            Image(
//                TODO : Replace the Gif
                painter = painterResource(id = R.drawable.buildings),
                contentDescription = "Splash screen",
                modifier = Modifier
                    .size(72.dp)
                    .align(Alignment.Center)
            )
        }

    }
    LaunchedEffect(key1 = true) {
        delay(3000);
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