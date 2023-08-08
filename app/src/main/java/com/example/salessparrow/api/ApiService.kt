package com.example.salessparrow.api

import com.example.salessparrow.data.CatImage
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("v1/images/0XYvRd7oD")
    suspend fun getCatImages(): Response<CatImage>
    @GET("v1/images/0XYvRd7oD")
    suspend fun getAccounts(): Response<Record>
}