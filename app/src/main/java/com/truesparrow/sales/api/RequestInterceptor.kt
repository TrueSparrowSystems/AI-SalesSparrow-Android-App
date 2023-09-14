package com.truesparrow.sales.api

import com.truesparrow.sales.util.CookieManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class RequestInterceptor @Inject constructor() : Interceptor {
    @Inject
    lateinit var cookieManager: CookieManager

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val cookie = cookieManager.getCookie()
        if (cookie != null) {
            request.addHeader("cookie", cookie)
            request.addHeader("Content-Type", "application/json")
        }
        return chain.proceed(request.build())
    }

}