package com.tegarpenemuan.secondhandecomerce.ui.akun

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tegarpenemuan.secondhandecomerce.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AkunViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    val showLogin: MutableLiveData<Boolean> = MutableLiveData()
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
                        shouldShowProfile.postValue(it.image_url)
                    }
                } else {
                    showResponseError.postValue("Request get Profile Tidak Failed" + response.code())
                }
            }
        }
    }

    fun clearCredential() {
        viewModelScope.launch {
            repository.clearToken()
            withContext(Dispatchers.Main) {
                showLogin.postValue(true)
            }
        }
    }

    fun logout() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.clearToken()
            repository.clearID()
            val result = repository.deleteAll()
            withContext(Dispatchers.Main) {
                result.let {
                    clearCredential()
                    shouldShowProfile.postValue("Anda Berhasil Logout")
                }
            }
        }
    }
}