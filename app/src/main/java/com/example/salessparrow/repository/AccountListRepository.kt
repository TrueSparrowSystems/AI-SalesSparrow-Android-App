package com.example.salessparrow.repository

import android.util.Log
import com.example.salessparrow.data.CatImage
import com.example.salessparrow.api.ApiService
import javax.inject.Inject

class AccountListRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getCatImage(callback: (CatImage?) -> Unit, errorCallback: (String) -> Unit) {
        try {
            Log.i("MyApp", "getCatImage: ");
            val response = apiService.getCatImages() // Call the correct API method
            val catImage = response.body()
            Log.i("MyApp", "Response: $catImage")
            if (response.isSuccessful) {
                callback(catImage)
            } else {
                errorCallback("Error fetching cat image: ${response.errorBody()}")
            }
        } catch (e: Exception) {
            errorCallback("Error fetching cat image: ${e.message}")
        }
    }
}
