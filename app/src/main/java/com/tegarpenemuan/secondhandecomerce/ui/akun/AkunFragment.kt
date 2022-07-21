package com.tegarpenemuan.secondhandecomerce.ui.akun

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.tegarpenemuan.secondhandecomerce.R
import com.tegarpenemuan.secondhandecomerce.common.GetInisial.getInitial
import com.tegarpenemuan.secondhandecomerce.databinding.FragmentAkunBinding
import com.tegarpenemuan.secondhandecomerce.showToastSuccess
import com.tegarpenemuan.secondhandecomerce.ui.login.Login
import com.tegarpenemuan.secondhandecomerce.ui.profile.Profile
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AkunFragment : Fragment() {

    private var _binding: FragmentAkunBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AkunViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAkunBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.getProfile()

        bindview()
        bindviewModel()

        return root
    }

    private fun bindviewModel() {
        viewModel.shouldShowProfile.observe(viewLifecycleOwner) {
            if (it.image_url == null) {
                binding.tvInisial.text = it.full_name.getInitial()
            } else {
                Glide.with(requireContext())
                    .load(it.image_url)
                    .error(R.drawable.error)
                    .placeholder(R.drawable.loading)
                    .circleCrop()
                    .into(binding.ivProfile)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProfile()
    }

    private fun bindview() {
        binding.ivProfile.setOnClickListener {
            startActivity(Intent(requireContext(), Profile::class.java))
        }

        binding.tvKeluar.setOnClickListener {
            val dialog = Dialog(requireContext())
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.setContentView(R.layout.layout_validasi_logout)

            dialog.setCancelable(false)
            val title = dialog.findViewById(R.id.tvTitleAddDialog) as TextView
            title.text = "Yakin Akan Logout ?"

            val btnYes = dialog.findViewById(R.id.btnIya) as Button
            btnYes.setOnClickListener {
                viewModel.logout()
                startActivity(Intent(requireContext(), Login::class.java))
                dialog.dismiss()
            }
            val btnNo = dialog.findViewById(R.id.btnTidak) as Button
            btnNo.setOnClickListener {
                dialog.dismiss()
                showToastSuccess(requireView(), "Batal Logout", R.color.success)
            }
            dialog.show()
            activity!!.finish()
        }
        binding.tvPengaturan.setOnClickListener {
            Toast.makeText(requireContext(), "Setting", Toast.LENGTH_SHORT).show()
        }
        binding.tvUbahAkun.setOnClickListener {
            startActivity(Intent(requireContext(), Profile::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}