package binar.and3.kelompok1.secondhand.ui.signin

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import binar.and3.kelompok1.secondhand.databinding.ActivityLoginUiBinding
import binar.and3.kelompok1.secondhand.ui.MenuActivity
import binar.and3.kelompok1.secondhand.ui.signup.RegisterUI
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginUI : AppCompatActivity() {

    private val viewModel: SignInViewModel by viewModels()
    private lateinit var binding: ActivityLoginUiBinding
    private val progressDialog: ProgressDialog by lazy { ProgressDialog(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginUiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        window.statusBarColor = Color.WHITE

        bindView()
        bindViewModel()
    }

    private fun bindView() {
        binding.etEmail.doAfterTextChanged {
            viewModel.onChangeEmail(it.toString())
        }

        binding.etPassword.doAfterTextChanged {
            viewModel.onChangePassword(it.toString())
        }

        binding.btnLogin.setOnClickListener {
            viewModel.onClickSignIn()
        }

        binding.llDaftar.setOnClickListener{
            val intent = Intent(this, RegisterUI::class.java)
            startActivity(intent)
        }
    }

    private fun bindViewModel() {
        viewModel.shouldShowError.observe(this) {
            val snackbar = Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
            snackbar.view.setBackgroundColor(Color.RED)
            snackbar.show()
        }

        viewModel.shouldOpenMenuPage.observe(this) {
            val intent = Intent(this, MenuActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        viewModel.shouldShowLoading.observe(this) {
            if (it) {
                progressDialog.setMessage("Sign in...")
                progressDialog.show()
            } else {
                progressDialog.hide()
            }
        }
    }
}