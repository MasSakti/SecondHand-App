package id.co.binar.secondhand.ui.profile

import android.graphics.Bitmap
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.binar.secondhand.data.local.AuthDao
import id.co.binar.secondhand.data.local.model.AuthLocal
import id.co.binar.secondhand.model.auth.AddAuthRequest
import id.co.binar.secondhand.model.auth.AddAuthResponse
import id.co.binar.secondhand.repository.AuthRepository
import id.co.binar.secondhand.util.DataStoreManager
import id.co.binar.secondhand.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    state: SavedStateHandle
) : ViewModel() {
    private val _field = state.getLiveData<AddAuthRequest>("FIELD")
    val field: LiveData<AddAuthRequest> = _field
    fun field(field: AddAuthRequest) {
        _field.postValue(field)
    }

    private val _bitmap = state.getLiveData<Bitmap>("BITMAP")
    val bitmap: LiveData<Bitmap> = _bitmap
    fun bitmap(bitmap: Bitmap) {
        _bitmap.postValue(bitmap)
    }

    private val _register = MutableLiveData<Resource<AddAuthResponse>>()
    val register : LiveData<Resource<AddAuthResponse>> = _register
    fun register(field: AddAuthRequest, image: MultipartBody.Part) = CoroutineScope(Dispatchers.IO).launch {
        _register.postValue(Resource.Loading())
        try {
            val response = authRepository.register(field, image)
            if (response.isSuccessful) {
                response.body()?.let {
                    authRepository.authDao().register(
                        AuthLocal(
                            id = it.id!!,
                            fullName = it.fullName,
                            email = it.email,
                            password = it.password,
                            address = it.address,
                            phoneNumber = it.phoneNumber,
                            imageUrl = it.imageUrl,
                            createdAt = it.createdAt
                        )
                    )
                    _register.postValue(Resource.Success(it))
                }
            } else if (response.code() == 400) {
                throw Exception("Email telah dibuat")
            } else {
                throw Exception("Terjadi kesalahan")
            }
        } catch (ex: Exception) {
            _register.postValue(Resource.Error(code = null, message = ex.message.toString()))
        }
    }
}