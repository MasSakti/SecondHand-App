package com.tegarpenemuan.secondhandecomerce.ui.jual

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class JualViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is jual Fragment"
    }
    val text: LiveData<String> = _text
}