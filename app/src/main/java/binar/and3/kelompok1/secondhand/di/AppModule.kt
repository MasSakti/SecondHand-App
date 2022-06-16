package binar.and3.kelompok1.secondhand.di

import binar.and3.kelompok1.secondhand.data.api.auth.AuthAPI
import binar.and3.kelompok1.secondhand.data.local.UserDAO
import binar.and3.kelompok1.secondhand.datastore.AuthDataStoreManager
import binar.and3.kelompok1.secondhand.repository.AuthRepository
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
        authDataStoreManager: AuthDataStoreManager,
        api: AuthAPI,
        dao: UserDAO
    ): AuthRepository {
        return AuthRepository(
            authDataStore = authDataStoreManager,
            api = api,
            dao = dao
        )
    }

}