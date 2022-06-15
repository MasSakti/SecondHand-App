package com.tegarpenemuan.secondhandecomerce.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import com.tegarpenemuan.secondhandecomerce.databinding.ActivityLoginBinding
import com.tegarpenemuan.secondhandecomerce.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindview()
        bindviewModel()
    }

    private fun bindviewModel() {
        viewModel.shouldShowError.observe(this) {
            Toast.makeText(applicationContext,it,Toast.LENGTH_SHORT).show()
        }
        viewModel.shouldShowSuccess.observe(this){
            if (it){
                startActivity(Intent(applicationContext,MainActivity::class.java))
            }
        }
    }

    private fun bindview() {
        binding.etEmail.doAfterTextChanged {
            viewModel.onChangeEmail(it.toString())
        }

        binding.etPassword.doAfterTextChanged {
            viewModel.onChangePassword(it.toString())
        }

        binding.btnSignIn.setOnClickListener {
            viewModel.onClickSignIn()
        }
    }
}