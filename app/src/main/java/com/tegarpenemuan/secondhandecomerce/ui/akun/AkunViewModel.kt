package com.tegarpenemuan.secondhandecomerce.ui.akun

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tegarpenemuan.secondhandecomerce.data.api.getProfile.GetProfileResponse
import com.tegarpenemuan.secondhandecomerce.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AkunViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    val showResponseError: MutableLiveData<String> = MutableLiveData()
    val showResponseSuccess: MutableLiveData<String> = MutableLiveData()
    val shouldShowProfile: MutableLiveData<String> = MutableLiveData()

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
                        //panggil get Room
                        updateUser(
                            id = repository.getId()!!,
                            full_name = it.full_name,
                            phone_number = it.phone_number,
                            address = it.address,
                            image_url = it.image_url,
                            city = it.city.toString()
                        )
                        shouldShowProfile.postValue(it.image_url)
                    }
                } else {
                    showResponseError.postValue("Request get Profile Tidak Failed" + response.code())
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
                    //getUser()
                }
            }
        }
    }

//    fun getUser() {
//        CoroutineScope(Dispatchers.IO).launch {
//            val result = repository.getUser()
//            withContext(Dispatchers.Main) {
//                result.let {
//                    shouldShowProfile.postValue(it)
//                }
//            }
//        }
//    }

    fun logout() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.clearToken()
            repository.clearID()
            val getUser = repository.getUser()
            val result = repository.deleteUser(getUser)
            withContext(Dispatchers.Main) {
                result.let {
                    //shouldShowProfile.postValue(it)
                }
            }
        }
    }
}