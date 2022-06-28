package id.co.binar.secondhand.ui.dashboard.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.binar.secondhand.model.notification.GetNotifResponse
import id.co.binar.secondhand.repository.NotificationRepository
import id.co.binar.secondhand.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
) : ViewModel() {
    val getNotif = notificationRepository.getNotif().asLiveData()

    private val _updateNotif = MutableLiveData<Resource<GetNotifResponse>>()
    val updateNotif: LiveData<Resource<GetNotifResponse>> = _updateNotif
    fun updateNotif(id: Int) = CoroutineScope(Dispatchers.IO).launch {
        notificationRepository.updateNotif(id).collectLatest {
            _updateNotif.postValue(it)
        }
    }
}