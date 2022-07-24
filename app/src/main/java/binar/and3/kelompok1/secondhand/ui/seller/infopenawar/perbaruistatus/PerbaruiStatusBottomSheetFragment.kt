package binar.and3.kelompok1.secondhand.ui.seller.infopenawar.perbaruistatus

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBindings
import binar.and3.kelompok1.secondhand.databinding.FragmentPerbaruiStatusBottomSheetBinding
import binar.and3.kelompok1.secondhand.ui.seller.infopenawar.InfoPenawarViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PerbaruiStatusBottomSheetFragment(private val productId: Int) : BottomSheetDialogFragment() {


    private var _binding: FragmentPerbaruiStatusBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val viewModel: InfoPenawarViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerbaruiStatusBottomSheetBinding.inflate(inflater, container, false)


        bindView(productId = productId)
        bindViewModel()

        return binding.root
    }

    private fun bindView(productId: Int) {
        binding.btnKirim.setOnClickListener {
            val radioGroup = binding.rgStatus

            when (radioGroup.checkedRadioButtonId) {
                binding.rbAccepted.id -> {
                    Toast.makeText(requireContext(), "Yeay kamu berhasil mendapat harga yang sesuai", Toast.LENGTH_SHORT).show()
                    viewModel.patchSellerProductById(id = productId, status = "sold")
                }
                binding.rbDeclined.id -> {
                    Toast.makeText(requireContext(), "Kamu membatalkan transaksi", Toast.LENGTH_SHORT).show()
                    viewModel.patchSellerProductById(id = productId, status = "available")
                }
                else -> {
                    Toast.makeText(requireContext(), "Mohon pilih salah satu", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun bindViewModel() {
        viewModel.shouldOpenInfoPenawar.observe(viewLifecycleOwner) {
            dismiss()
        }
        viewModel.shouldShowError.observe(viewLifecycleOwner) {
            val snackbar = Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
            snackbar.view.setBackgroundColor(Color.RED)
            snackbar.show()
        }
    }

}