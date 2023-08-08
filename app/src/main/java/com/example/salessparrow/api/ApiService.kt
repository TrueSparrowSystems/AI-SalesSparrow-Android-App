package com.example.salessparrow.api

import android.net.Uri
import com.example.salessparrow.data.CatImage
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("v1/images/0XYvRd7oD")
    suspend fun getCatImages(): Response<CatImage>

    @GET("/login-url")
    suspend fun getSalesForceConnectUrl(): Response<String>
}