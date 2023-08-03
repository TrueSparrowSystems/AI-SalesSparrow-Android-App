package com.example.salessparrow.api

import android.content.Context
import android.util.Log
import com.example.salessparrow.data.CatImage
import com.example.salessparrow.services.ApiService
import com.example.salessparrow.services.CatApiService
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

suspend fun getAccountList(context: Context) {
    val apiService = ApiService(context)
    val catApiService = apiService.createServiceClass(CatApiService::class.java)
    Log.i("MyApp", "Making API call - 2")
    val call: Call<List<CatImage>> = catApiService.getCatImages()
    Log.i("MyApp", "Making API call - 3")

    call.enqueue(object : Callback<List<CatImage>> {
        override fun onResponse(call: Call<List<CatImage>>, response: Response<List<CatImage>>) {
            if (response.isSuccessful) {
                val catImages = response.body()
                Log.i("MyApp", "Response: $catImages")
                // Process the catImages list here
            } else {
                // Handle error response
            }
        }

        override fun onFailure(call: Call<List<CatImage>>, t: Throwable) {
            // Handle network call failure
            Log.e("MyApp", "API call failed: ${t.message}")
        }
    })
}