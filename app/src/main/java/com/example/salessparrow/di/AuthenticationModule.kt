package com.example.salessparrow.di

import android.content.Context
import android.util.Log
import com.example.salessparrow.BuildConfig
import com.example.salessparrow.api.ApiService
import com.example.salessparrow.api.JsonReader
import com.example.salessparrow.api.ResponseInterceptor
import com.example.salessparrow.database.AppDatabase
import com.example.salessparrow.repository.AuthenticationRepository
import com.example.salessparrow.repository.SearchAccountRepository
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
    fun provideAuthenticationRepository(apiService: ApiService): AuthenticationRepository {
        return AuthenticationRepository(apiService)
    }

    @Singleton
    @Provides
    fun provideFakeResponseInterceptor(jsonReader: JsonReader): ResponseInterceptor {
        return ResponseInterceptor(jsonReader)
    }

    @Singleton
    @Provides
    fun providesRetrofit(interceptor: ResponseInterceptor): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient =
            OkHttpClient.Builder().addInterceptor(logging).addInterceptor(interceptor).build()
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val baseUrl = BuildConfig.BASE_URL;
        Log.i("okhttp.OkHttpClient", "Response: $baseUrl")
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

    @Provides
    fun provideSearchAccountRepository(): SearchAccountRepository {
        return SearchAccountRepository()
    }

    @Singleton
    @Provides
    fun provideJsonReader(@ApplicationContext context: Context): JsonReader {
        return JsonReader(context.assets)
    }

}