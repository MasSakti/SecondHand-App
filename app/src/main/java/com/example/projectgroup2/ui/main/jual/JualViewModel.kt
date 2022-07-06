package com.example.projectgroup2.ui.main.jual

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectgroup2.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class JualViewModel @Inject constructor(private val repo: AuthRepository): ViewModel() {

    fun getToken(){
        viewModelScope.launch {
            repo.getToken()
            withContext(Dispatchers.Main){

            }
        }
    }

}