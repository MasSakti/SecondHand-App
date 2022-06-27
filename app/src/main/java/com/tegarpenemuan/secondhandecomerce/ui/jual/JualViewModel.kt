package com.tegarpenemuan.secondhandecomerce.ui.jual

import androidx.lifecycle.ViewModel
import com.tegarpenemuan.secondhandecomerce.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class JualViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

}