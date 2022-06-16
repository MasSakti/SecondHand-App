package id.co.binar.secondhand.ui.dashboard.account

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.binar.secondhand.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    fun logout() = CoroutineScope(Dispatchers.IO).launch {
        authRepository.logout()
    }

    fun getTokenId() = runBlocking { authRepository.store().getTokenId() }
}