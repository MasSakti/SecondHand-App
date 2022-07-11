package binar.and3.kelompok1.secondhand.ui.seller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import binar.and3.kelompok1.secondhand.databinding.ActivityProductPreviewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductPreviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductPreviewBinding
    private val viewModel: ProductPreviewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.extras!!.getInt("id")
        viewModel.getProductById(id = id)
        binding.tvProductName.text = id.toString()

        bindViewModel()
    }

    private fun bindViewModel() {
        viewModel.shouldShowProduct.observe(this) {
        }
    }
}