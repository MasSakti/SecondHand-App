package com.tegarpenemuan.secondhandecomerce.ui.buyerOrder

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.tegarpenemuan.secondhandecomerce.R
import com.tegarpenemuan.secondhandecomerce.currency
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
        val orderId = intent.getIntExtra("id", 0)
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
                .error(R.drawable.error)
                .placeholder(R.drawable.loading)
                .transform(RoundedCorners(20))
                .into(binding.ivImageProduct)
            binding.tvNamaproduct.text = produk
            binding.tvkota.text = it.User.city
            binding.tvNama.text = it.User.full_name
            binding.tvDeskripsi.text = it.description
            binding.tvHarga.text = currency(it.base_price)

            Glide.with(this)
                .load(it.User.image_url)
                .error(R.drawable.error)
                .placeholder(R.drawable.loading)
                .transform(RoundedCorners(20))
                .into(binding.ivakun)

            var listCategory: String? = ""
            if (it.Categories.isNotEmpty()) {
                for (data in it.Categories) {
                    listCategory += ", ${data.name}"
                }
                binding.tvKategori.text = listCategory!!.drop(2)
            }
        }

        viewModel.shouldShowResponsBid.observe(this) {
            Toast.makeText(this, "Berhasil Bid ${it.product_name}", Toast.LENGTH_SHORT).show()
        }

        viewModel.shouldShowResponsError.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun bindview() {
        binding.btnTertarik.setOnClickListener {
            bottomSheetNego()
        }

        binding.cvBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun bottomSheetNego() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottom_sheet_dialog_nego)

        val imgView = dialog.findViewById<ImageView>(R.id.img_view)
        val tvBarang = dialog.findViewById<TextView>(R.id.tvBarang)
        val tvHarga = dialog.findViewById<TextView>(R.id.tvHarga)
        val etBid = dialog.findViewById<EditText>(R.id.txt_input_bid_harga)
        val btnClose = dialog.findViewById<Button>(R.id.btn_submit)

        Glide.with(this)
            .load(imageUrl)
            .transform(RoundedCorners(20))
            .into(imgView)
        tvBarang.text = produk
        tvHarga.text = harga

        btnClose.setOnClickListener {
            if (etBid.text.isEmpty()) {
                etBid.error = "Harga Penawaran Tidak Boleh Kosong"
                etBid.requestFocus()
                return@setOnClickListener
            } else {
                viewModel.createOrder(
                    product_id = produkID.toInt(),
                    bid_price = etBid.text.toString().toInt()
                )
            }

            dialog.dismiss()
        }

        dialog.show()
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialog.window!!.setGravity(Gravity.BOTTOM)
    }
}
