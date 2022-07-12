package com.example.projectgroup2.ui.login

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectgroup2.data.api.auth.login.LoginRequest
import com.example.projectgroup2.data.local.UserEntity
import com.example.projectgroup2.repository.AuthRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repo: AuthRepository): ViewModel() {
    private var email: String = ""
    private var password: String = ""

    val shouldShowError: MutableLiveData<String> = MutableLiveData()
    val shouldOpenHomePage: MutableLiveData<Boolean> = MutableLiveData()

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
            LoginAPI()
        }
    }

    private fun LoginAPI() {
        CoroutineScope(Dispatchers.IO).launch {
            val request = LoginRequest(
                email = email,
                password = password
            )
            val response = repo.loginUser(request)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    loginResponse?.let {
                        // mempersiapkan untuk simpan token
                        val token = it.access_token.orEmpty()
                        insertToken(token = token)
                        getUserData(token = token)

//                      mempersiapkan untuk insert ke database
//                        val userEntity = UserEntity(
//                            id = it.id.hashCode(),
//                            email = it.email.toString(),
//                        )
//                        insertProfile(userEntity)
                    }
                    shouldOpenHomePage.postValue(true)
                } else {
                    shouldShowError.postValue("Maaf, Gagal insert ke dalam database")
//                    val error =
//                        Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
//                    shouldShowError.postValue(error.message.orEmpty() + " #${error.code}")
                }
            }
        }
    }

    private fun getUserData(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repo.getUser(token = token)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val getUserResponse = response.body()
                    getUserResponse?.let {
                        val userEntity = UserEntity(
                            id = it.id.hashCode(),
                            full_name = it.fullName.orEmpty(),
                            email = it.email.orEmpty(),
                            password = it.password.orEmpty(),
                            phone_number = it.phoneNumber.hashCode(),
                            address = it.address.orEmpty(),
                            image_url = it.imageUrl.orEmpty()
                        )
                        insertProfile(userEntity)
                    }
                } else {
                    shouldShowError.postValue("Maaf, Gagal insert ke dalam database")
                }
            }
        }
    }

    private fun insertToken(token: String) {
        if (token.isNotEmpty()) {
            viewModelScope.launch {
                repo.updateToken(token)
            }
        }
    }

    private fun insertProfile(userEntity: UserEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = repo.insertUser(userEntity)
            withContext(Dispatchers.Main) {
                if (result != 0L) {
                    shouldOpenHomePage.postValue(true)
                } else {
                    shouldShowError.postValue("Maaf, Gagal insert ke dalam database")
                }
            }
        }
    }
}