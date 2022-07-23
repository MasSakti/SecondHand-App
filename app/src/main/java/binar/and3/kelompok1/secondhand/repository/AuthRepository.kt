package binar.and3.kelompok1.secondhand.repository

import binar.and3.kelompok1.secondhand.data.api.auth.*
import binar.and3.kelompok1.secondhand.data.local.auth.UserDAO
import binar.and3.kelompok1.secondhand.data.local.auth.UserEntity
import binar.and3.kelompok1.secondhand.datastore.AuthDataStoreManager
import binar.and3.kelompok1.secondhand.data.api.getNotification.GetNotifResponseItem
import kotlinx.coroutines.flow.firstOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    suspend fun updateUserData(
        access_token: String,
        imageUrl: MultipartBody.Part?,
        fullName: RequestBody?,
        address: RequestBody?,
        city: RequestBody?,
        phoneNumber: RequestBody?

    ): Response<UpdateUserDataResponse> {
        return api.updateUser(
            access_token = access_token,
            image = imageUrl,
            fullName = fullName,
            address = address,
            city = city,
            phoneNumber = phoneNumber
        )
    }

    suspend fun insertUser(userEntity: UserEntity): Long {
        return dao.insertUser(userEntity)
    }

    suspend fun getNotification(access_token: String): Response<List<GetNotifResponseItem>> {
        return api.getNotification(access_token)
    }
}