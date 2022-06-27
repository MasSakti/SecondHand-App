package binar.and3.kelompok1.secondhand.ui.menu.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import binar.and3.kelompok1.secondhand.data.api.buyer.BuyerProduct
import binar.and3.kelompok1.secondhand.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    lateinit var homeProductAdapter: HomeProductAdapter
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    val viewModel: HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        homeProductAdapter =
            HomeProductAdapter(listener = object : HomeProductAdapter.EventListener {
                override fun onClick(item: BuyerProduct.BuyerProductResponse) {
                    print("Hello")
                }
            }, emptyList())

        binding.rvItem.adapter = homeProductAdapter

        viewModel.onViewLoaded()
        bindViewModel()

        return binding.root
    }

    private fun bindViewModel() {
        viewModel.shouldShowBuyerProduct.observe(requireActivity()) {
//            homeProductAdapter.updateBuyerProduct(it)
//            homeProductAdapter.updateCategories(it.categories)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}