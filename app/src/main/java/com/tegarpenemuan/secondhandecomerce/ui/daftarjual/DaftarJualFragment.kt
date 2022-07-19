package com.tegarpenemuan.secondhandecomerce.ui.daftarjual

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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DaftarJualFragment : Fragment() {

    private var _binding: FragmentDaftarJualBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DaftarJualViewModel by viewModels()

    lateinit var productSellerAdapter: DaftarJualAdapter
    lateinit var sellerOrderAdapter: SellerOrderAdapter
    var idOrder = 0

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

    private fun bindview() {
        productSellerAdapter =
            DaftarJualAdapter(listener = object : DaftarJualAdapter.EventListener {
                override fun onClick(item: GetProductResponse) {
                    Toast.makeText(requireContext(), item.image_name, Toast.LENGTH_SHORT).show()
                }

            }, emptyList())
        binding.rvProduct.adapter = productSellerAdapter

        sellerOrderAdapter =
            SellerOrderAdapter(listener = object : SellerOrderAdapter.EventListener {
                override fun onClick(item: SellerOrderResponseItem) {
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

                    idOrder = item.id
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
                        dialog.dismiss()
                    }
                    btnTolak.setOnClickListener {
                        viewModel.updateStatusOrder(idOrder, UpdateStatusOrderRequest("declined"))
                        dialog.dismiss()
                    }
                    dialog.setContentView(view)
                    dialog.show()
                }
            }, emptyList())
        binding.rvOrder.adapter = sellerOrderAdapter
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
            binding.tvTitle.text = "Produk Diterima"
        }
        binding.btnProduct.setOnClickListener {
            viewModel.getProductSeller()
            binding.rvProduct.visibility = View.VISIBLE
            binding.rvOrder.visibility = View.GONE
            binding.tvTitle.text = "Produk Saya"
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}