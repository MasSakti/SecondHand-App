package binar.and3.kelompok1.secondhand.ui.signup

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import binar.and3.kelompok1.secondhand.R
import binar.and3.kelompok1.secondhand.databinding.ActivityRegisterUiBinding
import binar.and3.kelompok1.secondhand.ui.MenuActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterUI : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterUiBinding
    private val viewModel: SignUpViewModel by viewModels()
    private val progressDialog: ProgressDialog by lazy { ProgressDialog(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterUiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        bindView()
        bindViewModel()
    }

    private fun bindView() {
        binding.etNama.doAfterTextChanged {
            viewModel.onChangeFullName(it.toString())
        }
        binding.etEmail.doAfterTextChanged {
            viewModel.onChangeEmail(it.toString())
        }
        binding.etPassword.doAfterTextChanged {
            viewModel.onChangePassword(it.toString())
        }
        binding.btnDaftar.setOnClickListener {
            viewModel.onValidate()
        }
    }

    private fun bindViewModel() {
        viewModel.shouldShowError.observe(this) {
            val snackbar = Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
            snackbar.view.setBackgroundColor(Color.RED)
            snackbar.show()
        }
        viewModel.shouldShowLoading.observe(this) {
            if (it) {
                progressDialog.setMessage("Sign Up...")
                progressDialog.show()
            } else {
                progressDialog.hide()
            }
        }
        viewModel.shouldOpenUpdateProfile.observe(this) {
            if (it) {
                val intent = Intent(this, MenuActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }
}