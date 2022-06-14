package com.tegarpenemuan.secondhandecomerce.repository

import com.tegarpenemuan.secondhandecomerce.data.api.AuthApi
import com.tegarpenemuan.secondhandecomerce.data.api.register.request.SignUpRequest
import com.tegarpenemuan.secondhandecomerce.data.api.register.response.SuccessRegisterResponse
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
    private val api: AuthApi
) {
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
}