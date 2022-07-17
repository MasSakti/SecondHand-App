package com.tegarpenemuan.secondhandecomerce.ui.profile

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tegarpenemuan.secondhandecomerce.common.ConvertToMultipart.toMultipartBody
import com.tegarpenemuan.secondhandecomerce.data.api.getProfile.GetProfileResponse
import com.tegarpenemuan.secondhandecomerce.data.api.updateUser.UpdateUserRequest
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
class ProfileViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private var fullName: String = ""
    private var phoneNumber: String = ""
    private var address: String = ""
    private var city: String = ""
    private var fileImage: File? = null

    val showResponseError: MutableLiveData<String> = MutableLiveData()
    val showResponseSuccess: MutableLiveData<String> = MutableLiveData()
    val shouldShowUser: MutableLiveData<GetProfileResponse> = MutableLiveData()

    fun onChangeName(full_name: String) {
        this.fullName = full_name
    }

    fun onChangeKota(city: String) {
        this.city = city
    }

    fun onChangeAlamat(address: String) {
        this.address = address
    }

    fun onChangeNoHandphone(phone_number: String) {
        this.phoneNumber = phone_number
    }

    fun getUriPath(uri: Uri) {
        fileImage = File(uri.path ?: "")
    }

    fun onValidate() {
        if (fullName.isEmpty() && fullName.length < 3) {
            showResponseError.postValue("Nama tidak boleh kosong")
        } else if (city.isEmpty()) {
            showResponseError.postValue("Kota tidak boleh kosong")
        } else if (address.isEmpty()) {
            showResponseError.postValue("Alamat tidak boleh kosong")
        } else if (phoneNumber.isEmpty()) {
            showResponseError.postValue("No Telepon tidak boleh kosong")
        } else {
            updateProfile()
        }
    }

    private fun updateProfile() {
        val file = fileImage.toMultipartBody()
        val fullName = fullName.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val city = city.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val address = address.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val phoneNumber = phoneNumber.toRequestBody("multipart/form-data".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.updateUser(
                access_token = repository.getToken()!!,
                request = UpdateUserRequest(
                    full_name = fullName,
                    phone_number = phoneNumber,
                    address = address,
                    city = city,
                    image = file
                )
            )
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    showResponseSuccess.postValue("Data Berhasil Di update")
                    val data = response.body()
                    updateUser(
                        id = repository.getId()!!,
                        full_name = data!!.full_name,
                        phone_number = data.phone_number,
                        address = data.address,
                        image_url = data.image_url,
                        city = data.city
                    )
                } else {
                    showResponseError.postValue("Data gagal diupdate")
                }
            }
        }
    }

    private fun updateUser(
        id: String,
        full_name: String,
        phone_number: String,
        address: String,
        image_url: String? = null,
        city: String
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = repository.updateUser(
                id = id,
                full_name = full_name,
                phone_number = phone_number,
                address = address,
                image_url = image_url!!,
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
                if (response.isSuccessful) {
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
                    }
                } else {
                    showResponseError.postValue("Request get Profile Tidak Failed" + response.code())
                }
            }
        }
    }
}