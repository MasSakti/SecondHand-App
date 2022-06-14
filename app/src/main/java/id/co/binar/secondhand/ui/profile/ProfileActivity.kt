package id.co.binar.secondhand.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.EntryPoint
import id.co.binar.secondhand.R
import id.co.binar.secondhand.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}