package com.tegarpenemuan.secondhandecomerce.ui.register

import android.net.Uri
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tegarpenemuan.secondhandecomerce.common.ConvertToMultipart.toMultipartBody
import com.tegarpenemuan.secondhandecomerce.data.api.register.request.SignUpRequest
import com.tegarpenemuan.secondhandecomerce.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private var fullName: String = ""
    private var email: String = ""
    private var password: String = ""
    private var phoneNumber: String = ""
    private var address: String = ""
    private var city: String = ""
    private var fileImage: File? = null

    val showResponseError: MutableLiveData<String> = MutableLiveData()
    val shouldShowLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showResponseSuccess: MutableLiveData<String> = MutableLiveData()


    fun onChangeName(fullName: String) {
        this.fullName = fullName
    }

    fun onChangeEmail(email: String) {
        this.email = email
    }

    fun onChangePassword(password: String) {
        this.password = password
    }

    fun onChangePhone(phoneNumber: String) {
        this.phoneNumber = phoneNumber
    }

    fun onChangeAddress(address: String) {
        this.address = address
    }

    fun onChangeCity(city: String) {
        this.city = city
    }

    fun getUriPath(uri: Uri) {
        fileImage = File(uri.path ?: "")
    }

    fun onValidate() {
        if (fullName.isEmpty() && fullName.length < 3) {
            showResponseError.postValue("Nama tidak valid")
        } else if (email.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showResponseError.postValue("Email tidak valid")
        } else if (password.isEmpty() && password.length < 8) {
            showResponseError.postValue("Password tidak valid")
        } else if (phoneNumber.isEmpty()) {
            showResponseError.postValue("Nomer Telepon tidak valid")
        } else if (address.isEmpty()) {
            showResponseError.postValue("Alamat tidak valid")
        } else if (city.isEmpty()) {
            showResponseError.postValue("Alamat tidak valid")
        } else {
            register()
        }
    }

    fun register() {
        val file = fileImage.toMultipartBody("image")
        val fullName = fullName.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val email = email.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val password = password.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val phoneNumber = phoneNumber.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val address = address.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val city = city.toRequestBody("multipart/form-data".toMediaTypeOrNull())

        val request = SignUpRequest(
            full_name = fullName,
            email = email,
            password = password,
            phone_number = phoneNumber,
            address = address,
            image = file,
            city = city
        )
        CoroutineScope(Dispatchers.IO).launch {
            shouldShowLoading.postValue(true)
            val result = repository.register(request = request)
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    shouldShowLoading.postValue(false)
                    showResponseSuccess.postValue("Register Berhasil")
                } else {
                    shouldShowLoading.postValue(false)
                    showResponseError.postValue(result.errorBody().toString())
                }
            }
        }
    }
}