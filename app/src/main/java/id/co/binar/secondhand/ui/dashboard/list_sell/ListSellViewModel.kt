package id.co.binar.secondhand.ui.dashboard.list_sell

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.binar.secondhand.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class ListSellViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    fun getAccount() = authRepository.getAccount().asLiveData()
}