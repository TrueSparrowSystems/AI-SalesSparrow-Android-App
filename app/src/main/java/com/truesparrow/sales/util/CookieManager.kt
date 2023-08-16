package com.truesparrow.sales.util

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CookieManager @Inject constructor(@ApplicationContext context: Context) {
    private val sharedPreferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    fun saveCookie(cookie: String) {
        editor.putString("cookie", cookie)
        editor.apply()
    }

    fun getCookie(): String? {
        return sharedPreferences.getString("cookie", null)
    }

    fun clearCookie() {
        editor.clear()
        editor.apply()
    }

}