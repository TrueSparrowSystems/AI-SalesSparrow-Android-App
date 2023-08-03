package com.example.salessparrow.api

import com.example.salessparrow.data.CatImage
import retrofit2.http.GET

interface ApiService {
    @GET("v1/images/0XYvRd7oD")
    suspend fun getCatImages(): List<CatImage>
}