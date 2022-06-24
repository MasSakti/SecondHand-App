package com.tegarpenemuan.secondhandecomerce.ui.register

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import com.bumptech.glide.Glide
import com.github.drjacky.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import com.tegarpenemuan.secondhandecomerce.databinding.ActivityRegisterBinding
import com.tegarpenemuan.secondhandecomerce.ui.login.Login
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Register : AppCompatActivity() {

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

    private fun bindView() {
        binding.etNama.doAfterTextChanged {
            viewModel.onChangeName(it.toString())
        }
        binding.etEmail.doAfterTextChanged {
            viewModel.onChangeEmail(it.toString())
        }
        binding.etEmail.doAfterTextChanged {
            viewModel.onChangePassword(it.toString())
        }
        binding.etPassword.doAfterTextChanged {
            viewModel.onChangePassword(it.toString())
        }
        binding.etPhone.doAfterTextChanged {
            viewModel.onChangePhone(it.toString())
        }
        binding.etAddress.doAfterTextChanged {
            viewModel.onChangeAddress(it.toString())
        }
        binding.etCity.doAfterTextChanged {
            viewModel.onChangeCity(it.toString())
        }
        binding.ivimage.setOnClickListener {
            picImage()
        }
        binding.btnSignIn.setOnClickListener {
            viewModel.onValidate()
        }
    }

    private fun bindViewModel() {
        viewModel.shouldShowLoading.observe(this) {
            if (it) {
                progressDialog.setMessage("Loading...")
                progressDialog.show()
            } else {
                progressDialog.hide()
            }
        }

        viewModel.showResponseSuccess.observe(this) {
            Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@Register, Login::class.java))
            finish()
        }

        viewModel.showResponseError.observe(this) {
            val snackbar = Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
            snackbar.view.setBackgroundColor(Color.RED)
            snackbar.show()
        }
    }

    private fun picImage() {
        ImagePicker.with(this)
            .crop()
            .maxResultSize(1080, 1080, true)
            .createIntentFromDialog { launcher.launch(it) }
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                viewModel.getUriPath(uri)

                Glide.with(applicationContext)
                    .load(uri)
                    .circleCrop()
                    .into(binding.ivimage)
            }
        }
}