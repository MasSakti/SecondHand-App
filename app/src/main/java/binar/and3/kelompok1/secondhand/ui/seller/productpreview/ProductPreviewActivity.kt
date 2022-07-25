package binar.and3.kelompok1.secondhand.ui.seller.productpreview

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import binar.and3.kelompok1.secondhand.common.currency
import binar.and3.kelompok1.secondhand.databinding.ActivityProductPreviewBinding
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
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
            binding.tvProductBasePrice.text = it.basePrice?.let { it1 -> currency(it1) }
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

        viewModel.shouldShowError.observe(this) {
            val snackbar = Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
            snackbar.view.setBackgroundColor(Color.RED)
            snackbar.show()
        }

    }
}