package id.co.binar.secondhand.ui.dashboard.home.dialog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import id.co.binar.secondhand.databinding.BottomSheetSearchHomeBinding
import id.co.binar.secondhand.ui.dashboard.home.HomeProductAdapter
import id.co.binar.secondhand.ui.dashboard.home.HomeProductLoadStateAdapter
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
    private val loadStateHeader = HomeProductLoadStateAdapter { adapterProduct.retry() }
    private val loadStateFooter = HomeProductLoadStateAdapter { adapterProduct.retry() }
    private val concatAdapter = adapterProduct.withLoadStateHeaderAndFooter(
        header = loadStateHeader,
        footer = loadStateFooter
    )
    private val viewModel by activityViewModels<HomeViewModel>()

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

            val gridLayout = GridLayoutManager(requireContext(), 2)
            gridLayout.spanSizeLookup = gridLayoutSizeLookup()
            rvList.apply {
                setHasFixedSize(true)
                layoutManager = gridLayout
                itemAnimator = DefaultItemAnimator()
                addItemDecoration(ItemDecoration(requireContext(), 2, 16))
                isNestedScrollingEnabled = true
                adapter = concatAdapter
            }
        }

        adapterProduct.apply {
            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            addLoadStateListener {
                binding.progressBar.isVisible = it.source.refresh is LoadState.Loading
                binding.rvList.isVisible = it.source.refresh is LoadState.NotLoading
                binding.layoutError.isVisible = it.source.refresh is LoadState.Error
                binding.textView8.text = (it.source.refresh as? LoadState.Error)?.error?.message.toString()
            }
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
            adapterProduct.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    private fun gridLayoutSizeLookup() = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return if (position == 0 && loadStateHeader.itemCount > 0) {
                2
            } else if (position == concatAdapter.itemCount - 1 && loadStateFooter.itemCount > 0) {
                2
            } else {
                1
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
