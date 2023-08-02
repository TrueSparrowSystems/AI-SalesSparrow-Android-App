package com.example.salessparrow.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.net.Uri
import android.util.Log

class DeepLinkService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null && intent.action == Intent.ACTION_VIEW) {
            val deepLinkUri: Uri? = intent.data
            if (deepLinkUri != null) {
                // Handle the deep link URL here
                Log.d("DeepLinkService", "Deep Link URI: $deepLinkUri")
            }
        }
        stopSelf() // Stop the service once the deep link is handled
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
