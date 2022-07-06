package com.example.projectgroup2.ui.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.projectgroup2.data.api.main.buyer.product.GetProductResponse
import com.example.projectgroup2.data.api.main.buyer.product.ProductResponseItem
import com.example.projectgroup2.data.api.main.category.CategoryResponse
import com.example.projectgroup2.databinding.FragmentHomeBinding
import com.example.projectgroup2.ui.main.home.adapter.CategoryAdapter
import com.example.projectgroup2.ui.main.home.adapter.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        bindAdapterAndItem()

        val status = "available"
        val categoryId = ""
        val search = ""
        viewModel.getProductBuyer(status = status, categoryId = categoryId, search = search)
        viewModel.getCategory()
    }

    private fun bindViewModel() {
        viewModel.showShimmerProduct.observe(viewLifecycleOwner){
            if(it){
                binding.shimerProduct.visibility = View.VISIBLE
            }else{
                binding.shimerProduct.visibility = View.GONE
            }
        }

        viewModel.showShimmerCategory.observe(viewLifecycleOwner){
            if(it){
                binding.shimerCategory.visibility = View.VISIBLE
            }else{
                binding.shimerCategory.visibility = View.GONE
            }
        }

        viewModel.showEmpty.observe(viewLifecycleOwner){
            if(it){
                binding.lottieEmpty.visibility = View.VISIBLE
                binding.tvEmpty.visibility = View.VISIBLE
            }else{
                binding.lottieEmpty.visibility = View.GONE
                binding.tvEmpty.visibility = View.GONE
            }
        }

        viewModel.showRv.observe(viewLifecycleOwner){
            if(it){
                binding.rvProductHome.visibility = View.VISIBLE
            }else{
                binding.rvProductHome.visibility = View.GONE
            }
        }

        viewModel.showProductBuyer.observe(viewLifecycleOwner){
            productAdapter.submitData(it)
        }

        viewModel.showCategory.observe(viewLifecycleOwner){
            categoryAdapter.submitData(it)
        }
    }

    private fun bindAdapterAndItem(){
        productAdapter = ProductAdapter(object: ProductAdapter.OnClickListener{
            override fun onClickItem(data: GetProductResponse) {
                Toast.makeText(requireContext(),"click", Toast.LENGTH_SHORT).show()

            }
        })
        binding.rvProductHome.layoutManager =
            GridLayoutManager(requireContext(), 2)
        binding.rvProductHome.isNestedScrollingEnabled = false
        binding.rvProductHome.adapter = productAdapter


        categoryAdapter = CategoryAdapter(object: CategoryAdapter.OnClickListener{
            override fun onClickItem(data: CategoryResponse) {
                val status = "available"
                val search = ""
                viewModel.getProductBuyer(categoryId = data.id.toString(), status = status, search = search)
            }
        })
        binding.rvCategoryHome.adapter = categoryAdapter

        binding.etSearchProduct.setOnEditorActionListener { textView, i, keyEvent ->
            if(i == EditorInfo.IME_ACTION_SEARCH){
                val onSearch = binding.etSearchProduct.text.toString()
                viewModel.getProductBuyer(search = onSearch, status = "available", categoryId = "")
                true
            }else{
                false
            }
        }
    }


}