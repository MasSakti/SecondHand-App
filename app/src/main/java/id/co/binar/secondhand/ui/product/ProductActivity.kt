package id.co.binar.secondhand.ui.product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.EntryPoint
import id.co.binar.secondhand.R
import id.co.binar.secondhand.databinding.ActivityProductBinding

class ProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}