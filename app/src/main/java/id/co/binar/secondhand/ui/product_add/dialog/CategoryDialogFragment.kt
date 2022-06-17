package id.co.binar.secondhand.ui.product_add.dialog

import android.content.DialogInterface
import android.os.Bundle
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

const val TAG_CATEGORY_DIALOG = "CATEGORY_DIALOG"

@AndroidEntryPoint
class CategoryDialogFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetCategoryProductAddBinding? = null
    private val binding get() = _binding!!
    private val chooseItem = mutableListOf<GetCategoryResponseItem>()
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
        viewModel.categoryProduct()
        viewModel.categoryProduct.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    adapter.submitList(it.data)
                    binding.list.adapter = adapter
                }
                is Resource.Loading -> {
                    requireContext().onToast("Mohon menunggu...")
                }
                is Resource.Error -> {
                    requireContext().onToast(it.message.toString())
                }
            }

        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        viewModel.list(chooseItem)
    }

    private fun bindView() {
        adapter.onClickAdapter { _, getCategoryResponseItem ->
            if (getCategoryResponseItem.check == true) {
                chooseItem.add(getCategoryResponseItem)
            } else {
                chooseItem.remove(getCategoryResponseItem)
            }
        }
        binding.list.layoutManager = LinearLayoutManager(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
