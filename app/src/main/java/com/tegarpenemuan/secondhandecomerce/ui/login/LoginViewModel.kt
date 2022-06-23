package com.tegarpenemuan.secondhandecomerce.ui.login

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tegarpenemuan.secondhandecomerce.data.api.login.LoginRequest
import com.tegarpenemuan.secondhandecomerce.data.local.UserEntity
import com.tegarpenemuan.secondhandecomerce.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * com.tegarpenemuan.secondhandecomerce.ui.login
 *
 * Created by Tegar Penemuan on 15/06/2022.
 * https://github.com/tegarpenemuanr3
 *
 */

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private var email: String = ""
    private var password: String = ""
    private lateinit var token: String

    val shouldShowError: MutableLiveData<String> = MutableLiveData()
    val shouldOpenSignIn: MutableLiveData<Boolean> = MutableLiveData()
    val shouldShowSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val shouldOpenMain: MutableLiveData<Boolean> = MutableLiveData()

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
            val request = LoginRequest(
                email = email,
                password = password
            )
            val response = repository.login(request)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    loginResponse?.let {
                        token = it.access_token
                        insertToken(token)
                        insertId(it.id)
                    }
                    shouldShowSuccess.postValue(true)
                } else {
                    shouldShowError.postValue("Request login Tidak Failed")
                }
            }
        }
    }

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
                if (result.isNullOrEmpty()) {
                    //shouldOpenSignIn.postValue(true)
                } else {
                    shouldOpenMain.postValue(true)
                }
            }
        }
    }
}