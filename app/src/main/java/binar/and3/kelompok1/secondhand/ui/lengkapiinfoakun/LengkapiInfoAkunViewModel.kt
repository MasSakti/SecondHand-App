package binar.and3.kelompok1.secondhand.ui.lengkapiinfoakun

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import binar.and3.kelompok1.secondhand.common.reduceFileImage
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
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class LengkapiInfoAkunViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository
): ViewModel() {

    private var imageUrl: File? = null
    private var fullName: String = ""
    private var city: String = ""
    private var address: String = ""
    private var phoneNumber: String = ""

    val shouldShowProfile: MutableLiveData<ProfileModel> = MutableLiveData()
    val shouldShowSuccess: MutableLiveData<String> = MutableLiveData()
    val shouldShowError: MutableLiveData<String> = MutableLiveData()

    fun onChangeImageUrl(imageUrl: File) {
        this.imageUrl = imageUrl
    }
    fun onChangeFullName(fullName: String) {
        this.fullName = fullName
    }
    fun onChangeCity(city: String) {
        this.city = city
    }
    fun onChangeAddress(address: String) {
        this.address = address
    }
    fun onChangePhoneNumber(phoneNumber: String) {
        this.phoneNumber = phoneNumber
    }

    fun onViewLoaded() {
        getProfile()
    }

    fun onValidate() {
        if (fullName.isEmpty()) {
            shouldShowError.postValue("Kamu harus mengisi nama")
        } else if (city.isEmpty()) {
            shouldShowError.postValue("Kamu harus mengisi kota tinggalmu")
        } else if (address.isEmpty()) {
            shouldShowError.postValue("Kamu harus mengisi alamat tinggalmu")
        } else if (phoneNumber.isEmpty()) {
            shouldShowError.postValue("Nomor telpon harus diisi")
        } else {
            println("Tombol berhasil diklik!")
            imageUrl?.let {
                updateProfile(
                    image = it,
                    fullName = fullName,
                    city = city,
                    address = address,
                    phoneNumber = phoneNumber
                )
            }
        }
    }

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

    private fun updateProfile(
        image: File,
        fullName: String,
        city: String,
        address: String,
        phoneNumber: String
    ) {
        val requestFile = image.asRequestBody("image/jpg".toMediaTypeOrNull())
        val imageUrlRequestBody = MultipartBody.Part.createFormData("image", image.name, requestFile)
        val fullNameRequestBody = fullName.toRequestBody("text/plain".toMediaType())
        val cityRequestBody = city.toRequestBody("text/plain".toMediaType())
        val addressRequestBody = address.toRequestBody("text/plain".toMediaType())
        val phoneNumberRequestBody = phoneNumber.toRequestBody("text/plain".toMediaType())

        CoroutineScope(Dispatchers.IO).launch {
            val accessToken = authRepository.getToken().toString()
            println("Sedang dikirim update data!")
            val result = authRepository.updateUserData(
                access_token = accessToken,
                imageUrl = imageUrlRequestBody,
                fullName = fullNameRequestBody,
                city = cityRequestBody,
                address = addressRequestBody,
                phoneNumber = phoneNumberRequestBody
            )
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    println("Berhasil update data!")
                    getUserData(access_token = accessToken)
                } else {
                    shouldShowError.postValue(result.errorBody().toString())
                }
            }
        }
    }

    private fun getUserData(access_token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = authRepository.getUser(token = access_token)
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
                            city = it.city.orEmpty(),
                            imageUrl = it.imageUrl.orEmpty()
                        )
                        insertProfile(userEntity)
                        println("Berhasil Get User!")
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
                    println("Berhasil insert profile")
                    shouldShowSuccess.postValue("Profil berhasil diperbarui!")
                } else {
                    shouldShowError.postValue("Maaf, gagal insert ke dalam database")
                }
            }
        }
    }
}