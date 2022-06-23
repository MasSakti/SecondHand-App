package com.tegarpenemuan.secondhandecomerce.repository

import com.tegarpenemuan.secondhandecomerce.data.api.AuthApi
import com.tegarpenemuan.secondhandecomerce.data.api.getProfile.GetProfileResponse
import com.tegarpenemuan.secondhandecomerce.data.api.login.LoginRequest
import com.tegarpenemuan.secondhandecomerce.data.api.login.LoginResponse
import com.tegarpenemuan.secondhandecomerce.data.api.register.request.SignUpRequest
import com.tegarpenemuan.secondhandecomerce.data.api.register.response.SuccessRegisterResponse
import com.tegarpenemuan.secondhandecomerce.data.local.UserDAO
import com.tegarpenemuan.secondhandecomerce.data.local.UserEntity
import com.tegarpenemuan.secondhandecomerce.datastore.AuthDatastoreManager
import kotlinx.coroutines.flow.firstOrNull
import retrofit2.Response
import javax.inject.Inject

/**
 * com.tegarpenemuan.secondhandecomerce.repository
 *
 * Created by Tegar Penemuan on 14/06/2022.
 * https://github.com/tegarpenemuanr3
 *
 */

class AuthRepository  @Inject constructor(
    private val authDatastore: AuthDatastoreManager,
    private val api: AuthApi,
    private val dao: UserDAO
) {
    suspend fun clearToken() {
        updateToken("")
    }

    suspend fun updateToken(value: String) {
        authDatastore.setToken(value)
    }

    suspend fun getToken(): String? {
        return authDatastore.getToken().firstOrNull()
    }

    suspend fun setId(value: String) {
        authDatastore.setID(value)
    }

    suspend fun getId(): String? {
        return authDatastore.getId().firstOrNull()
    }

    suspend fun register(request: SignUpRequest): Response<SuccessRegisterResponse> {
        return api.register(
            full_name = request.full_name,
            email = request.email,
            password = request.password,
            phone_number = request.phone_number,
            address = request.address,
            image = request.image
        )
    }
    suspend fun login(request: LoginRequest): Response<LoginResponse> {
        return api.login(request)
    }

    suspend fun getProfile(access_token: String): Response<GetProfileResponse> {
        return api.getProfile(access_token)
    }

    suspend fun insertUser(userEntity: UserEntity): Long {
        return dao.insertUser(userEntity)
    }
}