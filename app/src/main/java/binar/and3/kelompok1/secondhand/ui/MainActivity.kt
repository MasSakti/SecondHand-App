package binar.and3.kelompok1.secondhand.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import binar.and3.kelompok1.secondhand.R
import binar.and3.kelompok1.secondhand.databinding.ActivityLoginUiBinding
import binar.and3.kelompok1.secondhand.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}