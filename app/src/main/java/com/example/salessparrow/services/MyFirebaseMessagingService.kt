package com.example.salessparrow.services

import android.util.Log
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.i("TAG", "Refreshed token: $token")
        super.onNewToken(token)
        GlobalScope.launch {
            saveGCMToken(token)
        }
    }

    private fun saveGCMToken(token: String) {
        val gcmToken = stringPreferencesKey("gcm_token");
        GlobalScope.launch {
            Log.i("TAG", "Refreshed token: $token")
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.i("CloudMessage", "From: ${remoteMessage.from}")

        //Log Data Payload
        if (remoteMessage.data.isNotEmpty()) {
            Log.i("CloudMessage", "Message data payload: ${remoteMessage.data}")
        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.i("CloudMessage", "Message Notification Body: ${it.body}")
        }

        // Check if notification has body and title
        remoteMessage.notification?.let {
            Log.i("CloudMessage", "Message Notification Body: ${it.body}")
            Log.i("CloudMessage", "Message Notification Title: ${it.title}")
        }

        //TODO: Handle notification

    }

    companion object {
        fun getInstance(): Any {
            return MyFirebaseMessagingService();
        }
    }

}