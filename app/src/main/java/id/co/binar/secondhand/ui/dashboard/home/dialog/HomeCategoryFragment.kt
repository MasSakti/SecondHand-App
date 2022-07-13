package id.co.binar.secondhand.ui.dashboard.home.dialog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import id.co.binar.secondhand.databinding.BottomSheetCategoryHomeBinding
import id.co.binar.secondhand.model.seller.category.GetCategoryResponse
import id.co.binar.secondhand.model.seller.order.GetOrderResponse
import id.co.binar.secondhand.ui.dashboard.home.HomeProductAdapter
import id.co.binar.secondhand.ui.dashboard.home.HomeProductLoadStateAdapter
import id.co.binar.secondhand.ui.dashboard.home.HomeViewModel
import id.co.binar.secondhand.ui.dashboard.list_sell.dialog.InfoBidSuccessFragment
import id.co.binar.secondhand.ui.product.ARGS_PASSING_SEE_DETAIL
import id.co.binar.secondhand.ui.product.ProductActivity
import id.co.binar.secondhand.util.ItemDecoration

const val TAG_CATEGORY_HOME_DIALOG = "CATEGORY_HOME_DIALOG"

@AndroidEntryPoint
class HomeCategoryFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetCategoryHomeBinding? = null
    private val binding get() = _binding!!
    private val adapterProduct = HomeProductAdapter()
    private val loadStateHeader = HomeProductLoadStateAdapter { adapterProduct.retry() }
    private val loadStateFooter = HomeProductLoadStateAdapter { adapterProduct.retry() }
    private val concatAdapter = adapterProduct.withLoadStateHeaderAndFooter(
        header = loadStateHeader,
        footer = loadStateFooter
    )
    private val viewModel by activityViewModels<HomeViewModel>()

    companion object {
        @JvmStatic
        fun newInstance(item: GetCategoryResponse): HomeCategoryFragment {
            return HomeCategoryFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(TAG_CATEGORY_HOME_DIALOG, item)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getProductByCategory(arguments?.getParcelable<GetCategoryResponse>(TAG_CATEGORY_HOME_DIALOG)?.id)
        viewModel.getTitleCategory(arguments?.getParcelable<GetCategoryResponse>(TAG_CATEGORY_HOME_DIALOG)?.name)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetCategoryHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObserver()
        bindView()
    }

    private fun bindView() {
        binding.apply {
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
                if (it.source.refresh is LoadState.NotLoading &&
                    it.append.endOfPaginationReached &&
                    adapterProduct.itemCount < 1
                ) {
                    binding.rvList.isVisible = false
                    binding.txtTitle.isVisible = false
                    binding.layoutEmpty.isVisible = true
                } else {
                    binding.rvList.isVisible = true
                    binding.txtTitle.isVisible = true
                    binding.layoutEmpty.isVisible = false
                }
            }
            onClickAdapter { _, item ->
                val intent = Intent(requireContext(), ProductActivity::class.java)
                intent.putExtra(ARGS_PASSING_SEE_DETAIL, item.id)
                requireActivity().startActivity(intent)
            }
        }
    }

    private fun bindObserver() {
        viewModel.getTitleCategory.observe(viewLifecycleOwner) {
            binding.apply {
                txtTitle.text = it
            }
        }

        viewModel.getProductByCategory.observe(viewLifecycleOwner) {
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
