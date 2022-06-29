package com.tegarpenemuan.secondhandecomerce.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tegarpenemuan.secondhandecomerce.data.api.category.GetCategoryResponseItem
import com.tegarpenemuan.secondhandecomerce.data.api.getProduct.GetProductResponse
import com.tegarpenemuan.secondhandecomerce.databinding.FragmentHomeBinding
import com.tegarpenemuan.secondhandecomerce.ui.buyer6.Buyer6Activity
import com.tegarpenemuan.secondhandecomerce.ui.home.adapter.CategoryAdapter
import com.tegarpenemuan.secondhandecomerce.ui.home.adapter.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    lateinit var homeAdapter: ProductAdapter
    lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.getProduct()
        viewModel.getCategory()

        bindview()
        bindviewModel()

        return root
    }

    private fun bindviewModel() {
        viewModel.shouldShowGetProduct.observe(viewLifecycleOwner) {
            //Log.d("TAG", "product:$it")
            homeAdapter.updateList(it)
        }

        viewModel.shouldShowGetCategory.observe(viewLifecycleOwner) {
            //Log.d("TAG", "category:$it")
            categoryAdapter.updateList(it)
        }
    }

    private fun bindview() {
        homeAdapter =
            ProductAdapter(listener = object : ProductAdapter.EventListener {
                override fun onClick(item: GetProductResponse) {
//                    Toast.makeText(requireContext(),item.name,Toast.LENGTH_SHORT).show()
                    val intent = Intent(requireContext(), Buyer6Activity::class.java)
                    intent.putExtra("id", item.id)
                    startActivity(intent)
                }

            }, emptyList())
        binding.rvProduct.adapter = homeAdapter

        categoryAdapter =
            CategoryAdapter(listener = object : CategoryAdapter.EventListener {
                override fun onClick(item: GetCategoryResponseItem) {
                    //Toast.makeText(requireContext(),item.name,Toast.LENGTH_SHORT).show()
                    //nanti masuk ke search
                    viewModel.getProduct(category_id = item.id)

                }
            }, emptyList())
        binding.rvCategory.adapter = categoryAdapter

        binding.etSearch.setOnEditorActionListener { textView, actionId, keyEvent ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                val keyword = binding.etSearch.text.toString()
                viewModel.getProduct(search = keyword)
                true
            } else {
                false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}