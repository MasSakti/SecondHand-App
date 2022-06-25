package id.co.binar.secondhand.repository

import androidx.room.withTransaction
import id.co.binar.secondhand.data.local.AuthDao
import id.co.binar.secondhand.data.local.model.AuthLocal
import id.co.binar.secondhand.data.remote.AuthApi
import id.co.binar.secondhand.database.RoomDatabase
import id.co.binar.secondhand.model.auth.*
import id.co.binar.secondhand.util.DataStoreManager
import id.co.binar.secondhand.util.Resource
import id.co.binar.secondhand.util.networkBoundResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApi: AuthApi,
    private val authDao: AuthDao,
    private val store: DataStoreManager,
    private val db: RoomDatabase
) {
    fun authDao() = authDao
    fun store() = store

    fun login(field: GetAuthRequest): Flow<Resource<GetAuthResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = authApi.login(field)
            if (response.isSuccessful) {
                response.body()?.let {
                    authDao.setAccount(
                        AuthLocal(
                            id = it.id!!,
                            fullName = it.name,
                            email = it.email,
                            token = it.accessToken
                        )
                    )
                    store.setTokenId(it.accessToken.toString())
                    store.setUsrId(it.id)
                    emit(Resource.Success(it))
                }
            } else if (response.code() == 401) {
                throw Exception("Email atau Password tidak valid")
            } else {
                throw Exception("Terjadi kesalahan")
            }
        } catch (ex: Exception) {
            emit(Resource.Error(ex))
        }
    }

    fun register(field: AddAuthRequest, image: MultipartBody.Part): Flow<Resource<AddAuthResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = authApi.register(
                hashMapOf(
                    "password" to field.password.toString().toRequestBody(MultipartBody.FORM),
                    "full_name" to field.fullName.toString().toRequestBody(MultipartBody.FORM),
                    "phone_number" to field.phoneNumber.toString().toRequestBody(MultipartBody.FORM),
                    "email" to field.email.toString().toRequestBody(MultipartBody.FORM),
                    "address" to field.address.toString().toRequestBody(MultipartBody.FORM),
                    "city" to field.city.toString().toRequestBody(MultipartBody.FORM)
                ),
                image = image
            )
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(it))
                }
            } else if (response.code() == 400) {
                throw Exception("Email telah dibuat")
            } else {
                throw Exception("Terjadi kesalahan")
            }
        } catch (ex: Exception) {
            emit(Resource.Error(ex))
        }
    }

    fun updateAccount(field: UpdateAuthByTokenRequest, image: MultipartBody.Part): Flow<Resource<UpdateAuthByTokenResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = authApi.updateAccount(
                store.getTokenId(),
                hashMapOf(
                    "password" to field.password.toString().toRequestBody(MultipartBody.FORM),
                    "full_name" to field.fullName.toString().toRequestBody(MultipartBody.FORM),
                    "phone_number" to field.phoneNumber.toString().toRequestBody(MultipartBody.FORM),
                    "email" to field.email.toString().toRequestBody(MultipartBody.FORM),
                    "address" to field.address.toString().toRequestBody(MultipartBody.FORM),
                    "city" to field.city.toString().toRequestBody(MultipartBody.FORM)
                ),
                image = image
            )
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(it))
                }
            } else if (response.code() == 400) {
                throw Exception("Email telah dibuat")
            } else {
                throw Exception("Terjadi kesalahan")
            }
        } catch (ex: Exception) {
            emit(Resource.Error(ex))
        }
    }

    fun getAccount() = networkBoundResource(
        query = {
            authDao.getAccount(store.getTokenId(), store.getUsrId())
        },
        fetch = {
            authApi.getAccount(store.getTokenId())
        },
        saveFetchResult = {
            val response = it.body()
            db.withTransaction {
                authDao.removeAccount(store.getTokenId(), store.getUsrId())
                authDao.setAccount(
                    AuthLocal(
                        id = store.getUsrId(),
                        fullName = response?.fullName,
                        address = response?.address,
                        city = response?.city,
                        imageUrl = response?.imageUrl,
                        token = store.getTokenId(),
                        email = response?.email,
                        phoneNumber = response?.phoneNumber
                    )
                )
            }
        }
    )
}