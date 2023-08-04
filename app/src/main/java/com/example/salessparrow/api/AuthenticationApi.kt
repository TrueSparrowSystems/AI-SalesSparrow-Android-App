package com.example.salessparrow.api

import retrofit2.http.GET
import retrofit2.http.POST

interface AuthenticationApi {

    @GET("/api/v1/auth")
    fun getSalesForceConnectUrl(): String

    @POST("/api/v1/auth")
    fun sendAuthCode(): String
}