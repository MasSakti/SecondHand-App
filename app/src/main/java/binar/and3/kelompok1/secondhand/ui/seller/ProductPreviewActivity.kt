package binar.and3.kelompok1.secondhand.ui.seller

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import binar.and3.kelompok1.secondhand.databinding.ActivityProductPreviewBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductPreviewActivity : AppCompatActivity() {
    lateinit var productPreviewCategoryAdapter: ProductPreviewCategoryAdapter
    private lateinit var binding: ActivityProductPreviewBinding
    private val viewModel: ProductPreviewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        window.statusBarColor = Color.WHITE

        val id = intent.extras!!.getInt("id")
        viewModel.getProductById(id = id)

        productPreviewCategoryAdapter =
            ProductPreviewCategoryAdapter(emptyList())
        binding.rvProductCategory.adapter = productPreviewCategoryAdapter

        bindViewModel()
        bindView()
    }

    private fun bindView() {
        binding.llBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun bindViewModel() {
        viewModel.shouldShowProduct.observe(this) {
            productPreviewCategoryAdapter.updateProductCategory(it.categories)
            binding.tvProductName.text = it.name
            binding.tvProductBasePrice.text = it.basePrice.toString()
            binding.tvDescription.text = it.description

            Glide.with(this)
                .load(it.imageUrl)
                .into(binding.ivProductImage)

            binding.tvUserName.text = it.user?.fullName
            binding.tvUserCity.text = it.user?.city

            Glide.with(this)
                .load(it.user?.imageUrl)
                .into(binding.ivAvatar)
        }

    }
}