package com.example.projectgroup2.ui.register

import androidx.lifecycle.ViewModel
import com.example.projectgroup2.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repo: AuthRepository): ViewModel() {
}