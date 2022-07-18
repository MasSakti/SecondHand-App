package com.tegarpenemuan.secondhandecomerce.ui.detailnotification

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tegarpenemuan.secondhandecomerce.data.api.Notification.GetDetail.GetDetailNotifResponse
import com.tegarpenemuan.secondhandecomerce.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailNotificationViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val shouldShowDetailNotif: MutableLiveData<GetDetailNotifResponse> = MutableLiveData()

    fun getNotification(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getDetailNotification(id, repository.getToken()!!)
            repository.updateReadNotif(id, repository.getToken()!!)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val getDetailNotif = response.body()
                    shouldShowDetailNotif.postValue(getDetailNotif)
                } else {
                    //shouldShowError.postValue("Request get Profile Tidak Failed" + response.code())
                }
            }
        }
    }
}