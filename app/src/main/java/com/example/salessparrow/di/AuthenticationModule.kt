package com.example.salessparrow.di

import android.content.Context
import com.example.salessparrow.database.AppDatabase
import com.example.salessparrow.repository.AuthenticationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }


}