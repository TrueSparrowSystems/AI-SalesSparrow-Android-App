package com.example.salessparrow.api

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("api/v1/accounts")
    suspend fun getAccounts(): Response<Record>
}