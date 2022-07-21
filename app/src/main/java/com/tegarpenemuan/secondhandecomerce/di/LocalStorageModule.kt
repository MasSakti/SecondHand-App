package com.tegarpenemuan.secondhandecomerce.di

import android.content.Context
import com.tegarpenemuan.secondhandecomerce.datastore.AuthDatastoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalStorageModule {

    @Singleton
    @Provides
    fun provideAuthDataStoreManager(@ApplicationContext context: Context)
            : AuthDatastoreManager {
        return AuthDatastoreManager(context = context)
    }
}