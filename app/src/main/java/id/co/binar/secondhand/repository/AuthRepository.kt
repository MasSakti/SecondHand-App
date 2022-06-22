package id.co.binar.secondhand.repository

import android.graphics.Bitmap
import androidx.room.withTransaction
import id.co.binar.secondhand.data.local.AuthDao
import id.co.binar.secondhand.data.local.model.AuthLocal
import id.co.binar.secondhand.data.remote.AuthApi
import id.co.binar.secondhand.database.RoomDatabase
import id.co.binar.secondhand.model.auth.AddAuthRequest
import id.co.binar.secondhand.model.auth.GetAuthRequest
import id.co.binar.secondhand.model.auth.UpdateAuthByTokenRequest
import id.co.binar.secondhand.util.DataStoreManager
import id.co.binar.secondhand.util.Resource
import id.co.binar.secondhand.util.buildImageMultipart
import id.co.binar.secondhand.util.networkBoundResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
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

    suspend fun login(field: GetAuthRequest) = authApi.login(field)

    suspend fun register(field: AddAuthRequest, image: MultipartBody.Part) = authApi.register(
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

    suspend fun updateAccount(field: UpdateAuthByTokenRequest, image: MultipartBody.Part) = authApi.updateAccount(
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