package binar.and3.kelompok1.secondhand.ui.menu.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import binar.and3.kelompok1.secondhand.R
import binar.and3.kelompok1.secondhand.data.api.buyer.BuyerProductResponse
import binar.and3.kelompok1.secondhand.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    lateinit var homeProductAdapter: HomeProductAdapter
    val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.statusBarColor = Color.WHITE
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeProductAdapter =
            HomeProductAdapter(listener = object : HomeProductAdapter.EventListener {
                override fun onClick(item: BuyerProductResponse) {
                    print("Hello")
                }
            }, emptyList())

        binding.rvItem.adapter = homeProductAdapter

        viewModel.onViewLoaded()
        bindViewModel()

        return root
    }

    private fun bindViewModel() {
        viewModel.shouldShowBuyerProduct.observe(requireActivity()) {
//           homeProductAdapter.updateBuyerProduct(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}