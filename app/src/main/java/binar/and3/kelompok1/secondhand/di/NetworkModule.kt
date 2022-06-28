package binar.and3.kelompok1.secondhand.di

import binar.and3.kelompok1.secondhand.Constant
import binar.and3.kelompok1.secondhand.data.api.auth.AuthAPI
import binar.and3.kelompok1.secondhand.data.api.buyer.BuyerAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    @Named(Constant.Named.BASE_URL_SECONDHAND)
    fun provideBaseUrlSecondHand(): String = "https://market-final-project.herokuapp.com/"

    @Singleton
    @Provides
    fun provideHttpLogging(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        return httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(logging: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Singleton
    @Provides
    @Named(Constant.Named.RETROFIT_SECONDHAND)
    fun provideRetrofitSecondHand(
        @Named(Constant.Named.BASE_URL_SECONDHAND) baseUrl: String,
        client: OkHttpClient
    ) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthAPI(
        @Named(Constant.Named.RETROFIT_SECONDHAND) retrofit: Retrofit
    ): AuthAPI {
        return retrofit.create(AuthAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideProductAPI(
        @Named(Constant.Named.RETROFIT_SECONDHAND) retrofit: Retrofit
    ): BuyerAPI {
        return  retrofit.create(BuyerAPI::class.java)
    }
}