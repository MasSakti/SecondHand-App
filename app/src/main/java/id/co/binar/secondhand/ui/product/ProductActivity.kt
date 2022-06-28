package id.co.binar.secondhand.ui.product

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import id.co.binar.secondhand.R
import id.co.binar.secondhand.databinding.ActivityProductBinding
import id.co.binar.secondhand.model.buyer.product.GetProductByIdResponse
import id.co.binar.secondhand.util.Resource
import id.co.binar.secondhand.util.convertRupiah

const val ARGS_PASSING_PREVIEW = "PREVIEW"
const val ARGS_PASSING_SEE_DETAIL = "SEE_DETAIL"

@AndroidEntryPoint
class ProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductBinding
    private val viewModel by viewModels<ProductViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setNavigationIcon(R.drawable.ic_round_arrow_back_white)
        bindObserver()
        bindView()
    }

    private fun bindView() {
        if (intent.hasExtra(ARGS_PASSING_SEE_DETAIL)) {
            viewModel.getProductById(intent.getIntExtra(ARGS_PASSING_SEE_DETAIL, 0))
        }
        if (intent.hasExtra(ARGS_PASSING_PREVIEW)) {
            val item = intent.getParcelableExtra<GetProductByIdResponse>(ARGS_PASSING_PREVIEW)
            binding.apply {
                ivImageSeller18.setImageBitmap(item?.photoProduct)
                imageView.setImageBitmap(item?.photoProfile)
                tvProductSeller18.text = item?.name
                tvKotaPenjual.text = item?.location
                tvHargaSeller18.text = item?.basePrice?.convertRupiah()
            }
        }
    }

    private fun bindObserver() {
        viewModel.getProductById.observe(this) {
            when (it) {
                is Resource.Success -> {}
                is Resource.Loading -> {}
                is Resource.Error -> {}
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}