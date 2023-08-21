package com.truesparrow.sales.screens

import android.os.Build.VERSION.SDK_INT
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.truesparrow.sales.R
import com.truesparrow.sales.services.NavigationService
import com.truesparrow.sales.ui.theme.nero
import com.truesparrow.sales.util.NetworkResponse
import com.truesparrow.sales.util.Screens
import com.truesparrow.sales.viewmodals.AuthenticationViewModal
import kotlinx.coroutines.delay

@Composable
fun SplashScreen() {

    val authenticationViewModal: AuthenticationViewModal = hiltViewModel()

    val currentUserResponse by authenticationViewModal.currentUserLiveData.observeAsState();


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
        delay(2000);
        currentUserResponse?.let {
           when(it){
                is NetworkResponse.Success -> {
                     Log.i("SalesSparow", "SplashScreen: ${it.data!!.current_user}")
                     NavigationService.navigateWithPopUp(Screens.HomeScreen.route, Screens.SplashScreen.route)
                }
                is NetworkResponse.Error -> {
                     NavigationService.navigateWithPopUp(Screens.LoginScreen.route, Screens.SplashScreen.route)
                }
                is NetworkResponse.Loading -> {

                }
           }

        }


    }
}