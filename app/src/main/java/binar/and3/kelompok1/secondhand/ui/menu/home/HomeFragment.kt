package binar.and3.kelompok1.secondhand.ui.menu.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import binar.and3.kelompok1.secondhand.data.api.buyer.BuyerProductResponse
import binar.and3.kelompok1.secondhand.data.api.seller.GetSellerCategoryResponse
import binar.and3.kelompok1.secondhand.databinding.FragmentHomeBinding
import binar.and3.kelompok1.secondhand.ui.menu.home.adapter.HomeCategoryButtonAdapter
import binar.and3.kelompok1.secondhand.ui.menu.home.adapter.product.HomeProductAdapter
import binar.and3.kelompok1.secondhand.ui.seller.ProductPreviewActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    companion object {
        var result = 0
        const val PRODUCT_ID = "id"
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    lateinit var homeProductAdapter: HomeProductAdapter
    lateinit var homeCategoryButtonAdapter: HomeCategoryButtonAdapter

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
                    val intent = Intent(activity, ProductPreviewActivity::class.java)
                    val bundle = Bundle()
                    item.id?.let { bundle.putInt(PRODUCT_ID, it) }
                    intent.putExtras(bundle)
                    startActivity(intent)
                }
            }, emptyList())

        homeCategoryButtonAdapter =
            HomeCategoryButtonAdapter(listener = object : HomeCategoryButtonAdapter.EventListener {
                override fun onClick(item: GetSellerCategoryResponse) {
                    item.id?.let { viewModel.getBuyerProductByCategory(categoryId = it) }
                }
            }, emptyList())

        binding.rvItem.adapter = homeProductAdapter
        binding.rvItem.isNestedScrollingEnabled = false
        binding.rvCategories.adapter = homeCategoryButtonAdapter

        viewModel.onViewLoaded()
        bindViewModel()

        return root
    }

    private fun bindViewModel() {
        viewModel.shouldShowBuyerProductByCategory.observe(viewLifecycleOwner) {
            homeProductAdapter.submitData(it)
        }
        viewModel.tempShouldShowCategory.observe(viewLifecycleOwner) {
            homeCategoryButtonAdapter.submitData(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}