package com.tegarpenemuan.secondhandecomerce.ui.login

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tegarpenemuan.secondhandecomerce.data.api.login.LoginRequest
import com.tegarpenemuan.secondhandecomerce.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private var email: String = ""
    private var password: String = ""
    private var token: String? = ""

    val shouldShowError: MutableLiveData<String> = MutableLiveData()
    val shouldOpenSignIn: MutableLiveData<Boolean> = MutableLiveData()
    val shouldShowSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val shouldOpenMain: MutableLiveData<Boolean> = MutableLiveData()
    val shouldShowLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun onChangeEmail(email: String) {
        this.email = email
    }

    fun onChangePassword(password: String) {
        this.password = password
    }

    fun onClickSignIn() {
        if (email.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            shouldShowError.postValue("Email tidak valid")
        } else if (password.isEmpty() && password.length < 8) {
            shouldShowError.postValue("Password tidak valid")
        } else {
            login()
        }
    }

    private fun login() {
        CoroutineScope(Dispatchers.IO).launch {
            shouldShowLoading.postValue(true)
            val request = LoginRequest(
                email = email,
                password = password
            )
            val response = repository.login(request)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    shouldShowLoading.postValue(false)
                    val loginResponse = response.body()
                    loginResponse?.let {
                        token = it.access_token
                        insertToken(token!!)
                        insertId(it.id)
                        getProfile(token!!)
                    }
                } else if(response.code() == 500){
                    shouldShowLoading.postValue(false)
                    shouldShowError.postValue("Server Sedang Error")
                } else {
                    shouldShowLoading.postValue(false)
                    shouldShowError.postValue("Email atau Password Salah")
                }
            }
        }
    }

    fun getProfile(access_token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getProfile(access_token)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    shouldShowSuccess.postValue(true)
                } else {
                    shouldShowError.postValue("Request get Profile Tidak Failed" + response.code())
                }
            }
        }
    }

// Room
//    private fun insertProfile(userEntity: UserEntity) {
//        CoroutineScope(Dispatchers.IO).launch {
//            val result = repository.insertUser(userEntity)
//            withContext(Dispatchers.Main) {
//                if (result != 0L) {
//                    shouldShowSuccess.postValue(true)
//                } else {
//                    shouldShowError.postValue("Maaf, gagal insert ke dalam database")
//                }
//            }
//        }
//    }

    private fun insertToken(token: String) {
        if (token.isNotEmpty()) {
            viewModelScope.launch {
                repository.updateToken(token)
            }
        }
    }

    private fun insertId(id: String) {
        if (id.isNotEmpty()) {
            viewModelScope.launch {
                repository.setId(id)
            }
        }
    }

    fun onViewLoaded() {
        viewModelScope.launch {
            val result = repository.getToken()
            withContext(Dispatchers.Main) {
                if (result == "") {
                    println("Hai")
                } else {
                    shouldOpenMain.postValue(true)
                }
            }
        }
    }
}