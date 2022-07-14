package com.tegarpenemuan.secondhandecomerce.ui.notifications

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tegarpenemuan.secondhandecomerce.data.api.getNotification.GetNotifResponseItem
import com.tegarpenemuan.secondhandecomerce.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val repository: Repository
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