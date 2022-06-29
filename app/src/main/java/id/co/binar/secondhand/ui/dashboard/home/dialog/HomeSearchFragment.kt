package id.co.binar.secondhand.ui.dashboard.home.dialog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import id.co.binar.secondhand.databinding.BottomSheetSearchHomeBinding
import id.co.binar.secondhand.ui.dashboard.home.HomeProductAdapter
import id.co.binar.secondhand.ui.dashboard.home.HomeViewModel
import id.co.binar.secondhand.ui.product.ARGS_PASSING_SEE_DETAIL
import id.co.binar.secondhand.ui.product.ProductActivity
import id.co.binar.secondhand.util.ItemDecoration
import id.co.binar.secondhand.util.Resource
import id.co.binar.secondhand.util.onSnackError
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val TAG_SEARCH_HOME_DIALOG = "SEARCH_HOME_DIALOG"

@AndroidEntryPoint
class HomeSearchFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetSearchHomeBinding? = null
    private val binding get() = _binding!!
    private val adapterProduct = HomeProductAdapter()
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetSearchHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObserver()
        bindView()
    }

    private fun bindView() {
        binding.apply {
            txtSearch.doAfterTextChanged {
                MainScope().launch {
                    delay(500)
                    viewModel.getSearch(it.toString())
                }
            }

            rvList.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(requireContext(), 2)
                itemAnimator = DefaultItemAnimator()
                addItemDecoration(ItemDecoration(requireContext(), 2, 16))
                isNestedScrollingEnabled = true
            }
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
        viewModel.getSearch()

        viewModel.getSearch.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.isVisible = false
                    adapterProduct.asyncDiffer.submitList(it.data ?: emptyList())
                    binding.rvList.adapter = adapterProduct
                }
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is Resource.Error -> {
                    binding.progressBar.isVisible = false
                    requireContext().onSnackError(binding.root, it.error?.message.toString())
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
