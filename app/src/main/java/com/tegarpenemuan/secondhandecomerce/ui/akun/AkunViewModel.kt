package com.tegarpenemuan.secondhandecomerce.ui.akun

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tegarpenemuan.secondhandecomerce.data.local.UserEntity
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

    val shouldShowProfile: MutableLiveData<UserEntity> = MutableLiveData()

    fun getUser() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = repository.getUser()
            withContext(Dispatchers.Main) {
                result.let {
                    shouldShowProfile.postValue(it)
                }
            }
        }
    }

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