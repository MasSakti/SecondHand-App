package com.example.projectgroup2.repository

import com.example.projectgroup2.data.api.auth.AuthAPI
import com.example.projectgroup2.data.api.auth.login.LoginRequest
import com.example.projectgroup2.data.api.auth.login.LoginResponse
import com.example.projectgroup2.data.api.auth.register.RegisterRequest
import com.example.projectgroup2.data.api.auth.register.RegisterResponse
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(private val api: AuthAPI) {
    suspend fun Register(request: RegisterRequest): Response<RegisterResponse>{
        return api.postRegister(request)
    }

    suspend fun Login(request: LoginRequest): Response<LoginResponse>{
        return api.postLogin(request)
    }
}