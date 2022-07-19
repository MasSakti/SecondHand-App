package com.tegarpenemuan.secondhandecomerce.ui.bottomsheet

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.tegarpenemuan.secondhandecomerce.databinding.BottomSheetHubungiPenawarBinding

class HubungiPenawarFragment : Fragment() {
    private var _binding: BottomSheetHubungiPenawarBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetHubungiPenawarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.btnHubungi.setOnClickListener{
//            Toast.makeText(requireContext(), "Hubungi Wa", Toast.LENGTH_SHORT).show()
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}