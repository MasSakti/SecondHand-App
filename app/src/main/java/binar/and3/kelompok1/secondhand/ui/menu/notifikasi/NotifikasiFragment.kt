package binar.and3.kelompok1.secondhand.ui.menu.notifikasi

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import binar.and3.kelompok1.secondhand.data.api.getNotification.GetNotifResponseItem
import binar.and3.kelompok1.secondhand.databinding.FragmentNotifikasiBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotifikasiFragment : Fragment() {

    private var _binding: FragmentNotifikasiBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NotifikasiViewModel by viewModels()

    lateinit var notificationsAdapter: NotificationsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.statusBarColor = Color.WHITE
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotifikasiBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.getNotification()
        bindview()
        bindviewModel()
        return root
    }

    private fun bindview() {
        notificationsAdapter =
            NotificationsAdapter(listener = object : NotificationsAdapter.EventListener {
                override fun onClick(item: GetNotifResponseItem) {
                    
                }

            }, emptyList())
        binding.rvNotifikasi.adapter = notificationsAdapter
    }

    private fun bindviewModel() {
        viewModel.shouldShowGetNotification.observe(viewLifecycleOwner){
            notificationsAdapter.updateList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}