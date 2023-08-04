package com.example.salessparrow.services

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.salessparrow.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class ApiService(private val context: Context) {
    private val retrofit: Retrofit;

    init {
        //In Gson, the lenient mode allows parsing of malformed or unexpected JSON.
        val gson: Gson = GsonBuilder().setLenient().create();

        val interceptor = Interceptor { chain ->
            val request = chain.request();
            val newRequestBuilder = request.newBuilder()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
            if (isInternetConnected()) {
                val response = chain.proceed(newRequestBuilder.build())

                if (response.code == 401) {
                    //handle unauthorized error
                }
                response
            } else {
                throw NoInternetException()
            }
        }

        val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build();

        val baseUrl = BuildConfig.BASE_URL;

        retrofit = Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()

    }

    fun <T> createServiceClass(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }

    private fun isInternetConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false;
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        }
    }

    class NoInternetException : IOException("No Internet connection");

}