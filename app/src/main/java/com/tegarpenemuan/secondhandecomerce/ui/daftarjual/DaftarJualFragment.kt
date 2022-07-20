package com.tegarpenemuan.secondhandecomerce.ui.daftarjual

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tegarpenemuan.secondhandecomerce.R
import com.tegarpenemuan.secondhandecomerce.convertDate
import com.tegarpenemuan.secondhandecomerce.currency
import com.tegarpenemuan.secondhandecomerce.data.api.BuyerOrder.UpdateStatusOrder.UpdateStatusOrderRequest
import com.tegarpenemuan.secondhandecomerce.data.api.Product.GetProductResponse
import com.tegarpenemuan.secondhandecomerce.data.api.SellerOrder.SellerOrderResponseItem
import com.tegarpenemuan.secondhandecomerce.databinding.FragmentDaftarJualBinding
import com.tegarpenemuan.secondhandecomerce.ui.daftarjual.adapter.DaftarJualAdapter
import com.tegarpenemuan.secondhandecomerce.ui.daftarjual.adapter.SellerOrderAdapter
import com.tegarpenemuan.secondhandecomerce.ui.profile.Profile
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DaftarJualFragment : Fragment() {

    private var _binding: FragmentDaftarJualBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DaftarJualViewModel by viewModels()

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
        viewModel.getUser()

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
                    Toast.makeText(requireContext(), item.image_name, Toast.LENGTH_SHORT).show()
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
//            hubungiAdapter()
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

    private fun bottomSheetHubPenjual() {
        val dialog = BottomSheetDialog(requireContext())
        val view =
            layoutInflater.inflate(R.layout.bottom_sheet_hubungi_penawar, null)

        val ivPembeli = view.findViewById<ImageView>(R.id.iv_pembeli)
        val tvNamaPembeli = view.findViewById<TextView>(R.id.tv_nama_pembeli)
        val tvKotaPembeli = view.findViewById<TextView>(R.id.tv_Kota_pembeli)

        val ivProduk = view.findViewById<ImageView>(R.id.iv_barang)
        val tvNamaProduk = view.findViewById<TextView>(R.id.tv_nama_barang)
        val tvHargaProduk = view.findViewById<TextView>(R.id.tv_harga_awal)
        val tvPenawaran = view.findViewById<TextView>(R.id.tv_harga_tawar)
        val tvTime = view.findViewById<TextView>(R.id.tv_notif_time)

        val btnHubungi = view.findViewById<Button>(R.id.btnHubungi)

        viewModel.getDetailOrder(idOrder)

        viewModel.shouldShowDetailNotif.observe(viewLifecycleOwner) {

            Glide.with(requireContext())
                .load(it.User.image_url)
                .transform(RoundedCorners(20))
                .into(ivPembeli)
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
            //viewModel.updateStatusOrder(idOrder, UpdateStatusOrderRequest("accepted"))
            //dialog.dismiss()

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

        dialog.setContentView(view)
        dialog.show()
    }

    private fun bottomSheetAccOrder() {
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.bottom_sheet_acc_produk, null)

        val ivPembeli = view.findViewById<ImageView>(R.id.ivPembeli)
        val tvNamaPembeli = view.findViewById<TextView>(R.id.namaPembeli)
        val tvKotaPembeli = view.findViewById<TextView>(R.id.tvKotaPembeli)

        val ivProduk = view.findViewById<ImageView>(R.id.img_product)
        val tvNamaProduk = view.findViewById<TextView>(R.id.tv_nama_product)
        val tvHargaProduk = view.findViewById<TextView>(R.id.tv_info_harga)
        val tvPenawaran = view.findViewById<TextView>(R.id.tv_info_tawar)
        val tvTime = view.findViewById<TextView>(R.id.tv_notif_time)

        val btnTolak = view.findViewById<Button>(R.id.btnTolakProduct)
        val btnTerima = view.findViewById<Button>(R.id.btnTerimaProduct)

        viewModel.getDetailOrder(idOrder)

        viewModel.shouldShowDetailNotif.observe(viewLifecycleOwner) {

            Glide.with(requireContext())
                .load(it.User.image_url)
                .transform(RoundedCorners(20))
                .into(ivPembeli)
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
        dialog.setContentView(view)
        dialog.show()
    }

    private fun bindviewModel() {
        viewModel.shouldShowGetProductSeller.observe(viewLifecycleOwner) {
            productSellerAdapter.updateList(it)
        }
        viewModel.shouldShowGetSellerOrder.observe(viewLifecycleOwner) {
            sellerOrderAdapter.updateList(it)
        }
        viewModel.shouldShowUser.observe(viewLifecycleOwner) {
            Glide.with(requireContext())
                .load(it.image_url)
                .error(R.drawable.ic_launcher_background)
                .transform(RoundedCorners(20))
                .into(binding.ivakun)
            binding.tvnama.text = it.full_name
            binding.tvkota.text = it.city
        }
    }

    private fun refreshData() {
        when (binding.tvTitle.text.toString()) {
            "Produk Ditolak" -> viewModel.getOrder("declined")
            "Produk Terjual" -> viewModel.getOrder("accepted")
            "Produk Saya" -> viewModel.getProductSeller()
            "Produk Diminati" -> viewModel.getOrder("pending")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}