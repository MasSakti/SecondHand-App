package binar.and3.kelompok1.secondhand.ui.jualform

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import binar.and3.kelompok1.secondhand.databinding.ActivityJualBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JualActivity : AppCompatActivity() {
    lateinit var binding: ActivityJualBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityJualBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }
}