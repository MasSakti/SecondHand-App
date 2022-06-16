package binar.and3.kelompok1.secondhand.ui.signin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import binar.and3.kelompok1.secondhand.R
import binar.and3.kelompok1.secondhand.databinding.ActivityLoginUiBinding
import dagger.hilt.android.AndroidEntryPoint

class LoginUI : AppCompatActivity() {

    private lateinit var binding: ActivityLoginUiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginUiBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}