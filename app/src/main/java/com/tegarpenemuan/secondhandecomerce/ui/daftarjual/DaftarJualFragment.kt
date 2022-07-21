package com.tegarpenemuan.secondhandecomerce.ui.daftarjual

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tegarpenemuan.secondhandecomerce.*
import com.tegarpenemuan.secondhandecomerce.common.GetInisial.getInitial
import com.tegarpenemuan.secondhandecomerce.data.api.BuyerOrder.UpdateStatusOrder.UpdateStatusOrderRequest
import com.tegarpenemuan.secondhandecomerce.data.api.Product.GetProductResponse
import com.tegarpenemuan.secondhandecomerce.data.api.SellerOrder.SellerOrderResponseItem
import com.tegarpenemuan.secondhandecomerce.databinding.FragmentDaftarJualBinding
import com.tegarpenemuan.secondhandecomerce.ui.buyerOrder.BuyerOrderActivity
import com.tegarpenemuan.secondhandecomerce.ui.daftarjual.adapter.DaftarJualAdapter
import com.tegarpenemuan.secondhandecomerce.ui.daftarjual.adapter.SellerOrderAdapter
import com.tegarpenemuan.secondhandecomerce.ui.editproduct.EditProductFragment
import com.tegarpenemuan.secondhandecomerce.ui.profile.Profile
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DaftarJualFragment : Fragment() {

    private var _binding: FragmentDaftarJualBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DaftarJualViewModel by viewModels()
    private val bundleEdit = Bundle()

    lateinit var productSellerAdapter: DaftarJualAdapter
    lateinit var sellerOrderAdapter: SellerOrderAdapter
    var idOrder: Int = 0

    var nomorPembeli: String = ""
    var namaPembeli: String = ""
    var namaProduk: String = ""
    var hargaDitawarProduk: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDaftarJualBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.getProductSeller()
        viewModel. getUserData()
        //viewModel.getUser() //Room

        bindview()
        bindviewModel()
        return root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProductSeller()
    }

    private fun bindview() {
        binding.btnedit.setOnClickListener {
            startActivity(Intent(requireContext(), Profile::class.java))
        }

        productSellerAdapter =
            DaftarJualAdapter(listener = object : DaftarJualAdapter.EventListener {
                override fun onClick(item: GetProductResponse) {
                    bottomSheetClickProduct(item)
                }
            }, emptyList())
        binding.rvProduct.adapter = productSellerAdapter

        setupSellerOrderAdapter()
        binding.tvTitle.text = "Produk Saya"

        binding.btnDiminati.setOnClickListener {
            viewModel.getOrder("pending")
            binding.rvProduct.visibility = View.GONE
            binding.rvOrder.visibility = View.VISIBLE
            binding.tvTitle.text = "Produk Diminati"
        }
        binding.btnDitolak.setOnClickListener {
            viewModel.getOrder("declined")
            binding.rvProduct.visibility = View.GONE
            binding.rvOrder.visibility = View.VISIBLE
            binding.tvTitle.text = "Produk Ditolak"
        }
        binding.btnTerjual.setOnClickListener {
            viewModel.getOrder("accepted")
            binding.rvProduct.visibility = View.GONE
            binding.rvOrder.visibility = View.VISIBLE
            binding.tvTitle.text = "Produk Terjual"

        }
        binding.btnProduct.setOnClickListener {
            viewModel.getProductSeller()
            binding.rvProduct.visibility = View.VISIBLE
            binding.rvOrder.visibility = View.GONE
            binding.tvTitle.text = "Produk Saya"
        }

        binding.swipe.setOnRefreshListener {
            refreshData()
            binding.swipe.isRefreshing = false
        }
    }

    private fun setupSellerOrderAdapter() {
        sellerOrderAdapter =
            SellerOrderAdapter(listener = object : SellerOrderAdapter.EventListener {
                override fun onClick(item: SellerOrderResponseItem) {
                    idOrder = item.id
                    when (item.status) {
                        "accepted" -> bottomSheetHubPenjual()
                        else -> bottomSheetAccOrder()
                    }
                }
            }, emptyList())
        binding.rvOrder.adapter = sellerOrderAdapter
    }

    private fun bottomSheetClickProduct(item: GetProductResponse) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottom_sheet_click_product)

        val ivProduk = dialog.findViewById<ImageView>(R.id.iv_image_product)
        val tvCategory = dialog.findViewById<TextView>(R.id.tv_jenis_product)
        val tvNamaProduk = dialog.findViewById<TextView>(R.id.tv_nama_product)
        val tvHargaProduk = dialog.findViewById<TextView>(R.id.tv_harga_product)
        val btnUpdate = dialog.findViewById<CardView>(R.id.cvEdit)
        val btnDelete = dialog.findViewById<CardView>(R.id.cvDelete)

        Glide.with(requireContext())
            .load(item.image_url)
            .error(R.drawable.error)
            .placeholder(R.drawable.loading)
            .transform(RoundedCorners(20))
            .into(ivProduk)

        var listCategoryy :String? = ""
        if (item.Categories.isNotEmpty()) {
            for (data in item.Categories){
                listCategoryy += ", ${data.name}"
            }
            tvCategory.text = listCategoryy!!.drop(2)
        }
        tvNamaProduk.text = item.name
        tvHargaProduk.text = item.base_price.toString()

        btnUpdate.setOnClickListener {
            bundleEdit.apply {
                putInt("PRODUCT_ID", item.id)
                putString("PRODUCT_NAME", item.name)
                putInt("PRODUCT_PRICE", item.base_price)
                listCategory.clear()
                listCategoryId.clear()
                for (kategori in item.Categories){
                    listCategory.add(kategori.name)
                    listCategoryId.add(kategori.id)
                }
                putString("PRODUCT_DESCRIPTION",item.description)
                putString("PRODUCT_IMAGE",item.image_url)
                putString("PRODUCT_LOCATION",item.location)
            }
            findNavController().navigate(R.id.action_navigation_daftar_jual_to_editProductFragment, bundleEdit)
            dialog.dismiss()
        }
        btnDelete.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Pesan")
                .setMessage("Yakin ingin menghapus produk ${item.name}?")
                .setPositiveButton("Ya") { dialogP, _ ->
                    viewModel.deleteSellerProduct(item.id)
                    dialogP.dismiss()
                    onResume()
                    Toast.makeText(requireContext(), "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Batal") { dialogN, _ ->
                    dialogN.dismiss()
                    onResume()
                    Toast.makeText(requireContext(), "Data Batal Dihapus", Toast.LENGTH_SHORT).show()
                }
                .setCancelable(false)
                .show()

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

    private fun bottomSheetHubPenjual() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottom_sheet_hubungi_penawar)

        val ivPembeli = dialog.findViewById<ImageView>(R.id.iv_pembeli)
        val tvNamaPembeli = dialog.findViewById<TextView>(R.id.tv_nama_pembeli)
        val tvInisial = dialog.findViewById<TextView>(R.id.tvInisialHub)
        val tvKotaPembeli = dialog.findViewById<TextView>(R.id.tv_Kota_pembeli)

        val ivProduk = dialog.findViewById<ImageView>(R.id.iv_barang)
        val tvNamaProduk = dialog.findViewById<TextView>(R.id.tv_nama_barang)
        val tvHargaProduk = dialog.findViewById<TextView>(R.id.tv_harga_awal)
        val tvPenawaran = dialog.findViewById<TextView>(R.id.tv_harga_tawar)
        val btnHubungi = dialog.findViewById<Button>(R.id.btnHubungi)

        viewModel.getDetailOrder(idOrder)

        viewModel.shouldShowDetailNotif.observe(viewLifecycleOwner) {
            when (it.image_url) {
                null -> tvInisial.text = it.User.full_name.getInitial()
                else -> Glide.with(requireContext())
                    .load(it.User.image_url)
                    .transform(RoundedCorners(20))
                    .into(ivPembeli)
            }

            tvNamaPembeli.text = it.User.full_name
            tvKotaPembeli.text = it.User.city
            nomorPembeli = it.User.phone_number
            namaPembeli = it.User.full_name
            namaProduk = it.Product.name
            hargaDitawarProduk = it.price

            Glide.with(requireContext())
                .load(it.Product.image_url)
                .transform(RoundedCorners(20))
                .into(ivProduk)
            tvNamaProduk.text = it.Product.name
            tvHargaProduk.text = currency(it.Product.base_price)
            tvPenawaran.text = "Ditawar ${currency(it.price)}"
        }

        btnHubungi.setOnClickListener {
            val smilingFaceUnicode = 0x1F60A
            val waveUnicode = 0x1F44B
            val stringBuilder1 = StringBuilder()
            val stringBuilder2 = StringBuilder()
            val emoteSmile =
                stringBuilder1.append(Character.toChars(smilingFaceUnicode))
            val emoteWave = stringBuilder2.append(Character.toChars(waveUnicode))
            val phonenumberPenawar = "+62$nomorPembeli"
            val message =
                "Halo ${namaPembeli}${emoteWave} Tawaranmu pada product *$namaProduk* telah disetujui oleh penjual dengan harga *${
                    currency(hargaDitawarProduk)
                }*. Penjual akan menghubungimu secepatnya$emoteSmile"

            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(
                        String.format(
                            "https://api.whatsapp.com/send?phone=%s&text=%s",
                            phonenumberPenawar,
                            message
                        )
                    )
                )
            )
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

    private fun bottomSheetAccOrder() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottom_sheet_acc_produk)

        val ivPembeli = dialog.findViewById<ImageView>(R.id.ivPembeli)
        val tvNamaPembeli = dialog.findViewById<TextView>(R.id.namaPembeli)
        val tvKotaPembeli = dialog.findViewById<TextView>(R.id.tvKotaPembeli)

        val ivProduk = dialog.findViewById<ImageView>(R.id.img_product)
        val tvNamaProduk = dialog.findViewById<TextView>(R.id.tv_nama_product)
        val tvHargaProduk = dialog.findViewById<TextView>(R.id.tv_info_harga)
        val tvPenawaran = dialog.findViewById<TextView>(R.id.tv_info_tawar)
        val tvTime = dialog.findViewById<TextView>(R.id.tv_notif_time)
        val tvInisialAcc = dialog.findViewById<TextView>(R.id.tvInisialAcc)

        val btnTolak = dialog.findViewById<Button>(R.id.btnTolakProduct)
        val btnTerima = dialog.findViewById<Button>(R.id.btnTerimaProduct)

        viewModel.getDetailOrder(idOrder)

        viewModel.shouldShowDetailNotif.observe(viewLifecycleOwner) {
            when (it.image_url) {
                null -> tvInisialAcc.text = it.User.full_name.getInitial()
                else -> Glide.with(requireContext())
                    .load(it.User.image_url)
                    .transform(RoundedCorners(20))
                    .into(ivPembeli)
            }

            tvNamaPembeli.text = it.User.full_name
            tvKotaPembeli.text = it.User.city

            Glide.with(requireContext())
                .load(it.Product.image_url)
                .transform(RoundedCorners(20))
                .into(ivProduk)
            tvNamaProduk.text = it.Product.name
            tvHargaProduk.text = currency(it.Product.base_price)
            tvPenawaran.text = "Ditawar ${currency(it.price)}"
            tvTime.text = convertDate(it.createdAt)
        }

        btnTerima.setOnClickListener {
            viewModel.updateStatusOrder(idOrder, UpdateStatusOrderRequest("accepted"))
            refreshData()
            onResume()
            dialog.dismiss()
            bottomSheetHubPenjual()
        }
        btnTolak.setOnClickListener {
            viewModel.updateStatusOrder(idOrder, UpdateStatusOrderRequest("declined"))
            refreshData()
            onResume()
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

    private fun bindviewModel() {
        viewModel.shouldShowGetProductSeller.observe(viewLifecycleOwner) {
            productSellerAdapter.updateList(it)
        }
        viewModel.shouldShowGetSellerOrder.observe(viewLifecycleOwner) {
            sellerOrderAdapter.updateList(it)
        }

        viewModel.showUser.observe(viewLifecycleOwner) {
            if (it.image_url == null) {
                binding.tvInisial.text = it.full_name.getInitial()
            } else {
                Glide.with(requireContext())
                    .load(it.image_url)
                    .error(R.drawable.error)
                    .placeholder(R.drawable.loading)
                    .transform(RoundedCorners(20))
                    .into(binding.ivakun)
            }
            binding.tvnama.text = it.full_name
            binding.tvkota.text = it.city
        }
//Room
//        viewModel.shouldShowUser.observe(viewLifecycleOwner) {
//            if (it.image_url == null) {
//                binding.tvInisial.text = it.full_name.getInitial()
//            } else {
//                Glide.with(requireContext())
//                    .load(it.image_url)
//                    .error(R.drawable.ic_launcher_background)
//                    .transform(RoundedCorners(20))
//                    .into(binding.ivakun)
//            }
//            binding.tvnama.text = it.full_name
//            binding.tvkota.text = it.city
//        }
    }

    private fun refreshData() {
        when (binding.tvTitle.text.toString()) {
            "Produk Ditolak" -> viewModel.getOrder("declined")
            "Produk Terjual" -> viewModel.getOrder("accepted")
//            "Produk Saya" -> viewModel.getProductSeller()
            "Produk Diminati" -> viewModel.getOrder("pending")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}