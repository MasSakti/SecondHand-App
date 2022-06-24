package com.tegarpenemuan.secondhandecomerce.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tegarpenemuan.secondhandecomerce.data.api.getProduct.GetProductResponse
import com.tegarpenemuan.secondhandecomerce.databinding.FragmentHomeBinding
import com.tegarpenemuan.secondhandecomerce.ui.home.adapter.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    lateinit var homeAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.getProduct()

        bindview()
        bindviewModel()

        return root
    }

    private fun bindviewModel() {
        viewModel.shouldShowGetProduct.observe(viewLifecycleOwner) {
            //Log.d("TAG", "product:$it")
            homeAdapter.updateList(it)
        }
    }

    private fun bindview() {
        homeAdapter =
            ProductAdapter(listener = object : ProductAdapter.EventListener {
                override fun onClick(item: GetProductResponse) {
                    Toast.makeText(requireContext(),item.name,Toast.LENGTH_SHORT).show()
                }

            }, emptyList())
        binding.rvProduct.adapter = homeAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}