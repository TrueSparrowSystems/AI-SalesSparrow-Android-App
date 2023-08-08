package com.example.salessparrow.repository

import android.util.Log
import com.example.salessparrow.api.ApiService
import com.example.salessparrow.data.DataWrapper
import com.example.salessparrow.data.sampleApiResponse
import javax.inject.Inject

class SearchAccountRepository @Inject constructor() {
    suspend fun getAccountRecords(
        callback: (DataWrapper?) -> Unit, errorCallback: (String) -> Unit
    ) {
        try {
            val response = sampleApiResponse()
            val dataWrapper = response
            Log.i("MyApp", "Response: $dataWrapper")
            if (true) {
                callback(dataWrapper)
            } else {
                errorCallback("Error fetching records: Error}")
            }
        } catch (e: Exception) {
            errorCallback("Error fetching records: ${e.message}")
        }
    }
}
