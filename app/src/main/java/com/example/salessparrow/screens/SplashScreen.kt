package com.example.salessparrow.screens

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.salessparrow.R
import com.example.salessparrow.services.NavigationService
import com.example.salessparrow.util.Screens
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

            val imageLoader = ImageLoader.Builder(LocalContext.current).components {
                    if (SDK_INT >= 28) {
                        add(ImageDecoderDecoder.Factory())
                    } else {
                        add(GifDecoder.Factory())
                    }
                }.build()

            Image(
                painter = rememberAsyncImagePainter(R.drawable.splash_screen, imageLoader),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
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