package com.tegarpenemuan.secondhandecomerce.ui.akun

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.tegarpenemuan.secondhandecomerce.databinding.FragmentAkunBinding
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
            Glide.with(requireContext())
                .load(it)
                .circleCrop()
                .into(binding.ivProfile)

        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProfile()
    }

    private fun bindview() {
        binding.tvKeluar.setOnClickListener {
            viewModel.logout()
            startActivity(Intent(requireContext(), Login::class.java))
        }
        binding.tvPengaturan.setOnClickListener {
            Toast.makeText(requireContext(),"Setting",Toast.LENGTH_SHORT).show()
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