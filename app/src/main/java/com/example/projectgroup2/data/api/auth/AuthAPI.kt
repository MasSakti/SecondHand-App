package com.example.projectgroup2.data.api.auth

import com.example.projectgroup2.data.api.auth.login.LoginRequest
import com.example.projectgroup2.data.api.auth.login.LoginResponse
import com.example.projectgroup2.data.api.auth.register.RegisterRequest
import com.example.projectgroup2.data.api.auth.register.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST

interface AuthAPI {
    @Multipart
    @POST("auth/register")
    suspend fun postRegister(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>

    @POST("auth/login")
    suspend fun postLogin(
        @Body request: LoginRequest
    ): Response<LoginResponse>
}