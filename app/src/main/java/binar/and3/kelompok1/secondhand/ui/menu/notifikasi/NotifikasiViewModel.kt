package binar.and3.kelompok1.secondhand.ui.menu.notifikasi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import binar.and3.kelompok1.secondhand.data.api.getNotification.GetNotifResponseItem
import binar.and3.kelompok1.secondhand.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NotifikasiViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    val shouldShowGetNotification: MutableLiveData<List<GetNotifResponseItem>> = MutableLiveData()

    fun getNotification(){
        CoroutineScope(Dispatchers.IO).launch{
            val response = repository.getNotification(repository.getToken()!!)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val getNotificationResponse = response.body()
                    shouldShowGetNotification.postValue(getNotificationResponse!!)
                } else {
                    //shouldShowError.postValue("Request get Profile Tidak Failed" + response.code())
                }
            }
        }
    }
}