package com.example.salessparrow.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.salessparrow.BuildConfig
import com.example.salessparrow.dao.AuthorizationDao
import com.example.salessparrow.entity.AuthorizationEntity
import com.example.salessparrow.services.NavigationService
import com.example.salessparrow.util.Screens
import java.util.concurrent.Executors
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(private val authorizationDao: AuthorizationDao) {

    fun isLoggedIn(): Boolean {
        val authCode = getAuthorizationCode()
        return !authCode.isNullOrEmpty()
    }

    fun connectWithSalesForce(): String {
        val salesForceConnectURl = BuildConfig.SALESFORCE_LOGIN_URL
        Log.i("SalesForceConnectURl", salesForceConnectURl)
        return salesForceConnectURl
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