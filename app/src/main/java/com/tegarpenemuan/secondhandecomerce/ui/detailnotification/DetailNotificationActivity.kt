package com.tegarpenemuan.secondhandecomerce.ui.detailnotification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.tegarpenemuan.secondhandecomerce.R
import com.tegarpenemuan.secondhandecomerce.currency
import com.tegarpenemuan.secondhandecomerce.databinding.ActivityDetailNotificationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailNotificationActivity : AppCompatActivity() {

    var id: String? = null
    private lateinit var binding: ActivityDetailNotificationBinding
    private val viewModel: DetailNotificationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        id = intent.getStringExtra("id")
        viewModel.getNotification(id!!.toInt())

        bindviewModel()
    }

    private fun bindviewModel() {
        viewModel.shouldShowDetailNotif.observe(this) {
            binding.tvNamaproduct.text = it.product_name
            binding.tvDeskripsi.text = it.Product.description
            binding.tvHarga.text = currency(it.base_price.toInt())
            binding.tvNama.text = it.User.full_name
            binding.tvkota.text = it.User.city

            Glide.with(this)
                .load(it.image_url)
                .error(R.drawable.ic_launcher_background)
                .into(binding.ivImageProduct)

            Glide.with(this)
                .load(it.User.image_url)
                .error(R.drawable.ic_launcher_background)
                .into(binding.ivakun)
        }
    }
}