package com.tegarpenemuan.secondhandecomerce.ui.profile

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tegarpenemuan.secondhandecomerce.common.ConvertToMultipart.toMultipartBody
import com.tegarpenemuan.secondhandecomerce.data.api.getProfile.GetProfileResponse
import com.tegarpenemuan.secondhandecomerce.data.api.updateUser.UpdateUserRequest
import com.tegarpenemuan.secondhandecomerce.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private var full_name: String = ""
    private var phone_number: String = ""
    private var address: String = ""
    private var city: String = ""
    private var fileImage: File? = null

    val showResponseError: MutableLiveData<String> = MutableLiveData()
    val showResponseSuccess: MutableLiveData<String> = MutableLiveData()
    val shouldShowUser: MutableLiveData<GetProfileResponse> = MutableLiveData()

    fun onChangeName(full_name: String) {
        this.full_name = full_name
    }

    fun onChangeKota(city: String) {
        this.city = city
    }

    fun onChangeAlamat(address: String) {
        this.address = address
    }

    fun onChangeNoHandphone(phone_number: String) {
        this.phone_number = phone_number
    }

    fun getUriPath(uri: Uri) {
        fileImage = File(uri.path ?: "")
    }

    fun onValidate() {
        if (full_name.isEmpty() && full_name.length < 3) {
            showResponseError.postValue("Nama tidak boleh kosong")
        } else if (city.isEmpty()) {
            showResponseError.postValue("Kota tidak boleh kosong")
        } else if (address.isEmpty()) {
            showResponseError.postValue("Alamat tidak boleh kosong")
        } else if (phone_number.isEmpty()) {
            showResponseError.postValue("No Telepon tidak boleh kosong")
        } else {
            updateProfile()
        }
    }

    private fun updateProfile() {
        val file = fileImage.toMultipartBody("image")
        val full_name = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), full_name)
        val city = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), city)
        val address = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), address)
        val phone_number = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), phone_number)

        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.updateUser(
                access_token = repository.getToken()!!,
                request = UpdateUserRequest(
                    full_name = full_name,
                    phone_number = phone_number,
                    address = address,
                    image = file,
                    city = city
                )
            )
            withContext(Dispatchers.Main) {
                if (response!!.isSuccessful) {
                    showResponseSuccess.postValue("Data Berhasil Di update")
                } else {
                    showResponseError.postValue("Data gagal diupdate" + response.code())
                }
            }
        }
    }

    fun updateUser(
        id: String,
        full_name: String,
        phone_number: String,
        address: String,
        image_url: String,
        city: String
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = repository.updateUser(
                id = id,
                full_name = full_name,
                phone_number = phone_number,
                address = address,
                image_url = image_url,
                city = city
            )
            withContext(Dispatchers.Main) {
                result.let {
                    //showResponseSuccess.postValue("Data Berhasil Di update")
                }
            }
        }
    }

    fun getProfile() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getProfile(repository.getToken()!!)
            withContext(Dispatchers.Main) {
                if (response!!.isSuccessful) {
                    val getProfileResponse = response.body()
                    getProfileResponse?.let {
                        val getprofile = GetProfileResponse(
                            address = it.address,
                            city = it.city,
                            createdAt = it.createdAt,
                            email = it.email,
                            full_name = it.full_name,
                            id = it.id,
                            image_url = it.image_url,
                            password = it.password,
                            phone_number = it.phone_number,
                            updatedAt = it.updatedAt
                        )
                        shouldShowUser.postValue(getprofile)

                        updateUser(
                            id = repository.getId()!!,
                            full_name = it.full_name,
                            phone_number = it.phone_number,
                            address = it.address,
                            image_url = it.image_url,
                            city = it.city.toString()
                        )
                    }
                } else {
                    showResponseError.postValue("Request get Profile Tidak Failed" + response.code())
                }
            }
        }
    }
}