package com.example.salessparrow.di

import com.example.salessparrow.api.ApiService
import android.content.Context
import com.example.salessparrow.BuildConfig
import com.example.salessparrow.database.AppDatabase
import com.example.salessparrow.repository.AuthenticationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModule {

    @Singleton
    @Provides
    fun provideAuthenticationRepository(appDatabase: AppDatabase): AuthenticationRepository {
        return AuthenticationRepository(appDatabase.authorizationDao())
    }

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        val baseUrl = BuildConfig.BASE_URL;
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()).build()
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
}