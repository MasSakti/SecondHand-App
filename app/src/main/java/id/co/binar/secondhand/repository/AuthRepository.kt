package id.co.binar.secondhand.repository

import id.co.binar.secondhand.data.remote.AuthApi
import id.co.binar.secondhand.model.auth.GetAuthRequest
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApi: AuthApi
) {
    suspend fun login(email: String, password: String) = authApi.login(
        GetAuthRequest(
            email = email,
            password = password
        )
    )
}