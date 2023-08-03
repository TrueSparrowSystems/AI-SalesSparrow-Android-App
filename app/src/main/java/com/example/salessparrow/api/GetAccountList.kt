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

fun getAccountList(context: Context) {
    val apiService = ApiService(context)
    val catApiService = apiService.createServiceClass(CatApiService::class.java)
    Log.i("MyApp", "Making API call - 2")
    val call: Call<CatImage> = catApiService.getCatImage()
    Log.i("MyApp", "Making API call - 3")

    call.enqueue(object : Callback<CatImage> {
        override fun onResponse(call: Call<CatImage>, response: Response<CatImage>) {
            if (response.isSuccessful) {
                val catImageData = response.body()
                val gson = Gson()
                val catImageJson = gson.toJson(catImageData)
                Log.i("MyApp", "responseData: $catImageJson")
            } else {
                println("Error: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<CatImage>, t: Throwable) {
            t.printStackTrace()
            Log.i("MyApp", "Making API call - 5")
        }
    })
}