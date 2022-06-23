package com.tegarpenemuan.secondhandecomerce.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import com.tegarpenemuan.secondhandecomerce.databinding.ActivityLoginBinding
import com.tegarpenemuan.secondhandecomerce.ui.main.MainActivity
import com.tegarpenemuan.secondhandecomerce.ui.register.Register
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.onViewLoaded()

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
        viewModel.shouldOpenSignIn.observe((this)) {
            val intent = Intent(this, Login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        viewModel.shouldOpenMain.observe(this) {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
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
        binding.tvDaftarDiSini.setOnClickListener{
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }
}