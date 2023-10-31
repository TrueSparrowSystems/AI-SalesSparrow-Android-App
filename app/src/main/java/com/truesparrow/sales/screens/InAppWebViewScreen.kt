package com.truesparrow.sales.screens

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun InAppWebViewScreen(url: Any) {
    val context = LocalContext.current

    var isLoading by remember { mutableStateOf(true) }

    // This LaunchedEffect listens to changes in the isLoading value.
    LaunchedEffect(isLoading) {
        // Wait for the WebView to finish loading.
        // When loading is done, set isLoading to false.
        if (isLoading) {
            val job = launch {
                // Wait for the WebView to finish loading (your logic here).
                // For demonstration, let's delay for a few seconds.
                delay(2000) // You can replace this with logic that waits for WebView to load.
                isLoading = false
            }
            job.join()
        }
    }

    AndroidView(factory = {
        WebView(context).apply {

            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    url: String?
                ): Boolean {
                    Log.i("SalesSparow", "shouldOverrideUrlLoading: $url")
                    if(url.toString().contains("salessparrow") || url.toString().contains("salessparrowdev")){
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                        return true
                    } else {
                        return false
                    }
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    Log.e("SalesSparow", "onReceivedError: $error")
                }

                override fun onPageStarted(
                    view: WebView?,
                    url: String?,
                    favicon: Bitmap?
                ) {
                    super.onPageStarted(view, url, favicon)
                    Log.i("SalesSparow", "onPageStarted: $url")
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    Log.i("SalesSparow", "onPageFinished: $url")

                    // Set isLoading to false when the page has finished loading.
                    isLoading = false
                }

                override fun onLoadResource(view: WebView?, url: String?) {
                    super.onLoadResource(view, url)
                    Log.i("SalesSparow", "onLoadResource: $url")
                }
            }

            webChromeClient = object : WebChromeClient() {
                // Implement methods as needed for handling web-related events
            }

            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.allowFileAccess = true
            settings.allowContentAccess = true
            settings.databaseEnabled = true
            settings.setGeolocationEnabled(true)
            settings.setSupportZoom(true)
            settings.builtInZoomControls = true
            settings.displayZoomControls = false
            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.mediaPlaybackRequiresUserGesture = false
            settings.setSupportMultipleWindows(true)
            settings.userAgentString = settings.userAgentString + " " + "SalesSparrow"
            settings.cacheMode = android.webkit.WebSettings.LOAD_DEFAULT

            loadUrl(url.toString())
        }
    })

    // Show the loader while isLoading is true.
    if (isLoading) {
        Loader() // Create a Loader Composable.
    }
}


