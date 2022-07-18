package com.tegarpenemuan.secondhandecomerce.ui.daftarjual

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.tegarpenemuan.secondhandecomerce.R
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
                    Toast.makeText(requireContext(), item.product_name, Toast.LENGTH_SHORT).show()
                }
            }, emptyList())
        binding.rvOrder.adapter = sellerOrderAdapter

        binding.btnDiminati.setOnClickListener {
            viewModel.getOrder("pending")
            binding.rvProduct.visibility = View.GONE
            binding.rvOrder.visibility = View.VISIBLE
        }
        binding.btnDitolak.setOnClickListener {
            viewModel.getOrder("declined")
            binding.rvProduct.visibility = View.GONE
            binding.rvOrder.visibility = View.VISIBLE
        }
        binding.btnTerjual.setOnClickListener {
            viewModel.getOrder("accepted")
            binding.rvProduct.visibility = View.GONE
            binding.rvOrder.visibility = View.VISIBLE
        }
        binding.btnProduct.setOnClickListener {
            viewModel.getProductSeller()
            binding.rvProduct.visibility = View.VISIBLE
            binding.rvOrder.visibility = View.GONE
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