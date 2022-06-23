package binar.and3.kelompok1.secondhand.ui.menu.akun

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import binar.and3.kelompok1.secondhand.model.ProfileModel
import binar.and3.kelompok1.secondhand.repository.AuthRepository
import binar.and3.kelompok1.secondhand.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import retrofit2.http.Multipart
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

    private fun getProfile() {
        CoroutineScope(Dispatchers.Main).launch {
            val result = profileRepository.getProfile()
            withContext(Dispatchers.Main) {
                result.let {
                    shouldShowProfile.postValue(it)
                }
            }
        }
    }

//    fun uploadImage(body: MultipartBody.Part) {
//        shouldShowLoading.postValue(true)
//        CoroutineScope(Dispatchers.IO).launch {
//            val result =
//        }
//    }
}