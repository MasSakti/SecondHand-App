package com.example.projectgroup2.ui.register

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import com.example.projectgroup2.R
import com.example.projectgroup2.databinding.ActivityRegisterBinding
import com.example.projectgroup2.ui.login.LoginActivity
import com.example.projectgroup2.ui.main.MainActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()
    private val progressDialog: ProgressDialog by lazy { ProgressDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindView()
        bindViewModel()
    }

    private fun bindViewModel() {
        viewModel.shouldShowError.observe(this) {
            val snackbar = Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
            snackbar.view.setBackgroundColor(Color.RED)
            snackbar.show()
        }

        viewModel.shouldShowLoading.observe(this) {
            if (it) {
                progressDialog.setMessage("Loading...")
                progressDialog.show()
            } else {
                progressDialog.hide()
            }
        }

        viewModel.shouldOpenUpdateProfile.observe(this) {
            if (it) {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }

        viewModel.shouldOpenLoginPage.observe(this){
            if (it) {
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }

    private fun bindView() {
        binding.etNamaRegister.doAfterTextChanged {
            viewModel.onChangeFullName(it.toString())
        }
        binding.etEmailRegister.doAfterTextChanged {
            viewModel.onChangeEmail(it.toString())
        }
        binding.etEmailPasswordRegister.doAfterTextChanged {
            viewModel.onChangePassword(it.toString())
        }
        binding.etPhoneRegister.doAfterTextChanged {
            viewModel.onChangePhoneNumber(it.hashCode())
        }
        binding.etAddressRegister.doAfterTextChanged {
            viewModel.onChangeAddress(it.toString())
        }
        binding.etCityRegister.doAfterTextChanged {
            viewModel.onChangeCity(it.toString())
        }
        binding.btnDaftarRegister.setOnClickListener {
            viewModel.onValidate()
        }
    }
}