package com.example.salessparrow.screens

import android.os.Build.VERSION.SDK_INT
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.salessparrow.R
import com.example.salessparrow.services.NavigationService
import com.example.salessparrow.ui.theme.nero
import com.example.salessparrow.util.Screens
import com.example.salessparrow.viewmodals.AuthenticationViewModal
import kotlinx.coroutines.delay
import androidx.compose.runtime.collectAsState

@Composable
fun SplashScreen() {

    val authenticationViewModal: AuthenticationViewModal = hiltViewModel()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(nero)
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


    LaunchedEffect(key1 = true) {
        delay(1000);
        authenticationViewModal.checkUserLoggedIn()

    }
}