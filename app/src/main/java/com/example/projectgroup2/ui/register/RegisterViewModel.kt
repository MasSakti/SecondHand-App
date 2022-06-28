package com.example.projectgroup2.ui.register

import android.util.Patterns
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectgroup2.data.api.auth.login.LoginRequest
import com.example.projectgroup2.data.api.auth.register.RegisterRequest
import com.example.projectgroup2.data.local.UserEntity
import com.example.projectgroup2.repository.AuthRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repo: AuthRepository): ViewModel() {
    private var full_name: String = ""
    private var email: String = ""
    private var password: String = ""
    private var phone_number: Int = hashCode()
    private var address: String = ""
    private var city: String = ""

    val shouldShowError: MutableLiveData<String> = MutableLiveData()
    val shouldShowLoading: MutableLiveData<Boolean> = MutableLiveData()
    val shouldOpenUpdateProfile: MutableLiveData<Boolean> = MutableLiveData()
    val shouldOpenLoginPage: MutableLiveData<Boolean> = MutableLiveData()

    fun onChangeFullName(full_name: String) {
        this.full_name = full_name
    }

    fun onChangeEmail(email: String) {
        this.email = email
    }

    fun onChangePassword(password: String) {
        this.password = password
    }

    fun onChangePhoneNumber(phone_number: Int) {
        this.phone_number = phone_number
    }

    fun onChangeAddress(address: String) {
        this.address = address
    }

    fun onChangeCity(city: String) {
        this.city = city
    }

    fun onValidate() {
        if (full_name.isEmpty() && full_name.length < 3) {
            shouldShowError.postValue("Nama tidak valid")
        } else if (email.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            shouldShowError.postValue("Email tidak valid")
        } else if (password.isEmpty() && password.length < 8) {
            shouldShowError.postValue("Password tidak valid")
        } else {
            processToSignUp()
        }
    }

    private fun processToSignUp() {
        CoroutineScope(Dispatchers.IO).launch {
            shouldShowLoading.postValue(true)
            val request = RegisterRequest(
                full_name = full_name,
                email = email,
                password = password,
                phone_number = phone_number,
                address = address,
                city = city
            )
            val result = repo.registerUser(request = request)
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    processToSignIn()
                } else {
//                    showErrorMessage(result.errorBody())
                    shouldShowLoading.postValue(false)
                }
            }
        }
    }

    private fun processToSignIn() {
        CoroutineScope(Dispatchers.IO).launch {
            val request = LoginRequest(
                email = email,
                password = password
            )
            val response = repo.loginUser(request)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val signInResponse = response.body()
                    signInResponse?.let {
                        // mempersiapkan untuk simpan token
                        insertToken(it.access_token.orEmpty())

                        // mempersiapkan untuk insert ke database
//                        val userEntity = UserEntity(
//                            id = it.objectId.orEmpty(),
//                            name = it.name.orEmpty(),
//                            email = it.email.orEmpty(),
//                            job = it.job.orEmpty(),
//                            image = it.image.orEmpty()
//                        )
//                        insertProfile(userEntity)
                    }
                    shouldOpenLoginPage.postValue(true)
                    shouldShowLoading.postValue(false)
                } else {
                    shouldShowError.postValue("Maaf, Gagal insert ke dalam database")
//                    showErrorMessage(response.errorBody())
                    shouldShowLoading.postValue(false)
                }
            }
        }
    }

//    private fun showErrorMessage(response: ResponseBody?) {
//        val error =
//            Gson().fromJson(response?.string(), ErrorResponse::class.java)
//        shouldShowError.postValue(error.message.orEmpty() + " #${error.code}")
//    }

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
                    shouldOpenUpdateProfile.postValue(true)
                } else {
                    shouldShowError.postValue("Maaf, Gagal insert ke dalam database")
                }
            }
        }
    }
}