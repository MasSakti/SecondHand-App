package com.tegarpenemuan.secondhandecomerce.ui.register

import android.net.Uri
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tegarpenemuan.secondhandecomerce.common.ConvertToMultipart.toMultipartBody
import com.tegarpenemuan.secondhandecomerce.data.api.getCity.getCityResponse
import com.tegarpenemuan.secondhandecomerce.data.api.getProvince.getProvinveResponse
import com.tegarpenemuan.secondhandecomerce.data.api.register.request.SignUpRequest
import com.tegarpenemuan.secondhandecomerce.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.Response
import java.io.File
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private var full_name: String = ""
    private var email: String = ""
    private var password: String = ""
    private var phone_number: String = ""
    private var address: String = ""
    private var city: String = ""
    private var fileImage: File? = null

    val showCity: MutableLiveData<getCityResponse> = MutableLiveData()
    val showProvince: MutableLiveData<getProvinveResponse> = MutableLiveData()
    val showResponseError: MutableLiveData<String> = MutableLiveData()
    val shouldShowLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showResponseSuccess: MutableLiveData<String> = MutableLiveData()


    fun onChangeName(full_name: String) {
        this.full_name = full_name
    }

    fun onChangeEmail(email: String) {
        this.email = email
    }

    fun onChangePassword(password: String) {
        this.password = password
    }

    fun onChangePhone(phone_number: String) {
        this.phone_number = phone_number
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
        if (full_name.isEmpty() && full_name.length < 3) {
            showResponseError.postValue("Nama tidak valid")
        } else if (email.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showResponseError.postValue("Email tidak valid")
        } else if (password.isEmpty() && password.length < 8) {
            showResponseError.postValue("Password tidak valid")
        } else if (phone_number.isEmpty()) {
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
        val full_name = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), full_name)
        val email = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), email)
        val password = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), password)
        val phone_number =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), phone_number)
        val address = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), address)
        val city = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), city)

        val request = SignUpRequest(
            full_name = full_name,
            email = email,
            password = password,
            phone_number = phone_number,
            address = address,
            image = file,
            city = city
        )
        CoroutineScope(Dispatchers.IO).launch {
            //shouldShowLoading.postValue(true)
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

    fun getProvince(){
        CoroutineScope(Dispatchers.IO).launch {
            val Response = repository.getProvince()
            withContext(Dispatchers.Main){
                if (Response.isSuccessful){
                    val province = Response.body()
                    showProvince.postValue(province!!)
                }else{
                    //error
                }
            }
        }
    }

    fun getCity(id_provinsi: Int){
        CoroutineScope(Dispatchers.IO).launch {
            val Response = repository.getCity(id_provinsi)
            withContext(Dispatchers.Main){
                if (Response.isSuccessful){
                    val city = Response.body()
                    showCity.postValue(city!!)
                }else{
                    //error
                }
            }
        }
    }
//    @Suppress("UNCHECKED_CAST")
//    class Factory(
//        private val repository: AuthRepository
//    ) : ViewModelProvider.Factory {
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
//                return RegisterViewModel(repository) as T
//            }
//            throw IllegalArgumentException("Unknown class name")
//        }
//    }
}