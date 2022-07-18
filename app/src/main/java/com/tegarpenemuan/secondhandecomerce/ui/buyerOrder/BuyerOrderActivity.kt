package com.tegarpenemuan.secondhandecomerce.ui.buyerOrder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tegarpenemuan.secondhandecomerce.R
import com.tegarpenemuan.secondhandecomerce.databinding.ActivityBuyerOrderBinding
import com.tegarpenemuan.secondhandecomerce.ui.main.MainActivity

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BuyerOrderActivity : AppCompatActivity() {

    var imageUrl = ""
    var produk = ""
    var produkID = ""
    var harga = ""
    private lateinit var binding: ActivityBuyerOrderBinding
    private val viewModel: BuyerOrderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyerOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var orderId = intent.getIntExtra("id", 0)
        viewModel.getOrderId(orderId)
        bindview()
        bindviewModel()
        viewModel.getProductDetails()
    }

    private fun bindviewModel() {
        viewModel.shouldShowGetProductDetails.observe(this) {
            imageUrl = it.image_url
            produk = it.name
            harga = it.base_price.toString()
            produkID = it.id.toString()

            Glide.with(this)
                .load(imageUrl)
                .transform(RoundedCorners(20))
                .into(binding.ivImageProduct)
            binding.tvNamaproduct.text = produk
            binding.tvkota.text = it.location
            binding.tvDeskripsi.text = it.description
            binding.tvHarga.text = harga
        }

        viewModel.shouldShowResponsBid.observe(this) {
            Toast.makeText(this,"Berhasil Bid ${it.product_name}",Toast.LENGTH_SHORT).show()
        }

        viewModel.shouldShowResponsError.observe(this) {
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
        }
    }

    private fun bindview() {
        binding.btnTertarik.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog_nego, null)

            val imgView = view.findViewById<ImageView>(R.id.img_view)
            val tvBarang = view.findViewById<TextView>(R.id.tvBarang)
            val tvHarga = view.findViewById<TextView>(R.id.tvHarga)
            val etBid = view.findViewById<EditText>(R.id.txt_input_bid_harga)
            val btnClose = view.findViewById<Button>(R.id.btn_submit)

            Glide.with(this)
                .load(imageUrl)
                .transform(RoundedCorners(20))
                .into(imgView)
            tvBarang.text = produk
            tvHarga.text = harga

            btnClose.setOnClickListener {
                viewModel.createOrder(
                    product_id = produkID.toInt(),
                    bid_price = etBid.text.toString().toInt()
                )
                dialog.dismiss()
            }

            dialog.setContentView(view)
            dialog.show()
        }

        binding.cvBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
