package id.co.binar.secondhand.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.co.binar.secondhand.R
import id.co.binar.secondhand.databinding.ActivityRegisterBinding
import id.co.binar.secondhand.model.auth.AddAuthRequest
import id.co.binar.secondhand.model.auth.GetAuthRequest
import id.co.binar.secondhand.ui.profile.PASSING_TO_PROFILE
import id.co.binar.secondhand.ui.profile.ProfileActivity
import id.co.binar.secondhand.util.emailValid
import id.co.binar.secondhand.util.generalValid
import id.co.binar.secondhand.util.passwordValid
import io.github.anderscheow.validator.Validator
import io.github.anderscheow.validator.constant.Mode
import io.github.anderscheow.validator.validator

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        bindView()
    }

    private fun bindView() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_round_arrow_back_24)
        binding.btnDaftar.setOnClickListener {
            onValidate()
        }
        binding.tvMasukDiSini.setOnClickListener {
            onBackPressed()
        }
    }

    private fun onValidate() {
        validator(this) {
            mode = Mode.SINGLE
            listener = onSignUp
            validate(
                generalValid(binding.etNama),
                emailValid(binding.etEmail),
                passwordValid(binding.etPassword)
            )
        }
    }

    private val onSignUp= object : Validator.OnValidateListener {
        override fun onValidateSuccess(values: List<String>) {
            val intent = Intent(this@RegisterActivity, ProfileActivity::class.java)
            intent.putExtra(PASSING_TO_PROFILE,
                    AddAuthRequest(
                        fullName = binding.txtInputLayoutNama.text.toString(),
                        email = binding.txtInputLayoutEmail.text.toString(),
                        password = binding.txtInputLayoutPassword.text.toString()
                    )
                )
            startActivity(intent)
        }

        override fun onValidateFailed(errors: List<String>) {}
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}