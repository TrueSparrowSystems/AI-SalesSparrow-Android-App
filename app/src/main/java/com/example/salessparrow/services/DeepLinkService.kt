package com.example.salessparrow.services

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.salessparrow.util.Screens
import com.example.salessparrow.viewmodals.AuthenticationViewModal


@Composable
fun handleDeepLink(intent: Intent?) {
    if (intent != null && intent.action == Intent.ACTION_VIEW) {
        val deepLinkUri: Uri? = intent.data
        if (deepLinkUri != null) {
            if (deepLinkUri.getQueryParameter("code") != null) {
                val authCode = deepLinkUri.getQueryParameter("code")
                val authenticationViewModal: AuthenticationViewModal = viewModel();
                Log.i("SalesSparow", "handleDeepLink authCode: $authCode")
                val redirectUri = "salessparrow://callback"
                authenticationViewModal.salesForceConnect(
                    authCode!!,
                    redirectUri
                )

            }
        }
    }
}







