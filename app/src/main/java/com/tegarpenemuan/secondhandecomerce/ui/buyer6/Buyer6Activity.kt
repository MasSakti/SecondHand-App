package com.tegarpenemuan.secondhandecomerce.ui.buyer6

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.tegarpenemuan.secondhandecomerce.databinding.ActivityBuyer6Binding
import com.tegarpenemuan.secondhandecomerce.ui.main.MainActivity

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Buyer6Activity : AppCompatActivity() {
    private lateinit var binding: ActivityBuyer6Binding
    private val viewModel: Buyer6ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyer6Binding.inflate(layoutInflater)
        setContentView(binding.root)
        var orderId = intent.getIntExtra("id",0)
        viewModel.getOrderId(orderId)
        bindview()
        bindviewModel()
        viewModel.getProductDetails()
    }

    private fun bindviewModel() {
        viewModel.shouldShowGetProductDetails.observe(this){
            Glide.with(this)
                .load(it.image_url)
                .transform(RoundedCorners(20))
                .into(binding.ivImageProduct)
            binding.tvNamaproduct.text = it.name
            binding.tvkota.text = it.location
            binding.tvDeskripsi.text = it.description
        }
    }

    private fun bindview() {
        binding.cvBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
