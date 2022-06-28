package id.co.binar.secondhand.ui.dashboard.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.co.binar.secondhand.databinding.FragmentHomeBinding
import id.co.binar.secondhand.model.seller.category.GetCategoryResponse
import id.co.binar.secondhand.ui.dashboard.home.dialog.HomeSearchFragment
import id.co.binar.secondhand.ui.dashboard.home.dialog.TAG_SEARCH_HOME_DIALOG
import id.co.binar.secondhand.ui.product.ARGS_PASSING_SEE_DETAIL
import id.co.binar.secondhand.ui.product.ProductActivity
import id.co.binar.secondhand.util.Resource
import id.co.binar.secondhand.util.castFromLocalToRemote
import id.co.binar.secondhand.util.onSnackError
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>()
    private val adapterCategory = HomeCategoryAdapter()
    private val adapterProduct = HomeProductAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObserver()
        bindView()
    }

    private fun bindView() {
        binding.etInputSearch.setOnClickListener {
            val dialog = HomeSearchFragment()
            dialog.show(parentFragmentManager, TAG_SEARCH_HOME_DIALOG)
        }

        binding.rvCategory.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            itemAnimator = DefaultItemAnimator()
            isNestedScrollingEnabled = true
        }

        adapterCategory.apply {
            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            onClickAdapter { _, GetCategoryResponse ->
                getSecond(GetCategoryResponse.id)
            }
        }

        binding.rvProduct.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 2)
            itemAnimator = DefaultItemAnimator()
            isNestedScrollingEnabled = false
            adapter = adapterProduct
        }

        adapterProduct.apply {
            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            onClickAdapter { _, item ->
                val intent = Intent(requireContext(), ProductActivity::class.java)
                intent.putExtra(ARGS_PASSING_SEE_DETAIL, item.id)
                requireActivity().startActivity(intent)
            }
        }
    }

    private fun bindObserver() {
        get()

        viewModel.getCategory.observe(viewLifecycleOwner) {
            val list = mutableListOf<GetCategoryResponse>()
            list.apply {
                add(GetCategoryResponse(name = "Semua"))
                addAll(it.data.castFromLocalToRemote())
            }
            adapterCategory.asyncDiffer.submitList(list)
            binding.rvCategory.adapter = adapterCategory
            when (it) {
                is Resource.Success -> {}
                is Resource.Loading -> {}
                is Resource.Error -> {
                    requireContext().onSnackError(binding.root, it.error?.message.toString())
                }
            }
        }

        viewModel.getProduct.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.isVisible = false
                    if (it.data.isNullOrEmpty()) {
                        binding.layoutEmpty.isVisible = true
                        binding.layoutError.isVisible = false
                        binding.rvProduct.isVisible = false
                    } else {
                        binding.layoutEmpty.isVisible = false
                        binding.layoutError.isVisible = false
                        binding.rvProduct.isVisible = true
                        adapterProduct.asyncDiffer.submitList(it.data)
                    }
                }
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.layoutEmpty.isVisible = false
                    binding.layoutError.isVisible = false
                    binding.rvProduct.isVisible = false
                }
                is Resource.Error -> {
                    binding.progressBar.isVisible = false
                    binding.layoutEmpty.isVisible = false
                    binding.layoutError.isVisible = true
                    binding.rvProduct.isVisible = false
                    binding.textView8.text = it.error?.message.toString()
                    requireContext().onSnackError(binding.root, it.error?.message.toString())
                }
            }
        }
    }

    private fun get() {
        MainScope().launch {
            viewModel.getProduct()
        }
    }

    private fun getSecond(i: Int? = null) {
        MainScope().launch {
            viewModel.getProduct(i)
            delay(500)
            viewModel.getProduct(i)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}