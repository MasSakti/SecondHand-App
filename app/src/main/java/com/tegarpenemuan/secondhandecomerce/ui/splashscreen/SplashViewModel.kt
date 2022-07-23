package com.tegarpenemuan.secondhandecomerce.ui.splashscreen

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tegarpenemuan.secondhandecomerce.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val shouldOpenOnBoarding: MutableLiveData<Boolean> = MutableLiveData()
    val shouldOpenHomePage: MutableLiveData<Boolean> = MutableLiveData()

    fun onViewLoaded() {
        viewModelScope.launch {
            val result = repository.getToken()
            withContext(Dispatchers.Main) {
                if (result.isNullOrEmpty()) {
                    shouldOpenOnBoarding.postValue(true)
                } else {
                    shouldOpenHomePage.postValue(true)
                }
            }
        }
    }
}