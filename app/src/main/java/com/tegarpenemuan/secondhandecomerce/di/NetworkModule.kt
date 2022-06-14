package com.tegarpenemuan.secondhandecomerce.di

import com.tegarpenemuan.secondhandecomerce.data.api.AuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    //baseURL
    @Singleton
    @Provides
    fun provideBaseUrl() = "https://market-final-project.herokuapp.com/"

    //logging
    @Singleton
    @Provides
    fun provideHttpLogging(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        return httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    //Client
    @Singleton
    @Provides
    fun provideOkhttpClient(logging: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    //Retrofit
    @Singleton
    @Provides
    fun provideRetrofit(baseUrl: String, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    //Instance
    @Singleton
    @Provides
    fun provideCovidAPI(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }
}