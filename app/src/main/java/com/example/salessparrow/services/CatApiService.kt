package com.example.salessparrow.services


import com.example.salessparrow.data.CatImage
import retrofit2.Call
import retrofit2.http.GET

// Define a service interface for the Cat API
interface CatApiService {
    @GET("v1/images/0XYvRd7oD")
    suspend fun getCatImages(): Call<List<CatImage>>
}