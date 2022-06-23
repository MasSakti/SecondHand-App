package com.example.projectgroup2.repository

import com.example.projectgroup2.data.api.auth.AuthAPI
import com.example.projectgroup2.data.api.auth.login.LoginRequest
import com.example.projectgroup2.data.api.auth.login.LoginResponse
import com.example.projectgroup2.data.api.auth.register.RegisterRequest
import com.example.projectgroup2.data.api.auth.register.RegisterResponse
import com.example.projectgroup2.data.local.UserDAO
import com.example.projectgroup2.data.local.UserEntity
import com.example.projectgroup2.datastore.AuthDataStoreManager
import kotlinx.coroutines.flow.firstOrNull
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authDataStore: AuthDataStoreManager,
    private val api: AuthAPI,
    private val dao: UserDAO
    ) {
    suspend fun registerUser(request: RegisterRequest): Response<RegisterResponse>{
        return api.postRegister(request)
    }

    suspend fun loginUser(request: LoginRequest): Response<LoginResponse>{
        return api.postLogin(request)
    }

    suspend fun clearToken() {
        return updateToken("")
    }

    suspend fun updateToken(value: String) {
        authDataStore.setToken(value)
    }

    suspend fun getToken(): String? {
        return authDataStore.getToken().firstOrNull()
    }

    suspend fun insertUser(userEntity: UserEntity): Long {
        return dao.insertUser(userEntity)
    }
}