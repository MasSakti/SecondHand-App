package binar.and3.kelompok1.secondhand.ui.menu.notifikasi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import binar.and3.kelompok1.secondhand.databinding.FragmentNotifikasiBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotifikasiFragment : Fragment() {

    private var _binding: FragmentNotifikasiBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NotifikasiViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNotifikasiBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}