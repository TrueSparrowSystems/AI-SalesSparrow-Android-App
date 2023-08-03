package com.example.salessparrow.di

import android.content.Context
import com.example.salessparrow.repository.AccountListRepository
import com.example.salessparrow.repository.AuthenticationRepository
import com.example.salessparrow.services.CatApiService
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
    fun provideAuthenticationRepository (): AuthenticationRepository {
        return AuthenticationRepository()
    }

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        val baseUrl = "https://api.thecatapi.com/"

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
    @Singleton
    @Provides
    fun provideAccountListApis(retrofit : Retrofit ) : CatApiService {
        return retrofit.create(CatApiService::class.java)
    }


    @Singleton
    @Provides
    fun provideAccountListRepository(): AccountListRepository {
        return AccountListRepository()
    }

}