package com.example.salessparrow.repository

import android.content.Context
import android.provider.Settings.Global
import android.util.Log
import com.example.salessparrow.data.CatImage
import com.example.salessparrow.services.ApiService
import com.example.salessparrow.services.CatApiService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class AccountListRepository() {


//    private val catApiService = apiService.createServiceClass(CatApiService::class.java)

    @Inject
    lateinit var catListApi: CatApiService

    fun getCatImages(callback: (List<CatImage>?) -> Unit, errorCallback: (String) -> Unit) {
        Log.i("MyApp", "Making API call - 2")

        GlobalScope.launch {
            val call: Call<List<CatImage>> = catListApi.getCatImages()
            Log.i("MyApp", "Making API call - 3")
            call.enqueue(object : Callback<List<CatImage>> {
                override fun onResponse(
                    call: Call<List<CatImage>>, response: Response<List<CatImage>>
                ) {
                    if (response.isSuccessful) {
                        val catImages = response.body()
                        Log.i("MyApp", "Response: $catImages")
                        callback(catImages)
                    } else {
                        errorCallback("Error fetching cat images: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<List<CatImage>>, t: Throwable) {
                    errorCallback("API call failed: ${t.message}")
                    Log.e("MyApp", "Making API call - Error ${t.message}")
                }
            })
        }



        Log.i("MyApp", "Making API call - last")
    }
}
