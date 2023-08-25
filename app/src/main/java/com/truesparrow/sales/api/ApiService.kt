package com.truesparrow.sales.api

import com.truesparrow.sales.models.AccountFeedResponse
import com.truesparrow.sales.models.SalesForceConnectRequest
import com.truesparrow.sales.models.RedirectUrl
import com.truesparrow.sales.models.SaveNote
import com.truesparrow.sales.models.CurrentUserResponse
import com.truesparrow.sales.models.AccountListResponse
import com.truesparrow.sales.models.AccountNotesResponse
import com.truesparrow.sales.models.AccountTasksResponse
import com.truesparrow.sales.models.CrmOrganisationUsersResponse
import com.truesparrow.sales.models.GetCrmActionRequest
import com.truesparrow.sales.models.GetCrmActionsResponse
import com.truesparrow.sales.models.NotesDetailResponse
import com.truesparrow.sales.models.SaveNoteRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("./v1/accounts")
    @Headers("$MOCK_RESPONSE_HEADER: AccountListResponse.json")
    suspend fun getAccounts(@Query("q") query: String): Response<AccountListResponse>

    @GET("./v1/auth/salesforce/redirect-url")
    @Headers("$MOCK_RESPONSE_HEADER: RedirectUri.json")
    suspend fun getSalesForceRedirectUrl(@Query("redirect_uri") redirectUri: String): Response<RedirectUrl>

    @POST("./v1/auth/salesforce/connect")
    @Headers("Content-Type: application/json")
    suspend fun salesForceConnect(
        @Body request: SalesForceConnectRequest
    ): Response<CurrentUserResponse>

    @GET("./v1/users/current")
    @Headers("$MOCK_RESPONSE_HEADER: CurrentUserResponse.json")
    suspend fun getCurrentUser(): Response<CurrentUserResponse>

    @POST("v1/accounts/{account_id}/notes")
    @Headers("$MOCK_RESPONSE_HEADER: SaveNoteResponse.json")
    suspend fun saveNote(
        @Path(value = "account_id", encoded = true) accountId: String,
        @Body request: SaveNoteRequest
    ): Response<SaveNote>

    @GET("v1/accounts/{account_id}/notes")
    @Headers("$MOCK_RESPONSE_HEADER: NotesResponse.json")
    suspend fun getAccountNotes(
        @Path(value = "account_id", encoded = true) accountId: String
    ): Response<AccountNotesResponse>

    @GET("v1/accounts/{account_id}/notes/{note_id}")
    @Headers("$MOCK_RESPONSE_HEADER: NoteDetailsResponse.json")
    suspend fun getNoteDetails(
        @Path(value = "account_id") accountId: String,
        @Path(value = "note_id") noteId: String
    ): Response<NotesDetailResponse>


    @Headers("$MOCK_RESPONSE_HEADER: LogoutResponse.json")
    @POST("./v1/auth/logout")
    suspend fun logout(): Response<Unit>

    @POST("./v1/auth/disconnect")
    suspend fun disconnectSalesForce(): Response<Unit>


    @GET("./v1/crm-organization-users")
    @Headers("$MOCK_RESPONSE_HEADER: CrmOrganisationUsersResponse.json")
    suspend fun getCrmOrganisationUser(@Query("q") query: String): Response<CrmOrganisationUsersResponse>

    @POST("./v1/suggestions/crm-actions")
    @Headers("$MOCK_RESPONSE_HEADER: GetCrmActionsResponse.json")
    suspend fun getCrmActions(
        @Body request: GetCrmActionRequest
    ): Response<GetCrmActionsResponse>

    @GET("v1/accounts/{account_id}/tasks")
    @Headers("$MOCK_RESPONSE_HEADER: AccountTaskResponse.json")
    suspend fun getAccountTasks(
        @Path(value = "account_id", encoded = true) accountId: String
    ): Response<AccountTasksResponse>

    @GET("./v1/accounts/feed")
    @Headers("$MOCK_RESPONSE_HEADER: AccountFeedResponse.json")
    suspend fun getAccountFeed(): Response<AccountFeedResponse>

}