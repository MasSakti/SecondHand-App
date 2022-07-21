package com.tegarpenemuan.secondhandecomerce.di

import com.tegarpenemuan.secondhandecomerce.data.api.Api
import com.tegarpenemuan.secondhandecomerce.datastore.AuthDatastoreManager
import com.tegarpenemuan.secondhandecomerce.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideAuthRepository(
        authDataStoreManager: AuthDatastoreManager,
        api: Api
    ): Repository {
        return Repository(
            authDatastore = authDataStoreManager,
            api = api,
        )
    }
}