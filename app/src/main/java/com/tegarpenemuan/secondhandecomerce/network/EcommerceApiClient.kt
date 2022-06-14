package com.tegarpenemuan.secondhandecomerce.network

object AuthApiClient {
    private const val BASE_URL = ""

    val instance: AuthApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(AuthApi::class.java)
    }
}