package com.truesparrow.sales.api

import com.truesparrow.sales.models.AccountEventsResponse
import com.truesparrow.sales.models.AccountFeedResponse
import com.truesparrow.sales.models.SalesForceConnectRequest
import com.truesparrow.sales.models.RedirectUrl
import com.truesparrow.sales.models.SaveNote
import com.truesparrow.sales.models.CurrentUserResponse
import com.truesparrow.sales.models.AccountListResponse
import com.truesparrow.sales.models.AccountNotesResponse
import com.truesparrow.sales.models.AccountTasksResponse
import com.truesparrow.sales.models.CreateAccountEventResponse
import com.truesparrow.sales.models.CreateAccountTaskRequest
import com.truesparrow.sales.models.CreateAccountTaskResponse
import com.truesparrow.sales.models.CrmOrganisationUsersResponse
import com.truesparrow.sales.models.EventDetailsResponse
import com.truesparrow.sales.models.GetCrmActionRequest
import com.truesparrow.sales.models.GetCrmActionsResponse
import com.truesparrow.sales.models.NotesDetailResponse
import com.truesparrow.sales.models.SaveNoteRequest
import com.truesparrow.sales.models.TaskDetailsResponse
import com.truesparrow.sales.models.createAccountEventRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
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

    @DELETE("v1/accounts/{account_id}/notes/{note_id}")
    @Headers("$MOCK_RESPONSE_HEADER: DeleteNoteResponse.json")
    suspend fun deleteNote(
        @Path(value = "account_id") accountId: String,
        @Path(value = "note_id") noteId: String
    ): Response<Unit>

    @PUT("v1/accounts/{account_id}/notes/{note_id}")
    @Headers("$MOCK_RESPONSE_HEADER: SaveNoteResponse.json")
    suspend fun updateNote(
        @Path(value = "account_id") accountId: String,
        @Path(value = "note_id") noteId: String,
        @Body request: SaveNoteRequest
    ): Response<Unit>

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

    @DELETE("v1/accounts/{account_id}/tasks/{task_id}")
    @Headers("$MOCK_RESPONSE_HEADER: DeleteAccountTaskResponse.json")
    suspend fun deleteTask(
        @Path(value = "account_id") accountId: String,
        @Path(value = "task_id") noteId: String
    ): Response<Unit>


    @POST("v1/accounts/{account_id}/tasks")
    @Headers("$MOCK_RESPONSE_HEADER: CreateAccountTaskResponse.json")
    suspend fun createAccountTasks(
        @Path(value = "account_id", encoded = true) accountId: String,
        @Body request: CreateAccountTaskRequest
    ): Response<CreateAccountTaskResponse>

    @PUT("v1/accounts/{account_id}/tasks/{task_id}")
    @Headers("$MOCK_RESPONSE_HEADER: CreateAccountTaskResponse.json")
    suspend fun updateTask(
        @Path(value = "account_id") accountId: String,
        @Path(value = "task_id") taskId: String,
        @Body request: CreateAccountTaskRequest
    ): Response<Unit>

    @GET("v1/accounts/{account_id}/tasks/{task_id}")
    @Headers("$MOCK_RESPONSE_HEADER: TaskDetailsResponse.json")
    suspend fun getTaskDetails(
        @Path(value = "account_id") accountId: String,
        @Path(value = "task_id") noteId: String
    ): Response<TaskDetailsResponse>

    @GET("./v1/accounts/feed")
    @Headers("$MOCK_RESPONSE_HEADER: AccountFeedResponse.json")
    suspend fun getAccountFeed(
    ): Response<AccountFeedResponse>

    @GET("./v1/accounts/feed")
    @Headers("$MOCK_RESPONSE_HEADER: AccountFeedResponse.json")
    suspend fun getAccountFeedWithPagination(
        @Query("pagination_identifier") paginationIdentifier: String
    ): Response<AccountFeedResponse>

    @GET("v1/accounts/{account_id}/events")
    @Headers("$MOCK_RESPONSE_HEADER: AccountsEventResponse.json")
    suspend fun getAccountEvents(
        @Path(value = "account_id", encoded = true) accountId: String
    ): Response<AccountEventsResponse>

    @POST("v1/accounts/{account_id}/events")
    @Headers("$MOCK_RESPONSE_HEADER: CreateAccountTaskResponse.json")
    suspend fun createAccountEvents(
        @Path(value = "account_id", encoded = true) accountId: String,
        @Body request: createAccountEventRequest
    ): Response<CreateAccountEventResponse>

    @DELETE("v1/accounts/{account_id}/events/{event_id}")
    @Headers("$MOCK_RESPONSE_HEADER: DeleteAccountTaskResponse.json")
    suspend fun deleteEvent(
        @Path(value = "account_id") accountId: String,
        @Path(value = "event_id") eventId: String
    ): Response<Unit>

    @PUT("v1/accounts/{account_id}/events/{event_id}")
    @Headers("$MOCK_RESPONSE_HEADER: CreateAccountTaskResponse.json")
    suspend fun updateEvent(
        @Path(value = "account_id") accountId: String,
        @Path(value = "event_id") eventId: String,
        @Body request: createAccountEventRequest
    ): Response<Unit>

    @GET("v1/accounts/{account_id}/events/{event_id}")
    @Headers("$MOCK_RESPONSE_HEADER: EventDetailsResponse.json")
    suspend fun getEventDetails(
        @Path(value = "account_id") accountId: String,
        @Path(value = "event_id") noteId: String
    ): Response<EventDetailsResponse>
}