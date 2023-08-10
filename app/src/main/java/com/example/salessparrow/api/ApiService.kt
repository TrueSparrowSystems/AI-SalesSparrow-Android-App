package com.example.salessparrow.api

import com.example.salessparrow.models.CurrentUser
import com.example.salessparrow.models.RedirectUrl
import com.example.salessparrow.models.SaveNote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("api/v1/accounts")
    suspend fun getAccounts(): Response<Record>

    @GET("/v1/auth/salesforce/redirect-url")
    @Headers("$MOCK_RESPONSE_HEADER: RedirectUri.json")
    suspend fun getSalesForceRedirectUrl(@Query("redirect_uri") redirectUri: String): Response<RedirectUrl>

    @POST("/v1/auth/salesforce/connect")
    @Headers("$MOCK_RESPONSE_HEADER: salesForceConnectResponse.json")
    suspend fun salesForceConnect(
        @Query("code") code: String,
        @Query("redirect_uri") redirectUri: String
    ): Response<CurrentUser>

    @GET("/v1/users/current")
    @Headers("$MOCK_RESPONSE_HEADER: CurrentUserResponse.json")
    suspend fun getCurrentUser(): Response<CurrentUser>

    @GET("/v1/accounts/{account_id}/note")
    @Headers("$MOCK_RESPONSE_HEADER: SaveNoteResponse.json")
    suspend fun saveNote(
        @Query("text") text: String,
    ): Response<SaveNote>

    @POST("/v1/auth/logout")
    suspend fun logout(): Response<Unit>

    @POST("/v1/auth/disconnect")
    suspend fun disconnectSalesForce(): Response<Unit>


}