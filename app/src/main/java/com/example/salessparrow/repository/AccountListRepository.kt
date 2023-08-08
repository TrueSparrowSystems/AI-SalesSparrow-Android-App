package com.example.salessparrow.repository


import android.util.Log
import com.example.salessparrow.data.CatImage
import com.example.salessparrow.api.ApiService
import javax.inject.Inject
import com.example.salessparrow.BuildConfig
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody

import retrofit2.Response


class AccountListRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getCatImage(callback: (CatImage?) -> Unit, errorCallback: (String) -> Unit) {
        try {

            val responseString = "{\n" + "  \"id\": \"1\",\n" + "  \"url\": \"google.com\"\n"+"}"

            val responseBody = ResponseBody.create(
                "application/json".toMediaTypeOrNull(),
                responseString.toByteArray()
            )

            val response = if (BuildConfig.IS_MOCK.toBoolean()) {
                Log.i("MyApp", "Response: $responseBody")
                Response.success(responseBody);

            } else {
                apiService.getCatImages()
            }

            val responseBodyString = response.body()?.toString() // Convert ResponseBody to String
            Log.i("MyApp stringify", "Response: $responseBodyString $responseString")

            if (response.isSuccessful) {
                val catImage = response.body().toString()
                Log.i("MyApp inside", "Response: $catImage")
                callback(catImage as CatImage)
            } else {
                errorCallback("Error fetching cat image: ${response.errorBody()}")
            }
        } catch (e: Exception) {
            errorCallback("Error fetching cat image: ${e.message}")
        }
    }

}
