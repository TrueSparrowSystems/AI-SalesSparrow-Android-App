package com.example.salessparrow.repository

import android.util.Log
import com.example.salessparrow.data.CatImage
import com.example.salessparrow.api.ApiService
import javax.inject.Inject

class AccountListRepository @Inject constructor(private val apiService: ApiService) {


    suspend fun getCatImages(callback: (List<CatImage>?) -> Unit, errorCallback: (String) -> Unit) {
        try {
            val catImages = apiService.getCatImages()
            Log.i("MyApp", "Response: $catImages")
            callback(catImages)
        } catch (e: Exception) {
            errorCallback("Error fetching cat images: ${e.message}")
        }
    }
}
