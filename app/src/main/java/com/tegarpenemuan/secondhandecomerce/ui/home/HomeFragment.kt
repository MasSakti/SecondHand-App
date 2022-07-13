package com.tegarpenemuan.secondhandecomerce.ui.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tegarpenemuan.secondhandecomerce.data.api.category.GetCategoryResponseItem
import com.tegarpenemuan.secondhandecomerce.data.api.Product.GetProductResponse
import com.tegarpenemuan.secondhandecomerce.databinding.FragmentHomeBinding
import com.tegarpenemuan.secondhandecomerce.ui.buyer6.Buyer6Activity
import com.tegarpenemuan.secondhandecomerce.ui.home.adapter.CategoryAdapter
import com.tegarpenemuan.secondhandecomerce.ui.home.adapter.ImageSliderAdapter
import com.tegarpenemuan.secondhandecomerce.ui.home.adapter.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.getSystemService
import com.tegarpenemuan.secondhandecomerce.common.ChangeCurrency


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    lateinit var homeAdapter: ProductAdapter
    lateinit var categoryAdapter: CategoryAdapter
    private lateinit var adapter: ImageSliderAdapter
    private lateinit var dots: ArrayList<TextView>
    private var size = 0

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.getProduct()
        viewModel.getCategory()
        viewModel.getBanner()

        bindview()
        bindviewModel()

        handler = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            var index = 0
            override fun run() {
                if (index == size)
                    index = 0
                Log.e("Runnable,", "$index")
                binding.vpBanner.currentItem = index
                index++
                handler.postDelayed(this, 5000)
            }
        }

        return root
    }

    private fun bindviewModel() {
        viewModel.shouldShowGetProduct.observe(viewLifecycleOwner) {
            homeAdapter.updateList(it)
        }

        viewModel.shouldShowGetCategory.observe(viewLifecycleOwner) {
            categoryAdapter.updateList(it)
        }

        binding.progressbar1.visibility = View.VISIBLE
        viewModel.shouldShowGetProduct.observe(viewLifecycleOwner) {
            binding.progressbar1.visibility = View.GONE
            homeAdapter.updateList(it)
        }

        binding.progressbar.visibility = View.VISIBLE
        viewModel.shouldShowGetCategory.observe(viewLifecycleOwner) {
            binding.progressbar.visibility = View.GONE
            categoryAdapter.updateList(it)
        }

        viewModel.shouldShowBanner.observe(viewLifecycleOwner) {
            adapter = ImageSliderAdapter(it)
            size = it.size
            binding.vpBanner.adapter = adapter
            dots = ArrayList()
            setIndicator()
        }
    }

    private fun bindview() {
        binding.swipe.setOnRefreshListener {
            viewModel.getProduct()
            binding.swipe.isRefreshing = false
            binding.etSearch.text.clear()
        }

        homeAdapter =
            ProductAdapter(listener = object : ProductAdapter.EventListener {
                override fun onClick(item: GetProductResponse) {
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
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val keyword = binding.etSearch.text.toString()
                viewModel.getProduct(search = keyword)
                hideKeyboard()
                true
            } else {
                false
            }
        }
    }

    private fun setIndicator() {
        for (i in 0 until size) {
            dots.add(TextView(requireContext())) //tambahkan textview kosong
            dots[i].text = HtmlCompat.fromHtml("&#9679", HtmlCompat.FROM_HTML_MODE_LEGACY)
            dots[i].textSize = 14f
            binding.dotsIndicator.addView(dots[i])
        }
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onStart() {
        super.onStart()
        handler.post(runnable)
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(runnable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}