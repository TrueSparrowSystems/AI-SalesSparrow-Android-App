package com.example.salessparrow.di

import android.content.Context
import android.util.Log
import com.example.salessparrow.BuildConfig
import com.example.salessparrow.api.ApiService
import com.example.salessparrow.api.JsonReader
import com.example.salessparrow.api.RequestInterceptor
import com.example.salessparrow.api.ResponseInterceptor
import com.example.salessparrow.database.AppDatabase
import com.example.salessparrow.util.CookieManager
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModule {


    @Singleton
    @Provides
    fun provideFakeResponseInterceptor(jsonReader: JsonReader): ResponseInterceptor {
        return ResponseInterceptor(jsonReader)
    }

    @Singleton
    @Provides
    fun provideCookieManager(@ApplicationContext context: Context): CookieManager {
        return CookieManager(context)
    }


    @Singleton
    @Provides
    fun providesRetrofit(
        responseInterceptor: ResponseInterceptor,
        requestInterceptor: RequestInterceptor
    ): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient =
            OkHttpClient.Builder().addInterceptor(logging).addInterceptor(responseInterceptor)
                .addInterceptor(requestInterceptor).build()
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val baseUrl = BuildConfig.BASE_URL;
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }


    @Singleton
    @Provides
    fun provideJsonReader(@ApplicationContext context: Context): JsonReader {
        return JsonReader(context.assets)
    }

}