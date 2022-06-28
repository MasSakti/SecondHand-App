package binar.and3.kelompok1.secondhand.repository

import binar.and3.kelompok1.secondhand.data.api.auth.*
import binar.and3.kelompok1.secondhand.data.local.auth.UserDAO
import binar.and3.kelompok1.secondhand.data.local.auth.UserEntity
import binar.and3.kelompok1.secondhand.datastore.AuthDataStoreManager
import kotlinx.coroutines.flow.firstOrNull
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authDataStore: AuthDataStoreManager,
    private val api: AuthAPI,
    private val dao: UserDAO
) {
    suspend fun getToken(): String? {
        return authDataStore.getToken().firstOrNull()
    }

    suspend fun updateToken(value: String) {
        authDataStore.setToken(value)
    }

    suspend fun clearToken() {
        updateToken("")
    }

    suspend fun signIn(request: SignInRequest): Response<SignInResponse> {
        return api.signIn(request)
    }

    suspend fun signUp(request: SignUpRequest): Response<SignUpResponse> {
        return api.signUp(request)
    }

    suspend fun getUser(token: String): Response<GetUserResponse> {
        return api.getUser(token = token)
    }

    suspend fun insertUser(userEntity: UserEntity): Long {
        return dao.insertUser(userEntity)
    }

    suspend fun updateUser(
        id: Int,
        fullName: String,
        email: String,
        password: String,
        phoneNumber: String,
        address: String,
        imageUrl: String
    ) {
        return dao.updateUser(
            id = id,
            fullName = fullName,
            email = email,
            password = password,
            phoneNumber = phoneNumber,
            address = address,
            imageUrl = imageUrl
        )
    }
}