package com.truesparrow.sales.api

import com.truesparrow.sales.BuildConfig
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import javax.inject.Inject

const val MOCK_RESPONSE_HEADER = "MOCK_RESPONSE"

const val SUCCESS_CODE = 200

class ResponseInterceptor @Inject constructor(private val assetReader: JsonReader) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return if (BuildConfig.IS_MOCK.toBoolean()) {
            handleMockResponse(chain)
        } else {
            var request = chain.request()
            val response = chain.proceed(request)
            if (response.code == 204) {
                response.newBuilder().removeHeader(MOCK_RESPONSE_HEADER).code(200).build()
            } else {
                response
                    .newBuilder()
                    .removeHeader(MOCK_RESPONSE_HEADER)
                    .build()

            }

        }
    }

    private fun handleMockResponse(chain: Interceptor.Chain): Response {
        val headers = chain.request().headers
        val responseString = assetReader.getJsonAsString(headers[MOCK_RESPONSE_HEADER])

        return chain.proceed(chain.request())
            .newBuilder()
            .code(SUCCESS_CODE)
            .protocol(Protocol.HTTP_2)
            .message("OK")
            .body(
                responseString.toByteArray()
                    .toResponseBody("application/json".toMediaTypeOrNull())
            )
            .addHeader("content-type", "application/json")
            .build()

    }
}