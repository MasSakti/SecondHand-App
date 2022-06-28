package id.co.binar.secondhand.ui.dashboard.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.binar.secondhand.repository.NotificationRepository
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    notificationRepository: NotificationRepository
) : ViewModel() {
    val getNotif = notificationRepository.getNotif().asLiveData()
}