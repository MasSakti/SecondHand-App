package id.co.binar.secondhand.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.binar.secondhand.data.local.AuthDao
import id.co.binar.secondhand.model.auth.GetAuthRequest
import id.co.binar.secondhand.model.auth.GetAuthResponse
import id.co.binar.secondhand.repository.AuthRepository
import id.co.binar.secondhand.util.DataStoreManager
import id.co.binar.secondhand.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val authDao: AuthDao,
    private val store: DataStoreManager
) : ViewModel() {

    private val _login = MutableLiveData<Resource<GetAuthResponse>>()
    val login: LiveData<Resource<GetAuthResponse>> = _login
    fun login(field: GetAuthRequest) = CoroutineScope(Dispatchers.IO).launch {
        _login.postValue(Resource.Loading())
        try {
            val response = authRepository.login(field)
            if (response.isSuccessful) {
                response.body()?.let {
                    authDao.login(it.name.toString(), it.email.toString(), it.accessToken.toString())
                    store.setTokenId(it.accessToken.toString())
                    _login.postValue(Resource.Success(it))
                }
            } else {
                throw Exception("Email atau Password salah!")
            }
        } catch (ex: Exception) {
            _login.postValue(Resource.Error(code = null, message = ex.message.toString()))
        }
    }
}