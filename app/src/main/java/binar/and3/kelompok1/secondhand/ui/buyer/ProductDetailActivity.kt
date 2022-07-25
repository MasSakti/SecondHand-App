package binar.and3.kelompok1.secondhand.ui.buyer

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import binar.and3.kelompok1.secondhand.common.currency
import binar.and3.kelompok1.secondhand.databinding.ActivityProductDetailBinding
import binar.and3.kelompok1.secondhand.ui.buyer.bottomsheets.TawarBottomSheetsFragment
import binar.and3.kelompok1.secondhand.ui.seller.productpreview.ProductPreviewCategoryAdapter
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    private val viewModel: ProductDetailViewModel by viewModels()
    lateinit var productPreviewCategoryAdapter: ProductPreviewCategoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        window.statusBarColor = Color.WHITE

        val id = intent.extras!!.getInt("id")
        viewModel.getProductById(id = id)

        productPreviewCategoryAdapter =
            ProductPreviewCategoryAdapter(emptyList())
        binding.rvProductCategory.adapter = productPreviewCategoryAdapter

        bindViewModel()
        bindView(productId = id)
    }

    private fun bindView(productId: Int) {
        binding.llBack.setOnClickListener {
            onBackPressed()
        }
        binding.btnTawar.setOnClickListener {
            val bottomFragment = TawarBottomSheetsFragment(
                productId = productId
            )
            bottomFragment.show(supportFragmentManager, "tag")
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