package com.example.projectgroup2.ui.main.akun

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectgroup2.repository.AuthRepository
import com.example.projectgroup2.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AkunViewModel @Inject constructor(private val repo: AuthRepository): ViewModel() {
    val showLogin: MutableLiveData<Boolean> = MutableLiveData()

    fun clearCredential(){
        viewModelScope.launch {
            repo.clearToken()
            withContext(Dispatchers.Main){
                showLogin.postValue(true)
            }
        }
    }
}