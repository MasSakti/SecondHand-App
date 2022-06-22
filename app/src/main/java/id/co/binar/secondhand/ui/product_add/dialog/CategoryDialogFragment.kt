package id.co.binar.secondhand.ui.product_add.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import id.co.binar.secondhand.databinding.BottomSheetCategoryProductAddBinding
import id.co.binar.secondhand.model.seller.category.GetCategoryResponseItem
import id.co.binar.secondhand.ui.product_add.ProductAddViewModel
import id.co.binar.secondhand.util.Resource
import id.co.binar.secondhand.util.onToast
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val TAG_CATEGORY_DIALOG = "CATEGORY_DIALOG"

@AndroidEntryPoint
class CategoryDialogFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetCategoryProductAddBinding? = null
    private var chooseList = mutableListOf<GetCategoryResponseItem>()
    private val binding get() = _binding!!
    private val adapter = CategoryDialogAdapter()
    private val viewModel by activityViewModels<ProductAddViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetCategoryProductAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindObserver()
        bindView()
    }

    private fun bindObserver() {
        viewModel.categoryProduct.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    adapter.submitList(it.data)
                    binding.list.adapter = adapter

                    viewModel.list.observe(viewLifecycleOwner) {
                        chooseList = it
                    }

                    viewModel.lastList.observe(viewLifecycleOwner) {
                        adapter.submitList(it)
                    }
                }
                is Resource.Loading -> {
                    requireContext().onToast("Mohon menunggu...")
                }
                is Resource.Error -> {
                    requireContext().onToast(it.error?.message.toString())
                }
            }
        }
    }

    private fun bindView() {
        adapter.onClickAdapter { _, getCategoryResponseItem ->
            if (getCategoryResponseItem.check == true) {
                chooseList.add(getCategoryResponseItem)
            } else {
                chooseList.remove(getCategoryResponseItem)
            }
        }
        binding.list.layoutManager = LinearLayoutManager(context)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        viewModel.list(chooseList)
        viewModel.lastList(adapter.currentList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
