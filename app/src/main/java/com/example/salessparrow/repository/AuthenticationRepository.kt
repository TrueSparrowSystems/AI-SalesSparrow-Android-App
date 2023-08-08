package com.example.salessparrow.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.salessparrow.BuildConfig
import com.example.salessparrow.api.ApiService
import com.example.salessparrow.dao.AuthorizationDao
import com.example.salessparrow.entity.AuthorizationEntity
import java.util.concurrent.Executors
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    private val authorizationDao: AuthorizationDao,
    private val apiService: ApiService,
) {

    fun isLoggedIn(): Boolean {
        return true
//        val authCode = getAuthorizationCode()
//        return !authCode.isNullOrEmpty()
    }

    fun connectWithSalesForce(): String {
        val salesForceConnectURl = BuildConfig.SALESFORCE_LOGIN_URL
        Log.i("SalesForceConnectURl", salesForceConnectURl)
        return salesForceConnectURl
    }

    suspend fun getConnectWithSalesForceUrl(
    ): String {
        return try {
            val response = apiService.getSalesForceConnectUrl();
            val loginUrl = response.body();
            if (response.isSuccessful) {
                loginUrl.toString()
            } else {
                "Error fetching logIn url: ${response.errorBody()}"
            }

        } catch (e: Exception) {
            ("Error fetching cat image: ${e.message}")
        }
    }

    fun insertAuthorization(authorizationEntity: AuthorizationEntity) {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute { authorizationDao.insertAuthorization(authorizationEntity) }
    }

    fun getAuthorization(): LiveData<AuthorizationEntity> {
        return authorizationDao.getAuthorization()
    }

    private fun getAuthorizationCode(): String? {
        val authorizationEntity = authorizationDao.getAuthorization().value
        return authorizationEntity?.authCode
    }

}