package binar.and3.kelompok1.secondhand.ui.signin

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import binar.and3.kelompok1.secondhand.data.ErrorResponse
import binar.and3.kelompok1.secondhand.data.api.auth.SignInRequest
import binar.and3.kelompok1.secondhand.data.local.auth.UserEntity
import binar.and3.kelompok1.secondhand.repository.AuthRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    private var email: String = ""
    private var password: String = ""

    val shouldShowError: MutableLiveData<String> = MutableLiveData()
    val shouldOpenMenuPage: MutableLiveData<Boolean> = MutableLiveData()

    val shouldShowLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun onChangeEmail(email: String) {
        this.email = email
    }

    fun onChangePassword(password: String) {
        this.password = password
    }

    fun onClickSignIn() {
        if (email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            shouldShowError.postValue("Email tidak valid")
        } else if (password.isNotEmpty() && password.length < 6) {
            shouldShowError.postValue("Password tidak valid")
        } else {
            signInFromAPI()
        }
    }

    private fun signInFromAPI() {
        shouldShowLoading.postValue(true)
        CoroutineScope(Dispatchers.IO).launch {
            val request = SignInRequest(
                email = email,
                password = password
            )
            val response = authRepository.signIn(request)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val signinResponse = response.body()
                    signinResponse?.let {
                        val token = it.accessToken.orEmpty()
                        insertToken(token = token)
                        getUserData(token = token)
                    }
                } else {
                    val error =
                        Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    shouldShowError.postValue(error.message.orEmpty() + " #${error.code}")
                }
            }
            shouldShowLoading.postValue(false)
        }
    }

    private fun insertToken(token: String) {
        if (token.isNotEmpty()) {
            viewModelScope.launch {
                authRepository.updateToken(token)
            }
        }
    }

    private fun getUserData(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = authRepository.getUser(token = token)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val getUserResponse = response.body()
                    getUserResponse?.let {
                        val userEntity = UserEntity(
                            id = it.id.hashCode(),
                            fullName = it.fullName.orEmpty(),
                            email = it.email.orEmpty(),
                            password = it.password.orEmpty(),
                            phoneNumber = it.phoneNumber.hashCode(),
                            address = it.address.orEmpty(),
                            imageUrl = it.imageUrl.orEmpty()
                        )
                        insertProfile(userEntity)
                    }
                } else {
                    val error =
                        Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    shouldShowError.postValue(error.message.orEmpty() + " #${error.code}")
                }
            }
        }
    }

    private fun insertProfile(userEntity: UserEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = authRepository.insertUser(userEntity)
            withContext(Dispatchers.Main) {
                if (result != 0L) {
                    shouldOpenMenuPage.postValue(true)
                } else {
                    shouldShowError.postValue("Maaf, gagal insert ke dalam database")
                }
            }
        }
    }
}