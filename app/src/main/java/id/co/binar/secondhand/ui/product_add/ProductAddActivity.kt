package id.co.binar.secondhand.ui.product_add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.EntryPoint
import id.co.binar.secondhand.R
import id.co.binar.secondhand.databinding.ActivityProductAddBinding

class ProductAddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}