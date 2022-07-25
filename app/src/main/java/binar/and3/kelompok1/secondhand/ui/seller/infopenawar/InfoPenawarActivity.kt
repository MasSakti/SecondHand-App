package binar.and3.kelompok1.secondhand.ui.seller.infopenawar

import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import binar.and3.kelompok1.secondhand.common.convertDate
import binar.and3.kelompok1.secondhand.databinding.ActivityInfoPenawarBinding
import binar.and3.kelompok1.secondhand.ui.seller.infopenawar.berhasilterima.BerhasilTerimaBottomSheetFragment
import binar.and3.kelompok1.secondhand.ui.seller.infopenawar.perbaruistatus.PerbaruiStatusBottomSheetFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoPenawarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInfoPenawarBinding
    private val viewModel: InfoPenawarViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoPenawarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        window.statusBarColor = Color.WHITE

        val id = intent.extras!!.getInt("id")

        bindView()
        bindViewModel(id = id)
        viewModel.onViewLoaded(id)
    }

    private fun bindView() {
        binding.ivBtnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun bindViewModel(id: Int) {
        viewModel.shouldShowOrder.observe(this) {
            Glide.with(this)
                .load(it.user?.imageUrl)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(12)))
                .into(binding.ivAvatar)
            binding.tvName.text = it.user?.fullName
            binding.tvCity.text = it.user?.city

            // Order
            binding.tvDatetime.text = it.transactionDate?.let { it1 -> convertDate(it1) }
            binding.tvProductName.text = it.productName
            binding.tvBasePrice.text = "Rp ${it.basePrice}"
            binding.tvBidPrice.text = "Ditawar Rp ${it.price}"

            // Buttons
            if (it.status == "pending") {
                binding.btnLeft.text = "Tolak"
                binding.btnLeft.setOnClickListener {
                    viewModel.patchSellerOrderById(id = id, status = "declined")
                    onBackPressed()
                }
                binding.btnRight.text = "Terima"
                binding.btnRight.setOnClickListener {
                    viewModel.patchSellerOrderById(id = id, status = "accepted")
                }
            } else {
                binding.btnLeft.text = "Status"
                binding.btnLeft.setOnClickListener {
                    val bottomFragment = PerbaruiStatusBottomSheetFragment(productId = id)
                    bottomFragment.show(supportFragmentManager, "Tag")
                }
                binding.btnRight.text = "WA Buyer"
                binding.btnRight.setOnClickListener {

                }
            }
        }

        viewModel.shouldShowSuccess.observe(this) {
            viewModel.onViewLoaded(id = id)
            val bottomFragment = BerhasilTerimaBottomSheetFragment(
                productId = id
                )
            bottomFragment.show(supportFragmentManager, "Tag")

        }
        viewModel.shouldShowError.observe(this) {
            val snackbar = Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
            snackbar.view.setBackgroundColor(Color.RED)
            snackbar.show()
        }
    }
}