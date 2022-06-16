package id.co.binar.secondhand.repository

import android.graphics.Bitmap
import id.co.binar.secondhand.data.local.AuthDao
import id.co.binar.secondhand.data.remote.AuthApi
import id.co.binar.secondhand.model.auth.AddAuthRequest
import id.co.binar.secondhand.model.auth.GetAuthRequest
import id.co.binar.secondhand.util.DataStoreManager
import id.co.binar.secondhand.util.buildImageMultipart
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApi: AuthApi,
    private val authDao: AuthDao,
    private val store: DataStoreManager
) {
    suspend fun login(field: GetAuthRequest) = authApi.login(field)

    suspend fun register(field: AddAuthRequest, image: MultipartBody.Part) = authApi.register(
        hashMapOf(
            "password" to field.password.toString().toRequestBody(MultipartBody.FORM),
            "full_name" to field.fullName.toString().toRequestBody(MultipartBody.FORM),
            "phone_number" to field.phoneNumber.toString().toRequestBody(MultipartBody.FORM),
            "email" to field.email.toString().toRequestBody(MultipartBody.FORM),
            "address" to field.address.toString().toRequestBody(MultipartBody.FORM)
        ),
        image = image
    )

    suspend fun logout() {
        store.clear()
        authDao.logout()
    }

    fun store() = store
}