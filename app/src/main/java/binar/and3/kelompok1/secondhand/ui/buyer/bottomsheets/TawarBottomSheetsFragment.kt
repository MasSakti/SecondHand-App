package binar.and3.kelompok1.secondhand.ui.buyer.bottomsheets

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import binar.and3.kelompok1.secondhand.R
import binar.and3.kelompok1.secondhand.common.currency
import binar.and3.kelompok1.secondhand.databinding.FragmentCategoryBottomSheetsBinding
import binar.and3.kelompok1.secondhand.databinding.FragmentTawarBottomSheetsBinding
import binar.and3.kelompok1.secondhand.ui.buyer.ProductDetailViewModel
import binar.and3.kelompok1.secondhand.ui.menu.home.HomeFragment
import binar.and3.kelompok1.secondhand.ui.menu.jual.JualViewModel
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TawarBottomSheetsFragment(
    productId: Int,
    // private val update: () -> Unit
) : BottomSheetDialogFragment() {
    private val productId = productId

    private var _binding : FragmentTawarBottomSheetsBinding? = null
    private val binding get() = _binding!!
    private val productViewModel: ProductDetailViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTawarBottomSheetsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        productViewModel.getProductById(id = productId)

        bindView()
        bindViewModel()

        return binding.root
    }

    private fun bindView() {
        binding.etHargaTawar.doAfterTextChanged {
            productViewModel.onChangeBidPrice(it.toString())
        }
        binding.btnKirim.setOnClickListener {
            productViewModel.onValidate(productId = productId)
        }
    }

    private fun bindViewModel() {
        productViewModel.shouldShowProduct.observe(viewLifecycleOwner) {
            binding.tvProductName.text = it.name
            binding.tvProductBasePrice.text = it.basePrice?.let { it1 -> currency(it1) }
            Glide.with(binding.root)
                .load(it.imageUrl)
                .into(binding.ivProductImage)
        }
        productViewModel.shouldShowError.observe(viewLifecycleOwner) {
            val snackbar = Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
            snackbar.view.setBackgroundColor(Color.RED)
            snackbar.show()
        }
        productViewModel.shouldOpenHome.observe(viewLifecycleOwner) {
            dismiss()
        }
        productViewModel.shouldShowSuccessBiding.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }
    }

}