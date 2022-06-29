package id.co.binar.secondhand.ui.product

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import coil.load
import coil.size.ViewSizeResolver
import coil.transform.RoundedCornersTransformation
import dagger.hilt.android.AndroidEntryPoint
import id.co.binar.secondhand.R
import id.co.binar.secondhand.databinding.ActivityProductBinding
import id.co.binar.secondhand.model.buyer.product.GetProductResponse
import id.co.binar.secondhand.ui.product.dialog.ProductBidingFragment
import id.co.binar.secondhand.ui.product.dialog.TAG_BIDING_PRODUCT_DIALOG
import id.co.binar.secondhand.util.*
import io.github.anderscheow.validator.constant.Mode
import io.github.anderscheow.validator.validator
import java.util.*

const val ARGS_PASSING_PREVIEW = "PREVIEW"
const val ARGS_PASSING_SEE_DETAIL = "SEE_DETAIL"

@AndroidEntryPoint
class ProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductBinding
    private val viewModel by viewModels<ProductViewModel>()
    private var item = GetProductResponse()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        bindObserver()
        bindView()
    }

    private fun bindView() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_round_arrow_back_white)

        if (intent.hasExtra(ARGS_PASSING_SEE_DETAIL)) {
            viewModel.getProductById(intent.getIntExtra(ARGS_PASSING_SEE_DETAIL, 0))
            binding.apply {
                btnTerbitkan.text = "Di Nego Say"
                btnTerbitkan.setOnClickListener {
                    viewModel.passToBiding(item)
                    val dialog = ProductBidingFragment()
                    dialog.show(supportFragmentManager, TAG_BIDING_PRODUCT_DIALOG)
                }
            }
        }
        if (intent.hasExtra(ARGS_PASSING_PREVIEW)) {
            val item = intent.getParcelableExtra<GetProductResponse>(ARGS_PASSING_PREVIEW)
            viewModel.getAccount.observe(this) {
                binding.apply {
                    ivImageSeller18.setImageBitmap(convertFileLocalToBitmap(Base64.getDecoder().decode(item?.imageUrl)))

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
        viewModel.newOrder.observe(this) {
            when (it) {
                is Resource.Success -> this.onSnackSuccess(binding.root, "Hore, arga tawaranmu berhasil dikirim ke penjual")
                is Resource.Loading -> this.onToast("Mohon menunggu...")
                is Resource.Error -> this.onSnackError(binding.root, it.error?.message.toString())
            }
        }

        viewModel.getProductById.observe(this) {
            when (it) {
                is Resource.Success -> {

                    /** menyiapkan data untuk passing ke dialog biding harga */
                    it.data?.let {
                        item = it
                    }

                    binding.apply {
                        progressBar.isVisible = false
                        btnTerbitkan.isEnabled = true
                        scrollView3.isVisible = true
                        layoutError.isVisible = false
                        tvProductSeller18.text = it.data?.name
                        tvJenisSeller18.text = it.data?.categories?.toNameOnly()
                        tvHargaSeller18.text = it.data?.basePrice?.convertRupiah()
                        tvIsiDeskripsi.text = it.data?.description
                        ivImageSeller18.load(it.data?.imageUrl) {
                            crossfade(true)
                            placeholder(R.color.purple_100)
                            error(R.color.purple_100)
                            size(ViewSizeResolver(binding.ivImageSeller18))
                        }
                        imageView.load(it.data?.user?.imageUrl) {
                            crossfade(true)
                            placeholder(R.color.purple_100)
                            error(R.color.purple_100)
                            transformations(RoundedCornersTransformation(8F))
                            size(ViewSizeResolver(binding.imageView))
                        }
                        tvNamaPenjual.text = it.data?.user?.fullName
                        tvKotaPenjual.text = it.data?.user?.city
                    }
                }
                is Resource.Loading -> {
                    binding.apply {
                        progressBar.isVisible = true
                        btnTerbitkan.isEnabled = false
                        scrollView3.isVisible = false
                        layoutError.isVisible = false
                    }
                }
                is Resource.Error -> {
                    binding.apply {
                        progressBar.isVisible = false
                        btnTerbitkan.isEnabled = false
                        scrollView3.isVisible = false
                        layoutError.isVisible = true
                        textView8.text = it.error?.message.toString()
                        this@ProductActivity.onSnackError(root, it.error?.message.toString())
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}