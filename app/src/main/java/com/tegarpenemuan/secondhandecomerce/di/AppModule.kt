package com.tegarpenemuan.secondhandecomerce.di

import com.tegarpenemuan.secondhandecomerce.data.api.AuthApi
import com.tegarpenemuan.secondhandecomerce.data.local.UserDAO
import com.tegarpenemuan.secondhandecomerce.datastore.AuthDatastoreManager
import com.tegarpenemuan.secondhandecomerce.repository.AuthRepository
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
        api: AuthApi,
        dao: UserDAO
    ): AuthRepository {
        return AuthRepository(
            authDatastore = authDataStoreManager,
            api = api,
            dao = dao
        )
    }
}