package binar.and3.kelompok1.secondhand.di

import android.content.Context
import binar.and3.kelompok1.secondhand.data.local.auth.UserDAO
import binar.and3.kelompok1.secondhand.data.local.buyer.BuyerDAO
import binar.and3.kelompok1.secondhand.database.LocalDatabase
import binar.and3.kelompok1.secondhand.datastore.AuthDataStoreManager
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
    fun provideDatabase(@ApplicationContext context: Context): LocalDatabase {
        return LocalDatabase.getInstance(context = context)
    }

    @Singleton
    @Provides
    fun provideUserDao(db: LocalDatabase): UserDAO {
        return db.userDAO()
    }

    @Singleton
    @Provides
    fun provideBuyerProduct(db: LocalDatabase): BuyerDAO {
        return db.buyerDAO()
    }


    @Singleton
    @Provides
    fun provideAuthDataStoreManager(@ApplicationContext context: Context)
            : AuthDataStoreManager {
        return AuthDataStoreManager(context = context)
    }
}