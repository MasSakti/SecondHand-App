package binar.and3.kelompok1.secondhand.ui.menu.akun

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import binar.and3.kelompok1.secondhand.common.Status
import binar.and3.kelompok1.secondhand.data.ErrorResponse
import binar.and3.kelompok1.secondhand.data.local.auth.UserEntity
import binar.and3.kelompok1.secondhand.model.ProfileModel
import binar.and3.kelompok1.secondhand.repository.AuthRepository
import binar.and3.kelompok1.secondhand.repository.ProfileRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class AkunViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository,
) : ViewModel() {
    val shouldShowProfile: MutableLiveData<ProfileModel> = MutableLiveData()
    val shouldShowImageProfile: MutableLiveData<String> = MutableLiveData()

    val shouldShowSuccess: MutableLiveData<String> = MutableLiveData()
    val shouldShowLoading: MutableLiveData<Boolean> = MutableLiveData()
    val shouldShowError: MutableLiveData<String> = MutableLiveData()

    val shouldShowLogin: MutableLiveData<Boolean> = MutableLiveData()

    fun getProfile() {
        CoroutineScope(Dispatchers.Main).launch {
            val result = profileRepository.getProfile()
            withContext(Dispatchers.Main) {
                result.let {
                    shouldShowProfile.postValue(it)
                }
            }
        }
    }

    fun uploadImage(body: MultipartBody.Part) {
        shouldShowLoading.postValue(true)
        CoroutineScope(Dispatchers.IO).launch {
            val token = authRepository.getToken()
            val result = profileRepository.uploadImage(image = body, token = token.toString())
            withContext(Dispatchers.Main) {
                when (result.status) {
                    Status.SUCCESS -> {
                        shouldShowImageProfile.postValue(result.data?.imageUrl)
                    }
                    Status.ERROR -> {
                        shouldShowError.postValue(result.message.toString())
                        shouldShowLoading.postValue(false)
                    }
                    Status.LOADING -> {

                    }
                }
            }
            getUserData(token = token.toString())
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
                    getProfile()
                } else {
                    shouldShowError.postValue("Maaf, gagal insert ke dalam database")
                }
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            authRepository.clearToken()
            profileRepository.deleteUser()
            withContext(Dispatchers.Main) {
                shouldShowLogin.postValue(true)
            }
        }
    }
}