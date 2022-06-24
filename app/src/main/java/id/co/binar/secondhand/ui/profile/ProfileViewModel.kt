package id.co.binar.secondhand.ui.profile

import android.graphics.Bitmap
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.binar.secondhand.data.local.AuthDao
import id.co.binar.secondhand.data.local.model.AuthLocal
import id.co.binar.secondhand.model.auth.*
import id.co.binar.secondhand.repository.AuthRepository
import id.co.binar.secondhand.util.DataStoreManager
import id.co.binar.secondhand.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
                    withContext(Dispatchers.Main) {
                        _register.postValue(Resource.Success(it))
                    }
                }
            } else if (response.code() == 400) {
                throw Exception("Email telah dibuat")
            } else {
                throw Exception("Terjadi kesalahan")
            }
        } catch (ex: Exception) {
            withContext(Dispatchers.Main) {
                _register.postValue(Resource.Error(ex))
            }
        }
    }

    private val _updateAccount = MutableLiveData<Resource<UpdateAuthByTokenResponse>>()
    val updateAccount : LiveData<Resource<UpdateAuthByTokenResponse>> = _updateAccount
    fun updateAccount(field: UpdateAuthByTokenRequest, image: MultipartBody.Part) = CoroutineScope(Dispatchers.IO).launch {
        _updateAccount.postValue(Resource.Loading())
        try {
            val response = authRepository.updateAccount(field, image)
            if (response.isSuccessful) {
                response.body()?.let {
                    withContext(Dispatchers.Main) {
                        _updateAccount.postValue(Resource.Success(it))
                    }
                }
            } else if (response.code() == 400) {
                throw Exception("Email telah dibuat")
            } else {
                throw Exception("Terjadi kesalahan")
            }
        } catch (ex: Exception) {
            withContext(Dispatchers.Main) {
                _updateAccount.postValue(Resource.Error(ex))
            }
        }
    }

    fun getAccount() = authRepository.getAccount().asLiveData()
}