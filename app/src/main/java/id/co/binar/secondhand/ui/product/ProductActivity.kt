package id.co.binar.secondhand.ui.product

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import id.co.binar.secondhand.R
import id.co.binar.secondhand.databinding.ActivityProductBinding
import id.co.binar.secondhand.model.buyer.product.GetProductResponse
import id.co.binar.secondhand.util.*

const val ARGS_PASSING_PREVIEW = "PREVIEW"
const val ARGS_PASSING_PREVIEW_PHOTO = "PREVIEW_PHOTO"
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
            val item = intent.getParcelableExtra<GetProductResponse>(ARGS_PASSING_PREVIEW)
            val photoProduct = intent.getByteArrayExtra(ARGS_PASSING_PREVIEW_PHOTO)
            viewModel.getAccount.observe(this) {
                binding.apply {
                    ivImageSeller18.setImageBitmap(convertFileLocalToBitmap(photoProduct!!))

                    tvProductSeller18.text = item?.name
                    tvKotaPenjual.text = item?.location
                    tvHargaSeller18.text = item?.basePrice?.convertRupiah()
                    tvIsiDeskripsi.text = item?.description
                    tvJenisSeller18.text = item?.categories?.toNameOnly()

                    imageView.load(it.data?.imageUrl)
                    tvNamaPenjual.text = it.data?.fullName
                    tvKotaPenjual.text = it.data?.city
                }
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