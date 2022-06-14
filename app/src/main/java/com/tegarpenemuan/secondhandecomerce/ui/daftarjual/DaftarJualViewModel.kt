package com.tegarpenemuan.secondhandecomerce.ui.daftarjual

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DaftarJualViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is daftar jual Fragment"
    }
    val text: LiveData<String> = _text
}